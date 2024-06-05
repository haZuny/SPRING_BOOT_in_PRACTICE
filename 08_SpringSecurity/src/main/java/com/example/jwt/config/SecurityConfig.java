package com.example.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // Spring Security 인가 활성화
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
                    .requestMatchers("/", "/login", "/join", "/joinProc").permitAll()
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

//        http.csrf((auth)->auth.disable());


        // 다중 로그인 설정
        /**
         * sessionManagement(): 세션에 관련된 설정
         * maximumSessions(): 최대 동시 접속 가능 세션 수
         * maxSessionsPreventsLogin(): 최대 세션 수를 초과했을때의 동작
         *      true: 초과시 새로운 로그인 차단
         *      false: 초과시 이전 세션 정보 삭제
         */
        http.
                sessionManagement((auth)->{
                   auth.maximumSessions(1)
                           .maxSessionsPreventsLogin(true);
                });


        // 세션 고정 공격 방어
        /**
         * none(): 로그인시 세션 정보 변경 안함(세션 고정 약점에 취약)
         * newSession(): 로그인시 새로운 새션 생성
         * changeSessionId(): 로그인시 동일한 세션에 대한 아이디 변경(주로 사용)
         */
        http.sessionManagement((auth)->{
            auth.sessionFixation().changeSessionId();
        });

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
