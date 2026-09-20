package com.movie.api.auth;

import com.movie.api.model.support.ResponseResult;
import com.movie.api.utils.JwtTokenUtil;
import com.movie.api.utils.ResponseUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Authenticate requests using JWT authorization tokens
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //Apply the filter once per request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //Read the token from the request header
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);

        //Continue without authentication when no token is supplied
        //Protected endpoints enforce their configured authorization rules
        if (token == null || "null".equals(token)) {
            chain.doFilter(request, response);
            return;
        }

        //Check whether the JWT has expired
        if (JwtTokenUtil.isExpiration(token)) {
            ResponseUtil.writeJson(response, new ResponseResult<>(403, "Token expired. Please log in again"));
            return;
        }

        //Parse the JWT to read user information
        String username = JwtTokenUtil.getUsername(token);
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : JwtTokenUtil.getTokenRoles(token)) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        //Add authentication to the Spring Security context
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, null, authorities));
        super.doFilterInternal(request, response, chain);
    }

}
