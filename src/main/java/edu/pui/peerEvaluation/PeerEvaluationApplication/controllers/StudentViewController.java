package edu.pui.peerEvaluation.PeerEvaluationApplication.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.LoginDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroupService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentViewController {

    private final EvaluationService evaluationService;
    private final ProjectGroupService projectGroupService;
    private final StudentService studentService;

    public StudentViewController(StudentService studentService, EvaluationService evaluationService, ProjectGroupService projectGroupService) {
        this.evaluationService = evaluationService;
        this.projectGroupService = projectGroupService;
        this.studentService = studentService;
    }

        @GetMapping("/login")
    public String login() {
        return "student/login";
    }

    @PostMapping("/login/submit")
    public String loginSubmit(@ModelAttribute LoginDTO loginDTO, HttpSession session) {
        //validate input
        //return error if wrong input is entered
        Student thisStudent = studentService.findStudentByEmail(loginDTO.getEmail()).orElse(null);
        session.setAttribute("student", thisStudent); // Store student in session

        return "student/dashboard";
    }

    @GetMapping("/dashboard")
    public String studentDashboard(HttpSession session, Model model){
        Student student = (Student) session.getAttribute("student");
        model.addAttribute("student", student);

        return "student/dashboard";
    }


     @GetMapping("/viewEvaluations")
    public String studentViewEvaluations(HttpSession session, Model model) {


        Student student = (Student) session.getAttribute("student");
        List<Evaluation> userEvalList = evaluationService.findEvaluationsWithoutStudentFeedback(student.getStudentId());
        model.addAttribute("userEvalList", userEvalList);
        
        return "student/viewEvaluations";
    }

    @GetMapping("/viewPastEvaluations")
    public String studentViewPastEvaluations(HttpSession session, Model model) {

        Student student = (Student) session.getAttribute("student");
        List<Evaluation> userEvalList = evaluationService.findAllByStudentIdWithFeedback(student.getStudentId());
        model.addAttribute("userEvalList", userEvalList);
        
        return "student/viewPastEvaluations";
    }

    @GetMapping("/completeEvaluation")
    public String studentCompleteEvaluation(HttpSession session, Model model,
            @RequestParam("evaluationId") int evaluationId) {

        Student student = (Student) session.getAttribute("student");
        Evaluation evaluation = evaluationService.findById(evaluationId).orElse(null);
        ProjectGroup projectGroup = projectGroupService.findProjectGroupByEvaluationIdAndStudentId(evaluationId, student.getStudentId());


        model.addAttribute("currentStudentId", student.getStudentId());
        model.addAttribute("evaluation", evaluation);
        model.addAttribute("projectGroup", projectGroup);

        System.out.println(evaluation);
        System.out.println(evaluation.getEvaluationQuestions());

        //System.out.println(projectGroup);
        return "student/completeEvaluation";
    }



    
}
