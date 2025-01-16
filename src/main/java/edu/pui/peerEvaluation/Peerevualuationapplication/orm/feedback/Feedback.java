package edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback;

import java.time.LocalDateTime;
import java.util.List;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;
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
    private int feedback_id;

    private int grade_percent;

    private LocalDateTime date_completed;

    @ManyToOne
    @JoinColumn(name = "rated_by_student_id", referencedColumnName = "student_id")
    private Student rated_by_student;

    @ManyToOne
    @JoinColumn(name = "rated_student_id", referencedColumnName = "student_id")
    private Student rated_student;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private ProjectGroup group;

    // evaluation response
    @OneToMany(mappedBy = "feedback")
    private List<EvaluationResponse> responses;
}
