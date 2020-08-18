package com.rfs.repository;

import com.rfs.domain.RegimenAduanero;
import org.springframework.data.repository.CrudRepository;

public interface RegimenAduaneroRepository extends CrudRepository<RegimenAduanero, Long> {
    public RegimenAduanero findById(Integer id);

//    public List<RegimenAduanero> findAllOrderByNombre();

}
