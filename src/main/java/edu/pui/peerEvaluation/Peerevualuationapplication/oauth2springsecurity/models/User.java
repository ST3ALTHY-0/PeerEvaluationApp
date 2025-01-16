package edu.pui.peerEvaluation.Peerevualuationapplication.oauth2springsecurity.models;

import lombok.Data;

@Data //generates nice stuff for you, like getters, setters, constructors, equalsTo, etc
public class User {
    private String name;
    private String email;

}
