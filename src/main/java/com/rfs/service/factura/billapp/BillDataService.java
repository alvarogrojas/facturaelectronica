package com.rfs.service.factura.billapp;

import com.rfs.dtos.NotaCreditoDataDTO;
import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.fe.v43.nc.NotaCreditoElectronica;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BillDataService {

    public List<FacturaElectronica> getFacturas(Date fechaDesde, Date fechaHasta, EmisorService m);

    public List<FacturaElectronica> getFacturas(EmisorService m);

//    List<FacturaElectronica> getFacturaElectronica(FacturaDataDTO fdd, EmisorService e);

    public List<FacturaElectronica> getFacturaElectronica1(Integer id, EmisorService e);

    public FacturaElectronica getFacturaElectronicaById(Integer id, EmisorService e);

    public Map getData();

    public void updateFactura(Integer id, Integer enviadaHaciendaStatus);

    NotaCreditoElectronica getNotaCreditoElectronica(Integer notaCreditoId, EmisorService emisorService);

    NotaCreditoElectronica getNotaCreditoElectronica(NotaCreditoDataDTO dto, EmisorService emisorService);

    Map<Integer,NotaCreditoDataDTO> getNcData();

}
