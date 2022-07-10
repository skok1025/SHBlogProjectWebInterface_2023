package com.cafe24.shkim30.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class MemberDTO {

    private Long no;        // 인덱스

    @NotEmpty(message = "Member ID is a required value.")
    private String memberId;// 회원아이디

    @NotEmpty(message = "Member Name is a required value.")
    private String name;    // 회원명 (암호화)
    
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{10,16}",
            message = "The password must be between 10 and 16 characters, containing at least one uppercase letter, lowercase letter, number, and special symbol.")
    private String password;// 패스워드 (단방향 암호화)
    
    @Email(message = "Please follow the email format.")
    private String email;   // 이메일 (암호화)
    private String tel;     // 휴대전화번호 (암호화)
    private String is_delete = "F";
}
