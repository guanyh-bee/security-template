package com.lmm.task.controller;

import com.lmm.task.entity.MyUser;
import com.lmm.task.entity.MyUserBaseInfo;
import com.lmm.task.mapper.MyUserMapper;
import com.lmm.task.utils.errorCode.CommonResult;
import com.lmm.task.utils.errorCode.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private MyUserMapper myUserMapper;
//    @RequestMapping("/login")
//    public String login(){
////        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken());
//        return "登陆成功";
//    }




}
