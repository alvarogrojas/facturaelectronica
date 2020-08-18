package com.rfs.repository;

import com.rfs.domain.Tarifa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TarifaRepository extends CrudRepository<Tarifa, Long> {

    List<Tarifa> findByClienteId(Integer id);
}
