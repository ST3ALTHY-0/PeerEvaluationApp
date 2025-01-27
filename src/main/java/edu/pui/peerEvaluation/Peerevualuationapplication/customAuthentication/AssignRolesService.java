package edu.pui.peerEvaluation.Peerevualuationapplication.customAuthentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AssignRolesService {

    // Create other service/component class that we autowire here
    // the class should get data (or just look at data already gotten) from
    // brightSpace api to see class list of user and what role that person has for
    // each active class
    // and based on that data apply correct authorities here

    // also if we need to do any logic about restricting access to people within CIT
    // we can do so

    public Collection<? extends GrantedAuthority> getAuthorities(OAuth2User principal) {
        String email = principal.getAttribute("email"); // change logic to assign Roles Correctly based on the current
                                                        // class list of a user and if they are enrolled as a
                                                        // student/instructor/both
        if (email != null && email.endsWith("@gmail.com")) {
            return Collections.singletonList(new SimpleGrantedAuthority(Roles.INSTRUCTOR));
        } else if (email.endsWith("@hotmail.com")) {
            return Collections.singletonList(new SimpleGrantedAuthority(Roles.STUDENT));
        } else {
            return Arrays.asList(new SimpleGrantedAuthority(Roles.STUDENT),
                    new SimpleGrantedAuthority(Roles.INSTRUCTOR));
        }
    }
}