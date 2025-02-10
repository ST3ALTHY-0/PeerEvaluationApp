package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationFeedbackDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationFeedbackFormDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationFormDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationQuestionDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.ResponseDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion.EvaluationQuestionService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponseService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback.FeedbackService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.groupCategory.GroupCategoryService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.InstructorService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationController.class);

    private final FeedbackService feedbackService;
    private final EvaluationQuestionService evaluationQuestionService;
    private final EvaluationResponseService evaluationResponseService;
    private final EvaluationService evaluationService;
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final GroupCategoryService groupCategoryService;

    @Autowired
    public EvaluationController(FeedbackService feedbackService,
            EvaluationQuestionService evaluationQuestionService,
            EvaluationResponseService evaluationResponseService,
            EvaluationService evaluationService,
            StudentService studentService,
            InstructorService instructorService,
            GroupCategoryService groupCategoryService) {
        this.feedbackService = feedbackService;
        this.evaluationQuestionService = evaluationQuestionService;
        this.evaluationResponseService = evaluationResponseService;
        this.evaluationService = evaluationService;
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.groupCategoryService = groupCategoryService;
    }

    @PostMapping("/submit/feedback")
    public String submitFeedback(@ModelAttribute EvaluationFeedbackFormDTO evaluationFeedbackFormDTO,
            // @RequestParam("extraResponse") ResponseDTO extraResponse,
            Model model) {

                System.out.println(evaluationFeedbackFormDTO);

        logger.debug("Received feedback: {}", evaluationFeedbackFormDTO);

        List<EvaluationFeedbackDTO> filteredFeedbackList = evaluationFeedbackFormDTO.getEvaluationFeedbackDTOList().stream()
                .filter(feedback -> feedback.getEvaluationId() != null && feedback.getRatedByStudentId() != null && feedback.getRatedStudentId() != null)
                .collect(Collectors.toList());

        evaluationFeedbackFormDTO.setEvaluationFeedbackDTOList(filteredFeedbackList);
        for(EvaluationFeedbackDTO feedbackDTO : evaluationFeedbackFormDTO.getEvaluationFeedbackDTOList())
        {
            System.out.println("GroupID: " + feedbackDTO.getProjectGroupId());
            Feedback feedback = feedbackService.convertToEntity(feedbackDTO);
            feedbackService.addFeedback(feedback);
        }
        model.addAttribute("message", "Feedback submitted successfully");

        // Redirect or return a view name
        return "/student/feedback/finished";
    }

   @Transactional
@PostMapping("/submit/form")
public String createEvaluation(@ModelAttribute EvaluationFormDTO evaluationForm) {
    System.out.println("AHHHHHHHHHHHHHHHHH" + evaluationForm);
    Evaluation eval = evaluationService.convertToEntity(evaluationForm);

    GroupCategory gc = groupCategoryService.findById(evaluationForm.getGroupCategoryId());

    GroupCategory groupCategory = new GroupCategory();
    groupCategory.setCategoryName(gc.getCategoryName());
    groupCategory.setMyClass(gc.getMyClass());

    // Create a deep copy of the projectGroups collection
    List<ProjectGroup> projectGroupsCopy = new ArrayList<>();
    for (ProjectGroup pg : gc.getProjectGroups()) {
        ProjectGroup pgCopy = new ProjectGroup();
        pgCopy.setGroupName(pg.getGroupName());
        pgCopy.setGroupCategory(groupCategory); // Set the new groupCategory reference
        pgCopy.setStudents(new ArrayList<>(pg.getStudents())); // Copy the students list
        projectGroupsCopy.add(pgCopy);
    }
    groupCategory.setProjectGroups(projectGroupsCopy);

    evaluationService.addEvaluation(eval);
    groupCategoryService.addGroupCategory(groupCategory);

    return "/instructor/dashboard";
}
}