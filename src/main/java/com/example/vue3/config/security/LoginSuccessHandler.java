package com.example.vue3.config.security;

import com.alibaba.fastjson.JSONObject;
import com.example.vue3.entity.MyUser;
import com.example.vue3.utils.JWTutils;
import com.example.vue3.utils.errorCode.commonResult;
import com.example.vue3.utils.errorCode.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    AuthenticationManager manager;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;chartset=utf8");
        MyUser m  = (MyUser) authentication.getPrincipal();
        m.setPassword("");
        Map<String,Object> map = new HashMap<>();
        map.put("id",m.getMyUserId());
        map.put("username",m.getUsername());

        String token = JWTutils.generateToken(map);
        response.setHeader("Access-Control-Expose-Headers", "token");
        response.setHeader("token",token);


        response.getWriter().write(JSONObject.toJSONString(new commonResult<>(ErrorCode.SUCCESS)));

        response.flushBuffer();
    }
}
