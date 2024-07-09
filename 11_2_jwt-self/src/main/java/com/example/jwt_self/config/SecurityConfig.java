package com.example.jwt_self.config;

import com.example.jwt_self.jwt.CustomUsernamePasswordAuthenticationFilter;
import com.example.jwt_self.jwt.JWTUtil;
import com.example.jwt_self.jwt.JwtVerificationFilter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.logging.Filter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    JWTUtil jwtUtil;

    // authManager Bean을 얻기 위한 authConfiguration 객체
    private final AuthenticationConfiguration authenticationConfiguration;

    // get authManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        // CSRF Disable
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // Session login disable
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);    // UsernamePasswordAuthenticationFilter disable
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);    // 기본 로그인창 disable

        // 세션 정보를 저장하지 않음(jwt에서는 임시 세션 정보 사용, 사용된 세션은 이후 초기화)
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        // 커스텀 필터 등록
            // 로그인 경로 설정 후, 로그인 필터 등록
        CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter
                = new CustomUsernamePasswordAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        customUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/signin");
        httpSecurity.addFilterAt(customUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            // jwt 검증 필터 등록
        JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtUtil);
        httpSecurity.addFilterAfter(jwtVerificationFilter, CustomUsernamePasswordAuthenticationFilter.class);
        
        // 경로별 인가 설정
        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/", "/login", "/join", "/signin").permitAll()
                    .requestMatchers("/onlyuser").hasRole("USER");
        });

        return httpSecurity.build();
    }
}
