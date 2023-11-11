package com.example.demo.config.auth;

import com.example.demo.user.User;
import com.example.demo.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
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
        Optional<User> optionalUser = accountJPARepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return null;
        }
        else {
            User user = optionalUser.get();
            return new CustomUserDetails(user);
        }
    }
}
