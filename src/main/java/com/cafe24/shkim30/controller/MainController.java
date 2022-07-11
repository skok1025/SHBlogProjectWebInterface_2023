package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final BlogService blogService;

    @GetMapping("/basic-template")
    public String basicTemplate() {
        return "basic-template";
    }

    @GetMapping({"/", "/index"})
    public String indexPage(Model model, HttpSession httpSession) {
        if (httpSession != null) {
            model.addAttribute("blogList", blogService.getMainBlogList(0));
        }

        log.info("blogList: {}", blogService.getMainBlogList(0));
        return "index";
    }

}
