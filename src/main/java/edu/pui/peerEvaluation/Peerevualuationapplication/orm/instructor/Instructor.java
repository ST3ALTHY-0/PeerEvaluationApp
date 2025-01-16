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

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<MyClass> classes;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;
}
