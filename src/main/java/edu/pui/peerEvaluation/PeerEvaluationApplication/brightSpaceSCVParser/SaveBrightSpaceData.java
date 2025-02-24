package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser;

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

import edu.pui.peerEvaluation.PeerEvaluationApplication.controllers.EvaluationController;
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

    private static final Logger logger = LoggerFactory.getLogger(SaveBrightSpaceData.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SaveBrightSpaceData(StudentService studentService, ProjectGroupService projectGroupService,
            InstructorService instructorService, MyClassService myClassService,
            GroupCategoryService groupCategoryService, ProjectService projectService,
            StudentGradeService studentGradeService) {
        this.studentService = studentService;
        this.projectGroupService = projectGroupService;
        this.instructorService = instructorService;
        this.myClassService = myClassService;
        this.groupCategoryService = groupCategoryService;
        this.projectService = projectService;
        this.studentGradeService = studentGradeService;
    }

    Map<String, ProjectGroup> projectGroupMap = new HashMap<>();

    public Project saveProjectToDB(Project project) {
        return projectService.addProject(project);
    }

    @Transactional
    public void saveCSVDataToDB(List<CSVDataDTO> csvDataDTOList, MyClass myClass, GroupCategory groupCategory,
            Project project) {
        logger.debug("Processing CSV data: " + csvDataDTOList);

        for (CSVDataDTO csvDataDTO : csvDataDTOList) {
            logger.debug("Processing student {} for group {}", csvDataDTO.getStudent().getStudentEmail(),
                    csvDataDTO.getGroup());

            Student student = saveCSVDataToDBIndividualStudent(csvDataDTO);
            ProjectGroup projectGroup = getOrCreateProjectGroup(csvDataDTO, groupCategory, projectGroupMap, project);

            logger.debug("Adding student {} to group {}", student.getStudentEmail(), projectGroup.getGroupName());

            List<Student> groupStudentList = addStudentToNewGroupList(student, projectGroup);
            List<ProjectGroup> groupList = addNewGroupToStudentsGroupList(student, projectGroup);

            student.setGroups(groupList);
            projectGroup.setStudents(groupStudentList);
            projectGroupService.addProjectGroup(projectGroup);
        }

    }

    private Student saveCSVDataToDBIndividualStudent(CSVDataDTO csvDataDTO) {
        Student student = studentService.findStudentByEmail(csvDataDTO.getStudent().getStudentEmail());
        if (student == null) {
            student = csvDataDTO.getStudent();
            studentService.addStudent(student);
        }
        return student;
    }

    private ProjectGroup getOrCreateProjectGroup(CSVDataDTO csvDataDTO, GroupCategory groupCategory,
            Map<String, ProjectGroup> projectGroupMap, Project project) {
        String groupName = project.getProjectName() + " " + csvDataDTO.getGroup();

        return projectGroupMap.computeIfAbsent(groupName, key -> {
            logger.debug("Creating new Project Group: {}", groupName);

            ProjectGroup newGroup = new ProjectGroup();
            newGroup.setGroupName(groupName);
            newGroup.setProject(project);
            newGroup.setGroupCategory(groupCategory);

            return newGroup;
        });
    }

    private List<ProjectGroup> addNewGroupToStudentsGroupList(Student student, ProjectGroup projectGroup) {
        List<ProjectGroup> groupList = new ArrayList<>();
        if (student.getGroups() != null) {
            groupList.addAll(student.getGroups());
        }
        groupList.add(projectGroup);

        return groupList;

    }

    private List<Student> addStudentToNewGroupList(Student student, ProjectGroup projectGroup) {

        List<Student> groupStudentList = new ArrayList<>();
        if (projectGroup.getStudents() != null) {
            groupStudentList.addAll(projectGroup.getStudents());
        }
        groupStudentList.add(student);
        return groupStudentList;
    }

}
