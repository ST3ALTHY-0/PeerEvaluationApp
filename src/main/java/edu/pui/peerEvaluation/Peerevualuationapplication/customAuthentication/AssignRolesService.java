package edu.pui.peerEvaluation.Peerevualuationapplication.customAuthentication;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AssignRolesService{

    public Collection<? extends GrantedAuthority> getAuthorities(OAuth2User principal) {
        String email = principal.getAttribute("email"); //change logic to assign Roles Correctly based on the current class list of a user and if they are enrolled as a student/instructor/both
        if (email != null && email.endsWith("@gmail.com")) {
            return Collections.singletonList(new SimpleGrantedAuthority(Roles.INSTRUCTOR));
        } else {
            return Collections.singletonList(new SimpleGrantedAuthority(Roles.STUDENT));
        }
    }
}