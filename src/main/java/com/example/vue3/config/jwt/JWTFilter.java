package com.example.vue3.config.jwt;

import com.alibaba.fastjson.JSONObject;
import com.example.vue3.entity.MyUser;
import com.example.vue3.mapper.MyUserMapper;
import com.example.vue3.utils.JWTutils;
import com.example.vue3.utils.errorCode.ErrorCode;
import com.example.vue3.utils.errorCode.commonResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private MyUserMapper myUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("token");

        Claims claims = JWTutils.verifyJwt(token);
        if (claims == null) {

        } else {
            String username = (String) claims.get("username");
            MyUser myUser = myUserMapper.getUserDetail(username);
            myUser.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",myUser.getListAuthorities())));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUser, null, myUser.getAuthorities());
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(usernamePasswordAuthenticationToken);
            SecurityContextHolder.setContext(context);



        }
        filterChain.doFilter(request, response);

    }
}
