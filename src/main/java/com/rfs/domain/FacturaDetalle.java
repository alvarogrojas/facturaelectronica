package com.rfs.domain;


import javax.persistence.*;

@Entity
public class FacturaDetalle implements Comparable<FacturaDetalle> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

//    @ManyToOne
//    @JoinColumns({
//        @JoinColumn(name = "factura_id"),
//        @JoinColumn(
//                name = "empresa_id")
//    })
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "facturaId", referencedColumnName = "facturaId"),
            @JoinColumn(name = "empresaId", referencedColumnName = "empresaId")
    })
    private Factura factura;
//
//    @ManyToOne
//    @JoinColumn(name = "empresa_id")
//    private Factura empresa;

//    //@EmbeddedId
//    private FacturaIdentity facturaIdentity;



    private String detalle;

    private Double monto;
    private Double montoNeto;
    private Integer cantidad;



    @OneToOne
    @JoinColumn(name="tipo_cambio_id", referencedColumnName="id")
    private TipoCambio tipoCambio;

    @OneToOne
    @JoinColumn(name="tarifa_iva_id", referencedColumnName="id")
    private TarifaIva tarifaIva;

    @OneToOne
    @JoinColumn(name="medida_id", referencedColumnName="id")
    private Medida medida;

    private Double montoColones;

    private Double impuestos;
    private Double impuestosMonto;
    private String tipoImpuesto;



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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacturaDetalle that = (FacturaDetalle) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo( final FacturaDetalle o) {
        return Integer.compare(this.id, o.id);
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

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
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
}
