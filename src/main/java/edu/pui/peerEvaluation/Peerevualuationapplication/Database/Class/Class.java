package edu.pui.peerEvaluation.Peerevualuationapplication.Database.Class;

import java.util.List;

import edu.pui.peerEvaluation.Peerevualuationapplication.Database.Instructor.Instructor;
import edu.pui.peerEvaluation.Peerevualuationapplication.Database.ProjectGroup.ProjectGroup;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "class")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String class_id;

    private String class_name;

    //manyToOne means that many classes can have the same instructor
    @ManyToOne
    //JoinColumn means that the instructor will be related to classes via instructor_id (primary key)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;


    @OneToMany(mappedBy = "aClass", cascade = CascadeType.ALL) 
    private List<ProjectGroup> project_groups;

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<ProjectGroup> getProject_groups() {
        return project_groups;
    }

    public void setProject_groups(List<ProjectGroup> project_groups) {
        this.project_groups = project_groups;
    }


}
