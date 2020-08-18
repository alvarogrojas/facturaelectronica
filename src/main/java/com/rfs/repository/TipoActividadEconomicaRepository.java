package com.rfs.repository;

import com.rfs.domain.ErrorEnvio;
import com.rfs.domain.TipoActividadEconomica;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TipoActividadEconomicaRepository extends CrudRepository<TipoActividadEconomica, Long> {

    List<TipoActividadEconomica> findByEmpresaId(Integer empresaId);

}
