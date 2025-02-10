package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.StudentService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroupService;

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



     @GetMapping("/viewEvaluations")
    public String studentViewEvaluations(@AuthenticationPrincipal OAuth2User principal, Model model) {

        Student student = studentService.findStudentByEmail("monroe.luke36@gmail.com");

        List<Evaluation> userEvalList = evaluationService.findAllByStudentIdAndNoFeedback(student.getStudentId());


        model.addAttribute("userEvalList", userEvalList);
        
        return "student/viewEvaluations";
    }

    @GetMapping("/viewPastEvaluations")
    public String studentViewPastEvaluations(@AuthenticationPrincipal OAuth2User principal, Model model) {
        Student student = studentService.findStudentByEmail("monroe.luke36@gmail.com");

        List<Evaluation> userEvalList = evaluationService.findAllByStudentIdWithFeedback(student.getStudentId());

        model.addAttribute("userEvalList", userEvalList);
        
        return "student/viewPastEvaluations";
    }

    @GetMapping("/completeEvaluation")
    public String studentCompleteEvaluation(@AuthenticationPrincipal OAuth2User principal, Model model,
            @RequestParam("evaluationId") int evaluationId) {

        String currentStudentEmail = principal.getAttribute("email");
        Student currentStudent = studentService.findStudentByEmail(currentStudentEmail);
        Integer currentStudentId = currentStudent.getStudentId();
        Evaluation evaluation = evaluationService.findById(evaluationId);

        ProjectGroup projectGroup = projectGroupService.findProjectGroupByEvaluationIdAndStudentId(evaluationId, currentStudentId);

        System.out.println("Eval Id: " + evaluation.getEvaluationId());
         System.out.println("HELLLO PGID: " + projectGroup.getGroupId());
        // System.out.println("HELLLO currentStudentId: " + currentStudentId);
        // System.out.println("HELLLO evaluation: " + evaluation);


        model.addAttribute("currentStudentId", currentStudentId);
        model.addAttribute("evaluation", evaluation);
        model.addAttribute("projectGroup", projectGroup);



        //System.out.println(projectGroup);
        return "student/completeEvaluation";
    }

    @GetMapping("/dashboard")
    public String studentDashboard(){
        return "student/dashboard";
    }


    
}
