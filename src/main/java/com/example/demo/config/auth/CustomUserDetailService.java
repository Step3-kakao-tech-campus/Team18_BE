package com.example.demo.config.auth;

import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserJPARepository accountJPARepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalAccount = accountJPARepository.findByEmail(email);

        if (optionalAccount.isEmpty()) {
            return null;
        }
        else {
            User account = optionalAccount.get();
            return new CustomUserDetails(account);
        }
    }
}
