package com.rfs.repository;

import com.rfs.dtos.FacturaRegistroDTO;

import java.util.Date;
import java.util.List;

public interface FacturaRepositoryCustom {

//    public List<FacturaReciboDTO> getFacturaFiltrados(Integer reciboId, Integer facturaId, Integer clienteId, Date fechaInicio, Date fechaFinal, Integer pageNumber, Integer pageSize);
//
    public List<FacturaRegistroDTO> getFacturaFiltrados(Date fechaInicio, Date fechaFinal, Integer enviadas, Integer empresaId, String estado);

    public List<FacturaRegistroDTO> getFacturaFiltrados(Integer facturaId, Date fechaInicio, Date fechaFinal, Integer empresaId);
//
   public Long countFacturaFiltrados(Integer facturaId, Date fechaInicio, Date fechaFinal, Integer empresaId);

    public Long countFacturas(Integer clienteId, String estado, String estadoPago, Date fechaInicio, Date fechaFinal,Integer enviados, Integer empresaId);

    List<FacturaRegistroDTO> getFacturas(Integer clienteId, String estado, String estadoPago, Date fechaInicio, Date fechaFinal, Integer pageNumber, Integer pageSize, Integer enviadas, Integer empresaId);

    Double sumTotalesFacturas(Integer clienteId, String estado, String estadoPago, Date fechaInicio, Date fechaFinal, Integer pageNumber, Integer pageSize, Integer enviadas, Integer empresaId);
}
