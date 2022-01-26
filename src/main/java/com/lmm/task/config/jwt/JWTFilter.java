package com.lmm.task.config.jwt;

import com.lmm.task.entity.MyUser;
import com.lmm.task.mapper.MyUserMapper;
import com.lmm.task.utils.JWTutils;
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
    private MyUserMapper myUserMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("token");

        Claims claims = JWTutils.verifyJwt(token);
        if (claims == null) {

        } else {
            String username = (String) claims.get("username");
            MyUser myUser = myUserMapper.getUserDetail(username);
            if(myUser.isEnabled()){
                myUser.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",myUser.getListAuthorities())));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUser, null, myUser.getAuthorities());
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContextHolder.setContext(context);
            }






        }
        filterChain.doFilter(request, response);

    }
}
