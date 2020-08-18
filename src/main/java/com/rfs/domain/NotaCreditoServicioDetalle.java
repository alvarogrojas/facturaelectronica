package com.rfs.domain;


import com.rfs.dtos.FacturaServicioDetalleDTO;

import javax.persistence.*;

@Entity
public class NotaCreditoServicioDetalle implements Comparable<NotaCreditoServicioDetalle> {


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
    @JoinColumn(name="servicio_correduria_aduanero_id", referencedColumnName="id")
    private ServicioCorreduriaAduanera servicio;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof NotaCreditoServicioDetalle) {
            NotaCreditoServicioDetalle that = (NotaCreditoServicioDetalle) o;

            return id != null ? id.equals(that.id) : that.id == null;
        } else if (o instanceof FacturaServicioDetalleDTO) {
            FacturaServicioDetalleDTO that = (FacturaServicioDetalleDTO) o;

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
    public int compareTo( final NotaCreditoServicioDetalle o) {
        return Integer.compare(this.id, o.id);
    }
}
