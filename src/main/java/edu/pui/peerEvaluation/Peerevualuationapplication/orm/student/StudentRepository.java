package edu.pui.peerEvaluation.Peerevualuationapplication.orm.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.project.Project;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup.ProjectGroup;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT s FROM Student s WHERE s.student_email = :email")
    Optional<Student> findByEmail(String email);
    

    // Write the necessary queries and functions that will be needed

    // when a instructor wants to make a new evaluation

    // save instructor
    // save class
    // save project
    // save students(probably only partial info, though maybe can get student email
    // through classList enrollment)
    // save class students
    // save project groups

    // save evaluationQuestion if different from standard,
    // save evaluation if different from standard
    // save evaluation students

    // when a student logs in and want to complete their evaluation

    // find which student ID they are in my DB, if possible email will be stored
    // when the student is first created and we can check the new logged ined
    // student's email with the student emails in the DB to get their id
    // student selects which project they want to do evaluations for


    // find the evaluation









    //useless maybe

       // @Query("SELECT p, c " +
    // "FROM Project p " +
    // "JOIN p.aClass c " +
    // "JOIN c.classStudents cs " +
    // "WHERE cs.student.id = :studentId")
    // List<Object[]> findProjectsAndClassesByStudentId(@Param("studentId") Integer
    // studentId);

    // find their projectGroup (projectGroup is a join table s)
    // @Query("SELECT pg FROM ProjectGroup pg " +
    // "JOIN pg.students s " +
    // "WHERE pg.project = :project AND s.id = :studentId")
    // Optional<ProjectGroup> findByProjectAndStudent(@Param("project") Project
    // project, @Param("studentId") Integer studentId);
}
