package com.manage.company.company.payload;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;


}
