package com.test.config;

import com.test.service.DynamicSecurityService;
import com.test.service.DynamicSecurityServiceImpl;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //标注这是Security的配置文件
@EnableGlobalMethodSecurity(prePostEnabled=true) //开启权限控制的注解支持
public class LoginConfig extends SecurityConfig {
    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //指定认证对象的来源
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityServiceImpl() ;
    }
}
