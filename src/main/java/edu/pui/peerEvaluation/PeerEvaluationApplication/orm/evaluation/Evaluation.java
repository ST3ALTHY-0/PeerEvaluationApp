package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationOverride.EvaluationOverride;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory.GroupCategory;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "evaluation")
@EqualsAndHashCode(exclude = { "evaluationQuestions", "project", "groupCategories, evaluationOverrides" })
@ToString(exclude = { "project", "groupCategories", "feedbacks", "evaluationQuestions, evaluationOverrides" })
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer evaluationId;

    private LocalDateTime createdAt;

    private boolean isComplete;

    private boolean allowStudentsToViewFeedback;

    private boolean isGraded;

    private LocalDateTime dueDate;

    @OneToOne
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    private Project project;

    @ManyToMany
@JoinTable(
    name = "evaluation_question_mapping", // Name of the join table
    joinColumns = @JoinColumn(name = "evaluationId", referencedColumnName = "evaluationId"), // Column for Evaluation
    inverseJoinColumns = @JoinColumn(name = "questionId", referencedColumnName = "questionId") // Column for EvaluationQuestion
)
private List<EvaluationQuestion> evaluationQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();;

    // @ManyToMany
    // @JoinTable(
    // name = "group_category_evaluations",
    // joinColumns = @JoinColumn(name = "evaluation_id"),
    // inverseJoinColumns = @JoinColumn(name = "group_category_id")
    // )
    // private Set<GroupCategory> groupCategories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "groupCategoryId", nullable = false)
    private GroupCategory groupCategory;

    @OneToMany(mappedBy = "evaluation")
    private List<EvaluationOverride> evaluationOverrides;

    /*
     * Select e from Evaluation e Join project p on
     * 
     */
}
