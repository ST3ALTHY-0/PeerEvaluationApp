package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.ToString;

@Data
@Entity
@Table(name = "evaluation_response")
//unless this breaks something, keep these commented out so we can view the question and the feedback when we pass a evaluationResponse
// @EqualsAndHashCode(exclude = {"question", "feedback"})
// @ToString(exclude = { "question", "feedback" })
public class EvaluationResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer responseId;

    private String responseText;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "questionId", referencedColumnName = "questionId")
    private EvaluationQuestion question;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "feedbackId", referencedColumnName = "feedbackId")
    private Feedback feedback;
}
