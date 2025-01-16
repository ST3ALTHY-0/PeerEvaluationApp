package edu.pui.peerEvaluation.Peerevualuationapplication.orm.project;

import java.util.List;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;
import jakarta.persistence.CascadeType;
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

@Data
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int project_id;

    private String project_name;

    private int points_worth;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private MyClass aClass;


    //this class is referenced by
    //project_group
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectGroup> project_groups;

    //evaluation 
     @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Evaluation evaluation;
}


