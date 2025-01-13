package edu.pui.peerEvaluation.Peerevualuationapplication.Database.Grade;

import edu.pui.peerEvaluation.Peerevualuationapplication.Database.ProjectGroup.ProjectGroup;
import edu.pui.peerEvaluation.Peerevualuationapplication.Database.Student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // lets db auto assign id
    private int grade_id;

    private int grade_percentage;

    @ManyToOne
    @JoinColumn(name = "rated_by_student_id", nullable = false)
    private Student ratedBy;

    @ManyToOne
    @JoinColumn(name = "rated_student_id", nullable = false)
    private Student rated;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private ProjectGroup project_group;

    public int getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id = grade_id;
    }

    public int getGrade_percentage() {
        return grade_percentage;
    }

    public void setGrade_percentage(int grade_percentage) {
        this.grade_percentage = grade_percentage;
    }

    public Student getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(Student ratedBy) {
        this.ratedBy = ratedBy;
    }

    public Student getRated() {
        return rated;
    }

    public void setRated(Student rated) {
        this.rated = rated;
    }

    public ProjectGroup getGroup() {
        return project_group;
    }

    public void setGroup(ProjectGroup group) {
        this.project_group = group;
    }

}
