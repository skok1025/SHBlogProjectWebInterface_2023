(function (factory) {
  window.Cookies = factory();
})(function () {
  function extend() {
    var i = {};
    for (var j = 0; j < arguments.length; j++) {
      var attributes = arguments[j];
      for (var key in attributes) {
        i[key] = attributes[key];
      }
    }
    return i;
  }
  function init(converter) {
    function set(name, value, attributes) {
      var cookieString = encodeURIComponent(name) + '=' + encodeURIComponent(value);
      attributes = extend({}, set.defaults, attributes);
      if (typeof attributes.expires === 'number') {
        var d = new Date();
        d.setMilliseconds(d.getMilliseconds() + attributes.expires * 864e5);
        attributes.expires = d;
      }
      if (attributes.expires) {
        cookieString += '; expires=' + attributes.expires.toUTCString();
      }
      if (attributes.path) {
        cookieString += '; path=' + attributes.path;
      }
      if (attributes.domain) {
        cookieString += '; domain=' + attributes.domain;
      }
      if (attributes.secure) {
        cookieString += '; secure';
      }
      document.cookie = cookieString;
    }
    set.defaults = { path: '/' };
    function get(name) {
      var result = undefined;
      if (document.cookie && document.cookie !== '') {
        var cookies = document.cookie.split('; ');
        for (var i = 0; i < cookies.length; i++) {
          var parts = cookies[i].split('=');
          var cookieName = decodeURIComponent(parts.shift());
          var cookieValue = parts.join('=');
          if (name === cookieName) {
            result = converter ? converter(cookieValue) : cookieValue;
            break;
          }
        }
      }
      return result;
    }
    function remove(name, attributes) {
      set(name, '', extend(attributes, { expires: -1 }));
    }
    return {
      set: set,
      get: get,
      remove: remove,
      withConverter: init,
    };
  }
  return init();
});

const GEO_IP = 'ko';

(function (sourceLang) {
  let originalTextNodes = null;
  let originalTexts = null;
  const originalLanguage = sourceLang;
  const path = window.location.pathname;

  function decodeHtmlEntities(text) {
    const textarea = document.createElement('textarea');
    textarea.innerHTML = text;
    return textarea.value;
  }

  function changeLanguage(newLang) {
    Cookies.set('googtrans', newLang, { expires: 365, path: '/' });
    sessionStorage.setItem('lang', newLang);
    updatePageWithTranslation(newLang);
  }

  function extractTextNodes(element, nodes = []) {
    // Skip entire subtrees with class 'notranslate' or 'code-div'
    if (
      element.nodeType === Node.ELEMENT_NODE &&
      (element.classList.contains('notranslate') || element.classList.contains('code-div'))
    ) {
      return nodes;
    }
    element.childNodes.forEach((node) => {
      if (
        node.nodeType === Node.TEXT_NODE &&
        node.textContent.trim() &&
        !node.parentElement.classList.contains('notranslate') &&
        !node.parentElement.classList.contains('code-div')
      ) {
        nodes.push(node);
      } else if (
        node.nodeType === Node.ELEMENT_NODE &&
        !node.classList.contains('notranslate') &&
        !node.classList.contains('code-div')
      ) {
        extractTextNodes(node, nodes);
      }
    });
    return nodes;
  }

  function deduplicateTexts(texts) {
    const uniqueTexts = new Map();
    texts.forEach((text, index) => {
      if (!uniqueTexts.has(text)) {
        uniqueTexts.set(text, [index]);
      } else {
        uniqueTexts.get(text).push(index);
      }
    });
    return uniqueTexts;
  }

  async function translateTextsInBatchesProxy(texts, targetLanguage, batchSize = 50) {
    const translatedTexts = new Array(texts.length).fill(null);
    const apiEndpoint = 'https://shkim3000.cafe24.com:9092';
    const batches = [];
    for (let i = 0; i < texts.length; i += batchSize) {
      const end = Math.min(i + batchSize, texts.length);
      batches.push({ start: i, end: end, texts: texts.slice(i, end) });
    }
    for (const batch of batches) {
      try {
        const compressed = LZString.compressToEncodedURIComponent(
          JSON.stringify(batch.texts)
        );
        const params = new URLSearchParams({
          q: compressed,
          target: targetLanguage,
          token: document.getElementById('t9n_token').value,
        });
        const response = await fetch(`${apiEndpoint}/translate?${params}`, {
          method: 'GET',
          headers: { Accept: 'application/json' },
        });
        if (!response.ok) {
          const errorData = await response.json();
          throw new Error(`API request error: ${errorData.details || response.statusText}`);
        }
        const result = await response.json();
        const translations = result.data.translations.map((t) => t.translatedText);
        for (let i = 0; i < translations.length; i++) {
          translatedTexts[batch.start + i] = translations[i];
        }
      } catch (error) {
        console.error('Translation error:', error);
        throw error;
      }
    }
    return translatedTexts;
  }

  function initializeOriginalTexts() {
    if (originalTextNodes === null) {
      originalTextNodes = extractTextNodes(document.body);
      originalTexts = originalTextNodes.map((node) => node.textContent.trim());
    }
  }

  function restoreOriginalText() {
    if (originalTextNodes !== null) {
      originalTexts.forEach((text, index) => {
        originalTextNodes[index].textContent = text;
      });
    }
  }

  async function updatePageWithTranslation(targetLanguage) {
    if (originalTextNodes === null) {
      initializeOriginalTexts();
    }
    if (targetLanguage === originalLanguage) {
      restoreOriginalText();
      return;
    }
    const textNodes = originalTextNodes;
    const texts = originalTexts;
    const uniqueTextsMap = deduplicateTexts(texts);
    const uniqueTexts = Array.from(uniqueTextsMap.keys());
    const translatedUniqueTexts = await translateTextsInBatchesProxy(uniqueTexts, targetLanguage);
    if (translatedUniqueTexts) {
      uniqueTextsMap.forEach((indexes, text) => {
        const translatedText = translatedUniqueTexts[uniqueTexts.indexOf(text)];
        const decodedText = decodeHtmlEntities(translatedText);
        indexes.forEach((index) => {
          textNodes[index].textContent = decodedText;
        });
      });
    } else {
      console.error('Translation failure.');
    }
  }

  document.addEventListener('DOMContentLoaded', () => {
    const selector = document.getElementById('languageSelector');
    const savedLanguage = Cookies.get('googtrans') || sessionStorage.getItem('lang');
    if (savedLanguage && savedLanguage !== originalLanguage) {
      updatePageWithTranslation(savedLanguage);
      if (selector) {
        selector.value = savedLanguage;
      }
    }
    if (selector) {
      selector.addEventListener('change', (event) => {
        const selectedLanguage = event.target.value;
        changeLanguage(selectedLanguage);
      });
    }
  });
})(GEO_IP);
