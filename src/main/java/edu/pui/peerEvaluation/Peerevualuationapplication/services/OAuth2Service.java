package edu.pui.peerEvaluation.Peerevualuationapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public OAuth2Service(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    //get the access token of a authenticated user [principal] and clientRegId (the api we are using i.g brightSpace, google, etc)
    public String getAccessToken(OAuth2User principal, String clientRegistrationId) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, principal.getName());
        return authorizedClient.getAccessToken().getTokenValue();
    }
}
