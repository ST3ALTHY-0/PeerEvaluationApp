package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    public MainController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {

        //the following should probably be done in a @Service class
        //should first check that User is Instructor or Student

        //if Instructor, add all relevant attributes to model (add only needed attributes to DB after instructor actually makes a evaluation form)

        //if Student, add relevant attributes to model and DB (name, email)
        //check DB if the student has any eval forms they need to complete (when eval forms are first created, a list of students should be provided by instructor[and stored in DB], hopefully there is an email associated with the students at the time they are entered via instructor but if not then we should have a name for each student, and once the student signs in we might be able to check with BS api if they have any class/instructors that have made a eval form, and be able to match them that way)

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
