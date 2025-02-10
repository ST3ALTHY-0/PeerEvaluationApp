package edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback;

import java.time.LocalDateTime;
import java.util.List;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.project.Project;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    private int gradePercent;

    private LocalDateTime dateCompleted;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "evaluationId", nullable = false)
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "ratedByStudentId", referencedColumnName = "studentId")
    private Student ratedByStudent;

    @ManyToOne
    @JoinColumn(name = "ratedStudentId", referencedColumnName = "studentId")
    private Student ratedStudent;

    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "groupId")
    private ProjectGroup group;

    // evaluation response
    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationResponse> responses;
}
