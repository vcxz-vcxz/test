package com.test.service;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class DynamicSecurityServiceImpl implements DynamicSecurityService{
    @Override
    public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        map.put("/test", new org.springframework.security.access.SecurityConfig("test"));
//        map.put("/test2", new org.springframework.security.access.SecurityConfig("test"));
        map.put("/testApi1", new org.springframework.security.access.SecurityConfig("api1"));
        return map;
    }
}
