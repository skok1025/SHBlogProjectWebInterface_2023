package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.service.BlogService;
import com.cafe24.shkim30.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final BlogService blogService;
    private final CategoryService categoryService;

    @GetMapping("/basic-template")
    public String basicTemplate() {
        return "basic-template";
    }

    @GetMapping({"/", "/index"})
    public String indexPage(
            Model model,
            HttpSession httpSession,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "") Integer category_no) {

        if (httpSession != null) {
            model.addAttribute("blogList", blogService.getMainBlogList(currentPage, category_no));
            model.addAttribute("pagination", blogService.getPaging(currentPage));
        }

        List<CategoryDTO> categoryList = categoryService.getCategoryList(null);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("category_no", category_no);

        return "index";
    }

}
