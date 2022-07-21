package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.providor.CategoryProvidor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryProvidor categoryProvidor;

    public List<CategoryDTO> getCategoryList(Long loginUserNo) {
        return categoryProvidor.getCategoryList(loginUserNo);
    }
}
