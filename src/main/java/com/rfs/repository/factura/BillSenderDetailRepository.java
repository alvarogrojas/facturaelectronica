package com.rfs.repository.factura;

import com.rfs.domain.factura.BillSenderDetail;
import com.rfs.dtos.RespuestaHaciendaDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BillSenderDetailRepository
        extends CrudRepository<BillSenderDetail, Integer> {

    List<BillSenderDetail> findByBillIdAndEmpresaIdAndTipoOrderByDateSentDesc(Integer billId, Integer empresaId, String tipo);

    List<BillSenderDetail> findByBillIdAndEmpresaIdAndTipoAndStatusOrderByDateSentDesc(Integer billId, Integer empresaId, String tipo, String status);

    List<BillSenderDetail> findByClave(String clave);

    List<BillSenderDetail> findByBillIdAndEnviadaHacienda(Integer billId, Integer empresaId, Integer enviadaHacienda);

    List<BillSenderDetail> findByBillIdAndEmpresaIdAndEnviadaHaciendaAndTipo(Integer billId, Integer empresaId, Integer enviadaHacienda, String tipo);
    List<BillSenderDetail> findByBillIdAndTipoAndStatusAndEmpresaIdOrderByDateSentDesc(Integer billId, String tipo, String status, Integer empresaId);
    //List<BillSenderDetail> findByBillIdAndTipoAndStatusAndEmpresaIdOrderByDateSentDesc(Integer billId, String tipo, String status, Integer empresaId);



    List<BillSenderDetail> findByBillIdAndEmpresaIdAndTipoOrderByFechaIngresoDesc(Integer billId, Integer empresaId, String tipo);

    @Query(value = "select b from BillSenderDetail b where b.billId=?1 and  b.path like %?2% or  b.path like %?3%")
    List<BillSenderDetail> findByBillIdAndPathLikeCedula(Integer facturaId, String cedula, String cedulaEnhanced);
    //BillSenderDetail findByBillIdAndPathLikeCedula(Integer facturaId, String cedula, String cedulaEnhanced);

    @Query(value = "select new com.rfs.dtos.RespuestaHaciendaDTO(b) from BillSenderDetail b where b.billId=?1 and b.empresaId=?2 order by date_sent desc")
    public List<RespuestaHaciendaDTO> getEnviosHaciendaPorFacturaId(Integer facturaId, Integer empresaId);
}
