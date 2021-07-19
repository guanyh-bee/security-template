package com.example.vue3.service;

import com.example.vue3.entity.MyUser;
import com.example.vue3.mapper.MyUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private MyUserMapper myUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (s.isEmpty()){
            throw new UsernameNotFoundException("用户名不能为空");
        }

        MyUser userDetail = myUserMapper.getUserDetail(s);
        String join = String.join(",", userDetail.getListAuthorities());
        userDetail.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(join));


        return userDetail;
    }
}
