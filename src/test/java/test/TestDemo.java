package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.util.EncryptUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDemo {
    @Test
    public void login() throws JsonProcessingException {
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
        System.out.println(token);
    }
}
