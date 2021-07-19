package com.example.vue3.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
//    @RequestMapping("/login")
//    public String login(){
////        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken());
//        return "登陆成功";
//    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/test1")
    public String test1(){
        return "test1";
    }
}
