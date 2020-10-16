package com.website.website.vo;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsModifyRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005112126L;

    private List<String> facilitiesAccess = new ArrayList<String>();
    private List<String> roles = new ArrayList<String>();
    private String name;
    private String username;
    private String email;
    @JsonProperty("isActive")
    private boolean isActive = false;

    public List<String> getFacilitiesAccess() {
        return facilitiesAccess;
    }

    public void setFacilitiesAccess(List<String> facilitiesAccess) {
        this.facilitiesAccess = facilitiesAccess;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}