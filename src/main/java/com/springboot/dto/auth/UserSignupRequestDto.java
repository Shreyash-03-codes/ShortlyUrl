package com.springboot.dto.auth;

import com.springboot.enums.Roles;

public class UserSignupRequestDto {

    private String name;

    private String email;

    private String password;

    private Roles roles;

    public UserSignupRequestDto(String name, String email, String password, Roles roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = Roles.USER;
    }

    public UserSignupRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
