package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceUser;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.project.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Service
public class TransformUserIntoORMInstructor {

    // before calling this method check which role the user has
    public Instructor switchInstructorUserDataType(BrightSpaceUser brightSpaceUser) {
        Instructor instructor = new Instructor();
        instructor.setInstructor_email(brightSpaceUser.get);
        instructor.setInstructor_name();
    }

}
