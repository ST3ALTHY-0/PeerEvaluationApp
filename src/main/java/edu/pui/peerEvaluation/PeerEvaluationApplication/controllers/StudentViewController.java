package edu.pui.peerEvaluation.PeerEvaluationApplication.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public StudentViewController(StudentService studentService, EvaluationService evaluationService,
            ProjectGroupService projectGroupService) {
        this.evaluationService = evaluationService;
        this.projectGroupService = projectGroupService;
        this.studentService = studentService;
    }

    @GetMapping("/login")
    public String login() {
        return "student/login";
    }

    @PostMapping("/login/submit")
    public String loginSubmit(@ModelAttribute LoginDTO loginDTO, HttpSession session, Model model) {
        // Find the student by email and PUID
        Optional<Student> student = studentService.findStudentByEmailAndPuid(loginDTO.getEmail(), loginDTO.getPuid());
        
        if (student.isPresent()) {
            session.setAttribute("studentId", student.get().getStudentId());
            return "student/dashboard";
        } else {
            model.addAttribute("errorMessage", "Invalid email or PUID. Please try again.");
            return "student/login";
        }
    }

    @GetMapping("/dashboard")
public String studentDashboard(HttpSession session, Model model) {
    Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return "student/login"; // Redirect to login if session is invalid
    }
    Student student = studentService.findById(studentId).orElse(null);
    model.addAttribute("student", student);
    return "student/dashboard";
}

@GetMapping("/viewEvaluations")
public String studentViewEvaluations(HttpSession session, Model model) {
    Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return "student/login"; // Redirect to login if session is invalid
    }
    List<Evaluation> userEvalList = evaluationService.findEvaluationsWithoutStudentFeedback(studentId);
    //TODO: only select evaluations not past dueDate, and if there is an evaluationOverride for this student, not past that deadline
    userEvalList.removeIf(evaluation -> {
        boolean isPastDueDate = evaluation.getDueDate().isBefore(java.time.LocalDate.now().atStartOfDay());
        boolean hasOverride = evaluation.getEvaluationOverrides().stream()
            .anyMatch(override -> override.getStudent().getStudentId().equals(studentId));
        boolean isPastOverrideDate = hasOverride && evaluation.getEvaluationOverrides().stream()
            .filter(override -> override.getStudent().getStudentId().equals(studentId))
            .findFirst()
            .map(override -> override.getExtendedDeadline().isBefore(java.time.LocalDate.now().atStartOfDay()))
            .orElse(false);

        boolean hasResponded = evaluationService.hasStudentRespondedToEvaluation(studentId, evaluation.getEvaluationId());
        // boolean isCompleted = evaluationService.isEvaluationCompletedByStudent(studentId, evaluation.getEvaluationId());
        return isPastDueDate || isPastOverrideDate || hasResponded;
    });
    model.addAttribute("userEvalList", userEvalList);
    return "student/viewEvaluations";
}

@GetMapping("/viewPastEvaluations")
public String studentViewPastEvaluations(HttpSession session, Model model) {
    Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return "student/login"; // Redirect to login if session is invalid
    }
    //find evaluations that this student has completed
    List<Evaluation> userEvalList = evaluationService.findAllByStudentIdWithFeedback(studentId);

    //filter out evaluations that allowStudentsToViewFeedback == false;
    List<Evaluation> filteredEvalList = userEvalList.stream()
        .filter(eval -> eval.isAllowStudentsToViewFeedback())
        .toList();

    model.addAttribute("userEvalList", filteredEvalList);
    return "student/viewPastEvaluations";
}

@GetMapping("/completeEvaluation")
public String studentCompleteEvaluation(HttpSession session, Model model,
        @RequestParam("evaluationId") int evaluationId) {
    Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return "student/login"; // Redirect to login if session is invalid
    }
    Evaluation evaluation = evaluationService.findById(evaluationId).orElse(null);
    ProjectGroup projectGroup = projectGroupService.findProjectGroupByEvaluationIdAndStudentId(evaluationId, studentId);
    Integer numOfStudentsInGroup = projectGroupService.countStudentsInProjectGroup(projectGroup.getGroupId());
    Integer maxScore = (numOfStudentsInGroup - 1) * 100;

    model.addAttribute("currentStudentId", studentId);
    model.addAttribute("evaluation", evaluation);
    model.addAttribute("projectGroup", projectGroup);
    model.addAttribute("maxScore", maxScore);

    return "student/completeEvaluation";
}

@GetMapping("/viewEvaluationDetails")
public String viewEvaluationDetails(HttpSession session, Model model,
        @RequestParam("evaluationId") int evaluationId) {
    Integer studentId = (Integer) session.getAttribute("studentId");
    if (studentId == null) {
        return "student/login"; // Redirect to login if session is invalid
    }
    Evaluation evaluation = evaluationService.findById(evaluationId).get();

    model.addAttribute("currentStudentId", studentId);
    model.addAttribute("evaluation", evaluation);


    return "student/viewEvaluationDetails";
}

}
