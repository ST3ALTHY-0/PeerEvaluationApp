package edu.pui.peerEvaluation.Peerevualuationapplication.services;

import org.springframework.stereotype.Service;

@Service
public class InstructorService {
    //the following should probably be done in a @Service class
        //should first check that User is Instructor or Student

        //if Instructor, add all relevant attributes to model (add only needed attributes to DB after instructor actually makes a evaluation form)

        //if Student, add relevant attributes to model and DB (name, email)
        //check DB if the student has any eval forms they need to complete (when eval forms are first created, a list of students should be provided by instructor[and stored in DB], hopefully there is an email associated with the students at the time they are entered via instructor but if not then we should have a name for each student, and once the student signs in we might be able to check with BS api if they have any class/instructors that have made a eval form, and be able to match them that way)

        // Add user name to the model
    public Instructor createInstructor

}
