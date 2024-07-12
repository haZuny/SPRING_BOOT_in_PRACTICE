package com.example.jwt_self.jwt;

import com.example.jwt_self.dto.JwtDto;
import com.example.jwt_self.dto.LoginDto;
import com.example.jwt_self.entity.CustomUserDetails;
import com.example.jwt_self.repository.RefreshRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class JwtVerificationFilter extends OncePerRequestFilter {
    ObjectMapper objectMapper = new ObjectMapper();

    JWTUtil jwtUtil;

    public JwtVerificationFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request body GET
        String token = request.getHeader("Authentication");

        // 토큰 존재 여부 검증
//        if (token == null || !token.startsWith("Bearer ")){
        if (token == null){
            filterChain.doFilter(request, response);
            return;
        }

        token = token.split(" ")[1];

        // 토큰 만료 검증
        try {
            jwtUtil.isExpired(token);
        } catch (ExpiredJwtException e) {
            response.getWriter().write("access token is expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String category = jwtUtil.getCategory(token);

        // 카테고리 검사(access)
        if (!category.equals("access")){
            response.getWriter().write("invalid access token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        // 임시 세션 추가
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setRole(role);
        customUserDetails.setUsername(username);
            // 세션 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        System.out.println("Success");
        filterChain.doFilter(request, response);
    }
}
