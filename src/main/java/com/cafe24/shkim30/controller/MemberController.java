package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.MemberDTO;
import com.cafe24.shkim30.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage() {
        return "member/login";
    }

    @PostMapping("/login")
    public String loginAction(MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginMember = memberService.selectMember(memberDTO);

        if (loginMember != null) {
            session.setAttribute("loginUserId", loginMember.getMemberId());
            session.setAttribute("loginUserNo", loginMember.getNo());
            session.setAttribute("loginMemberName", loginMember.getName());

            return "redirect:/";
        }

        return "redirect:/login?d=fail";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("loginUserId", null);
        session.setAttribute("loginMemberName", null);

        return "redirect:/";
    }
}
