package com.rfs.repository;

import com.rfs.domain.Empresa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EmpresaRepository extends CrudRepository<Empresa, Integer> {
    public Empresa findById(Integer id);

    //public List<Empresa> findByNombreOrderByNombreAsc(Integer pageNumber, Integer pageSize);
    //public List<Empresa> findByNombreOrderByNombreAsc();

    public List<Empresa> findByIdOrderByNombre(Integer id);
}
