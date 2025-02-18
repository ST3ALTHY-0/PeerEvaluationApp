package edu.pui.peerEvaluation.PeerEvaluationApplication.customAuthentication;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    //redirect user to appropriate webpage based on their role
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       
        System.out.println("redirect happening");
       authentication.getAuthorities().forEach(authority -> System.out.println(authority.getAuthority()));
       
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Roles.INSTRUCTOR)) 
            && authentication.getAuthorities().contains(new SimpleGrantedAuthority(Roles.STUDENT))) {
            response.sendRedirect("/instructorStudent");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Roles.INSTRUCTOR))) {
            response.sendRedirect("/instructor/dashboard");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Roles.STUDENT))) {
            response.sendRedirect("/student/dashboard");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Roles.ADMIN))) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/error");
        }
    }
    
}
