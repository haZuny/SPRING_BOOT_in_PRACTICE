package com.example.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        // 커스텀 로그인 페이지 설정
        /**
         * loginPage(): formLogin 설정
         *      formLogin 동작 방식:
         *          1. 권한이 필요한 페이지 접근
         *          2. 로그인 페이지로 redirect
         *          3. 로그인 정보 post
         *          4. 해당 정보가 일치하면, 인증
         *  loginPage: 커스텀 로그인 페이지 설정
         *  loginProcessingURL: 로그인 정보를 받을 url(form action)
         */
        http.formLogin((auth)->{
            auth.loginPage("/login")
                    .loginProcessingUrl("/loginProc")
                    .permitAll();
        });

        http.csrf((auth)->auth.disable());

        return http.build();
    }


    /**
     * 단방향 암호화
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
