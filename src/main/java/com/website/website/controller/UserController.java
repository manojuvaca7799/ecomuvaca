package com.website.website.controller;


import com.website.website.model.Category;
import com.website.website.model.User;
import com.website.website.jwt.JwtUser;
import com.website.website.service.ICategoryService;
import com.website.website.service.IUserService;
import com.website.website.vo.ChangePasswordRequest;
import com.website.website.vo.CreateUserRequest;
import com.website.website.vo.UserDetailsModifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(value = "/me/change-password", method = RequestMethod.PUT)
    public ResponseEntity<User> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser user = (JwtUser) auth.getPrincipal();
        if (user == null)
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        String email = user.getUsername();
        User u = userService.changePassword(email, changePasswordRequest);
        return new ResponseEntity<User>(u, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}/userdata", method = RequestMethod.GET)
    public User getById(@PathVariable("id") String userId)
    {
        return userService.getUserById(userId);
    }


}

