package com.lmm.task.config.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmm.task.entity.MyAuthority;
import com.lmm.task.mapper.role.MyAuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class MySecurityMetaData implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MyAuthorityMapper mapper;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        QueryWrapper<MyAuthority> myAuthorityQueryWrapper = new QueryWrapper<>();
        myAuthorityQueryWrapper.eq("enable",true);
        List<MyAuthority> myAuthorities = mapper.selectList(myAuthorityQueryWrapper);

        for (MyAuthority myAuthority : myAuthorities) {
            if(antPathMatcher.match(myAuthority.getUrl(),requestUrl)){
                return SecurityConfig.createList(myAuthority.getAuthorityName());
            }


        }

        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
