package com.codeartist.authservice.config;

import com.codeartist.authservice.filters.JwtFilter;
import com.codeartist.authservice.services.SignUpServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.security.Provider;
import java.util.List;
import java.util.logging.Handler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    SignUpServiceHandler signUpServiceHandler;
    @Autowired
    JwtFilter jwtFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider =  new DaoAuthenticationProvider(signUpServiceHandler);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public ProviderManager provider ()
    {
        return  new ProviderManager(List.of(daoAuthenticationProvider()));
    }
    @Bean
   public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception{
       return httpSecurity
               .csrf(csrf-> csrf.disable())
               .sessionManagement(session
                       -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authorizeHttpRequests(auth
                       -> auth.requestMatchers("/v1/login","/v1/signUp","/v1/refreshToken").permitAll()
                       .anyRequest().authenticated()
               )
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
   }
}
