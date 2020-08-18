package com.rfs.service;

import com.rfs.domain.BitacoraFactura;
import com.rfs.domain.Factura;
import com.rfs.repository.BitacoraFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by alvaro on 11/3/17.
 */
@Service
public class BitacoraFacturaService {
    
    @Autowired
    private BitacoraFacturaRepository bitacoraFacturaRepository;
    
    @Transactional
    public void saveBitacoraFactura(Factura anterior, Factura nuevo) {
        BitacoraFactura result = new BitacoraFactura();
        
        result.setFacturaId(nuevo.getFacturaIdentity().getFacturaId());
        result.setEmpresaId(nuevo.getFacturaIdentity().getEmpresaId());

//        if (anterior.getReciboId()!=null && anterior.getReciboId()!=0) {
//            result.setReciboIdAnterior(anterior.getReciboId());
//        }
//
//        result.setReciboIdNuevo(nuevo.getReciboId());

        result.setFechaFacturacionNuevo(nuevo.getFechaFacturacion());

        result.setFechaFacturacionAnterior(anterior.getFechaFacturacion());

        result.setEncargadoIdAnterior(anterior.getEncargado().getId());
//
        result.setEncargadoIdNuevo(nuevo.getEncargado().getId());

        result.setEstadoFacturaAnterior(anterior.getEstado());

        result.setEstadoFacturaNuevo(nuevo.getEstado());

        result.setUltimoCambioPorNuevo(nuevo.getUltimoCambioPor());
        result.setUltimoCambioPorAnterior(anterior.getUltimoCambioPor());


        result.setFechaUltimoCambioAnterior(anterior.getFechaUltimoCambio());
        result.setFechaUltimoCambioNuevo(nuevo.getFechaUltimoCambio());


        this.bitacoraFacturaRepository.save(result);

    }
}
