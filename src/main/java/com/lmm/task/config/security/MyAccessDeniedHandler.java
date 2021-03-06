package com.lmm.task.config.security;

import com.alibaba.fastjson.JSONObject;
import com.lmm.task.utils.errorCode.ErrorCode;
import com.lmm.task.utils.errorCode.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(new CommonResult<>(ErrorCode.NO_AUTHORITY)));
        response.flushBuffer();
    }
}
