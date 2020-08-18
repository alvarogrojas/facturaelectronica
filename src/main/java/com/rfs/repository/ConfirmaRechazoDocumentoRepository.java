package com.rfs.repository;

import com.rfs.domain.ConfirmaRechazaDocumento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ConfirmaRechazoDocumentoRepository extends CrudRepository<ConfirmaRechazaDocumento, Long> {

    ConfirmaRechazaDocumento findByClave(String clave);

    @Query(value = "select c from ConfirmaRechazaDocumento c where c.emisor like %?1% or c.consecutivo like %?1% and c.fechaEmision>=?2 and c.fechaEmision<=?3 and empresaId=?4 order by c.fechaEmision")
    public List<ConfirmaRechazaDocumento> getConfirmaRechazaDocumentoByFilters(String filter, Date fechaInicio, Date fechaFinal, Integer empresaId);

    @Query(value = "select c from ConfirmaRechazaDocumento c where c.fechaEmision>=?1 and c.fechaEmision<=?2 and empresaId=?3 order by c.fechaEmision")
    public List<ConfirmaRechazaDocumento> getConfirmaRechazaDocumentoByFilters(Date fechaInicio, Date fechaFinal, Integer empresaId);

    @Query(value = "select c from ConfirmaRechazaDocumento c where c.emisor like %?1% or c.consecutivo like %?2% order by c.fechaEmision")
    List<ConfirmaRechazaDocumento> findByEmisorAndConsecutivo(String emisor, String consecutivo);//OrderByFechaEmision

    ConfirmaRechazaDocumento findById(Integer id);
}