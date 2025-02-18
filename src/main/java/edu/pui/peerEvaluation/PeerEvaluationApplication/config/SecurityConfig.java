package edu.pui.peerEvaluation.PeerEvaluationApplication.config;

import org.springframework.beans.factory.annotation.Autowired;

/*
 * This file shouldnt be needed if we do authentication via endpoints,
 * instead of logging in with a form (Maybe use this as a back-up if that doesnt work)
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;

import edu.pui.peerEvaluation.PeerEvaluationApplication.customAuthentication.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection (not recommended for production)
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .anyRequest().permitAll()) // Allow all requests
            .sessionManagement(sessionManagement -> sessionManagement
                .maximumSessions(1)
                .expiredUrl("/login?expired=true")
                .maxSessionsPreventsLogin(true));

        return http.build();
    }
}
