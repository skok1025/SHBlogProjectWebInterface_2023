package com.cafe24.shkim30.controller;

import com.cafe24.shkim30.dto.BlogDTO;
import com.cafe24.shkim30.dto.BlogInsertDTO;
import com.cafe24.shkim30.dto.BlogUpdateDTO;
import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.service.BlogService;
import com.cafe24.shkim30.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final CategoryService categoryService;

    @GetMapping("/add")
    public String blogAddPage(Model model) {
        List<CategoryDTO> categoryList = categoryService.getCategoryList(null);

        model.addAttribute("categoryList", categoryList);

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

    @GetMapping("/edit/{blogNo}")
    public String blogEditPage(@PathVariable("blogNo") Long blogNo, Model model, HttpSession session) {
        if (session.getAttribute("loginUserNo") == null) {
            return "redirect:/";
        }
        BlogDTO blogDTO = blogService.getBlog(blogNo);
        model.addAttribute("blogDTO", blogDTO);
        return "front/blog_edit";
    }

    @PostMapping("/edit")
    public String blogEditAction(BlogUpdateDTO blogUpdateDTO) {
        blogService.updateBlog(blogUpdateDTO);

        return "redirect:/";
    }
}
