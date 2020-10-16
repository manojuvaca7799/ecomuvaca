package com.website.website.service;

import com.website.website.controller.BaseController;
import com.website.website.dao.IGenericDao;
import com.website.website.dao.IUserDao;
import com.website.website.model.User;
import com.website.website.repository.UserRepository;
import com.website.website.exceptions.InvalidInputException;
import com.website.website.jwt.JwtTokenUtil;
import com.website.website.utils.BcryptHasher;
import com.website.website.vo.ChangePasswordRequest;
import com.website.website.vo.CreateUserRequest;
import com.website.website.vo.UserDetailsModifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseController implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IGenericDao genericDao;
    @Autowired

    private JwtTokenUtil jwtTokenUtil;


    @Override
    public User checkUserCredential(String email, String password) {
        //credential could contain name or email
        User u = userRepository.findByUsername(email);
        if (u == null) {
            u = userRepository.findByEmail(email);
        }
        if (u == null) {
            System.out.println("no user");
            throw new InvalidInputException("Invalid userId or password");
        }
        System.out.println("user found");
        BcryptHasher bcryptHasher = new BcryptHasher();
        if (bcryptHasher.verifyHash(password, u.getPassword())) {
            System.out.println("password verified");
            return u;
        } else {
            System.out.println("invalid password:" + password);
            System.out.println("invalid password:" + u.getPassword());
            throw new BadCredentialsException("Invalid userId or password");
        }
    }

    @Override
    public User changePassword(String userEmail, ChangePasswordRequest changePasswordRequest) {
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();
        if (oldPassword == null || oldPassword.isEmpty()) {
            throw new InvalidInputException("please provide oldPassword");
        }
        if (newPassword == null || newPassword.isEmpty()) {
            throw new InvalidInputException("please provide newPassword");
        }
        User u = userRepository.findByEmail(userEmail);
        if (u == null) {
            throw new InvalidInputException("No such user");
        }
        String currPasswordHashed = u.getPassword();
        BcryptHasher bcryptHasher = new BcryptHasher();
        if (!bcryptHasher.verifyHash(changePasswordRequest.getOldPassword(), currPasswordHashed)) {
            throw new InvalidInputException("Old Password did not match");
        }
        u.setPassword(bcryptHasher.hash(changePasswordRequest.getNewPassword()));
        return userRepository.save(u);
    }


    @Override
    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }


}
