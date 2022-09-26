package com.cafe24.shkim30.providor;

import com.cafe24.shkim30.dto.BlogDTO;
import com.cafe24.shkim30.dto.BlogInsertDTO;
import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogProvidor {
    private final RestTemplate restTemplate;

    @Value("${constant.backendUrl}")
    public String BACKEND_URL;

    public BlogInsertDTO insertBlog(BlogInsertDTO blogInsertDTO) {
        JSONResultBlogInserDTO jsonresult =
                restTemplate.postForObject(BACKEND_URL + "/blog-api/blog/contents", blogInsertDTO, JSONResultBlogInserDTO.class);

        return jsonresult.getData();
    }

    public List<BlogDTO> getMainBlogList(int startIndex, Integer categoryNo) {
        String callUrl = BACKEND_URL + "/blog-api/blog/contents-list?page_content_size=5&start_index=" + startIndex;

        if (categoryNo != null) {
            callUrl += "&category_no=" + categoryNo;
        }

        JSONResultBlogDTOList jsonresult =
                restTemplate.getForObject(
                        callUrl ,
                        JSONResultBlogDTOList.class
                );

        return jsonresult.getData();
    }

    private static class JSONResultBlogInserDTO extends JSONResult<BlogInsertDTO> {}
    private static class JSONResultBlogDTOList extends JSONResult<List<BlogDTO>> {}
}
