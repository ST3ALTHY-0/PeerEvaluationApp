package edu.pui.peerEvaluation.Peerevualuationapplication.orm.student;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int student_id;

    private String student_name;
    private String student_email;

    @OneToMany(mappedBy = "rated_by_student", cascade = CascadeType.ALL)
    private List<Feedback> given_grades;

    @OneToMany(mappedBy = "rated_student", cascade = CascadeType.ALL)
    private List<Feedback> received_grades;

    @ManyToMany
    @JoinTable(name = "group_membership", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<ProjectGroup> groups = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "evaluation_student", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "evaluation_id"))
    private Set<Evaluation> evaluations = new HashSet<>();

    // another join table for class_student

}
