package com.cafe24.shkim30.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BlogInsertDTO {

    private Long no;

    @NotEmpty(message = "contents is a required value.")
    private String contents;
    private String title;

    private Long member_no;
    private Long category_no;
}
