package edu.pui.peerEvaluation.PeerEvaluationApplication.saveDataToDB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFeedbackDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFeedbackFormDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFormDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationQuestionDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.LoginDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.SignUpDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.StandardEvaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser.CSVDataDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.exceptions.InstructorAlreadyExistsException;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestionService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponseService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.FeedbackService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategoryService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.InstructorService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClassService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.ProjectService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroupService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade.StudentGrade;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade.StudentGradeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
public class SaveData {

    private final StudentService studentService;
    private final ProjectGroupService projectGroupService;
    private final InstructorService instructorService;
    private final GroupCategoryService groupCategoryService;
    private final ProjectService projectService;
    private final StudentGradeService studentGradeService;
    private final EvaluationQuestionService evaluationQuestionService;
    private final EvaluationService evaluationService;
    private final StandardEvaluation standardEvaluation;
    private final FeedbackService feedbackService;

    private static final Logger logger = LoggerFactory.getLogger(SaveData.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SaveData(StudentService studentService, ProjectGroupService projectGroupService,
            InstructorService instructorService,
            GroupCategoryService groupCategoryService, ProjectService projectService,
            StudentGradeService studentGradeService, EvaluationQuestionService evaluationQuestionService,
            EvaluationService evaluationService,
            StandardEvaluation standardEvaluation,
            FeedbackService feedbackService) {
        this.studentService = studentService;
        this.projectGroupService = projectGroupService;
        this.instructorService = instructorService;
        this.groupCategoryService = groupCategoryService;
        this.projectService = projectService;
        this.studentGradeService = studentGradeService;
        this.evaluationQuestionService = evaluationQuestionService;
        this.evaluationService = evaluationService;
        this.standardEvaluation = standardEvaluation;
        this.feedbackService = feedbackService;
    }

    Map<String, ProjectGroup> projectGroupMap = new HashMap<>();

    public Project saveProjectToDB(Project project, Integer instructorId, MyClass myClass) {
        Instructor instructor = instructorService.findById(instructorId).orElse(null);
        project.setMyClass(myClass);
        project.setInstructor(instructor);
        return projectService.saveProject(project);
    }

    @Transactional
    public GroupCategory saveCSVDataToDB(List<CSVDataDTO> csvDataDTOList, MyClass myClass,
            Project project) {
        logger.debug("Processing CSV data: " + csvDataDTOList);

        List<Student> studentsToSave = new ArrayList<>();
        List<ProjectGroup> groupsToSave = new ArrayList<>();
        List<StudentGrade> studentGradesToSave = new ArrayList<>();

        Map<String, ProjectGroup> projectGroupMap = new HashMap<>();

        // we are maybe creating and saving redundant groupCategories, but
        // this is hard to fix without changing the data schema, see line 146
        GroupCategory groupCategory = new GroupCategory();
        groupCategory.setMyClass(myClass);
        GroupCategory savedGroupCategory = groupCategoryService.addGroupCategory(groupCategory);

        for (CSVDataDTO csvDataDTO : csvDataDTOList) {
            logger.debug("Processing student {} for group {}", csvDataDTO.getStudent().getStudentEmail(),
                    csvDataDTO.getGroup());

            Student student = getOrCreateStudent(csvDataDTO);

            ProjectGroup projectGroup = projectGroupMap.computeIfAbsent(csvDataDTO.getGroup(),
                    key -> createProjectGroup(csvDataDTO, savedGroupCategory, project));
            logger.debug("Adding student {} to group {}", student.getStudentEmail(), projectGroup.getGroupName());

            student.setGroups((List<ProjectGroup>) getAndAddEntityToList(student.getGroups(), projectGroup));
            projectGroup.setStudents((List<Student>) getAndAddEntityToList(projectGroup.getStudents(), student));


            //save student grade 
            StudentGrade studentGrade = new StudentGrade();
            studentGrade.setProject(project);
            studentGrade.setStudent(student);
            studentGrade.setGrade(csvDataDTO.getStudentGrade());

            studentsToSave.add(student);
            groupsToSave.add(projectGroup);
            studentGradesToSave.add(studentGrade);
        }

        studentService.saveAll(studentsToSave);
        studentGradeService.saveAll(studentGradesToSave);
        projectGroupService.saveAllProjectGroups(groupsToSave);

        // TODO: find if groupCategory exists
        System.out.println(savedGroupCategory);
        groupCategoryService.addGroupCategory(groupCategory);

        entityManager.flush(); // Single flush after batch insert
        return savedGroupCategory;
    }

    private Student getOrCreateStudent(CSVDataDTO csvDataDTO) {
        return studentService.findStudentByEmail(csvDataDTO.getStudent().getStudentEmail())
                .orElseGet(() -> {
                    Student newStudent = csvDataDTO.getStudent();
                    studentService.addStudent(newStudent);
                    return newStudent;
                });
    }

    private ProjectGroup createProjectGroup(CSVDataDTO csvDataDTO, GroupCategory groupCategory, Project project) {
        String groupName = csvDataDTO.getGroup();

        return projectGroupService.findByGroupNameAndProject(groupName, project)
                .orElseGet(() -> {
                    logger.debug("Creating new Project Group: {}", groupName);

                    ProjectGroup newGroup = new ProjectGroup();
                    newGroup.setGroupName(groupName);
                    newGroup.setProject(project);
                    newGroup.setGroupCategory(groupCategory);

                    projectGroupService.saveProjectGroup(newGroup);
                    return newGroup;
                });
    }

    @Transactional
    public Evaluation saveEvaluationToDB(EvaluationFormDTO evaluationFormDTO, GroupCategory groupCategory, Project project) {
        logger.debug("EvalForm DTO: {}", evaluationFormDTO);
    
        Evaluation evaluation = new Evaluation();
        evaluation.setDueDate(LocalDate.parse(evaluationFormDTO.getDueDate()).atStartOfDay());
        evaluation.setCreatedAt(LocalDateTime.now());
        evaluation.setProject(project);
        evaluation.setIsGraded(true);
        evaluation.setAllowStudentsToViewFeedback(evaluationFormDTO.isAllowStudentToViewFeedback());
        evaluation.setGroupCategory(groupCategory);
        evaluation.setEvaluationQuestions(new ArrayList<>());
    
        //set standard questions if instructor selected yes
        if (evaluationFormDTO.isUseStandardForm()) {
            List<EvaluationQuestion> savedQuestions = new ArrayList<>();
            for (EvaluationQuestion question : standardEvaluation.getEvaluationQuestions()) {
                if (question.getQuestionId() == null) {
                    evaluationQuestionService.save(question);
                }
                savedQuestions.add(question);
            }
            evaluation.setEvaluationQuestions(savedQuestions);
            logger.debug("Evaluation StandardForm: {}", savedQuestions);
            
            //set the questions to whatever the instructor set
        } else {
            List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
            for (EvaluationQuestionDTO evaluationQuestionDTO : evaluationFormDTO.getEvaluationQuestions()) {
                EvaluationQuestion evaluationQuestion = new EvaluationQuestion();
                evaluationQuestion.setEnforceAnswer(evaluationQuestionDTO.isRequired());
                evaluationQuestion.setQuestionText(evaluationQuestionDTO.getQuestionText());
                evaluationQuestions.add(evaluationQuestion);
            }
            evaluationQuestionService.saveAll(evaluationQuestions);
            evaluation.setEvaluationQuestions(evaluationQuestions);
        }
    
        logger.debug("Creating new Evaluation: {}", evaluation);
        evaluationService.save(evaluation);
    
        groupCategory.getEvaluations().add(evaluation);
        groupCategoryService.save(groupCategory);
    
        entityManager.flush();
        return evaluation;
    }
    

    // Takes in a list of entities and adds another entity to it and returns the new
    // list

    // This is something we do repeatedly to add a new entity to a list. We do it
    // like this instead of doing
    // 'entity.getEntityList.add(newEntity)' (example :
    // evaluation.getGroupCategories().add(groupCategory)) because I ran into errors
    // with entity.getEntityList() returning null.
    private static <T> List<T> getAndAddEntityToList(Iterable<T> entities, T entity) {
        List<T> newEntityList = new ArrayList<>();
        if (entities != null) {
            entities.forEach(newEntityList::add);
        }
        newEntityList.add(entity);
        return newEntityList;
    }

    @Transactional
    public List<Feedback> saveFeedbackToDB(EvaluationFeedbackFormDTO evaluationFeedbackFormDTO) {
        List<Feedback> feedbacks = new ArrayList<>();

        // Create Feedback Entity based on the feedbackDTO we received and add it to the
        // list
        for (EvaluationFeedbackDTO feedbackDTO : evaluationFeedbackFormDTO.getEvaluationFeedbackDTOList()) {
            Feedback feedback = new Feedback();

            feedback.setRatedByStudent(studentService.findStudentById(feedbackDTO.getRatedByStudentId()));
            feedback.setRatedStudent(studentService.findStudentById(feedbackDTO.getRatedStudentId()));
            feedback.setEvaluation(evaluationService.findById(feedbackDTO.getEvaluationId()).orElse(null));
            feedback.setGradePercent(feedbackDTO.getGrade());
            feedback.setDateCompleted(LocalDateTime.now());
            feedback.setGroup(projectGroupService.findById(feedbackDTO.getProjectGroupId()));
            feedback.setProject(projectService.findById(feedbackDTO.getProjectId()));

            // create evaluationResponse entity from feedbackDTO.Responses
            List<EvaluationResponse> evaluationResponses = new ArrayList<>();
            if (feedbackDTO.getResponses() != null) {
                evaluationResponses = feedbackDTO.getResponses().stream()
                        .map(dto -> {
                            EvaluationResponse response = new EvaluationResponse();
                            response.setResponseText(dto.getResponse());
                            response.setQuestion(evaluationQuestionService.findById(dto.getQuestionId()).orElse(null));
                            response.setFeedback(feedback);
                            return response;
                        })
                        .collect(Collectors.toList());
            }

            feedback.setResponses(evaluationResponses);
            feedbacks.add(feedback);
        }

        feedbackService.saveAll(feedbacks);
        entityManager.flush();
        return feedbacks;
    }

    @Transactional
    public Instructor saveInstructorSignUp(SignUpDTO signUpDTO) throws Exception {
        // Check if the instructor already exists
        Optional<Instructor> existingInstructor = instructorService.findInstructorByEmail(signUpDTO.getEmail());
        if (existingInstructor.isPresent()) {
            throw new InstructorAlreadyExistsException(
                    "Instructor with email " + signUpDTO.getEmail() + " already exists.");
        }

        Instructor instructor = new Instructor();
        instructor.setInstructorEmail(signUpDTO.getEmail());
        instructor.setPuid(signUpDTO.getPuid());

        return instructorService.save(instructor);
    }

    
}