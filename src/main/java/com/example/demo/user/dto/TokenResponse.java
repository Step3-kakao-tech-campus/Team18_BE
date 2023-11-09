package com.example.demo.user.dto;

import lombok.Getter;
import lombok.Setter;

public class TokenResponse {
    @Getter
    @Setter
    public static class TokenDTO {
        private String accessToken;
        private String refreshToken;

        public TokenDTO(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
