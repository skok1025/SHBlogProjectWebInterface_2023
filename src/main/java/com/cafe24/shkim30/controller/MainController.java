package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
    public String indexPage(Model model, HttpSession httpSession, @RequestParam(defaultValue = "1") Integer currentPage) {
        if (httpSession != null) {
            model.addAttribute("blogList", blogService.getMainBlogList(currentPage));
            model.addAttribute("pagination", blogService.getPaging(currentPage));
        }

        log.info("blogList: {}", blogService.getMainBlogList(0));
        log.info("pagination: {}",blogService.getPaging(currentPage));


        return "index";
    }

}
