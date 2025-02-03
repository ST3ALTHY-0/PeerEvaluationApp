package edu.pui.peerEvaluation.Peerevualuationapplication.orm.myClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
