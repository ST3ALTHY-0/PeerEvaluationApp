package edu.pui.peerEvaluation.PeerEvaluationApplication.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFormDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationQuestionDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.LoginDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.SignUpDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceApi.BrightSpaceAPIService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceApi.brightSpaceGroup.BrightSpaceGroupCategory;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser.SaveBrightSpaceData;
import edu.pui.peerEvaluation.PeerEvaluationApplication.exceptions.InstructorAlreadyExistsException;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategoryService;
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
@RequestMapping("/instructor")
public class InstructorController {

    private final EvaluationService evaluationService;
    private final InstructorService instructorService;
    private final SaveBrightSpaceData saveBrightSpaceData;


    // declare any/all endpoint urls we will use

    @Autowired
    public InstructorController(EvaluationService evaluationService, InstructorService instructorService, SaveBrightSpaceData saveBrightSpaceData) {
        this.evaluationService = evaluationService;
        this.instructorService = instructorService;
        this.saveBrightSpaceData = saveBrightSpaceData;
    }

    @GetMapping("/login")
    public String login(){

        return "instructor/login";
    }

    @PostMapping("login/submit")
    public String loginSubmit(HttpSession session, @ModelAttribute LoginDTO loginDTO){
        // Find the instructor by email
    Instructor instructor = instructorService.findInstructorByEmail(loginDTO.getEmail()).orElse(null);
    
    // Check if the instructor is present
    if (instructor == null) {
        return "login/failed";
    }
        session.setAttribute("instructorId", instructor.getInstructorId());
        return "instructor/dashboard";
    }

    @GetMapping("/dashboard")
    public String instructorDashboard(HttpSession session) {
        return "instructor/dashboard";
    }


    @GetMapping("/viewEvaluations")
    public String viewEvaluations(HttpSession session, Model model) {
        //we need to pass all evaluations that have projects with that have this instructor

        //Instructor instructor = instructorService.findById(((Integer) session.getAttribute("instructorId"))).orElse(null);
        List<Evaluation> evaluations = evaluationService.findEvaluationsByInstructorId(((Integer) session.getAttribute("instructorId")));
        model.addAttribute("evaluations", evaluations);
        

        return "instructor/viewEvaluations";
    }

    @GetMapping("/createEvaluation")
    public String createEvaluation(HttpSession session, Model model) {
        Integer instructorId = (Integer) session.getAttribute("instructorId");
        model.addAttribute("instructorId", instructorId);
        System.out.println("Passing instructorId: " + instructorId);
        return "instructor/createEvaluation";
    }

    @PostMapping("/previewEvaluationForm")
    public String previewEvaluationForm(Model model, @ModelAttribute EvaluationFormDTO evaluationForm) {

        System.out.println(evaluationForm);

        Evaluation sampleEvaluation = new Evaluation();
        sampleEvaluation.setEvaluationId(1);

        List<EvaluationQuestionDTO> questions = evaluationForm.getEvaluationQuestions();
        List<EvaluationQuestion> eQuestions = new ArrayList<>();

        for(EvaluationQuestionDTO question : questions){
            System.out.println(question.getQuestionText());
            EvaluationQuestion q = new EvaluationQuestion();
            q.setQuestionText(question.getQuestionText());
            q.setEnforceAnswer(question.isRequired());
            eQuestions.add(q);
        }

        sampleEvaluation.setEvaluationQuestions(eQuestions);


        ProjectGroup sampleProjectGroup = new ProjectGroup();
        sampleProjectGroup.setGroupId(1);
        sampleProjectGroup.setGroupName("Sample Project Group");

        // Add sample data to the model
        model.addAttribute("currentStudentId", 1);
        model.addAttribute("evaluation", sampleEvaluation);
        model.addAttribute("projectGroup", sampleProjectGroup);

        return "instructor/previewEvaluationForm";
    }

    @GetMapping("/signUp")
    public String instructorSignUp() {
        return "instructor/signUp";
    }

    @PostMapping("/signUp/submit")
    public String signUpSubmit(@ModelAttribute SignUpDTO signUpDTO, HttpSession session, Model model) throws Exception {

        try {
            Instructor instructor = saveBrightSpaceData.saveInstructorSignUp(signUpDTO);
            session.setAttribute("instructorId", instructor.getInstructorId());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred during sign up: " + e.getMessage());
            return "instructor/error";
        }
        
        return "instructor/dashboard";
    }

}
