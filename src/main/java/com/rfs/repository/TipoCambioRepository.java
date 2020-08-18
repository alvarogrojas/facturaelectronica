package com.rfs.repository;

import com.rfs.domain.TipoCambio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TipoCambioRepository extends CrudRepository<TipoCambio, Long> {
    List<TipoCambio> findAll();

    TipoCambio findById(Integer tipoCambioId);

    List<TipoCambio> findByEmpresaId(Integer empresaId);
}
