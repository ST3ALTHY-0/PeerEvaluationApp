package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.student;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityService;

@Service
public class StudentService extends BaseEntityService<Student, Integer>{

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    public Student addStudent(Student student) {
        return studentRepository.saveAndFlush(student);
    }

    public List<Student> addStudents(List<Student> students) {
        return studentRepository.saveAllAndFlush(students);
    }

    public Student findStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findByStudentEmail(email);
    }

    public Optional<Student> findStudentByEmailAndPuid(String email, String puid){
        return studentRepository.findByStudentEmailAndPuid(email, puid);
    }

    @Override
    protected BaseEntityRepository<Student, Integer> getRepository() {
        return studentRepository;
    }

}
