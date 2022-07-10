package com.cafe24.shkim30.dto;

import lombok.Data;

@Data
public class JSONResult<T>{

    private String result;  //success, fail
    private String message; //if fail, set
    private T data;    //if success, set
}