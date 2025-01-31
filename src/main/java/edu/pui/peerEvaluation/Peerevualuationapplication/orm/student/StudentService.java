package edu.pui.peerEvaluation.Peerevualuationapplication.orm.student;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.saveAndFlush(student);
    }

    public Optional<Student> findStudentById(int id) {
        return studentRepository.findById(id);
    }

}
