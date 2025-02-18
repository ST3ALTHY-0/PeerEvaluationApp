package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "evaluation_response")
@EqualsAndHashCode(exclude = {"question"})
public class EvaluationResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int responseId;

    private String responseText;

    @OneToOne
    @JoinColumn(name = "questionId", referencedColumnName = "questionId")
    private EvaluationQuestion question;

    @ManyToOne
    @JoinColumn(name = "feedbackId", referencedColumnName = "feedbackId")
    private Feedback feedback;
}
