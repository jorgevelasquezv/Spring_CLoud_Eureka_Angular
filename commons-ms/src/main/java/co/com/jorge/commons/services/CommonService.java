package co.com.jorge.commons.services;

import java.util.Optional;

public interface CommonService<E> {

    Iterable<E> findAll();

    Optional<E> findById(Long id);

    E save(E entity);

    void deleteById(Long id);
}
