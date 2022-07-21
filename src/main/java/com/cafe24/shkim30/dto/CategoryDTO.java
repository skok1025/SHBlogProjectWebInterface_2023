package com.cafe24.shkim30.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 블로그 카테고리 정보
 */

@Data
public class CategoryDTO {
    private Long no;            // 인덱스

    @NotEmpty(message = "Category name is a required value.")
    private String name;        // 카테고리명

    private Long parent_no;  // 부모 카테고리 정보

    private Long member_no; // 블로그 카테고리 소유자 멤버번호

    private Long level; // 카테고리 레벨 (대분류-1, 중분류-2)
}
