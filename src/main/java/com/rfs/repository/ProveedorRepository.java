package com.rfs.repository;

import com.rfs.domain.Proveedor;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProveedorRepository extends CrudRepository<Proveedor, Long> {

}
