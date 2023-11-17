package com.cafe24.shkim30.providor;

import com.cafe24.shkim30.dto.BlogDTO;
import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.dto.fconline.FcOnlineInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcOnlineProvidor {
    private final RestTemplate restTemplate;

    @Value("${constant.backendUrl}")
    public String BACKEND_URL;


    public FcOnlineInfoDTO getInfoDTO(String fcOnlineUserName) {
        String callUrl = BACKEND_URL + "/blog-api/fconline/info?nickname=" + fcOnlineUserName;

        System.out.println(callUrl);

        JSONResultFcOnlineInfoDTO jsonresult =
                restTemplate.getForObject(
                        callUrl,
                        JSONResultFcOnlineInfoDTO.class
                );

        return jsonresult.getData();
    }

    private static class JSONResultFcOnlineInfoDTO extends JSONResult<FcOnlineInfoDTO> {}
}
