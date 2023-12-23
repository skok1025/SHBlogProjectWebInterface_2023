package com.cafe24.shkim30.providor;

import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.dto.seoulinfo.CultureInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SeoulInfoProvidor {
    private final RestTemplate restTemplate;

    @Value("${constant.backendUrl}")
    public String BACKEND_URL;

    public CultureInfoDTO selectCulttureList() {
        CultureInfoDTO result =
                restTemplate.getForObject(BACKEND_URL + "/blog-api/seoul-info/culture-list"
                        , CultureInfoDTO.class);


        return result;
    }
}
