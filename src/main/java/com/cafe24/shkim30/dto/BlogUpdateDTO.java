package com.cafe24.shkim30.dto;

import lombok.Data;

@Data
public class BlogUpdateDTO {
    private Long no; // 블로그 게시번호
    private String contents; // 블로그 게시내용
    private String title; // 제목
}
