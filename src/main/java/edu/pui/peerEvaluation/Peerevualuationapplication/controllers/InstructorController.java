package edu.pui.peerEvaluation.Peerevualuationapplication.controllers;

import java.util.ArrayList;
import java.util.List;

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

import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationFormDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationQuestionDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.BrightSpaceAPIService;
import edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceGroup.BrightSpaceGroupCategory;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.groupCategory.GroupCategoryService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.InstructorService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.project.Project;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.StudentService;
import edu.pui.peerEvaluation.Peerevualuationapplication.services.OAuth2Service;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroupService;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    private final EvaluationService evaluationService;
    private final ProjectGroupService projectGroupService;
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final GroupCategoryService groupCategoryService;

    // declare any/all endpoint urls we will use

    @Autowired
    public InstructorController(EvaluationService evaluationService, ProjectGroupService projectGroupService,
            StudentService studentService, InstructorService instructorService, GroupCategoryService groupCategoryService) {
        this.evaluationService = evaluationService;
        this.projectGroupService = projectGroupService;
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.groupCategoryService = groupCategoryService;
    }

    @GetMapping("/viewEvaluations")
    public String viewEvaluations() {
        return "instructor/viewEvaluations";
    }

    @GetMapping("/createEvaluation")
    public String createEvaluation(@AuthenticationPrincipal OAuth2User principal, Model model) {
        // for google OAuth
        String userEmail = principal.getAttribute("email");

        // for brightspace user, get and transform userData into instructor obj
        // check if exist in db, if so return the instructor
        Instructor instructor = instructorService.findInstructorByEmail(userEmail);
        System.out.println(instructor.getInstructorName());
        List<MyClass> classList = instructor.getClasses();
        List<Project> projects = new ArrayList<Project>();
        for (MyClass c : classList) {
            projects.addAll(c.getProjects());
        }
        model.addAttribute("instructorClassList", classList);
        model.addAttribute("projectList", projects);


        List<GroupCategory> groupCategories = groupCategoryService.findAllByInstructorId(instructor.getInstructorId());


        model.addAttribute("groupCategoryList", groupCategories);

        // else create new user for db

        // add Brightspace Classes
        // add BrightSpace Students for those classes
        // add projects for those classes if possible
        // add groups for those classes if possible

        return "instructor/createEvaluation";
    }

    @GetMapping("/dashboard")
    public String studentDashboard(@AuthenticationPrincipal OAuth2User principal) {
        principal.getAttribute("email");// may need to get different attribute based on what brightSpace provides
                                        // (userId)
        return "instructor/dashboard";
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

}
