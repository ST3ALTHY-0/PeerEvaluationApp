package edu.pui.peerEvaluation.Peerevualuationapplication.orm.student;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public List<Student> addStudents(List<Student> students) {
        return studentRepository.saveAllAndFlush(students);
    }

    public Student findStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student findStudentByEmail(String email) {
        return studentRepository.findByEmail(email).orElse(null);
    }

}
