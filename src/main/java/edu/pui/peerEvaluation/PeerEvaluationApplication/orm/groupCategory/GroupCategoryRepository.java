
package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.groupCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;

public interface GroupCategoryRepository extends BaseEntityRepository<GroupCategory, Integer> {

    @Query("SELECT gc FROM GroupCategory gc " +
           "JOIN gc.myClass mc " +
           "JOIN mc.instructor i " +
           "WHERE i.instructorId = :instructorId")
    List<GroupCategory> findAllByInstructorId(@Param("instructorId") Integer instructorId);


}

