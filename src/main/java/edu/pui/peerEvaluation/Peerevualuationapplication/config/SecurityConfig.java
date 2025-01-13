package edu.pui.peerEvaluation.Peerevualuationapplication.config;

/*
 * This file shouldnt be needed if we do authentication via endpoints,
 * instead of logging in with a form (Maybe use this as a back-up if that doesnt work)
 */


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    //     return http.authorizeHttpRequests( auth -> {
    //         auth.requestMatchers("/instructor")
    //         .hasRole("INSTRUCTOR");
    //         auth.requestMatchers("/student")
    //         .authenticated();
    //     })
    //     .oauth2Login(withDefaults())
    //     .formLogin(withDefaults())
    //     .build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests( auth -> {
            auth.requestMatchers("/instructor", "/student")
            .permitAll(); // Allow unauthenticated access to these endpoints
             })
             .build();
    }
    
}
