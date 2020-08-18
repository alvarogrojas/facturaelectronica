package com.rfs.repository;

import com.rfs.domain.TarifaIva;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TarifaIvaRepository extends CrudRepository<TarifaIva, Long> {

    List<TarifaIva> findByEmpresaId(Integer empresaId);

    List<TarifaIva> findAll();

}
