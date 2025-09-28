package com.springboot.dto.auth;

import com.springboot.enums.Roles;

public class UserLoginResponseDto {
    private String username;
    private String jwtToken;
    private Roles role;

    public String getUsername() {
        return username;
    }

    public UserLoginResponseDto() {
    }

    public UserLoginResponseDto(String username, String jwtToken,Roles role) {
        this.username = username;
        this.jwtToken = jwtToken;
        this.role=role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
