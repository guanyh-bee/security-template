package com.lmm.task.utils.errorCode;


public enum ErrorCode {
    SUCCESS(200,"请求成功"),
    USERNAME_PASSWORD_ERROR(4001,"用户名密码错误或账户被冻结或账户已过期"),
    ACCOUNT_NOT_NORMAL(4002,"账户被冻结或账户已过期"),
    NO_AUTHORITY(4003,"权限不足,无法访问"),
    EXPIRE(4004,"用户未登录或登陆已过期"),



    DEPT_LEVEL(5001,"最多只能添加四级部门"),
    DEPT_DUPLICATE(5002,"部门名字重复"),
    DEPT_HAVE_PERSON(5003,"部门下存在人员"),
    CAN_NOT_DELETE_ROOT(5004,"不能删除根部门"),




    COMMON_TYPE(10000,"通用无效结果"),

    ;



    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }
    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
