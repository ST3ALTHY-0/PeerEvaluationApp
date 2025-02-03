package edu.pui.peerEvaluation.Peerevualuationapplication.orm.projectGroup;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;

@Repository
public interface ProjectGroupRepository extends JpaRepository<ProjectGroup, Integer> {

    // Write the necessary queries and functions that will be needed

    @Query("SELECT pg FROM ProjectGroup pg WHERE pg.groupId = :projectGroupId")
    public <Optional> ProjectGroup findByProjectId(int projectGroupId);

    // @Query("SELECT group_id FROM ProjectGroup pg WHERE pg.group_name = :grou")

    // @Query("SELECT s FROM ProjectGroup pg JOIN pg.students s WHERE pg.group_id =
    // :projectGroupId")

    // @Query("SELECT students FROM ProjectGroup pg JOIN pg.students s WHERE
    // pg.group_id = :projectGroupId")

    @Query("SELECT pg FROM ProjectGroup pg " +
    "JOIN pg.project p " +
    "JOIN pg.students s " +
    "JOIN Evaluation e ON e.project.projectId = p.projectId " +
    "WHERE e.evaluationId = :evaluationId " +
    "AND s.studentId = :studentId")
ProjectGroup findByEvaluationIdAndStudentId(@Param("evaluationId") Integer evaluationId, 
                                         @Param("studentId") Integer studentId);


}
