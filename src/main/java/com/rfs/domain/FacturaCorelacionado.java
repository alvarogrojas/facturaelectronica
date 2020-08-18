package com.rfs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class FacturaCorelacionado {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer reciboId;

    private Integer facturaId;

    private Date fechaFacturacion;

    private Integer encargadoId;

    private Integer ultimoCambioPor;

    private Date fechaUltimoCambio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReciboId() {
        return reciboId;
    }

    public void setReciboId(Integer reciboId) {
        this.reciboId = reciboId;
    }

    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public Integer getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(Integer encargadoId) {
        this.encargadoId = encargadoId;
    }

    public Integer getUltimoCambioPor() {
        return ultimoCambioPor;
    }

    public void setUltimoCambioPor(Integer ultimoCambioPor) {
        this.ultimoCambioPor = ultimoCambioPor;
    }

    public Date getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(Date fechaUltimoCambio) {

        this.fechaUltimoCambio = fechaUltimoCambio;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }
}
