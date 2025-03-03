package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.instructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityRepository;
import edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity.BaseEntityService;

@Service
public class InstructorService extends BaseEntityService<Instructor, Integer> {

    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public Optional<Instructor> findInstructorByEmail(String email) {
        return instructorRepository.findByEmail(email);
    }

    public Optional<Instructor> findInstructorByEmailAndPuid(String email, String puid) {
        return instructorRepository.findByInstructorEmailAndPuid(email, puid);
    }

    @Override
    protected BaseEntityRepository<Instructor, Integer> getRepository() {
        return instructorRepository;
    }

}

