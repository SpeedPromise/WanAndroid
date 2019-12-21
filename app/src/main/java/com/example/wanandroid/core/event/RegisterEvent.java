package com.example.wanandroid.core.event;

public class RegisterEvent {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public RegisterEvent(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
