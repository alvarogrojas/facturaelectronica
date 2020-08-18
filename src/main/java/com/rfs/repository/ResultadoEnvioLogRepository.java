package com.rfs.repository;

import com.rfs.domain.ResultadoEnvioLog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultadoEnvioLogRepository extends JpaRepository<ResultadoEnvioLog, Long> {

    //@Query(value = "select new com.rfs.domain.ResultadoEnvioLog (f) from resultado_envio_log f where f.facturaIdentity.facturaId=?1 and f.facturaIdentity.empresaId=?2")
    public List<ResultadoEnvioLog> findByFacturaIdAndEmpresaId(Integer facturaId, Integer empresaId);

}
