package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceEnrollment;

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
public class BrightSpaceClassService {

    public MyClass switchClassDataTypeInstructor(BrightSpaceEnrollment brightSpaceEnrollment, Instructor instructor) {
        MyClass myClass = new MyClass();
        myClass.setClass_name(brightSpaceEnrollment.getOrgUnit().getName());
        myClass.setClass_code(brightSpaceEnrollment.getOrgUnit().getCode());
        myClass.setInstructor(instructor);
        return myClass;
    }

      //I dont think a student version is necessary because we only need to save classes from instructor side
    // public MyClass switchClassDataTypeStudent(BrightSpaceEnrollment brightSpaceEnrollment, Instructor instructor) {
    //     MyClass myClass = new MyClass();
    //     myClass.setClass_name(brightSpaceEnrollment.getOrgUnit().getName());
    //     myClass.setClass_code(brightSpaceEnrollment.getOrgUnit().getCode());
    //     myClass.setInstructor(instructor);
    //     return myClass;
    // }


}
