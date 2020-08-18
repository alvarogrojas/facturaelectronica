package com.rfs.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvaro on 11/13/17.
 */
public class FacturarResult {

    private String facturaId;

    private List<FacturaReciboDTO> facturas = new ArrayList<FacturaReciboDTO>();

    public String getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(String facturaId) {
        this.facturaId = facturaId;
    }

    public List<FacturaReciboDTO> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaReciboDTO> facturas) {
        this.facturas = facturas;
    }
}
