package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AuthController {

    //gets 'client.id' value from application.properties file and stores it in clientId
    //for now there are no actual values bc I need a BrightSpace admin to get the values
    @Value("${client.id}") 
    private String clientId;

    @Value("${auth.endpoint}")
    private String authCodeEndpoint;

    @Value("${redirect.uri}")
    private String redirectUri;

    // @Value("${google.client.id}")
    // private String clientId;

    // @Value("${google.client.secret}")
    // private String clientSecret;

    // @Value("${google.redirect.uri}")
    // private String redirectUri;


//This is the first endpoint that a user will get when coming from BrightSpace.
//The method builds a new URL to get the BrightSpaces OAuth2 endpoint using a few variables
//like the redirectURI, clientID and state.
//Got an example of this method from 'https://community.d2l.com/brightspace/kb/articles/21863-how-to-get-started-with-oauth-2-0'
    // @GetMapping("/auth") 
    // public RedirectView authenticateUser(@RequestParam(required = false) String state) {
    //     String params = String.format(
    //         "response_type=code&redirect_uri=%s&client_id=%s&scope=%s&state=%s",
    //         URLEncoder.encode(redirectUri, StandardCharsets.UTF_8),
    //         URLEncoder.encode(clientId, StandardCharsets.UTF_8),
    //         URLEncoder.encode("core:*:*", StandardCharsets.UTF_8), //core:*:* is requesting all details about the user from brightSpace TODO change later to only the needed content from brightSpace
    //         URLEncoder.encode(state != null ? state : "NotASecureState_rfc6749_section_10.12", StandardCharsets.UTF_8)
    //     );

    //     // Redirect the user to the authorization endpoint with the query parameters/ will require user to log in using brightSpace credentials  
    //     return new RedirectView(authCodeEndpoint + "?" + params);
    // }

    //This method is the callback method that brightSpace will go to after the user has
    //been authenticated by BrightSpace servers, it then redirects users
    @GetMapping("/auth/callback")
        public String handleAuthCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        //TODO Use the 'code' to request an access token from Brightspace || then using that access code you can build a http request to get the users information including their role 'instructor'/'student', and based on that you can then send them to their appropiate endpoint 
        // Validate the 'state' parameter to protect against CSRF attacks
            if(true){
                return "redirect:/instructor";
            }else{
                return "redirect:/student";
            }
}




// @GetMapping("/oauth2callback")
//         public String handleGoogleAuthCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
//         System.out.println("hello: " + code);
//         exchangeCodeForAccessToken(code);
//         return "redirect:/instructor/Dashboard";    
// }

//     private String exchangeCodeForAccessToken(String code) throws IOException, InterruptedException {
//     HttpClient client = HttpClient.newHttpClient();

//     String requestBody = "code=" + URLEncoder.encode(code, StandardCharsets.UTF_8) +
//             "&client_id=" + clientId +
//             "&client_secret=" + clientSecret +
//             "&redirect_uri=" + redirectUri +
//             "&grant_type=authorization_code";

//     HttpRequest request = HttpRequest.newBuilder()
//             .uri(URI.create("https://oauth2.googleapis.com/token"))
//             .header("Content-Type", "application/x-www-form-urlencoded")
//             .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//             .build();

//     HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
//     if (response.statusCode() == 200) {
//         // Parse the response to extract the access token
//         ObjectMapper objectMapper = new ObjectMapper();
//         JsonNode jsonNode = objectMapper.readTree(response.body());
//         return jsonNode.get("access_token").asText();
//     } else {
//         throw new RuntimeException("Failed to exchange code for access token: " + response.body());
//     }
// }


    
}
