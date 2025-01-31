package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "evaluation_question")
public class EvaluationQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;

    private String questionText;

    private boolean enforceAnswer; // not null

    @ManyToOne
    @JoinColumn(name = "evaluationId", referencedColumnName = "evaluationId")
    private Evaluation evaluation;

    @OneToOne(mappedBy = "question")
    private EvaluationResponse response;
}
