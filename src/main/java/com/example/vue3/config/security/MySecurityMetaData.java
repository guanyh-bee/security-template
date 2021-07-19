package com.example.vue3.config.security;

import com.example.vue3.entity.MyAuthority;
import com.example.vue3.mapper.MyAuthorityMapper;
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
        List<MyAuthority> myAuthorities = mapper.selectList(null);

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
