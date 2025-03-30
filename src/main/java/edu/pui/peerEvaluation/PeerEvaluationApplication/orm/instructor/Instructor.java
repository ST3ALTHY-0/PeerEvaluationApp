package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor;

import java.util.List;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "instructor")
@ToString(exclude = {"projects"})
public class Instructor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer instructorId;

    private String instructorEmail;

    private String puid;

    // @OneToMany(mappedBy = "instructor")
    // private List<MyClass> classes;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Project> projects;

}
