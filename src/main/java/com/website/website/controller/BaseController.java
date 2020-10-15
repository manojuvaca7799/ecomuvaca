package com.website.website.controller;

import com.website.website.exceptions.NotFoundException;
import com.website.website.jwt.JwtUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;



public class BaseController {

    protected JwtUser getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) auth.getPrincipal();
        if (user == null)
            throw new NotFoundException("No logged in user");
        return user;
    }

    protected String getLoggedInUserId() {
        JwtUser user = getLoggedInUser();
        return user.getId();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Exception> handleNoResultException(
            NotFoundException nre) {
        return new ResponseEntity<Exception>(HttpStatus.NOT_FOUND);
    }
}
