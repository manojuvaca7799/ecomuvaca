package com.website.website.jwt;

import com.website.website.vo.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        //logger.warn(String.format("processing authentication for %s", request.getRequestURL()));

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null) {
            //jwtToken = requestTokenHeader.substring(7);
            jwtToken = requestTokenHeader;
            boolean isError = false;
            String errMsg = "";
            try {

                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                isError = true;
                errMsg = "Unable to get JWT Token!";
            } catch (ExpiredJwtException e) {
                isError = true;
                errMsg = "Invalid or Expired token!";;
            } catch (Exception e) {
                isError = true;
                errMsg = "Unauthorized Access";
            }
            if(isError) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setCode(401);
                errorResponse.setError(errMsg);
                byte[] responseToSend = restResponseBytes(errorResponse);
                ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
                ((HttpServletResponse) response).setStatus(401);
                response.getOutputStream().write(responseToSend);
                return;
            }
        } else {
            if (!(request.getRequestURI().contains("/authenticate"))) {
                logger.warn("JWT Token does not contain auth string");
            }
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {


            UserDetails userDetails = null;
            try
            {
//                System.out.println(username);
//                System.out.println("try block");

                userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            }
            catch (Exception e)
            {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setCode(401);
                errorResponse.setError("Invalid token or unknown user!");
                byte[] responseToSend = restResponseBytes(errorResponse);
                ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
                ((HttpServletResponse) response).setStatus(401);
                response.getOutputStream().write(responseToSend);
                return;
            }

        // if token is valid configure Spring Security to manually set
        // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setCode(401);
                errorResponse.setError("Expired or invalid token!");
                byte[] responseToSend = restResponseBytes(errorResponse);
                ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
                ((HttpServletResponse) response).setStatus(401);
                response.getOutputStream().write(responseToSend);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private byte[] restResponseBytes(ErrorResponse eErrorResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
        return serialized.getBytes();
    }

}
