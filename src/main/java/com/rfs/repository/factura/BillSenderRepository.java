package com.rfs.repository.factura;

import com.rfs.domain.factura.BillSender;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BillSenderRepository
        extends CrudRepository<BillSender, Integer> {

}
