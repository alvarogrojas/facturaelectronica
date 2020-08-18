package com.rfs.dtos;

import java.util.List;

public class ReciboFacturacionDTO {

    private Integer id;

    private String recibo;

    private Integer corelacionId;

    private List<Integer> facturas;

    private Integer facturaId;

    public ReciboFacturacionDTO () {}

    public ReciboFacturacionDTO(Integer id, String recibo, Integer corelacionId) {
        this.id = id;
        this.recibo = recibo;
        this.corelacionId = corelacionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public Integer getCorelacionId() {
        return corelacionId;
    }

    public void setCorelacionId(Integer corelacionId) {
        this.corelacionId = corelacionId;
    }

    public List<Integer> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Integer> facturas) {
        this.facturas = facturas;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }
}
