package edu.pui.peerEvaluation.Peerevualuationapplication.Database.myClass;

import java.util.List;

import edu.pui.peerEvaluation.Peerevualuationapplication.Database.instructor.Instructor;
import edu.pui.peerEvaluation.Peerevualuationapplication.Database.project.Project;
import edu.pui.peerEvaluation.Peerevualuationapplication.Database.projectGroup.ProjectGroup;
import jakarta.persistence.CascadeType;
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
@Table(name = "class")
public class MyClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String class_id;

    private String class_name;

    //manyToOne means that many classes can have the same instructor
    @ManyToOne
    //JoinColumn means that the instructor will be related to classes via instructor_id (primary key)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;


    @OneToMany(mappedBy = "aClass", cascade = CascadeType.ALL) 
    private List<Project> projects;

}
