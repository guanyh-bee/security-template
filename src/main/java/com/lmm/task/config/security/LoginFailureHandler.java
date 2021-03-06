package com.lmm.task.config.security;

import com.alibaba.fastjson.JSONObject;
import com.lmm.task.utils.errorCode.ErrorCode;
import com.lmm.task.utils.errorCode.CommonResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;chartset=utf8");
        PrintWriter writer = response.getWriter();
        if(exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException){
            writer.write(JSONObject.toJSONString(new CommonResult<>(ErrorCode.USERNAME_PASSWORD_ERROR,exception.getMessage())));

        }else {
            writer.write(JSONObject.toJSONString(new CommonResult<>(ErrorCode.ACCOUNT_NOT_NORMAL,exception.getMessage())));

        }


        response.flushBuffer();
    }
}
