package com.rfs.repository;

import com.rfs.domain.Moneda;
import org.springframework.data.repository.CrudRepository;

public interface MonedaRepository extends CrudRepository<Moneda, Long> {
    public Moneda findById(Integer id);
}
