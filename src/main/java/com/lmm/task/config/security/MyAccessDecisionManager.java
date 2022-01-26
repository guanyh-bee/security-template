package com.lmm.task.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            if(configAttribute.getAttribute().equals("ROLE_LOGIN")){
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("权限不足,无法访问");
                }else {
                    return;
                }

            }
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if(authority.getAuthority().equals(configAttribute.getAttribute())){
                    return;
                }

            }
        }
        throw new AccessDeniedException("权限不足,无法访问");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
