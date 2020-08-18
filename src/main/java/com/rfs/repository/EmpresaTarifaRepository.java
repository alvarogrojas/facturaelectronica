package com.rfs.repository;

import com.rfs.domain.EmpresaTarifa;
import org.springframework.data.repository.CrudRepository;


public interface EmpresaTarifaRepository extends CrudRepository<EmpresaTarifa, Integer> {

    EmpresaTarifa findByEmpresaIdAndMesAno(Integer empresaId, String mesAno);
}
