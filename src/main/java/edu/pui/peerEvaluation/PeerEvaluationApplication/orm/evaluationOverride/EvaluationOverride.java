package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationOverride;

import java.time.LocalDateTime;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "evaluation_override")
@Data
public class EvaluationOverride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer evaluationOverrideId;

    private LocalDateTime extendedDeadline;

   @ManyToOne
    @JoinColumn(name = "evaluationId")
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    
}
