package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.myClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pui.peerEvaluation.PeerEvaluationApplication.exceptions.EntityNotFoundException;

@Service
public class MyClassService {

    private final MyClassRepository myClassRepository;

    @Autowired
    public MyClassService(MyClassRepository myClassRepository){
        this.myClassRepository = myClassRepository;
    }

    public MyClass addMyClass(MyClass myClass){
        return myClassRepository.saveAndFlush(myClass);
    }

    public MyClass findById(Integer id){
        return myClassRepository.findById(id).orElse(null);
    }

    public MyClass findByClassCode(String classCode){
        return myClassRepository.findByClassCode(classCode).orElseThrow(() -> new EntityNotFoundException("No MyClass found with " + "class code: " + classCode));
    }

    public MyClass findByClassCodeOrCreate(String classCode) {
        return myClassRepository.findByClassCode(classCode).orElseGet(() -> {
            MyClass newClass = new MyClass();
            newClass.setClassCode(classCode);
            // Set other default properties for newClass here if needed
            return myClassRepository.saveAndFlush(newClass);
        });
    }
}
