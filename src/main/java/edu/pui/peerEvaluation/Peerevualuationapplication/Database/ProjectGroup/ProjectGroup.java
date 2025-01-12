package edu.pui.peerEvaluation.Peerevualuationapplication.Database.ProjectGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pui.peerEvaluation.Peerevualuationapplication.Database.Class.Class;
import edu.pui.peerEvaluation.Peerevualuationapplication.Database.Grade.Grade;
import edu.pui.peerEvaluation.Peerevualuationapplication.Database.Student.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "project_group")
public class ProjectGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int group_id;
    
    private String group_name;

     @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class aClass;

    @OneToMany(mappedBy = "project_group", cascade = CascadeType.ALL)
    private List<Grade> grades;

    @ManyToMany(mappedBy = "groups")
    private Set<Student> students = new HashSet<>();

    //maybe there should be Project Table that this Table references
    //Project Table could contain Project_id, Project_name, points_worth, class(foreign key)

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Set<Student> getStudents(){
        return students;
    }

    public void setStudents(Set<Student> students){
        this.students = students;
    }
}
