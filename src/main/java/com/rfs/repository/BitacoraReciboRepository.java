package com.rfs.repository;


import com.rfs.domain.BitacoraRecibo;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BitacoraReciboRepository extends CrudRepository<BitacoraRecibo, Long> {

}
