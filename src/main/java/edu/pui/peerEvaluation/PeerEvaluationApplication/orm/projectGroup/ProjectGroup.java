package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "project_group")
public class ProjectGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupId;

    private String groupName;

    @ManyToOne
    @JoinColumn(name = "groupCategoryId")
    private GroupCategory groupCategory;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Feedback> grades;

    @ManyToMany(mappedBy = "groups")
    private List<Student> students;

}
