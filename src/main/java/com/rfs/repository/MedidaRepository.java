package com.rfs.repository;

import com.rfs.domain.Medida;
import com.rfs.domain.TarifaIva;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MedidaRepository extends CrudRepository<Medida, Long> {
    List<Medida> findAll();
}
