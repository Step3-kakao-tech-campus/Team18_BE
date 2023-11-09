package com.example.demo.config.security;

import com.example.demo.config.errors.ErrorCode;
import com.example.demo.config.errors.exception.UserNotExistException;
import com.example.demo.user.domain.User;
import com.example.demo.user.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserJPARepository accountJPARepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = accountJPARepository.findByEmail(email).orElse(null);
        return new CustomUserDetails(user);
    }
}
