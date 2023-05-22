package com.cafe24.shkim30.providor;

import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MemberProvidor {

    private final RestTemplate restTemplate;

    @Value("${constant.backendUrl}")
    public String BACKEND_URL;
//
    public MemberDTO selectMemberById(String memberId) {
        JSONResultMember jsonresult =
                restTemplate.getForObject(BACKEND_URL + "/blog-api/member/"+ memberId
                        , JSONResultMember.class);

        return jsonresult.getData();
    }

    private static class JSONResultMember extends JSONResult<MemberDTO> {}
}
