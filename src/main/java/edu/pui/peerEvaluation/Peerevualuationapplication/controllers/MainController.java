package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;


import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/*
 * This controller maps get requests to the respective webpages
 */

@Controller
public class MainController {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model){
        // Map<String, Object> map = principal.getAttributes();   //testing to print everything that brightSpace api gives access to
        // for (Map.Entry<String, Object> entry : map.entrySet()) {
        //     System.out.println(entry.getKey() + ": " + entry.getValue());
        // }
        String role = principal.getAttribute("role");  //will need to change 'role' to whatever brightSpace calls it,
        return "home";
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
