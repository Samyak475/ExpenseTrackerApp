package com.codeartist.authservice.config;

import com.codeartist.authservice.filters.JwtFilter;
import com.codeartist.authservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserService userService;
    @Autowired
    JwtFilter jwtFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider =  new DaoAuthenticationProvider(userService);
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
                       -> auth.requestMatchers("/v1/login","/v1/signup","/v1/refreshToken","/error").permitAll()
                       .anyRequest().authenticated()
               )
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
   }

//   @Bean
//    public AuthenticationManager authenticationManager(){
//        return new ProviderManager();
//   }
}
