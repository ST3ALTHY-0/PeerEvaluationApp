package edu.pui.peerEvaluation.Peerevualuationapplication.Database.projectGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectGroupRepository extends JpaRepository<ProjectGroup, Integer>{
    
    //Write the necessary queries and functions that will be needed
    
}
