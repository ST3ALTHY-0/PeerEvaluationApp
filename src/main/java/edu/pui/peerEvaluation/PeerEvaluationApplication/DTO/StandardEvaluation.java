package edu.pui.peerEvaluation.PeerEvaluationApplication.DTO;

import java.util.ArrayList;
import java.util.List;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StandardEvaluation {

    public static final String[] QUESTIONS = {
        "How would you describe this team member’s overall contribution to the project?",
        "How reliable and dependable was this person throughout the project?",
        "Is there anything else you’d like to share about your experience working with this team member?"
    };

    private List<EvaluationQuestion> evaluationQuestions;
    private Boolean isGraded;

    public StandardEvaluation() {
        this.evaluationQuestions = new ArrayList<>();
        this.isGraded = true;

        for (int i = 0; i < QUESTIONS.length; i++) {
            EvaluationQuestion question = new EvaluationQuestion();
            question.setQuestionText(QUESTIONS[i]);
            question.setEnforceAnswer(i != 2); // Enforce answer for the first two questions
            this.evaluationQuestions.add(question);
        }
    }

}