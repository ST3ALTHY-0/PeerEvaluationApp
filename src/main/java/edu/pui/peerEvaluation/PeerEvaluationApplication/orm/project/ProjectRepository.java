package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
