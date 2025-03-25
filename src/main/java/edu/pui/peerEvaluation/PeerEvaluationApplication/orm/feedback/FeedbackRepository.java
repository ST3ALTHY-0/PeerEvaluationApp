package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;

public interface FeedbackRepository extends BaseEntityRepository<Feedback, Integer> {


    //find the avg grade received

}
