package com.example.demo.config.security;

import com.example.demo.config.errors.exception.Exception401;
import com.example.demo.config.errors.exception.Exception403;
import com.example.demo.config.jwt.JWTAuthenticationFilter;
import com.example.demo.config.jwt.JWTTokenProvider;
import com.example.demo.config.utils.FilterResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JWTAuthenticationFilter(authenticationManager, jwtTokenProvider));
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // 1. CSRF 비활성화
        httpSecurity.csrf().disable();

        // 2. iframe 거부
        httpSecurity.headers().frameOptions().sameOrigin();

        // 3. cors 재설정
        httpSecurity.cors().configurationSource(configurationSource());

        // 4. 세션 비활성화
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 5. form 로그인 비활성화
        httpSecurity.formLogin().disable();

        // 6. 기존의 HTTP 로그인 방식 비활성화 -> Token 방식
        httpSecurity.httpBasic().disable();

        // 7. 커스텀 필터 적용 (시큐리티 필터 교환)
        httpSecurity.apply(new CustomSecurityFilterManager());

        // 8. 인증 실패 처리
        httpSecurity.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            FilterResponseUtils.unAuthorized(response, new Exception401("인증되지 않은 사용자입니다."));
        });

        // 9. 권한 실패 처리
        httpSecurity.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            FilterResponseUtils.forbidden(response, new Exception403("권한이 없는 사용자입니다."));
        });

        // 11. 인증, 권한 필터 설정
        httpSecurity.authorizeRequests(
                authorize -> authorize.antMatchers("/profiles", "/users/passwordcheck").authenticated()
                        .antMatchers("/admin/**").access("hasRole('ADMIN')")
                        .anyRequest().permitAll()
        );

        return httpSecurity.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
