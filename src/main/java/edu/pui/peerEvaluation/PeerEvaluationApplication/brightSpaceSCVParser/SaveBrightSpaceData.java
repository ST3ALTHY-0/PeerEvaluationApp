package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade.StudentGrade;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade.StudentGradeService;

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

    public MyClass saveClassAndProjectToDB(Project project, String classCode){
        MyClass myClass = myClassService.findByClassCode(classCode);

        project.setMyClass(myClass);
        projectService.addProject(project);

        return myClass;
    }

     public void saveCSVDataToDB(List<CSVDataDTO> csvDataDTOList, MyClass myClass) {    

        //TODO: check that gc isnt already saved in db
        GroupCategory groupCategory = new GroupCategory();
        groupCategory.setMyClass(myClass);
        groupCategoryService.addGroupCategory(groupCategory);

        Map<String, ProjectGroup> projectGroupMap = new HashMap<>();
    
        for (CSVDataDTO csvDataDTO : csvDataDTOList) {

            //check that student isnt allready in db
            studentService.addStudent(csvDataDTO.getStudent());

            
            String groupName = csvDataDTO.getGroup();
            Project project = csvDataDTO.getProject();
    
            ProjectGroup projectGroup = projectGroupMap.get(groupName);
            if (projectGroup == null) {
                System.out.println("Project Group:" + csvDataDTO.getGroup() + " created.");
                logger.debug("Project Group:" + csvDataDTO.getGroup() + " created.");
                projectGroup = new ProjectGroup();
                projectGroup.setGroupName(groupName);
                projectGroup.setProject(project);
                projectGroup.setStudents(new ArrayList<>());
                projectGroupMap.put(groupName, projectGroup);
            }
    
            System.out.println("Student: " + csvDataDTO.getStudent().getStudentEmail() + " added to group: " + csvDataDTO.getGroup());
            logger.debug("Student: " + csvDataDTO.getStudent().getStudentEmail() + " added to group: " + csvDataDTO.getGroup());
            projectGroup.getStudents().add(csvDataDTO.getStudent());
            projectGroup.setGroupCategory(groupCategory);
            projectGroupService.addProjectGroup(projectGroup);

            StudentGrade studentGrade = new StudentGrade();
            studentGrade.setProject(csvDataDTO.getProject());
            studentGrade.setGrade(csvDataDTO.getStudentGrade());
            studentGrade.setStudent(csvDataDTO.getStudent());
    
            studentGradeService.addStudentGrade(studentGrade);
        }

    }
    
}

