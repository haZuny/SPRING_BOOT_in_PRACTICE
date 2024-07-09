package com.example.jwt_self.jwt;

import com.example.jwt_self.dto.JwtDto;
import com.example.jwt_self.dto.LoginDto;
import com.example.jwt_self.entity.CustomUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    ObjectMapper objectMapper = new ObjectMapper();

    AuthenticationManager authenticationManager;
    JWTUtil jwtUtil;

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // request body GET
        ServletInputStream inputStream;
        String requestBody;
        try {
            inputStream = request.getInputStream();
            requestBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Json data parsing
        LoginDto loginDto;
        try {
            loginDto = objectMapper.readValue(requestBody, LoginDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword(), null);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // 토큰 생성
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        String jwtToken = jwtUtil.createJwt(userDetails.getUsername(), userDetails.getRole(), 1);


        // 토큰을 JSON 형태로 변경
        JwtDto jwtDto = new JwtDto("Bearer " + jwtToken);
        String jsonResponse = objectMapper.writeValueAsString(jwtDto);

        // JSON 타입 객체 응답
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
