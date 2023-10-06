package com.example.demo.config.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.account.Account;
import com.example.demo.config.auth.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(JWTTokenProvider.HEADER);

        if (jwt == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            DecodedJWT decodedJWT = JWTTokenProvider.verify(jwt);
            int id = decodedJWT.getClaim("user_id").asInt();
            String email = decodedJWT.getClaim("user_email").asString();
            Account user = Account.builder().id(id).email(email).build();
            CustomUserDetails customUserDetails = new CustomUserDetails(user);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            customUserDetails,
                            customUserDetails.getPassword(),
                            customUserDetails.getAuthorities()
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("디버그 : 인증 객체 만들어짐");
        } catch (SignatureVerificationException sve) {
            System.out.println("토큰 검증 실패");
        } catch (TokenExpiredException tee) {
            System.out.println("토큰 만료됨");
        } finally {
            chain.doFilter(request, response);
        }
    }
}
