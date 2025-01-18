package edu.pui.peerEvaluation.Peerevualuationapplication.customAuthentication;

public class Roles {
    public static final String INSTRUCTOR = "INSTRUCTOR";
    public static final String STUDENT = "STUDENT";
    public static final String STUDENT_INSTRUCTOR = "STUDENT_INSTRUCTOR"; //for special or less common cases if a user is registered as instructor in some classes and student in others, i.g. a TA
    public static final String ADMIN = "ADMIN"; //for admins, probably not really needed
}
