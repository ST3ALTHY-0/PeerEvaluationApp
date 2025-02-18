package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationOverride.EvaluationOverride;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade.StudentGrade;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    private String studentName;
    private String studentEmail;
    private String puid;
    private Boolean overrideAccessToEvaluation; //if an instructor wants to open a eval for a singular person after an eval is finished for everyone else we can override access here
    
    @OneToMany(mappedBy = "ratedByStudent", cascade = CascadeType.ALL)
    private List<Feedback> givenGrades;

    @OneToMany(mappedBy = "ratedStudent", cascade = CascadeType.ALL)
    private List<Feedback> receivedGrades;

    @ManyToMany
    @JoinTable(name = "groupMembership", joinColumns = @JoinColumn(name = "studentId"), inverseJoinColumns = @JoinColumn(name = "groupId"))
    private List<ProjectGroup> groups;

    @OneToMany(mappedBy = "student")
    private List<StudentGrade> studentGrades;

    @OneToMany(mappedBy = "student")
    private List<EvaluationOverride> evaluationOverrides;
}
