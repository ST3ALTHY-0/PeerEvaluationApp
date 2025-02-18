package edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceApi.brightSpaceUser;

import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;

//after we get a brightspace users details from APIBrightSpaceUserService and store the retrieved data in the BrightSpaceUser class
//we then use this class to retrieve t
@Service
public class BrightSpaceUserService {

    //maybe brightSpaceUserExtended should extend BrightSpaceUser, but im not sure because the order of which we have to call data goes, user then userExtended

    public Student switchStudentUserDataType(BrightSpaceUser brightSpaceUser, BrightSpaceUserExtended brightSpaceUserExtended) {
        Student student = new Student();
        student.setStudentEmail(brightSpaceUserExtended.getEmail());
        student.setStudentName(brightSpaceUser.getFirstName() + " " + brightSpaceUser.getLastName());
        return student;
    }

    public Instructor switchInstructorUserDataType(BrightSpaceUser brightSpaceUser, BrightSpaceUserExtended brightSpaceUserExtended) {
        Instructor instructor = new Instructor();
        instructor.setInstructorEmail(brightSpaceUserExtended.getEmail());
        instructor.setInstructorName(brightSpaceUser.getFirstName() + " " + brightSpaceUser.getLastName());
        return instructor;
    }

}
