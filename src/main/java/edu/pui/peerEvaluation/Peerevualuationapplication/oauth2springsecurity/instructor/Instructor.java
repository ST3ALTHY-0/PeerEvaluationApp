package edu.pui.peerEvaluation.Peerevualuationapplication.oauth2springsecurity.instructor;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Instructor {

    //create fields for whatever data we need to get from brightSpace api, like class list, project list, student list, name, email, etc
    private String name;

    private String email;

    //private String department;


    // private List<Class> classes;

    // private Map<Class, Project> projects;

    // private Map<Class, Student> students;

    // private Map<Project, Group> projectGroups;

    // private Map<Group, Student> groupMembership;
    
}
