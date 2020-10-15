package com.website.website.jwt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthRequest implements Serializable {
    private static final long serialVersionUID = 592683005150707L;
    private String email;
    private String username;
    private String password;
    private List<String> roles= new ArrayList<String>();
    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}