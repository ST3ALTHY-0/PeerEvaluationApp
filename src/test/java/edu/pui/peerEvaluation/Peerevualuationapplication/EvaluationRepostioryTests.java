package edu.pui.peerEvaluation.Peerevualuationapplication;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.Evaluation;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluation.EvaluationRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestion;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationQuestion.EvaluationQuestionRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponse;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.evaluationResponse.EvaluationResponseRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.Feedback;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.feedback.FeedbackRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.Instructor;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor.InstructorRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClass;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass.MyClassRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.Project;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.project.ProjectRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroup;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.projectGroup.ProjectGroupRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.Student;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringJUnitConfig
class EvaluationRepositoryTests {

    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MyClassRepository classRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectGroupRepository  projectGroupRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private EvaluationQuestionRepository evaluationQuestionRepository;
    @Autowired
    private EvaluationResponseRepository evaluationResponseRepository;

    // @BeforeEach
    // void setUp() {
    //     // Insert your test data into the DB if needed
    // }

    @Test
    void testInstructorData() {
        List<Instructor> instructors = instructorRepository.findAll();
        assertThat(instructors).hasSize(3);

        Instructor instructor = instructorRepository.findById(1).orElse(null);
        assertThat(instructor).isNotNull();
        assertThat(instructor.getInstructorName()).isEqualTo("Luke Monroe");
        assertThat(instructor.getInstructorEmail()).isEqualTo("monroe.luke36@gmail.com");
    }

    @Test
    void testStudentData() {
        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(5);

        Student student = studentRepository.findById(1).orElse(null);
        assertThat(student).isNotNull();
        assertThat(student.getStudentName()).isEqualTo("Luke Monroe");
        assertThat(student.getStudentEmail()).isEqualTo("monroe.luke36@gmail.com");
    }

    @Test
    void testClassData() {
        List<MyClass> classes = classRepository.findAll();
        assertThat(classes).hasSize(3);

        MyClass myClass = classRepository.findById(1).orElse(null);
        assertThat(myClass).isNotNull();
        assertThat(myClass.getClassName()).isEqualTo("Introduction to Programming");
        assertThat(myClass.getClassCode()).isEqualTo("CS101");
    }

    @Test
    void testProjectData() {
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(3);

        Project project = projectRepository.findById(1).orElse(null);
        assertThat(project).isNotNull();
        assertThat(project.getProjectName()).isEqualTo("Programming Basics Project");
        assertThat(project.getPointsWorth()).isEqualTo(100);
    }

    @Test
    void testProjectGroupData() {
        List<ProjectGroup> groups = projectGroupRepository.findAll();
        assertThat(groups).hasSize(3);

        ProjectGroup group = projectGroupRepository.findById(1).orElse(null);
        assertThat(group).isNotNull();
        assertThat(group.getGroupName()).isEqualTo("Group Alpha");
    }


    @Test
    void testEvaluationData() {
        List<Evaluation> evaluations = evaluationRepository.findAll();
        assertThat(evaluations).hasSize(3);

        Evaluation evaluation = evaluationRepository.findById(1).orElse(null);
        assertThat(evaluation).isNotNull();
        assertThat(evaluation.isComplete()).isFalse();
    }

    @Test
    void testFeedbackData() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(5);

        Feedback feedback = feedbackRepository.findById(1).orElse(null);
        assertThat(feedback).isNotNull();
        assertThat(feedback.getGradePercent()).isEqualTo(90);
    }

    @Test
    void testEvaluationQuestionData() {
        List<EvaluationQuestion> questions = evaluationQuestionRepository.findAll();
        assertThat(questions).hasSize(4);

        EvaluationQuestion question = evaluationQuestionRepository.findById(1).orElse(null);
        assertThat(question).isNotNull();
        assertThat(question.getQuestionText()).isEqualTo("How well did your teammate contribute to the project?");
    }

    @Test
    void testEvaluationResponseData() {
        List<EvaluationResponse> responses = evaluationResponseRepository.findAll();
        assertThat(responses).hasSize(4);

        EvaluationResponse response = evaluationResponseRepository.findById(1).orElse(null);
        assertThat(response).isNotNull();
        assertThat(response.getResponseText()).isEqualTo("Sarah was very helpful and contributed significantly to the project.");
    }



    //TODO: Test putting in data 

    //TODO: Test putting in Bad Data

    //Test API?


}

    // Add more test cases for other queries
