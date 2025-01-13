package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/*
 * This controller maps get requests to the respective webpages
 */

@Controller
public class MainController {


    @GetMapping("/instructor") //maps /instructor to instructor page
    public String instructor(){
        return "instructor";
    }

    @GetMapping("/student")
    public String student(){
        return "student";
    }
}
