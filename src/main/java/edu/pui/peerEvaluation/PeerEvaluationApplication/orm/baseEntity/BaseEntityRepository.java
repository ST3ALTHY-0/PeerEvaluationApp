package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

//Base Repository class, we make this because we need a generic repository class for our base service class to use.
@NoRepositoryBean
public interface BaseEntityRepository<T, ID> extends JpaRepository<T, ID> {

}
