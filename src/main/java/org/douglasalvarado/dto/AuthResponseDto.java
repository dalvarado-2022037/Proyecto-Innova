package org.douglasalvarado.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {
    private String jwtToken;

    public AuthResponseDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}