package com.rfs.repository;

import com.rfs.domain.Impuesto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImpuestoRepository extends CrudRepository<Impuesto, Long> {
    List<Impuesto> findAllByOrderByNombreAsc();
}
