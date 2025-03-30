package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
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
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "group_category")
@EqualsAndHashCode(exclude = {"evaluations"})
@ToString(exclude = {"myClass", "projectGroups", "evaluations"})
public class GroupCategory {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupCategoryId;
 
    // @ManyToOne
    // @JoinColumn(name = "projectId", nullable = false)
    // private Project project;

    @ManyToOne
    @JoinColumn(name = "classId", nullable = false)
    private MyClass myClass;

    // @ManyToMany(mappedBy = "groupCategories")
    // private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "groupCategory", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations = new ArrayList<>();

    @OneToMany(mappedBy = "groupCategory", cascade = CascadeType.ALL)
    private List<ProjectGroup> projectGroups;


}
