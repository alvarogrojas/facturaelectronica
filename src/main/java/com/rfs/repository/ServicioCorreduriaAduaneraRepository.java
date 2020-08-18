package com.rfs.repository;

import com.rfs.domain.ServicioCorreduriaAduanera;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ServicioCorreduriaAduaneraRepository extends CrudRepository<ServicioCorreduriaAduanera, Long> {
    List<ServicioCorreduriaAduanera> findAllByOrderByNombreAsc();
}
