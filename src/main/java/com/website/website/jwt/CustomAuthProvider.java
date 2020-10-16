package com.website.website.jwt;

import com.website.website.model.User;
import com.website.website.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    private IUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        Object pwd = authentication.getCredentials();
        if(name == null || pwd == null) {
            throw new BadCredentialsException("Invalid user or password");
        }
        String password = pwd.toString();
        System.out.println(name);
        final User userDetail = userService.checkUserCredential(name, password);
//        System.out.println("********************");
//        System.out.println(userDetail.getName());
//        System.out.println(userDetail.getEmail());
//        System.out.println("********************");


        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("users"));

        UsernamePasswordAuthenticationToken authInfo = new UsernamePasswordAuthenticationToken(
                name, password, authorities);
        authInfo.setDetails(userDetail);
//        System.out.println("------------------------");
//        System.out.println(userDetail.getName());
//        System.out.println(userDetail.getEmail());
//        System.out.println("-------------------------");
        return authInfo;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
