package edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.project.Project;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "project_group")
public class ProjectGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int group_id;
    
    private String group_name;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;



    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Feedback> grades;

    @ManyToMany(mappedBy = "groups")
    private Set<Student> students = new HashSet<>();

    //maybe there should be Project Table that this Table references
    //Project Table could contain Project_id, Project_name, points_worth, class(foreign key)
}
