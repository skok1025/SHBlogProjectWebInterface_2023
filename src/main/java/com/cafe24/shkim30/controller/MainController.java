package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.service.BlogService;
import com.cafe24.shkim30.service.CategoryService;
import com.cafe24.shkim30.template.TimeLogTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final BlogService blogService;
    private final CategoryService categoryService;

    private final TimeLogTemplate timeLogTemplate;

    @GetMapping("/basic-template")
    public String basicTemplate() {
        return "basic-template";
    }

    @GetMapping({"/", "/index"})
    public String indexPage(
            Model model,
            HttpSession httpSession,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "") Integer category_no,
            @RequestParam(required = false) String keyword) {
        return timeLogTemplate.execute("MainPage", () -> {
            if (httpSession != null) {
                model.addAttribute("blogList", blogService.getMainBlogList(currentPage, category_no, keyword));
                model.addAttribute("pagination", blogService.getPaging(currentPage));
            }

            List<CategoryDTO> categoryList = categoryService.getCategoryList(null);
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("category_no", category_no);
            model.addAttribute("keyword", keyword);

            return "index";
        });
    }

}
