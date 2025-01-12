package edu.pui.peerEvaluation.Peerevualuationapplication.Database.Student;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pui.peerEvaluation.Peerevualuationapplication.Database.Grade.Grade;
import edu.pui.peerEvaluation.Peerevualuationapplication.Database.ProjectGroup.ProjectGroup;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int student_id;
    
    private String student_name;
    private String student_email;
    
    @OneToMany(mappedBy = "ratedBy", cascade = CascadeType.ALL)
    private List<Grade> givenGrades;

    @OneToMany(mappedBy = "rated", cascade = CascadeType.ALL)
    private List<Grade> receivedGrades;

    @ManyToMany
    @JoinTable(
        name = "group_membership",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<ProjectGroup> groups = new HashSet<>();

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public List<Grade> getGivenGrades() {
        return givenGrades;
    }

    public void setGivenGrades(List<Grade> givenGrades) {
        this.givenGrades = givenGrades;
    }

    public List<Grade> getReceivedGrades() {
        return receivedGrades;
    }

    public void setReceivedGrades(List<Grade> receivedGrades) {
        this.receivedGrades = receivedGrades;
    }

    public Set<ProjectGroup> getGroups(){
        return groups;
    }

    public void setGroups(Set<ProjectGroup> groups){
        this.groups = groups;
    }
}
