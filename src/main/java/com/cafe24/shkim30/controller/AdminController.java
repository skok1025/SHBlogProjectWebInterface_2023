package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public String categoryManage(HttpSession session, HttpServletRequest request, Model model) {
        Long loginUserNo = (Long) session.getAttribute("loginUserNo");
        List<CategoryDTO> categoryList = categoryService.getCategoryList(loginUserNo);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("backendUrl", request.getAttribute("backendUrl"));

        return "admin/category";
    }

}
