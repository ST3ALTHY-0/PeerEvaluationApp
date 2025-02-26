package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseEntityRepository<T, ID> extends JpaRepository<T, ID> {

}
