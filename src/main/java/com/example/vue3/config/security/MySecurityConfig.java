package com.example.vue3.config.security;

import com.alibaba.fastjson.JSONObject;
import com.example.vue3.config.jwt.JWTFilter;
import com.example.vue3.utils.errorCode.ErrorCode;
import com.example.vue3.utils.errorCode.commonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final JWTFilter jwtFilter;

    private final MySecurityMetaData securityMetadataSource;

    private final MyAccessDecisionManager accessDecisionManager;


    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        return httpServletRequest -> {
            CorsConfiguration cfg = new CorsConfiguration();
            cfg.addAllowedHeader("*");
            cfg.addAllowedMethod("*");
//            cfg.addAllowedOrigin("*");
            cfg.setAllowCredentials(true);
            cfg.addAllowedOriginPattern("*");
            cfg.checkOrigin("*");
            return cfg;
        };
    }



    @Autowired
    public MySecurityConfig(UserDetailsService userDetailsService, JWTFilter jwtFilter, MySecurityMetaData securityMetadataSource, MyAccessDecisionManager accessDecisionManager) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
        this.securityMetadataSource = securityMetadataSource;
        this.accessDecisionManager = accessDecisionManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(accessDecisionManager);
                        object.setSecurityMetadataSource(securityMetadataSource);
                        return object;
                    }
                })
                .and().formLogin().loginProcessingUrl("/login").successHandler(new LoginSuccessHandler()).failureHandler(new LoginFailureHandler())

                .and().exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler())
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/json;chartset=utf8");
                    response.getWriter().write(JSONObject.toJSONString(new commonResult<>(ErrorCode.EXPIRE)));
                    response.flushBuffer();
                })
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

