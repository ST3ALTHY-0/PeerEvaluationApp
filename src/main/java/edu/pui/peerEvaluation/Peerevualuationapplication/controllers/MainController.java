package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;


import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/*
 * This controller maps get requests to the respective webpages
 */

import edu.pui.peerEvaluation.Peerevualuationapplication.oauth2springsecurity.models.User;
import edu.pui.peerEvaluation.Peerevualuationapplication.oauth2springsecurity.models.UserService;

@Controller
public class MainController {

    private final UserService userService;
    private final OAuth2AuthorizedClientService authorizedClientService;
    //declare any/all endpoint urls we will use


    @Autowired
    public MainController(UserService userService, OAuth2AuthorizedClientService authorizedClientService){
        this.userService = userService;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {

        String clientRegistrationId = "google"; // Replace with your brightSpace RegID later
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, principal.getName()); //way to manually get the access token so we can make api calls with it
        String accessToken = authorizedClient.getAccessToken().getTokenValue(); 

        System.out.println(accessToken);
        // Add user name to the model
        model.addAttribute("name", principal.getAttribute("name")); //vars you add to the model will be available in the templates to use ${} with
        userService.createUser(principal);
        String name = principal.getAttribute("name");
        System.out.println(name);
        

        if(name.equals("Jacob (S3ALTHY)")){
            return "instructorDashboard";
        }else{
            return "student";
        }
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("hello login: ");

        return "login";
    }

  

    @GetMapping("/instructor") //maps /instructor to instructor page
    public String instructor(){
        return "instructor";
    }

    @GetMapping("/student")
    public String student(){
        return "student";
    }
}
