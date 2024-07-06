package com.example.jwt_self.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.logging.Filter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        // CSRF Disable
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // Session login disable
//        httpSecurity.formLogin(AbstractHttpConfigurer::disable);    // UsernamePasswordAuthenticationFilter disable
//        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);    // 기본 로그인창 disable

        // 세션 정보를 저장하지 않음(jwt에서는 임시 세션 정보 사용, 사용된 세션은 이후 초기화)
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        
        // 경로별 인가 설정
        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/", "/login", "/join").permitAll()
                    .requestMatchers("/onlyuser").hasRole("USER");
        });

        return httpSecurity.build();
    }
}
