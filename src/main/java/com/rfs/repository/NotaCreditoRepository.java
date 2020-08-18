package com.rfs.repository;

import com.rfs.domain.NotaCredito;
import com.rfs.dtos.NotaCreditoDataDTO;
import com.rfs.dtos.NotaCreditoStatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotaCreditoRepository extends JpaRepository<NotaCredito, Integer>,NotaCreditoRepositoryCustom {
    public NotaCredito findTopByOrderByIdDesc();

    public NotaCredito findByIdAndEmpresaId(Integer id, Integer empresaId);

    public List<NotaCredito> findByFacturaIdAndEmpresaIdOrderByFechaNotaCreditocionDesc(Integer id, Integer empresaId);

    @Query(value = "select new com.rfs.dtos.NotaCreditoStatusDTO(nc.id,nc.facturaId,nc.estado) from NotaCredito nc where nc.facturaId=?1 and nc.empresaId=?2")
    public List<NotaCreditoStatusDTO> findByFacturaIdForStatus(Integer id, Integer empresaId);

    @Query(value = "select new com.rfs.dtos.NotaCreditoDataDTO(nc,factura) from NotaCredito nc, Factura factura where nc.id=?1 and nc.facturaId = factura.facturaIdentity.facturaId and nc.empresaId=factura.facturaIdentity.empresaId")
    public NotaCreditoDataDTO findByIdToNotaCreditoDTO(Integer id);


}
