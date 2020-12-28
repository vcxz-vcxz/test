package com.test.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.service.UserService;
import com.test.util.EncryptUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class APIController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() throws JsonProcessingException {
        List<String> authList = new ArrayList<>();
        authList.add("test");
        String authJson = new ObjectMapper().writeValueAsString(authList);
        String encryptAuthJson = "";
        try {
            encryptAuthJson = EncryptUtil.encrypt(authJson, "vicovico");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("res", encryptAuthJson);


        String secretString ="vaboiA+PCGjzff+jeNUg9bZfzyIURjnk2UxLfTy1fwk=";
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
        String token = Jwts.builder()
                .addClaims(map)
                .signWith(secretKey)
                .compact();
        return token;
    }

    @GetMapping("/test")
    public String test() {
        return "这是一个测试接口";
    }



    @GetMapping("test2")
    public String test2() {
        return "这是一个测试接口";
    }

    @GetMapping("/testApi1")
    public String testApi1() {
        return "这是一个测试接口";
    }

    //test1

}
