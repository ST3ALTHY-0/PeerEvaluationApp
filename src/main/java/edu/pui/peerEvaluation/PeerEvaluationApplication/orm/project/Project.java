package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project;

import java.util.List;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade.StudentGrade;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "project")
@EqualsAndHashCode(exclude = {"myClass", "evaluation"})
@ToString(exclude = {"projectGroups", "feedbacks", "myClass", "instructor"})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    private String projectName;

    private String fullProjectName;


    private Integer pointsWorth;


    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Evaluation evaluation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "classId", referencedColumnName = "classId")
    private MyClass myClass;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructorId", referencedColumnName = "instructorId")
    private Instructor instructor;

    // evaluation
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

     @OneToMany(mappedBy = "project")
    private List<StudentGrade> studentGrades;

    @OneToMany(mappedBy = "project")
    private List<ProjectGroup> projectGroups;
    
    //TODO:
    /* 
    @OneToMany(mappedBy = "project")
    private List<GroupCategory> groupCategories;
     */
}
