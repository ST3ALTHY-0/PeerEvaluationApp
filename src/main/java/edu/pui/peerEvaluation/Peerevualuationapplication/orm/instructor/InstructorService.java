package edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public Instructor addInstructor(Instructor instructor) {
        // do any error handling and logging here
        instructorRepository.saveAndFlush(instructor);
        return instructor;
    }

    public Instructor findInstructorByEmail(String email) {
        return instructorRepository.findByEmail(email).orElse(null);
    }

}

// I really need to see what sort of information is available when using
// brightSpace API

// when instructor hits submit on form, some amount of data should be added to
// DB,
// firstly the class data should be entered into Class table in DB
// then some student data should be added to Student Table if possible (maybe
// email cant be added yet)
// then project_group data should be added to Project_group Table
// then group_membership data should be added to Group_membership Table