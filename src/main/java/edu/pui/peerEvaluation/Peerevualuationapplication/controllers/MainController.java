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
import edu.pui.peerEvaluation.Peerevualuationapplication.services.OAuth2Service;

@Controller
public class MainController {

    private final UserService userService;
    private final OAuth2Service oAuth2Service;
    private static final String CLIENT_REGISTRATION_ID = "google"; //change to brightSpace later (i think we can define regId in SecurityConfig Class in commented out code)
    //declare any/all endpoint urls we will use


    @Autowired
    public MainController(UserService userService, OAuth2Service oAuth2Service){
        this.userService = userService;
        this.oAuth2Service = oAuth2Service;
    }

    // @GetMapping("/home")
    // public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {

    //     String accessToken = oAuth2Service.getAccessToken(principal, CLIENT_REGISTRATION_ID);
    //     System.out.println(accessToken);

    //     // Add user name to the model
    //     model.addAttribute("name", principal.getAttribute("name")); //vars you add to the model will be available in the templates to use ${} with
    //     userService.createUser(principal);
    //     String name = principal.getAttribute("name");
    //     System.out.println(name);
        

    //     if(name.equals("Jacob (S3ALTHY)")){
    //         return "instructorDashboard";
    //     }else{
    //         return "student";
    //     }
    // }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

  

    @GetMapping("/instructorDashboard") 
    public String instructor(){
        return "instructorDashboard";
    }

    @GetMapping("/student")
    public String student(){
        return "student";
    }


}
