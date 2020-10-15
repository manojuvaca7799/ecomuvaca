package com.website.website.service;

import com.website.website.model.User;
import com.website.website.vo.ChangePasswordRequest;
import com.website.website.vo.CreateUserRequest;
import com.website.website.vo.UserDetailsModifyRequest;
import java.util.List;

public interface IUserService {
    User checkUserCredential(String username, String password);
    User changePassword(String username, ChangePasswordRequest changePasswordRequest);
    User getUserById(String userId);

}
