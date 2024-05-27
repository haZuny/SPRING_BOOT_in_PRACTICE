package com.example.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // Spring Security 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // 경로별 권한 설정
        /**
         * requestMatcher: 특정 경로에 대한 요청을 처리
         * permitAll: 권한에 상관 없이 모든 요청 허용
         * hasRole: 특정 역할이 있으면 접근 허용
         * hasAnyRole: 특정 역할(여러개중 어느거나)이 있으면 접근 허용
         * anyRequest: 그 외 모든 경로에 대하여 처리
         * authenticated: 인증된 사용자만 접근 허용
         * !!! 순서 주의. 위에서부터 제한적인 순으로!
         */
        http.authorizeHttpRequests((auth)->{
            auth
                    .requestMatchers("/", "/login").permitAll()
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                    .anyRequest().authenticated();
        });

        return http.build();
    }

}
