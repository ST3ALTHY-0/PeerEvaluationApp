package edu.pui.peerEvaluation.PeerEvaluationApplication.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opencsv.CSVWriter;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationDetailsDTO;
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
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
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
import edu.pui.peerEvaluation.PeerEvaluationApplication.saveDataToCsv.SaveDataToCSV;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    private final EvaluationService evaluationService;
    private final InstructorService instructorService;
    private final SaveBrightSpaceData saveBrightSpaceData;
    private final SaveDataToCSV saveDataToCSV;


    // declare any/all endpoint urls we will use

    @Autowired
    public InstructorController(EvaluationService evaluationService, InstructorService instructorService, SaveBrightSpaceData saveBrightSpaceData, SaveDataToCSV saveDataToCSV) {
        this.evaluationService = evaluationService;
        this.instructorService = instructorService;
        this.saveBrightSpaceData = saveBrightSpaceData;
        this.saveDataToCSV = saveDataToCSV;
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

        List<Evaluation> evaluations = evaluationService
                .findEvaluationsByInstructorId(((Integer) session.getAttribute("instructorId")));


        // pass the number of people that have responded and number of people that can
        // respond
        List<EvaluationDetailsDTO> evaluationsDetails = evaluations.stream()
                .map(evaluation -> new EvaluationDetailsDTO(
                        evaluation,
                        evaluationService.countStudentsWhoSubmittedFeedback(evaluation.getEvaluationId()),
                        evaluationService.countStudentsAssignedToEvaluation(evaluation.getEvaluationId())))
                .toList();
        model.addAttribute("evaluationsDetails", evaluationsDetails);


        return "instructor/viewEvaluations";
    }


    @GetMapping("/createEvaluation")
    public String createEvaluation(HttpSession session, Model model) {
        Integer instructorId = (Integer) session.getAttribute("instructorId");
        model.addAttribute("instructorId", instructorId);
        System.out.println("Passing instructorId: " + instructorId);
        return "instructor/createEvaluation";
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

    @GetMapping("/downloadCSV/{id}")
    public ResponseEntity<byte[]> downloadCSV(@PathVariable("id") Integer evaluationId) throws IOException{
        Evaluation evaluation = evaluationService.findById(evaluationId).orElse(null);

        if (evaluation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        List<String[]> data = new ArrayList<>();

        //Add Headers
        //rename points grade to something else so its a weighted grade instead of numeric
        data.add(new String[]{"OrgDefinedId", evaluation.getProject().getProjectName() + " evaluation" + " Points Grade", "End-of-Line Indicator"});
       
        List<Student> students = evaluationService.findStudentsAssignedToEvaluation(evaluationId);
        
        data.addAll(students.stream()
            .map(student -> new String[]{ 
                student.getPuid(), 
                saveDataToCSV.calculateAverageGrade(student.getStudentId(), evaluationId), 
                "#" 
            })
            .toList());

        byte[] csvContent = saveDataToCSV.generateCSV(data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", evaluation.getProject().getProjectName() + " evaluation" + ".csv");

        return new ResponseEntity<>(csvContent, headers, HttpStatus.OK);
    
    }

    @GetMapping("/viewEvaluation/details/{id}")
    public String viewEvaluationDetails(@PathVariable ("id") Integer evaluationId, Model model){
        Evaluation evaluation = evaluationService.findById(evaluationId).orElse(null);

        if(evaluation == null){
            model.addAttribute("errorMessage", "Evaluation does not exist");
            return "instructor/error";
        }
        System.out.println("WERE HERE");
        model.addAttribute("evaluation", evaluation);
        return "instructor/evaluationDetails";
    }

}
