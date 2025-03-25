package edu.pui.peerEvaluation.PeerEvaluationApplication.saveDataToCsv;

import com.opencsv.CSVWriter;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SaveDataToCSV {

    private final EvaluationService evaluationService;

    public SaveDataToCSV(EvaluationService evaluationService){
        this.evaluationService = evaluationService;
    }

    public byte[] generateCSV(List<String[]> data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);

         // Customize the CSV writer to avoid enclosing strings in quotation marks
         CSVWriter csvWriter = new CSVWriter(outputStreamWriter,
         CSVWriter.DEFAULT_SEPARATOR,
         CSVWriter.NO_QUOTE_CHARACTER,
         CSVWriter.DEFAULT_ESCAPE_CHARACTER,
         CSVWriter.DEFAULT_LINE_END);

        for (String[] record : data) {
            csvWriter.writeNext(record);
        }

        csvWriter.flush();
        csvWriter.close();

        return byteArrayOutputStream.toByteArray();
    }

    
public String calculateAverageGrade(Integer studentId, Integer evaluationId) {
    
    //check if this student graded their fellow students, if they didn't give a 0
    List<Feedback> studentGivenFeedbacks = evaluationService.findFeedbacksByStudentInEvaluation(studentId, evaluationId)
                                                 .stream()
                                                 .filter(f -> f.getGradePercent() != null)
                                                 .toList();
    if(studentGivenFeedbacks.size() == 0){
        return "0";
    }
    
    //get feedback from other students and filter out any 
    List<Feedback> feedbacks = evaluationService.findFeedbacksForStudentInEvaluation(studentId, evaluationId)
                                                .stream()
                                                .filter(f -> f.getGradePercent() != null)
                                                .toList();

    return feedbacks.isEmpty() 
        ? "10" //if feedbacks is empty (no one responded to this student, but this student responded to others)
        : String.valueOf(feedbacks.stream()
        .mapToDouble(Feedback::getGradePercent)
        .average()
        .orElse(10));
}

}
