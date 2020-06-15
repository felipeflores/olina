package io.github.felipeflores.service;

import io.github.felipeflores.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ServiceAbstract<E, ID, DTO, R extends JpaRepository<E, ID>> {

    protected abstract R getRepository();

    private Class<DTO> dtoClazz;
    private Class<E> clazz;
    private ExampleMatcher matcher;

    public ServiceAbstract(Class<E> clazz, Class<DTO> dtoClazz) {
        this.clazz = clazz;
        this.dtoClazz = dtoClazz;
        this.matcher = ExampleMatcher.matching();
    }
    protected PageRequest getPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        if (page == null || page < 0) {
            return PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.valueOf(direction), orderBy);
        }

        return PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
    }

    protected void addMatcher(String field, ExampleMatcher.GenericPropertyMatcher genericPropertyMatcher) {
        this.matcher.withMatcher(field, genericPropertyMatcher);
    }

    protected Example<E> getExample(E t) {
        this.matcher.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)   // Match string containing pattern
                .withIgnoreCase();                 // ignore case sensitivity
        return Example.of(t, matcher);
    }

    public <IDTO> DTO save(IDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        E e = modelMapper.map(dto, clazz);
        e = getRepository().save(e);
        return modelMapper.map(e, dtoClazz);
    }

    public List<DTO> getAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<E> entities = getRepository().findAll();
        return entities.stream().map(e -> modelMapper.map(e, dtoClazz)).collect(Collectors.toList());
    }

    public DTO findById(ID id) {
        E e = findByIdEntity(id);
        return new ModelMapper().map(e, dtoClazz);
    }

    private E findByIdEntity(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ObjectNotFoundException());
    }

    public <UDTO> DTO update(ID id, UDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        E e = findByIdEntity(id);
        e = modelMapper.map(dto, clazz);
        e = getRepository().save(e);
        return modelMapper.map(e, dtoClazz);
    }
    public void delete(ID id) {
        E e = findByIdEntity(id);
        deleteEntity(e);
    }
    public void deleteEntity(E e) {
        if (validDelete(e)) {
            getRepository().delete(e);
        }
    }

    protected boolean validDelete(E e) {
        return true;
    }

    protected boolean valid(E t) {
        return true;
    }

}
