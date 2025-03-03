package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion;

import java.util.List;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

//this is perhaps a bad way of making this class,
//questions should be reusable, but im not sure they are rn
@Data
@Entity
@Table(name = "evaluation_question")
@EqualsAndHashCode(exclude = {"evaluation", "responses"})
public class EvaluationQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    private String questionText;

    private boolean enforceAnswer; // not null

    @ManyToOne
    @JoinColumn(name = "evaluationId", referencedColumnName = "evaluationId")
    private Evaluation evaluation;

    @OneToMany(mappedBy = "question")
    private List<EvaluationResponse>  responses;
}
