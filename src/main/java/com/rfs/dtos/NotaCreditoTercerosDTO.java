package com.rfs.dtos;


import com.rfs.domain.NotaCreditoTerceros;
import com.rfs.domain.Terceros;
import com.rfs.domain.TipoCambio;

public class NotaCreditoTercerosDTO {

    private Integer id;

    private String tipoDetalle;

    private String detalle;

    private Double monto;
    private Integer cantidad;

    //private Integer monedaId;

    private TipoCambio tipoCambio;

    private Terceros terceros;

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

    public Terceros getTerceros() {
        return terceros;
    }

    public void setTerceros(Terceros terceros) {

        this.terceros = terceros;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof NotaCreditoTercerosDTO) {
            NotaCreditoTercerosDTO that = (NotaCreditoTercerosDTO) o;

            return id != null ? id.equals(that.id) : that.id == null;
        } else if (o instanceof NotaCreditoTerceros) {
            NotaCreditoTerceros that = (NotaCreditoTerceros) o;

            return id != null ? id.equals(that.getId()) : that.getId() == null;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
