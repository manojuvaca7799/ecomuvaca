package com.website.website.jwt;

import com.website.website.dao.IGenericDao;
import com.website.website.model.Customer;
import com.website.website.model.User;
import com.website.website.utils.BcryptHasher;
import com.website.website.vo.CreateCustomerRequest;
import com.website.website.vo.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private IGenericDao genericDao;


    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {

        String email = authenticationRequest.getEmail();
        if(email != null && !email.isEmpty())
        {
            authenticationRequest.setUsername(email);
        }

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        User u = (User) authentication.getDetails();

            final UserDetails userDetails = JwtUserFactory.create(u);
            final String token = jwtTokenUtil.generateToken(userDetails);
            AuthResponse r = new AuthResponse(token);
            r.setUserId(u.getId());
            Calendar calendar = Calendar.getInstance();
            calendar.getTime();
            calendar.add(Calendar.SECOND, (int) jwtTokenUtil.getJwtTokenValidity());
            r.setTokenExpiryDate(calendar.getTime());
            r.setName(u.getName());
            r.setUsername(u.getUsername());
            r.setRole(u.getRole());
            return ResponseEntity.ok(r);
        }



    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
        public Object createUser(@RequestBody CreateUserRequest createUserRequest, AuthRequest authenticationRequest) {
        String email = createUserRequest.getEmail();
        String username = createUserRequest.getUsername();
        String name = createUserRequest.getName();
        String password = createUserRequest.getPassword();
        String role = createUserRequest.getRole();

        BcryptHasher bcryptHasher = new BcryptHasher();
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setRole(role);
        newUser.setPassword(bcryptHasher.hash(password));

        User saved = (User) genericDao.save(newUser);
        System.out.println("created user with id:" + saved.getId());
        return "created successfully";
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Object createUser(@RequestBody CreateCustomerRequest createCustomerRequest, AuthRequest authenticationRequest) {
        String email = createCustomerRequest.getEmail();
        String username = createCustomerRequest.getUsername();
        String name = createCustomerRequest.getName();
        String password = createCustomerRequest.getPassword();
        String role = createCustomerRequest.getRole();

        BcryptHasher bcryptHasher = new BcryptHasher();
        Customer newUser = new Customer();
        newUser.setUsername(username);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setRole(role);
        newUser.setPassword(bcryptHasher.hash(password));

        Customer saved = (Customer) genericDao.save(newUser);
        System.out.println("created user with id:" + saved.getId());
        return "created successfully";
    }

    private Authentication authenticate(String username, String password) throws AuthenticationException
    {
       return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> userLogout(@RequestHeader("Authorization") String authHeader) {
        if(authHeader == null || authHeader.isEmpty()) {
            return ResponseEntity.ok("No auth token");
        } else {
            String jwtToken;
            if (authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7);
                JwtTokenStore.remove(jwtToken);
                return ResponseEntity.ok("logged out successfully!");
            } else {
                jwtToken = authHeader;
                JwtTokenStore.remove(jwtToken);
                return ResponseEntity.ok("logged out successfully!");
            }
        }

    }
}
