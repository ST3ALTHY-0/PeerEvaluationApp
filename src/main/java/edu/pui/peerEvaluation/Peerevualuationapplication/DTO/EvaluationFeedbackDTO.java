package edu.pui.peerEvaluation.Peerevualuationapplication.DTO;

import java.time.LocalDateTime;
import java.util.List;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class EvaluationFeedbackDTO {

    private LocalDateTime date_completed;

    private Evaluation evaluation;

    private Student rated_by_student;

    private Student rated_student;

    private ProjectGroup group;
 
    private List<EvaluationResponse> responses;

    //private List<RatingResponse> ratingResponses;

}
