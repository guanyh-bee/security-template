package com.example.vue3.utils.errorCode;

import lombok.Data;

@Data
public class commonResult<T> {
    private Integer code;
    private String message;
    private T data;


    public commonResult(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = null;
    }

    public commonResult(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }

    public commonResult(ErrorCode errorCode,String message) {
        this.code = errorCode.getCode();
        this.message = message;
        this.data = null;
    }




}
