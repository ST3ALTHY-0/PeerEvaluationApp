package edu.pui.peerEvaluation.Peerevualuationapplication;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationFeedbackDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationFeedbackFormDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.ResponseDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class EvaluationControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationControllerTest.class);

    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSubmitFeedback() throws Exception {
        // Create mock feedback data
        EvaluationFeedbackFormDTO formDTO = new EvaluationFeedbackFormDTO();
        
        List<EvaluationFeedbackDTO> feedbackList = new ArrayList<>();
        EvaluationFeedbackDTO feedback1 = new EvaluationFeedbackDTO();
        feedback1.setEvaluationId(1);
        feedback1.setRatedByStudentId(1);
        feedback1.setRatedStudentId(2);
        feedback1.setProjectGroupId(1);
        
        List<ResponseDTO> responses = new ArrayList<>();
        ResponseDTO response1 = new ResponseDTO();
        response1.setQuestionId(1);
        response1.setResponse("Good teamwork");
        responses.add(response1);
        
        feedback1.setResponses(responses);
        feedbackList.add(feedback1);
        
        formDTO.setEvaluationFeedbackDTOList(feedbackList);

        logger.debug("Submitting feedback: {}", formDTO);


        // Perform a POST request
        mockMvc.perform(post("/evaluation/submit/feedback") //this seems to be working
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("evaluationFeedbackDTOList[0].evaluationId", "1")
                .param("evaluationFeedbackDTOList[0].ratedByStudentId", "1")
                .param("evaluationFeedbackDTOList[0].ratedStudentId", "2")
                .param("evaluationFeedbackDTOList[0].projectGroupId", "1")
                .param("evaluationFeedbackDTOList[0].responses[0].questionId", "1")
                .param("evaluationFeedbackDTOList[0].responses[0].response", "Good teamwork")
                .with(csrf())) // If CSRF protection is enabled
                .andExpect(status().is3xxRedirection()) // Expect HTTP 302 status
                .andExpect(redirectedUrl("/student/feedback/finished")); // Expect redirection URL
    }

    
}