package com.rfs.domain;


import com.rfs.dtos.FacturaTercerosDTO;

import javax.persistence.*;

@Entity
public class NotaCreditoTerceros implements Comparable<NotaCreditoTerceros> {

    @ManyToOne
    private NotaCredito notaCredito;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String tipoDetalle;

    private String detalle;

    private Double monto;
    private Integer cantidad;

    //private Integer monedaId;

    @OneToOne
    @JoinColumn(name="tipo_cambio_id", referencedColumnName="id")
    private TipoCambio tipoCambio;

    @OneToOne
    @JoinColumn(name="terceros_id", referencedColumnName="id")
    private Terceros terceros;

    private Double montoColones;

    public NotaCredito getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(NotaCredito facturaId) {
        this.notaCredito = facturaId;
    }

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
        if (o instanceof NotaCreditoTerceros) {
            NotaCreditoTerceros that = (NotaCreditoTerceros) o;

            return id != null ? id.equals(that.id) : that.id == null;
        } else if (o instanceof FacturaTercerosDTO) {
            FacturaTercerosDTO that = (FacturaTercerosDTO) o;

            return id != null ? id.equals(that.getId()) : that.getId() == null;
        } else {
            return false;
        }
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo( final NotaCreditoTerceros o) {
        return Integer.compare(this.id, o.id);
    }
}
