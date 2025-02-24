package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;

@Repository
public interface ProjectGroupRepository extends JpaRepository<ProjectGroup, Integer> {

    // Write the necessary queries and functions that will be needed

    @Query("SELECT pg FROM ProjectGroup pg WHERE pg.groupId = :projectGroupId")
    public Optional<ProjectGroup> findByProjectId(int projectGroupId);

//     @Query("SELECT pg FROM ProjectGroup pg " +
//             "JOIN pg.students s " +
//             "JOIN pg.groupCategory gc " +
//             "JOIN gc.evaluation e " +
//             "JOIN gc.project p " +
//             "WHERE e.evaluationId = :evaluationId " +
//             "AND s.studentId = :studentId " +
//             "AND p.projectId = :projectId")
//     Optional<ProjectGroup> findByEvaluationIdAndStudentIdAndProjectId(@Param("evaluationId") Integer evaluationId,
//             @Param("studentId") Integer studentId,
//             @Param("projectId") Integer projectId);

            @Query("SELECT pg FROM ProjectGroup pg " +
            "JOIN pg.students s " +
            "JOIN pg.groupCategory gc " +
            "WHERE element(gc.evaluations).evaluationId = :evaluationId " +
            "AND s.studentId = :studentId")
     Optional<ProjectGroup> findProjectGroupByEvaluationIdAndStudentId(@Param("evaluationId") Integer evaluationId, 
                                                                       @Param("studentId") Integer studentId);

    @Query("SELECT pg FROM ProjectGroup pg JOIN pg.students s WHERE s.studentId = :studentId")
    List<ProjectGroup> findAllByStudentId(@Param("studentId") Integer studentId);

    @Query("SELECT pg FROM ProjectGroup pg WHERE groupName = :groupName AND project = :project")
    Optional<ProjectGroup> findByGroupNameAndProject(String groupName, Project project);

}
