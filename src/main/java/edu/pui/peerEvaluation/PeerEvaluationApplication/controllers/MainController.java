package edu.pui.peerEvaluation.PeerEvaluationApplication.controllers;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
/*
 * This controller maps get requests to the respective webpages
 */
import org.springframework.web.bind.annotation.RequestParam;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.LoginDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceApi.BrightSpaceAPIService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceApi.brightSpaceUser.*;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.InstructorService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroupService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;
import jakarta.servlet.http.HttpSession;


@Controller
public class MainController {

    @GetMapping("/")
    public String home(){
        return "student/login";
    }

    @GetMapping("/login/failed")
    public String loginFailed(){
        return "student/error";
    }

    @GetMapping("/login")
    public String login(){
        return "student/login";
    }

    // @GetMapping("/error")
    // public String error() {
    //     return "error";
    // }
}
