package com.cafe24.shkim30.providor;

import com.cafe24.shkim30.dto.JSONResult;
import com.cafe24.shkim30.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MemberProvidor {

    private final RestTemplate restTemplate;


    public MemberDTO selectMemberById(String memberId) {
        JSONResultMember jsonresult =
                restTemplate.getForObject("http://localhost:8080/blog-api/member/"+ memberId
                        , JSONResultMember.class);

        return jsonresult.getData();
    }

    private static class JSONResultMember extends JSONResult<MemberDTO> {}
}
