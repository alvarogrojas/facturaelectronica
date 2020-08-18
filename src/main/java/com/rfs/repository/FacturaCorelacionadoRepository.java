package com.rfs.repository;

import com.rfs.domain.FacturaCorelacionado;
import com.rfs.dtos.FacturaReciboDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FacturaCorelacionadoRepository extends CrudRepository<FacturaCorelacionado, Integer> {

    @Query(value = "select new com.rfs.dtos.FacturaReciboDTO(f.facturaId, f.reciboId,f.fechaFacturacion,u.nombre, false, 1) from FacturaCorelacionado f, Usuario u where f.reciboId=?1 and u.id=f.encargadoId order by f.id")
    public List<FacturaReciboDTO> findFacturasByReciboAsc(Integer reciboId);

    public List<FacturaCorelacionado> findByFacturaId(Integer facturaId);
}
