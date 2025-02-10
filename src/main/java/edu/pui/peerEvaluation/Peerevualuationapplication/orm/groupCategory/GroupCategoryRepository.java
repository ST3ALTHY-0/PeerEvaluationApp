
package edu.pui.peerEvaluation.Peerevualuationapplication.orm.groupCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupCategoryRepository extends JpaRepository<GroupCategory, Integer> {

    @Query("SELECT gc FROM GroupCategory gc " +
           "JOIN gc.myClass mc " +
           "JOIN mc.instructor i " +
           "WHERE i.instructorId = :instructorId")
    List<GroupCategory> findAllByInstructorId(@Param("instructorId") Integer instructorId);


}

