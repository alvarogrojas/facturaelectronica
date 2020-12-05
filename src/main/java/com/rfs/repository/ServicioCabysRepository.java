package com.rfs.repository;


import com.rfs.domain.ServicioCabys;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ServicioCabysRepository extends CrudRepository<ServicioCabys, Long> {
//    public Cliente findById(Integer id);
//
//    public List<Cliente> findByEstadoAndEmpresaIdOrderByNombre(String estado, Integer empresaId);
//
    public List<ServicioCabys> findByEmpresaIdOrderByDescripcion(Integer empresaId);

}
