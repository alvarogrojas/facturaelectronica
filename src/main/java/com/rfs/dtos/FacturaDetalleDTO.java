package com.rfs.dtos;


import com.rfs.domain.Medida;
import com.rfs.domain.ServicioCabys;
import com.rfs.domain.TarifaIva;
import com.rfs.domain.TipoCambio;

public class FacturaDetalleDTO {

    private Integer id;

    private String detalle;

    private Double monto;

    private Integer cantidad;

    private TipoCambio tipoCambio;

    private Double montoColones;
    private Double montoNeto;

    private Double impuestos;
    private Double impuestosMonto;
    private String tipoImpuesto;
    private TarifaIva tarifaIva;
    private Medida medida;
    private ServicioCabys servicio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer detalleId) {
        this.id = detalleId;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public TipoCambio getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(TipoCambio tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Double getMontoColones() {
        return montoColones;
    }

    public void setMontoColones(Double totalColones) {
        this.montoColones = totalColones;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }

    public Double getMontoNeto() {
        return montoNeto;
    }

    public void setMontoNeto(Double montoNeto) {
        this.montoNeto = montoNeto;
    }

    public Double getImpuestosMonto() {
        return impuestosMonto;
    }

    public void setImpuestosMonto(Double impuestosMonto) {
        this.impuestosMonto = impuestosMonto;
    }

    public String getTipoImpuesto() {
        return tipoImpuesto;
    }

    public void setTipoImpuesto(String tipoImpuesto) {
        this.tipoImpuesto = tipoImpuesto;
    }

    public TarifaIva getTarifaIva() {
        return tarifaIva;
    }

    public void setTarifaIva(TarifaIva tarifaIva) {
        this.tarifaIva = tarifaIva;
    }

    public Medida getMedida() {
        return medida;
    }

    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    public ServicioCabys getServicio() {
        return servicio;
    }

    public void setServicio(ServicioCabys servicio) {
        this.servicio = servicio;
    }
}
