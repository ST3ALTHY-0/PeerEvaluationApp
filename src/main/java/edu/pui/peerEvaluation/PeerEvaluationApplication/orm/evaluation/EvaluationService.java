package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pui.peerEvaluation.PeerEvaluationApplication.DTO.EvaluationFormDTO;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestionRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestionService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClassRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.ProjectRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.ProjectService;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentService;

@Service
public class EvaluationService extends BaseEntityService<Evaluation, Integer> {

    private final ProjectService projectService;
    private final EvaluationQuestionService evaluationQuestionService;
    private final StudentService studentService;
    private final EvaluationRepository evaluationRepository;

        private static final Logger logger = LoggerFactory.getLogger(EvaluationService.class);


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
        return evaluationRepository.findDistinctByGroupCategory_ProjectGroups_Students_StudentId(studentId);
    }

    public List<Evaluation> findAllByStudentIdAndNoFeedback(Integer studentId){
        return evaluationRepository.findDistinctByGroupCategory_ProjectGroups_Students_StudentIdAndFeedbacks_FeedbackIdIsNull(studentId);
    }

    public List<Evaluation> findAllByStudentIdWithFeedback(Integer studentId){
        return evaluationRepository.findDistinctByGroupCategory_ProjectGroups_Students_StudentIdAndFeedbacks_FeedbackIdIsNotNull(studentId);
    }


    public List<Evaluation> findEvaluationsWithoutStudentFeedback(Integer studentId){
        List<Evaluation> evaluations = evaluationRepository.findEvaluationsWithoutStudentFeedback(studentId);
        logger.debug("Evaluations without feedback from student {}: {}", studentId, evaluations);
        System.out.println("Evaluations without feedback from student {" + studentId+ "}: {"+ evaluations +"}");
        return evaluations;
    }

    public List<Evaluation> findEvaluationsByInstructorId(Integer instructorId){
        return evaluationRepository.findByProject_Instructor_InstructorId(instructorId);
    }

    public Integer countStudentsAssignedToEvaluation(Integer evaluationId) {
        return evaluationRepository.countStudentsAssignedToEvaluation(evaluationId);
    }


    public Integer countStudentsWhoSubmittedFeedback(Integer evaluationId) {
        return evaluationRepository.countStudentsWhoSubmittedFeedback(evaluationId);
    }

    public List<Student> findStudentsAssignedToEvaluation(Integer evaluationId){
        return evaluationRepository.findStudentsAssignedToEvaluation(evaluationId);
    }

    public List<Feedback> findFeedbacksForStudentInEvaluation(Integer studentId, Integer evaluationId){
        return evaluationRepository.findFeedbacksForStudentInEvaluation(evaluationId, studentId);
    }

    public List<Feedback> findFeedbacksByStudentInEvaluation(Integer studentId, Integer evaluationId){
        return evaluationRepository.findFeedbacksByStudentInEvaluation(evaluationId, studentId);
    }

    @Transactional
    public Evaluation updateDueDate(Integer evaluationId, LocalDateTime dueDate) {
        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(evaluationId);
        if (optionalEvaluation.isPresent()) {
            Evaluation evaluation = optionalEvaluation.get();
            evaluation.setDueDate(dueDate);
            return evaluationRepository.saveAndFlush(evaluation);
        } else {
            throw new IllegalArgumentException("Evaluation with ID " + evaluationId + " not found.");
        }
    }

    @Override
    protected BaseEntityRepository<Evaluation, Integer> getRepository() {
        return evaluationRepository;
    }

}
