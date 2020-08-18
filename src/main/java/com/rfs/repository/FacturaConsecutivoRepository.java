package com.rfs.repository;

import com.rfs.domain.FacturaConsecutivo;
import org.springframework.data.repository.CrudRepository;

public interface FacturaConsecutivoRepository extends CrudRepository<FacturaConsecutivo, Long> {

    public FacturaConsecutivo findByEmpresa(String empresa);

    //public FacturaConsecutivo findByEmpresaId(Integer empresa);

    public FacturaConsecutivo findByEmpresaIdAndEmpresa(Integer empresaId, String empresa);


}
