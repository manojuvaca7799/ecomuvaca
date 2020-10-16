package com.website.website.jwt;

import com.website.website.model.User;
import com.website.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    private static HashMap<String, Object> localCache = new HashMap<String, Object>();

    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //logger.warn("loadUserByUsername called with user:" + username);

        System.out.println(username);

        User user = null;
        Object cachedUser = null;
        //cachedUser = localCache.get(username);

        if(cachedUser != null)
            user = (User) cachedUser;
        else
            user = userRepository.findByUsername(username);


        //logger.warn("loadUserByUsername called with user detail:" + user);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            /*
            if(cachedUser == null)
                localCache.put(username, user);
            */
            return JwtUserFactory.create(user);
        }
    }


}