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

    @GetMapping("/instructorDashboard")
    public String instructor(@AuthenticationPrincipal OAuth2User principal) {
        principal.getAttribute("email");//may need to get different attribute based on what brightSpace provides (userId)
        
        
        return "instructorDashboard";
    }

    @GetMapping("/studentViewEvaluations")
    public String studentViewEvaluations(@AuthenticationPrincipal OAuth2User principal, Model model) {
        List<Evaluation> userEvalList = evaluationService.findAll();
        for (Evaluation e : userEvalList) {
            System.out.println(e.getEvaluation_id());
        }
        model.addAttribute("userEvalList", userEvalList);
        return "studentViewEvaluations";
    }

    @GetMapping("/studentCompleteEvaluation")
    public String studentCompleteEvaluation(@AuthenticationPrincipal OAuth2User principal, Model model,
            @RequestParam("evaluationId") int evaluationId) {
        String currentStudentEmail = principal.getAttribute("email");
        Student currentStudent = studentService.findStudentByEmail(currentStudentEmail);
        Integer currentStudentId = currentStudent.getStudent_id();
        model.addAttribute("currentStudentId", currentStudentId);
        Evaluation evaluation = evaluationService.findById(evaluationId);
        model.addAttribute("evaluation", evaluation);
        ProjectGroup projectGroup = projectGroupService.findByProjectId(evaluation.getProject().getProject_id());
        model.addAttribute("projectGroup", projectGroup);

        return "studentCompleteEvaluation";
    }

    public String student() {
        return "studentCompleteEvaluation";
    }

    @GetMapping("/createEvaluation")
    public String createEvaluation(@AuthenticationPrincipal OAuth2User principal, Model model) {
        //for google OAuth
        String userEmail = principal.getAttribute("email");

        //for brightspace user, get and transform userData into instructor obj
        //check if exist in db, if so return the instructor
        Instructor instructor = instructorService.findInstructorByEmail(userEmail);
        System.out.println(instructor.getInstructor_name());
        List<MyClass> classList = instructor.getClasses();
        List<Project> projects = new ArrayList<Project>();
        for(MyClass c : classList){
            projects.addAll(c.getProjects());
        }
        model.addAttribute("instructorClassList", classList);
        model.addAttribute("projectList", projects);



        //else create new user for db



        // add Brightspace Classes
        // add BrightSpace Students for those classes
        // add projects for those classes if possible
        // add groups for those classes if possible

        return "createEvaluation";
    }
    
    @GetMapping("/evaluationFormExtraSettings")
    public String evaluationFormExtraSettings() {
        return "evaluationFormExtraSettings";
    }


}
