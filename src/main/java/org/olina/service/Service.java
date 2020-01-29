package org.olina.service;

import org.olina.domains.Entity;
import org.olina.exception.ObjectNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class Service<T extends Entity<ID>, ID, R extends JpaRepository<T, ID>> {

    protected abstract R getRepository();

    private Example<T> getExample(T t) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)   // Match string containing pattern
                .withIgnoreCase();                 // ignore case sensitivity

        return Example.of(t, matcher);
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public T findById(ID id) {
        T t = getRepository().findById(id)
                .orElseThrow(() -> new ObjectNotFoundException());
        return t;
    }

    public List<T> findByExample(T t) {
        return getRepository().findAll(getExample(t));
    }

    public T save(T t) {
        if (valid(t)) {
            return getRepository().save(t);
        }
        return null;
    }

    protected boolean validDelete(T t) {
        return true;
    }

    public void deleteById(T t) {
        if (validDelete(t)) {
            getRepository().deleteById(t.getId());
        }
    }

    protected boolean valid(T t) {
        return true;
    }

}
