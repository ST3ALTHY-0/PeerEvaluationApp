package edu.pui.peerEvaluation.Peerevualuationapplication.customAuthentication;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

//all this class really does is call AssignRolesService to assign our custom roles 
//and making a custom OAuth2User to hold the custom roles
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AssignRolesService assignRolesService;

    @Autowired
    public CustomOAuth2UserService(AssignRolesService assignRolesService) {
        this.assignRolesService = assignRolesService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Assign roles to the user
        Collection<? extends GrantedAuthority> authorities = assignRolesService.getAuthorities(oAuth2User);

        // Create a new OAuth2User with the assigned roles
        return new CustomOAuth2User(authorities, oAuth2User.getAttributes(), oAuth2User.getAttribute("email"));
    }

    private static class CustomOAuth2User implements OAuth2User {

        // a collection of authorities we have assigned to the user (i.g. Instructor,
        // Student, Instructor_Student)
        private final Collection<? extends GrantedAuthority> authorities;

        // attributes of a user (i.e. for a BrightSpaceUser they will have a map of
        // attributes correlating to the fields found in brightSpaceAPI.brightSpaceUser)
        private final Map<String, Object> attributes;

        // unique way to identify each user, email in our case
        private final String nameAttributeKey; 

        public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes,
                String nameAttributeKey) {
            this.authorities = authorities;
            this.attributes = attributes;
            this.nameAttributeKey = nameAttributeKey;
        }

        @Override
        public Map<String, Object> getAttributes() {
            return attributes;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getName() {
            return nameAttributeKey;
        }

    }

}
