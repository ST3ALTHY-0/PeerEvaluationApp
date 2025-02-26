package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFormDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationQuestionDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.StandardEvaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestionService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategoryService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.InstructorService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClassService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.ProjectService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroupService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;
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
    private static final Logger logger = LoggerFactory.getLogger(SaveBrightSpaceData.class);
    private final StandardEvaluation standardEvaluation;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SaveBrightSpaceData(StudentService studentService, ProjectGroupService projectGroupService,
            InstructorService instructorService, MyClassService myClassService,
            GroupCategoryService groupCategoryService, ProjectService projectService,
            StudentGradeService studentGradeService, EvaluationQuestionService evaluationQuestionService,
            EvaluationService evaluationService,
            StandardEvaluation standardEvaluation) {
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
    }

    Map<String, ProjectGroup> projectGroupMap = new HashMap<>();

    public Project saveProjectToDB(Project project) {
        return projectService.addProject(project);
    }

    @Transactional
    public void saveCSVDataToDB(List<CSVDataDTO> csvDataDTOList, MyClass myClass, GroupCategory groupCategory,
            Project project) {
        logger.debug("Processing CSV data: " + csvDataDTOList);

        List<Student> studentsToSave = new ArrayList<>();
        List<ProjectGroup> groupsToSave = new ArrayList<>();
        Map<String, ProjectGroup> projectGroupMap = new HashMap<>();

        for (CSVDataDTO csvDataDTO : csvDataDTOList) {
            logger.debug("Processing student {} for group {}", csvDataDTO.getStudent().getStudentEmail(),
                    csvDataDTO.getGroup());

            Student student = getOrCreateStudent(csvDataDTO);
            ProjectGroup projectGroup = projectGroupMap.computeIfAbsent(csvDataDTO.getGroup(),
                    key -> getOrCreateProjectGroup(csvDataDTO, groupCategory, project));

            logger.debug("Adding student {} to group {}", student.getStudentEmail(), projectGroup.getGroupName());

            student.getGroups().add(projectGroup);
            projectGroup.getStudents().add(student);

            studentsToSave.add(student);
            groupsToSave.add(projectGroup);
        }

        studentService.saveAllStudents(studentsToSave);
        projectGroupService.saveAllProjectGroups(groupsToSave);

        entityManager.flush(); // Single flush after batch insert
    }

    private Student getOrCreateStudent(CSVDataDTO csvDataDTO) {
        return studentService.findStudentByEmail(csvDataDTO.getStudent().getStudentEmail())
                .orElseGet(() -> {
                    Student newStudent = csvDataDTO.getStudent();
                    studentService.addStudent(newStudent);
                    return newStudent;
                });
    }

    private ProjectGroup getOrCreateProjectGroup(CSVDataDTO csvDataDTO, GroupCategory groupCategory, Project project) {
        String groupName = project.getProjectName() + " " + csvDataDTO.getGroup();

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

    // private List<ProjectGroup> addNewGroupToStudentsGroupList(Student student, ProjectGroup projectGroup) {
    //     List<ProjectGroup> groupList = new ArrayList<>();
    //     if (student.getGroups() != null) {
    //         groupList.addAll(student.getGroups());
    //     }
    //     groupList.add(projectGroup);

    //     return groupList;

    // }

    // private List<Student> addStudentToNewGroupList(Student student, ProjectGroup projectGroup) {

    //     List<Student> groupStudentList = new ArrayList<>();
    //     if (projectGroup.getStudents() != null) {
    //         groupStudentList.addAll(projectGroup.getStudents());
    //     }
    //     groupStudentList.add(student);
    //     return groupStudentList;
    // }

    public Evaluation saveEvaluationToDB(EvaluationFormDTO evaluationFormDTO, GroupCategory groupCategory, Project project){
        Evaluation evaluation = new Evaluation();
        
        evaluation.setDueDate(LocalDateTime.parse(evaluationFormDTO.getDueDate()));
        evaluation.setProject(project);
        
        if(evaluationFormDTO.isUseStandardForm()){
            evaluation.setGraded(standardEvaluation.getIsGraded());
            //TODO change standard eval to contain a List of EvaluationQuestions
            evaluation.setEvaluationQuestions(null);
            //set Standard Form, maybe have a DTO with set values that we use to assign variables
        }else{
            List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();
            
            //add the evaluation questions to the DB
            for(EvaluationQuestionDTO evaluationQuestionDTO : evaluationFormDTO.getEvaluationQuestions()){
                EvaluationQuestion evaluationQuestion = new EvaluationQuestion();
                evaluationQuestion.setEnforceAnswer(evaluationQuestionDTO.isRequired());
                evaluationQuestion.setQuestionText(evaluationQuestionDTO.getQuestionText());
                evaluationQuestion.setEvaluation(evaluation);
                evaluationQuestions.add(evaluationQuestion);
                evaluationQuestionService.save(evaluationQuestion);
            }
            evaluation.setEvaluationQuestions(evaluationQuestions);
            evaluation.setGraded(evaluationFormDTO.isEnableGrading());
        }


        List<GroupCategory> groupCategories = getAndAddEntityToList(evaluation.getGroupCategories(), groupCategory);
        evaluation.setGroupCategories(groupCategories);
        evaluationService.save(evaluation);

        List<Evaluation> evaluations = getAndAddEntityToList(groupCategory.getEvaluations(), evaluation);
        groupCategory.setEvaluations(evaluations);
        groupCategoryService.save(groupCategory);


        return evaluation;
    }

    //Takes in a list of entities, adds it to a list of other entities and returns it.
    //This is something we do repeatedly to add a new entity to a list. We do it like this instead of doing
    //'entity.getEntityList.add(newEntity)' (example : evaluation.getGroupCategories().add(groupCategory)) because I ran into errors with entity.getEntityList() returning null. 
    private static <T> List<T> getAndAddEntityToList(List<T> entities, T entity) {
        List<T> newEntityList = new ArrayList<>();
        if (entities != null) {
            newEntityList.addAll(entities);
        }
        newEntityList.add(entity);
        return newEntityList;
    }

}
