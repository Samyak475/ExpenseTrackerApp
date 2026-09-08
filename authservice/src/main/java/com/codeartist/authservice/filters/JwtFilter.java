package com.codeartist.authservice.filters;

import com.codeartist.authservice.dtos.UserDto;
import com.codeartist.authservice.services.JWTUtilService;
import com.codeartist.authservice.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    UserService userService;
    @Autowired
    JWTUtilService jwtUtilService ;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(header != null &&header.startsWith("Bearer ")){
            String token = header.substring(7);
            String userName = jwtUtilService.getUsername(token);
            if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDto userDto = userService.getUserByUsername(userName);

                if (jwtUtilService.isTokenValid(token, userDto)) {

                    Collection<GrantedAuthority> grantedAuth = new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_USER")));
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, null, grantedAuth);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        }

        filterChain.doFilter(request,response);

    }
}
