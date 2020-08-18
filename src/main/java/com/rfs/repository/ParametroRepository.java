package com.rfs.repository;

import com.rfs.domain.Parametro;
import org.springframework.data.repository.CrudRepository;

public interface ParametroRepository extends CrudRepository<Parametro, Long> {
    public Parametro findByNombre(String nombre);
}
