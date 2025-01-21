package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

    @PostMapping("/evaluation/feedback")
    public String createEvaluation(){
        return "createEvaluation";
    }
    
}
