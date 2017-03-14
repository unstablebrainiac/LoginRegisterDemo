package com.sajalnarang.loginregisterdemo;

/**
 * Created by sajalnarang on 14/3/17.
 */

public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
