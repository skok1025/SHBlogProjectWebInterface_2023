package com.cafe24.shkim30.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/basic-template")
    public String basicTemplate() {
        return "basic-template";
    }

    @GetMapping({"/", "/index"})
    public String indexPage() {
        return "index";
    }

}
