package com.app.readaholicv3.dto;

import javax.validation.constraints.NotEmpty;

public class UserLoginDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
