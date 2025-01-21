package edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor;

import java.util.List;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.myClass.MyClass;
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

@Data
@Entity
@Table(name = "instructor")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int instructor_id;

    private String instructor_name;

    private String instructor_email;

    // @Version
    // private int version;

    @OneToMany(mappedBy = "instructor")
    private List<MyClass> classes;

    @OneToMany(mappedBy = "instructor")
    private List<Evaluation> evaluations;
}
