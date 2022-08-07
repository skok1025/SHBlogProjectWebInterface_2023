package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.CategoryDTO;
import com.cafe24.shkim30.providor.CategoryProvidor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Value("${spring.profiles.active}")
    public String PROFILES_ACTIVE;

    private final CategoryProvidor categoryProvidor;

    public List<CategoryDTO> getCategoryList(Long loginUserNo) {

        if (loginUserNo == null) {
            if ("dev".equals(PROFILES_ACTIVE)) {
                loginUserNo = 16L;
            } else {
                loginUserNo = 1L;
            }
        }

        return categoryProvidor.getCategoryList(loginUserNo);
    }
}
