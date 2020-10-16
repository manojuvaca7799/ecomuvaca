package com.website.website.jwt;

import com.website.website.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {

        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getCategoryAccess()),
                true,
                new Date()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }
}
