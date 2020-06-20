package io.github.felipeflores.service;

import io.github.felipeflores.domains.BaseEntity;
import io.github.felipeflores.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CrudServiceAbstract<E extends BaseEntity<ID>, ID, DTO, R extends JpaRepository<E, ID>> extends ListServiceAbstract<E, ID, DTO, R> {

    public CrudServiceAbstract(Class<E> clazz, Class<DTO> dtoClazz) {
        super(clazz, dtoClazz);
    }

    public <IDTO> DTO save(IDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        E e = modelMapper.map(dto, this.getClazz());
        e = saveEntity(e);
        return modelMapper.map(e, this.getDtoClazz());
    }

    public E saveEntity(E e ) {
        if (valid(e)) {
            return getRepository().save(e);
        }
        return null;
    }

    public <UDTO> DTO update(ID id, UDTO dto) throws ObjectNotFoundException{
        ModelMapper modelMapper = new ModelMapper();
        E e = findByIdEntity(id);
        e = modelMapper.map(dto, this.getClazz());
        e = getRepository().save(e);
        return modelMapper.map(e, this.getDtoClazz());
    }

    public E updateEntity(E e) {
        if (validUpdate(e)) {
            return this.getRepository().save(e);
        }
        return null;
    }

    public void delete(ID id) throws ObjectNotFoundException {
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

    protected boolean validUpdate(E t) {
        return true;
    }
}
