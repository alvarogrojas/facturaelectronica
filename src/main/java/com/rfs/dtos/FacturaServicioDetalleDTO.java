package com.rfs.dtos;


import com.rfs.domain.ServicioCorreduriaAduanera;
import com.rfs.domain.TipoCambio;

public class FacturaServicioDetalleDTO {

    private Integer id;

    private String tipoDetalle;

    private String detalle;

    private Double monto;
    private Integer cantidad;

    private TipoCambio tipoCambio;

    private ServicioCorreduriaAduanera servicio;

    private Double montoColones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer detalleId) {
        this.id = detalleId;
    }

    public String getTipoDetalle() {
        return tipoDetalle;
    }

    public void setTipoDetalle(String tipoDetalle) {
        this.tipoDetalle = tipoDetalle;
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

    public ServicioCorreduriaAduanera getServicio() {
        return servicio;
    }

    public void setServicio(ServicioCorreduriaAduanera servicios) {
        this.servicio = servicios;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
