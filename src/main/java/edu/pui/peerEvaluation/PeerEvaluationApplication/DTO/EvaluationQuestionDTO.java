package edu.pui.peerEvaluation.PeerEvaluationApplication.DTO;

import lombok.Data;

@Data
public class EvaluationQuestionDTO {

    private String questionText;

    private boolean isRequired;

    private boolean isGeneralQuestion; //A general question is one asked only once at the end of the evaluation, if a question is asked for/about each member, its not general. most questions probably will not be general

}


