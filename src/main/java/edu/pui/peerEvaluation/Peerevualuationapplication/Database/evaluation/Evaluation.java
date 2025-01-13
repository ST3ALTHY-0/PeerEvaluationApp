package edu.pui.peerEvaluation.Peerevualuationapplication.Database.evaluation;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pui.peerEvaluation.Peerevualuationapplication.Database.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.Peerevualuationapplication.Database.instructor.Instructor;
import edu.pui.peerEvaluation.Peerevualuationapplication.Database.project.Project;
import edu.pui.peerEvaluation.Peerevualuationapplication.Database.student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "evaluation")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evaluation_id;

    private LocalDateTime date_completed;
    
    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "instructor_id")
    private Instructor instructor;

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    @OneToMany(mappedBy = "evaluation")
    private List<EvaluationQuestion> evaluation_questions;

    @ManyToMany(mappedBy = "evaluations")
    private Set<Student> students = new HashSet<>();
}
