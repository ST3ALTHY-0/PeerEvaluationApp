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
    private int studentId;

    private String studentName;
    private String studentEmail;

    @OneToMany(mappedBy = "ratedByStudent", cascade = CascadeType.ALL)
    private List<Feedback> givenGrades;

    @OneToMany(mappedBy = "ratedStudent", cascade = CascadeType.ALL)
    private List<Feedback> receivedGrades;

    @ManyToMany
    @JoinTable(name = "groupMembership", joinColumns = @JoinColumn(name = "studentId"), inverseJoinColumns = @JoinColumn(name = "groupId"))
    private List<ProjectGroup> groups;
}
