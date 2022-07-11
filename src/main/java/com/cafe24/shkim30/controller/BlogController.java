package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.BlogInsertDTO;
import com.cafe24.shkim30.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/add")
    public String blogAddPage() {
        return "front/blog_add";
    }

    @PostMapping("/add")
    public String blogAddAction(BlogInsertDTO blogInsertDTO, HttpSession session) {
        if (session != null) {
            blogInsertDTO.setMember_no((Long) session.getAttribute("loginUserNo"));
        }

        BlogInsertDTO insertBlog = blogService.addBlog(blogInsertDTO);

        if (insertBlog.getNo()>0) {
            return "redirect:/";
        }
        return "redirect:/blog/add";
    }
}
