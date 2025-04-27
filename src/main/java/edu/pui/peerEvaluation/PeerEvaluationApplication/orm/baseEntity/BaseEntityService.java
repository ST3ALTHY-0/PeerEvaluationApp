package edu.pui.peerEvaluation.PeerEvaluationApplication.orm.baseEntity;

import java.util.List;
import java.util.Optional;


//Base entity service class that all my other entities service classes will extend
public abstract class BaseEntityService<T, ID> {

    protected abstract BaseEntityRepository<T, ID> getRepository();

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public List<T> saveAll(List<T> entities){
        return getRepository().saveAll(entities);
    }

    public T saveAndFlush(T entity) {
        return getRepository().saveAndFlush(entity);
    }

    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }
    
}