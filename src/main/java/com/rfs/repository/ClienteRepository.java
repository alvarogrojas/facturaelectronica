package com.rfs.repository;

import com.rfs.domain.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    public Cliente findById(Integer id);

    public List<Cliente> findByEstadoAndEmpresaIdOrderByNombre(String estado, Integer empresaId);

    public List<Cliente> findByEmpresaIdOrderByNombre(Integer empresaId);

}
