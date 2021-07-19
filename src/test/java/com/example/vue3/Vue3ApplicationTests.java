package com.example.vue3;

import com.example.vue3.entity.MyUser;
import com.example.vue3.mapper.MyUserMapper;
import com.example.vue3.utils.JWTutils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class Vue3ApplicationTests {
    @Resource
    MyUserMapper myUserMapper;
    @Resource
    PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
        System.out.println(myUserMapper.getUserDetail("管运好"));

    }

    @Test
    void contextLoads1() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","管运好");
        map.put("age","28");
        String token = JWTutils.generateToken(map);
        System.out.println(token);

        System.out.println(JWTutils.verifyJwt(token).get("name"));

    }

}
