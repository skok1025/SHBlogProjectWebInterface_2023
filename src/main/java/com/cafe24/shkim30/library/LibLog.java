package com.cafe24.shkim30.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class LibLog {

    private static final LibLog INSTANCE = new LibLog();

    private final Logger libLogger = LoggerFactory.getLogger("libLog");
    private final ObjectMapper mapper = new ObjectMapper();

    private LibLog() {}

    public static LibLog getInstance() {
        return INSTANCE;
    }

    /**
     * key, data만 받고
     * 현재 HTTP 요청이 있으면 client IP 자동 획득해서 로그에 포함
     */
    public void write(String key, Map<String, Object> data) {
        try {
            LinkedHashMap<String, Object> logMap = new LinkedHashMap<>();

            logMap.put("logKey", key);

            String timestamp = ZonedDateTime.now(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            logMap.put("time", timestamp);

            // 현재 HTTP 요청에서 클라이언트 IP 추출
            String clientIp = getClientIpFromRequest();
            clientIp = normalizeIp(clientIp);
            if (clientIp != null && !clientIp.isEmpty()) {
                logMap.put("clientIp", clientIp);
            }

            if (data != null) {
                logMap.putAll(data);
            }

            String jsonLog = mapper.writeValueAsString(logMap);
            libLogger.info(jsonLog);
        } catch (JsonProcessingException e) {
            libLogger.error("Failed to serialize log data to JSON", e);
        }
    }

    private String normalizeIp(String ip) {
        if (ip == null) return null;

        // IPv4-mapped IPv6 예: ::ffff:192.168.0.1
        if (ip.startsWith("::ffff:")) {
            return ip.substring(7); // "192.168.0.1" 부분만 반환
        }
        return ip;
    }

    private String getClientIpFromRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null; // HTTP 요청이 없는 경우
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // X-Forwarded-For 헤더가 있으면 앞의 IP 사용, 없으면 remoteAddr
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            // 여러 IP가 있을 수 있으니 첫번째만 취함
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
