package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;

import java.security.Principal;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;


import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.InstructorService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroupService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.StudentService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.project.Project;

import edu.pui.peerEvaluation.Peerevualuationapplication.services.OAuth2Service;
import edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.BrightSpaceAPIService;
import edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceUser.*;


@Controller
public class MainController {

    private final OAuth2Service oAuth2Service;
    private static final String CLIENT_REGISTRATION_ID = "google"; // change to brightSpace later (i think we can define
                                                                   // regId in SecurityConfig Class in commented out
                                                                   // code)
    private final EvaluationService evaluationService;
    private final ProjectGroupService projectGroupService;
    private final StudentService studentService;
    private final InstructorService instructorService;

    private final BrightSpaceAPIService brightSpaceAPIService;


    // declare any/all endpoint urls we will use

    @Autowired
    public MainController(OAuth2Service oAuth2Service, EvaluationService evaluationService,
            ProjectGroupService projectGroupService, StudentService studentService, InstructorService instructorService, BrightSpaceAPIService brightSpaceAPIService) {
        this.oAuth2Service = oAuth2Service;
        this.evaluationService = evaluationService;
        this.projectGroupService = projectGroupService;
        this.studentService = studentService;
        this.brightSpaceAPIService = brightSpaceAPIService;
        this.instructorService = instructorService;
    }

    // @GetMapping("/home")
    // public String home(@AuthenticationPrincipal OAuth2User principal, Model
    // model) {

    // String accessToken = oAuth2Service.getAccessToken(principal,
    // CLIENT_REGISTRATION_ID);
    // System.out.println(accessToken);

    // // Add user name to the model
    // model.addAttribute("name", principal.getAttribute("name")); //vars you add to
    // the model will be available in the templates to use ${} with
    // userService.createUser(principal);
    // String name = principal.getAttribute("name");
    // System.out.println(name);

    // if(name.equals("Jacob (S3ALTHY)")){
    // return "instructorDashboard";
    // }else{
    // return "student";
    // }
    // }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
