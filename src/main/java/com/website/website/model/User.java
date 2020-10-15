package com.website.website.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class User implements Serializable {

    @Id
    private String id;
    private String name;
    private String username;
    private String password;
    private String role;
    private String email;
    private List<String> categoryAccess = new ArrayList<String>();

    @JsonIgnore
    private String verificationToken;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public List<String> getCategoryAccess() {
        return categoryAccess;
    }

    public void setCategoryAccess(List<String> categoryAccess) {
        this.categoryAccess = categoryAccess;
    }
}
