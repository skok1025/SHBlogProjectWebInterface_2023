package com.cafe24.shkim30.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BlogDTO {

    private Long no;

    @NotEmpty(message = "contents is a required value.")
    private String contents;

    private String ins_timestamp;

    private String upd_timestamp;

    private Long member_no;
    private String member_id;
    private String member_name;


    private Long category_no;
    private String category_name;
}
