package com.example.jwt_self.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDto {
    public JwtDto(){}
    public JwtDto(String authentication) {
        this.authentication = authentication;
    }

    String authentication;
}
