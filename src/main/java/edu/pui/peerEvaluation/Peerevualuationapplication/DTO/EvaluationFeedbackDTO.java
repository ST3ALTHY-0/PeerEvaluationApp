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
    private Integer evaluationId;
    private Integer ratedByStudentId;
    private Integer ratedStudentId;
    private Integer grade;
    private Integer projectGroupId;
    private Integer projectId;
    private List<ResponseDTO> responses;

}
// @RequestParam("evaluationId") Integer evaluationId,
//             @RequestParam("evaluatorId") Integer ratingStudentId,
//             @RequestParam("ratedStudentId") Integer ratedStudentId,
//             @RequestParam("projectGroupId") Integer projectGroupId,
//             @ModelAttribute("responses") Map<Integer, List<ResponseDTO>> responses