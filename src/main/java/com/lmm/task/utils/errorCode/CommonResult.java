package com.lmm.task.utils.errorCode;

import lombok.Data;

@Data
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;


    public CommonResult(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = null;
    }

    public CommonResult(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }

    public CommonResult(ErrorCode errorCode, String message) {
        this.code = errorCode.getCode();
        this.message = message;
        this.data = null;
    }




}
