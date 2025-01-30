package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//after we get a brightspace users details from APIBrightSpaceUserService and store the retrieved data in the BrightSpaceUser class
//we will then get the enrollment of a user
//then we will check which roles the user is assigned to and use that to assign them as instructor or student
//for students, we do not need to store the class data, just check if they are already in our database because there is a instructor that has created them in our db
//for instructors, we will get the class list of each of their enrollments, and maybe some other details in the background like groups and projects for those classes

import edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceUser.APIBrightSpaceUserService;
import edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceUser.BrightSpaceUser;
import edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceUser.BrightSpaceUserExtended;
import edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpaceUser.BrightSpaceUserService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;

@Service
public class BrightSpaceAPIService {

    private final BrightSpaceUserService brightSpaceUserService;
    private final APIBrightSpaceUserService apiBrightSpaceUserService;

    @Autowired
    public BrightSpaceAPIService(BrightSpaceUserService brightSpaceUserService, APIBrightSpaceUserService apiBrightSpaceUserService){
        this.brightSpaceUserService = brightSpaceUserService;
        this.apiBrightSpaceUserService = apiBrightSpaceUserService;
    }

    //add any error logic here

    public BrightSpaceUser getBasicUserData(String accessToken){
        return apiBrightSpaceUserService.callWhoamiEndpoint(accessToken);
    }

    public BrightSpaceUserExtended getExtendedUserData(String accessToken, String userId){
        return apiBrightSpaceUserService.callUserIdEndpoint(accessToken, userId);
    }

    public Instructor createOrmInstructor(BrightSpaceUser brightSpaceUser, BrightSpaceUserExtended brightSpaceUserExtended){
        return brightSpaceUserService.switchInstructorUserDataType(brightSpaceUser, brightSpaceUserExtended);
    }

    public Student createOrmStudent(BrightSpaceUser brightSpaceUser, BrightSpaceUserExtended brightSpaceUserExtended){
        return brightSpaceUserService.switchStudentUserDataType(brightSpaceUser, brightSpaceUserExtended);

    }



}