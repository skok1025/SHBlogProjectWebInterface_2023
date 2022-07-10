package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.library.libEncrypt;
import com.cafe24.shkim30.providor.MemberProvidor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberProvidor memberProvidor;

    public MemberDTO selectMember(MemberDTO memberDTO) {
        MemberDTO findMember = memberProvidor.selectMemberById(memberDTO.getMemberId());

        if (findMember == null) {
            return null;
        }

        String inputPassword = libEncrypt.getSHA512(memberDTO.getPassword());
        String realPassword = findMember.getPassword();


        log.info("inputPassword:{}", inputPassword);
        log.info("realPassword:{}", realPassword);

        return inputPassword.equals(realPassword) ? findMember : null;
    }
}
