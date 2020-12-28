package com.test.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.util.EncryptUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Override
    /*** 过滤请求 */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            //请求体的头中是否包含Authorization
            String token = request.getHeader("Authorization");
            //Authorization中是否包含Bearer，不包含直接返回
            if (token == null || !token.startsWith("Bearer ")) {
                chain.doFilter(request, response);//进入下一个过滤器
//                return;
            }
            //获取权限失败，会抛出异常
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, token);
            // 获取后，将Authentication写入SecurityContextHolder中供后序使用
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            chain.doFilter(request, response);//进入下一个过滤器
//            return;
        }
    }

    /*** 通过token，获取用户信息 */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) throws Exception {
        //解析token，不合法token会抛出异常，合法解析出载荷信息
        String secretString = "vaboiA+PCGjzff+jeNUg9bZfzyIURjnk2UxLfTy1fwk=";
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()//秘钥判断该token是否合法
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        String username= (String) claims.get("username");
        String encryptAuthJson= (String) claims.get("res");
        String authJson = EncryptUtil.decrypt(encryptAuthJson, "vicovico");
        List<String> authList=new ObjectMapper().readValue(authJson,new TypeReference<List<String>>() {});
        List<GrantedAuthority> authorities=new ArrayList<>();
        authList.forEach(a->authorities.add(new SimpleGrantedAuthority(a)));
        if(StringUtils.isEmpty(authorities)){
            return null;
        }
        //不为null，返回
        return new UsernamePasswordAuthenticationToken(username, null, authorities);


    }
}
