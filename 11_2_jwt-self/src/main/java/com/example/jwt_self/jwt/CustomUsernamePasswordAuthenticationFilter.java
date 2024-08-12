package com.example.jwt_self.jwt;

import com.example.jwt_self.dto.JwtDto;
import com.example.jwt_self.dto.LoginDto;
import com.example.jwt_self.entity.CustomUserDetails;
import com.example.jwt_self.entity.RefreshEntity;
import com.example.jwt_self.repository.RefreshRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.sql.Ref;
import java.util.Calendar;
import java.util.Date;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    ObjectMapper objectMapper = new ObjectMapper();

    AuthenticationManager authenticationManager;
    JWTUtil jwtUtil;
    RefreshRepository refreshRepository;

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager
            , JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
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

        // Refresh token 구현
        String username = authResult.getName();
        String role = authResult.getAuthorities().stream().findAny().get().getAuthority();

        String accessToken = jwtUtil.createJwt("access", username, role, 1);
        String refreshToken = jwtUtil.createJwt("refresh", username, role, 12*60);

        addRefresh(username, refreshToken, 12*60);

        response.setHeader("access", accessToken);
        response.addCookie(createCookie("refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());

    }

    protected void addRefresh(String username, String refresh, int expiredMinute){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiredMinute);
        Date date = calendar.getTime();

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }


    Cookie createCookie(String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(12*60*60); // 12h
        cookie.setHttpOnly(true);   //JS로 접근 불가, 탈취 위험 감소
        return cookie;
    }
}
