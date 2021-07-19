package com.example.vue3.config.security;

import com.alibaba.fastjson.JSONObject;
import com.example.vue3.utils.errorCode.ErrorCode;
import com.example.vue3.utils.errorCode.commonResult;
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
        response.getWriter().write(JSONObject.toJSONString(new commonResult<>(ErrorCode.NO_AUTHORITY)));
        response.flushBuffer();
    }
}
