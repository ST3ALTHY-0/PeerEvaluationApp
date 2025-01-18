package edu.pui.peerEvaluation.Peerevualuationapplication.orm.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository){
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback addFeedback(Feedback feedback){
        return feedbackRepository.saveAndFlush(feedback);
    }
}
