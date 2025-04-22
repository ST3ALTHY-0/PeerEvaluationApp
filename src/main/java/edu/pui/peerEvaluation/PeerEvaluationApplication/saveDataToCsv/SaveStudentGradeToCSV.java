package edu.pui.peerEvaluation.PeerEvaluationApplication.saveDataToCsv;

import com.opencsv.CSVWriter;

import edu.pui.peerEvaluation.PeerEvaluationApplication.brightSpaceSCVParser.SaveBrightSpaceData;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade.StudentGrade;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.studentGrade.StudentGradeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SaveStudentGradeToCSV {

    private final EvaluationService evaluationService;
    private final StudentService studentService;
    private final StudentGradeService studentGradeService;

        private static final Logger logger = LoggerFactory.getLogger(SaveStudentGradeToCSV.class);


    public SaveStudentGradeToCSV(EvaluationService evaluationService, StudentService studentService, StudentGradeService studentGradeService){
        this.evaluationService = evaluationService;
        this.studentService = studentService;
        this.studentGradeService = studentGradeService;
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

    //calculate average grade student received from their peers
public String calculateAverageGrade(Integer studentId, Integer evaluationId) {
    
    //check if this student graded their fellow students, if they didn't give a 0
    List<Feedback> studentGivenFeedbacks = evaluationService.findFeedbacksByStudentInEvaluation(studentId, evaluationId)
                                                 .stream()
                                                 .filter(f -> f.getGradePercent() != null)
                                                 .toList();
    if(studentGivenFeedbacks.size() == 0){
        return "0";
    }
    
    //get feedback from other students and filter out any where another student didnt rate the student we are grading, thus not affecting this students grade
    List<Feedback> feedbacks = evaluationService.findFeedbacksForStudentInEvaluation(studentId, evaluationId)
                                                .stream()
                                                .filter(f -> f.getGradePercent() != null)
                                                .toList();

    Student student = studentService.findById(studentId).orElse(null);
    logger.info("Saving Data, Feedbacks: {}  for student: {}", feedbacks, student.getStudentEmail());

    

    return feedbacks.isEmpty() 
        ? "100" //if feedbacks is empty (no one responded to this student, but this student responded to others)
        : String.valueOf(feedbacks.stream()//else find the average grade from the received feedbacks
        .mapToDouble(Feedback::getGradePercent)
        .average()
        .orElse(10));
}

//calculate final grade from the evaluation grade that the student received from their peers and the
//grade that the student originally received on their project/assignment
public Integer calculateFinalGrade(Integer studentId, Integer evaluationId, String averageGrade){
    Evaluation evaluation = evaluationService.findById(evaluationId).get();
    Integer projectId = evaluation.getProject().getProjectId();

    Double evaluationGrade = Double.valueOf(averageGrade);
    StudentGrade studentGrade = studentGradeService.findByStudentIdAndProjectId(studentId, projectId);

    Integer finalGrade = (int) Math.round(studentGrade.getGrade() * (evaluationGrade / 100));

    return finalGrade;
}

}
