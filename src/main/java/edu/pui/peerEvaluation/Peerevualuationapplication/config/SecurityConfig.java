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
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Authorize requests
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/login").permitAll()
                                .anyRequest().authenticated()
                )
                // Configure OAuth2 login
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login")
                                .defaultSuccessUrl("/home", true)
                );
        return http.build();
    }

    // @Bean
    // public ClientRegistrationRepository clientRegistrationRepository() {
    //     return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    // }

    // private ClientRegistration googleClientRegistration() {
    //     return ClientRegistration.withRegistrationId("google")
    //             .clientId("your-client-id")
    //             .clientSecret("your-client-secret")
    //             .scope("openid", "profile", "email")
    //             .authorizationUri("https://accounts.google.com/o/oauth2/auth")
    //             .tokenUri("https://oauth2.googleapis.com/token")
    //             .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
    //             .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")//changed
    //             .clientName("Google")
    //             .build();
    // }

    //Removes any auth requirements for testing
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    //     return http.authorizeHttpRequests( auth -> {
    //         auth.requestMatchers("/instructor", "/student")
    //         .permitAll(); // Allow unauthenticated access to these endpoints
    //          })
    //          .build();
    // }
    
}
