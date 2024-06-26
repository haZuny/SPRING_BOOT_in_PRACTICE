package com.example.jwt.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/hello").permitAll()
                    .anyRequest().authenticated();
        });

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webFilter() throws Exception {
        return ((web) -> {
            // h2 콘솔 요청은 필터 무시
            web.ignoring().requestMatchers("/h2-console/**", "/favicon.ico");
        });
    }
}
