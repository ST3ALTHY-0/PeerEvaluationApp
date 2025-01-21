package edu.pui.peerEvaluation.Peerevualuationapplication.config;

import org.springframework.beans.factory.annotation.Autowired;

/*
 * This file shouldnt be needed if we do authentication via endpoints,
 * instead of logging in with a form (Maybe use this as a back-up if that doesnt work)
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;

import edu.pui.peerEvaluation.Peerevualuationapplication.customAuthentication.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception{
    // return http.authorizeHttpRequests( auth -> {
    // auth.requestMatchers("/instructor")
    // .hasRole("INSTRUCTOR");
    // auth.requestMatchers("/student")
    // .authenticated();
    // })
    // .oauth2Login(withDefaults())
    // .formLogin(withDefaults())
    // .build();
    // }

    @Bean // still need to only allow instructors to instructor pages, rn it will send
          // instructors and students to the correct pages, but if a student types the
          // right url after authenticating they will be able to access the instructor
          // page
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Authorize requests
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/login").permitAll()
                        .anyRequest().authenticated()) // allow anyone access to login page
                // Configure OAuth2 login
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .successHandler(new CustomAuthenticationSuccessHandler())
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService)));
        return http.build();
    }

    // Removes any auth requirements for testing
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception{
    // return http.authorizeHttpRequests( auth -> {
    // auth.requestMatchers("/instructor", "/student")
    // .permitAll(); // Allow unauthenticated access to these endpoints
    // })
    // .build();
    // }

    // To build a client registration
    // @Bean
    // public ClientRegistrationRepository clientRegistrationRepository() {
    // return new
    // InMemoryClientRegistrationRepository(this.googleClientRegistration());
    // }

    // private ClientRegistration googleClientRegistration() {
    // return ClientRegistration.withRegistrationId("google")
    // .clientId("your-client-id")
    // .clientSecret("your-client-secret")
    // .scope("openid", "profile", "email")
    // .authorizationUri("https://accounts.google.com/o/oauth2/auth")
    // .tokenUri("https://oauth2.googleapis.com/token")
    // .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
    // .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
    // .clientName("Google")
    // .build();
    // }
}
