package edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pui.peerEvaluation.Peerevualuationapplication.DTO.EvaluationFormDTO;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion.EvaluationQuestionRepository;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.evaluationQuestion.EvaluationQuestionService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.myClass.MyClassRepository;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.project.ProjectRepository;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.project.ProjectService;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.Student;
import edu.pui.peerEvaluation.Peerevualuationapplication.orm.student.StudentService;

@Service
public class EvaluationService {

    private final ProjectService projectService;
    private final EvaluationQuestionService evaluationQuestionService;
    private final StudentService studentService;
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(ProjectService projectService, EvaluationQuestionService evaluationQuestionService, StudentService studentService, EvaluationRepository evaluationRepository) {
        this.projectService = projectService;
        this.evaluationQuestionService = evaluationQuestionService;
        this.studentService = studentService;
        this.evaluationRepository = evaluationRepository;
    }

    public Evaluation addEvaluation(Evaluation evaluation) {

        return evaluationRepository.saveAndFlush(evaluation);
    }

    public List<Evaluation> findEvaluationsByStudentId(Integer studentId){
        return evaluationRepository.findEvaluationsByStudentId(studentId);
    }

    public List<Evaluation> findAllByStudentIdAndNoFeedback(Integer studentId){
        return evaluationRepository.findAllByStudentIdAndNoFeedback(studentId);
    }

    public List<Evaluation> findAllByStudentIdWithFeedback(Integer studentId){
        return evaluationRepository.findAllByStudentIdWithFeedback(studentId);
    }

    public List<Evaluation> findAll() {
        return evaluationRepository.findAll();
    }

    public Evaluation findById(int evaluationId) {
        return evaluationRepository.findById(evaluationId).orElse(null);
    }

    public void deleteById(int evaluationId) {
        evaluationRepository.deleteById(evaluationId);
    }

    // convert Eval Form DTO into ORM Evaluation obj for saving in DB
    public Evaluation convertToEntity(EvaluationFormDTO evaluationForm) {
        // set basic attributes
        Evaluation evaluation = new Evaluation();
        evaluation.setProject(projectService.findById(evaluationForm.getProjectId()));
        evaluation.setGraded(evaluationForm.isEnableGrading());
        evaluation.setCreatedAt(LocalDateTime.now());

        // Parse the dueDate as LocalDate and convert to LocalDateTime at the start of the day
        LocalDate dueDate = LocalDate.parse(evaluationForm.getDueDate());
        evaluation.setDueDate(dueDate.atStartOfDay());


        // if the instructor wants to use default form we set the questions to the
        // questions of the first evaluation (default evaluation)
        if (evaluationForm.isUseStandardForm()) {
            String questionText1 = "How would you describe this team member’s overall contribution to the project?";
            String questionText2 = "How reliable and dependable was this person throughout the project?";
            String questionText3 = "Is there anything else you’d like to share about your experience working with this team member?";

            EvaluationQuestion eq1 = new EvaluationQuestion();
            EvaluationQuestion eq2 = new EvaluationQuestion();
            EvaluationQuestion eq3 = new EvaluationQuestion();

            eq1.setQuestionText(questionText1);
            eq1.setEnforceAnswer(true);
            eq2.setQuestionText(questionText2);
            eq2.setEnforceAnswer(true);
            eq3.setQuestionText(questionText3);
            eq3.setEnforceAnswer(false);

            List<EvaluationQuestion> defaultQuestions = new ArrayList<>();
            defaultQuestions.addAll(List.of(eq1, eq2, eq3));
            evaluation.setEvaluationQuestions(defaultQuestions);

        } else {
            List<EvaluationQuestion> evaluationQuestions = evaluationForm.getEvaluationQuestions().stream()
                    .map(dto -> {
                        EvaluationQuestion question = new EvaluationQuestion();
                        question.setQuestionText(dto.getQuestionText());
                        question.setEnforceAnswer(dto.isRequired());
                        return question;
                    })
                    .collect(Collectors.toList());

            evaluation.setEvaluationQuestions(evaluationQuestions);
        }
        return evaluation;
    }

}
