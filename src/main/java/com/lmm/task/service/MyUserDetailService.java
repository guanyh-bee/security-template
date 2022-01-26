package com.lmm.task.service;

import com.lmm.task.entity.MyUser;
import com.lmm.task.mapper.MyUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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
        if (userDetail == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<String> listNew = new ArrayList<>(new TreeSet<>(userDetail.getListAuthorities()));
        String join = String.join(",", listNew);
        userDetail.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(join));


        return userDetail;
    }
}
