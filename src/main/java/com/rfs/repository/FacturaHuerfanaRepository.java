package com.rfs.repository;

import com.rfs.domain.FacturaHuerfana;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface FacturaHuerfanaRepository extends CrudRepository<FacturaHuerfana, Integer> {
//    @Query("SELECT new com.rfs.domain.FacturaHuerfana(min(h.facturaId),h.id) FROM FacturaHuerfana h")
//    public FacturaHuerfana getMinFacturaId();

    @Query("SELECT min(h.facturaId) FROM FacturaHuerfana h")
    public Integer getMinFacturaId();

    public List<FacturaHuerfana> findByFacturaIdOrderById(Integer facturaId);
}
