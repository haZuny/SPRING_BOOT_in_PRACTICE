package com.example.jwt_self.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {

    @Autowired
    JWTUtil jwtUtil;

    @Test
    public void getToken(){
        int expMinute = 1;
        String jwt = jwtUtil.createJwt("aaa", "USER", expMinute);
        // get Token Assertion
        Assertions.assertNotNull(jwt);
        System.out.println("token: " + jwt);

        // exp Assertion
        if (expMinute > 0){
            Assertions.assertFalse(jwtUtil.isExpired(jwt));

            // parsing Assertion
            Assertions.assertEquals("aaa", jwtUtil.getUsername(jwt));
            Assertions.assertEquals("USER", jwtUtil.getRole(jwt));
        }
        else
            Assertions.assertTrue(jwtUtil.isExpired(jwt));  // exp 만료됨
    }
}
