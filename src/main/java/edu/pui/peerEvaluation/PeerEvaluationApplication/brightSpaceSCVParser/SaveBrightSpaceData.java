package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser;

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
public class SaveBrightSpaceData {

    private final StudentService studentService;
    private final ProjectGroupService projectGroupService;
    private final InstructorService instructorService;
    private final MyClassService myClassService;
    private final GroupCategoryService groupCategoryService;
    private final ProjectService projectService;
    private final StudentGradeService studentGradeService;
    private final EvaluationQuestionService evaluationQuestionService;
    private final EvaluationService evaluationService;
    private final StandardEvaluation standardEvaluation;
    private final FeedbackService feedbackService;
    private final EvaluationResponseService evaluationResponseService;
    private static final Logger logger = LoggerFactory.getLogger(SaveBrightSpaceData.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SaveBrightSpaceData(StudentService studentService, ProjectGroupService projectGroupService,
            InstructorService instructorService, MyClassService myClassService,
            GroupCategoryService groupCategoryService, ProjectService projectService,
            StudentGradeService studentGradeService, EvaluationQuestionService evaluationQuestionService,
            EvaluationService evaluationService,
            StandardEvaluation standardEvaluation,
            FeedbackService feedbackService,
            EvaluationResponseService evaluationResponseService) {
        this.studentService = studentService;
        this.projectGroupService = projectGroupService;
        this.instructorService = instructorService;
        this.myClassService = myClassService;
        this.groupCategoryService = groupCategoryService;
        this.projectService = projectService;
        this.studentGradeService = studentGradeService;
        this.evaluationQuestionService = evaluationQuestionService;
        this.evaluationService = evaluationService;
        this.standardEvaluation = standardEvaluation;
        this.feedbackService = feedbackService;
        this.evaluationResponseService = evaluationResponseService;
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

        // Wanted to implement a way to check if a projectGroup already exists and to
        // use that group instead of making a new one if it does exist,
        // but because project groups are tied to a particular project there really
        // should'nt be many (if any) cases where we can reuse a project group,
        // seeing as each time a instructor makes a new evaluation, it makes a new
        // project
        // TODO: potentially remove the relationship between projectGroups and project
        // TODO: and instead have a linking table, or ManyToMany relationship, between
        // TODO: the two, so we can improve reusability and reduce redundancy
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
    public Evaluation saveEvaluationToDB(EvaluationFormDTO evaluationFormDTO, GroupCategory groupCategory,
            Project project) {

        logger.debug("EvalForm DTO: {}", evaluationFormDTO);
        Evaluation evaluation = new Evaluation();

        // TODO: we are only asking for a date and not a specific time on webpage
        LocalDate dueDate = LocalDate.parse(evaluationFormDTO.getDueDate());
        LocalDateTime dueDateTime = dueDate.atStartOfDay();
        evaluation.setDueDate(dueDateTime);

        evaluation.setCreatedAt(LocalDateTime.now());

        evaluation.setProject(project);

        if (evaluationFormDTO.isUseStandardForm()) {
            evaluation.setGraded(standardEvaluation.getIsGraded());
            // Save the standard evaluation questions if they are not already saved
            List<EvaluationQuestion> savedQuestions = new ArrayList<>();
            for (EvaluationQuestion question : standardEvaluation.getEvaluationQuestions()) {
                if (question.getQuestionId() == null) { // Check if the question is not saved
                    evaluationQuestionService.save(question); // Save the question
                }
                savedQuestions.add(question); // Add the saved question to the list
            }

            evaluation.setEvaluationQuestions(savedQuestions); // Set the saved questions
            logger.debug("Evaluation StandardForm: {}", savedQuestions);

        } else {
            // Add the evaluation questions to the DB
            for (EvaluationQuestionDTO evaluationQuestionDTO : evaluationFormDTO.getEvaluationQuestions()) {
                EvaluationQuestion evaluationQuestion = new EvaluationQuestion();
                evaluationQuestion.setEnforceAnswer(evaluationQuestionDTO.isRequired());
                evaluationQuestion.setQuestionText(evaluationQuestionDTO.getQuestionText());
            
                // Save the EvaluationQuestion first to ensure it is persistent
                evaluationQuestionService.save(evaluationQuestion);
            
                // Ensure the evaluationQuestions list in Evaluation is initialized
                if (evaluation.getEvaluationQuestions() == null) {
                    evaluation.setEvaluationQuestions(new ArrayList<>());
                }
            
                // Add the EvaluationQuestion to the Evaluation if not already present
                if (!evaluation.getEvaluationQuestions().contains(evaluationQuestion)) {
                    evaluation.getEvaluationQuestions().add(evaluationQuestion);
                }
            }
            evaluation.setGraded(evaluationFormDTO.isEnableGrading());
        }

        //add the groupCategory to the evaluation
        evaluation.setGroupCategory(groupCategory);
        logger.debug("Creating new Evaluation : {}", evaluation);
        evaluationService.save(evaluation);
        //map the new groupCategory to the evaluation
        List<Evaluation> evaluations = (List<Evaluation>) getAndAddEntityToList(groupCategory.getEvaluations(),
                evaluation);
        groupCategory.setEvaluations(evaluations);
        logger.debug("Updating groupCategory : {}", groupCategory);
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

    private static <T> Set<T> getAndAddEntityToSet(Iterable<T> entities, T entity) {
        Set<T> newEntitySet = new HashSet<>();
        if (entities != null) {
            entities.forEach(newEntitySet::add);
        }
        newEntitySet.add(entity);
        return newEntitySet;
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