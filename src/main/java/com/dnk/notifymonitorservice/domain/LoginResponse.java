package com.dnk.notifymonitorservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    private String token;

    @JsonProperty("account_type")
    private String accountType;

    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
