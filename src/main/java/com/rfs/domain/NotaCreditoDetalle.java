package com.rfs.domain;


import javax.persistence.*;

@Entity
public class NotaCreditoDetalle implements Comparable<NotaCreditoDetalle> {

    @ManyToOne
    private NotaCredito notaCredito;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String detalle;

    private Double monto;
    private Double montoNeto;
    private Integer cantidad;

    @OneToOne
    @JoinColumn(name="tipo_cambio_id", referencedColumnName="id")
    private TipoCambio tipoCambio;

    private Double montoColones;

    private Double impuestos;
    private Double impuestosMonto;
    private String tipoImpuesto;

    @OneToOne
    @JoinColumn(name="tarifa_iva_id", referencedColumnName="id")
    private TarifaIva tarifaIva;

    @OneToOne
    @JoinColumn(name="medida_id", referencedColumnName="id")
    private Medida medida;


    public NotaCredito getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(NotaCredito facturaId) {
        this.notaCredito = facturaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getMontoNeto() {
        return montoNeto;
    }

    public void setMontoNeto(Double montoNeto) {
        this.montoNeto = montoNeto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

    public void setMontoColones(Double montoColones) {
        this.montoColones = montoColones;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
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

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo( final NotaCreditoDetalle o) {
        return Integer.compare(this.id, o.id);
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
}
