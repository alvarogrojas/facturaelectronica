package com.rfs.repository;

import com.rfs.domain.Factura;
import com.rfs.domain.FacturaIdentity;
import com.rfs.dtos.FacturaDataDTO;
import com.rfs.dtos.FacturaReciboDTO;
import com.rfs.dtos.NotaCreditoDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, FacturaIdentity>,FacturaRepositoryCustom {
    public Factura findTopByOrderByFechaFacturacionDesc();

    @Query(value = "select new com.rfs.dtos.FacturaReciboDTO(f.facturaIdentity.facturaId,0,f.fechaFacturacion,f.encargado.nombre,f.enviadaHacienda) from Factura f order by f.id")
    public List<FacturaReciboDTO> findFacturasByReciboAsc(Integer reciboId);

    @Query(value = "select count(f.facturaIdentity) from Factura f where date(f.fechaFacturacion)>=current_date and f.facturaIdentity.empresaId=?1")
    public Integer getCountFacturasHoy(Integer id);

    @Query(value = "select count(f.facturaIdentity) from Factura f where f.estadoPago=?1 and f.facturaIdentity.empresaId=?2 ")
    public Integer getCountFacturasEstadoPago(String estadoPago, Integer empresaId);

    public Factura findByFacturaIdentity(FacturaIdentity i);


    @Query(value = "select new com.rfs.dtos.NotaCreditoDataDTO(f) from Factura f where f.facturaIdentity.facturaId=?1 and f.facturaIdentity.empresaId=?2 ")
    public NotaCreditoDataDTO findByIdToNotaCreditoDTO(Integer id, Integer empresaId);

    @Query(value = "select new com.rfs.dtos.FacturaDataDTO(f) from Factura f where f.facturaIdentity.facturaId=?1 and f.facturaIdentity.empresaId=?2")
    public FacturaDataDTO findByFacturaIdentityToFacturaDTO(Integer id, Integer empresaId);

}
