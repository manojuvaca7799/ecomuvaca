package com.website.website.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateUserRequest implements Serializable {
    private static final long serialVersionUID = 5912348583005150757L;

    @Id

    private String name;
    private String username;
    private String password;
    private String email;
    private String role ="merchant";

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
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

}