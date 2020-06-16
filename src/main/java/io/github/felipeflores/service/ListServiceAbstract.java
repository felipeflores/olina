package io.github.felipeflores.service;

import io.github.felipeflores.domains.Entity;
import io.github.felipeflores.exception.ObjectNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ListServiceAbstract<E extends Entity<ID>, ID, DTO, R extends JpaRepository<E, ID>> {

    protected abstract R getRepository();

    @Getter(AccessLevel.PROTECTED)
    private Class<DTO> dtoClazz;
    @Getter(AccessLevel.PROTECTED)
    private Class<E> clazz;
    private ExampleMatcher matcher;

    public ListServiceAbstract(Class<E> clazz, Class<DTO> dtoClazz) {
        this.clazz = clazz;
        this.dtoClazz = dtoClazz;
        this.matcher = ExampleMatcher.matching();
    }
//    protected PageRequest getPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
//        if (page == null || page < 0) {
//            return PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.valueOf(direction), orderBy);
//        }
//
//        return PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
//    }
//
//    protected void addMatcher(String field, ExampleMatcher.GenericPropertyMatcher genericPropertyMatcher) {
//        this.matcher.withMatcher(field, genericPropertyMatcher);
//    }
//
//    protected Example<E> getExample(E t) {
//        this.matcher.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)   // Match string containing pattern
//                .withIgnoreCase();                 // ignore case sensitivity
//        return Example.of(t, matcher);
//    }

    public List<DTO> getAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<E> entities = getRepository().findAll();
        return entities.stream().map(e -> modelMapper.map(e, dtoClazz)).collect(Collectors.toList());
    }

    public DTO findById(ID id) throws ObjectNotFoundException {
        E e = findByIdEntity(id);
        return new ModelMapper().map(e, dtoClazz);
    }

    public E findByIdEntity(ID id) throws ObjectNotFoundException {
        return getRepository().findById(id)
                .orElseThrow(() -> new ObjectNotFoundException());
    }



}
