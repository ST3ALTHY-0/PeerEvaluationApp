package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi;

import org.springframework.stereotype.Service;
//after we get a brightspace users details from APIBrightSpaceUserService and store the retrieved data in the BrightSpaceUser class
//we will then get the enrollment of a user
//then we will check which roles the user is assigned to and use that to assign them as instructor or student
//for students, we do not need to store the class data, just check if they are already in our database because there is a instructor that has created them in our db
//for instructors, we will get the class list of each of their enrollments, and maybe some other details in the background like groups and projects for those classes

@Service
public class BrightSpaceAPIService {

}