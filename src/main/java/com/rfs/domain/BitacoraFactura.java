package com.rfs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by alvaro on 11/3/17.
 */
@Entity
public class BitacoraFactura {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer facturaId;
    private Integer empresaId;

    private Date fechaFacturacionAnterior;

    private Date fechaFacturacionNuevo;

    private String estadoFacturaAnterior;

    private String estadoFacturaNuevo;

    private Integer encargadoIdAnterior;

    private Integer encargadoIdNuevo;

    private Integer ultimoCambioPorAnterior;
    private Integer ultimoCambioPorNuevo;

    private Date fechaUltimoCambioAnterior;
    private Date fechaUltimoCambioNuevo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public Date getFechaFacturacionAnterior() {
        return fechaFacturacionAnterior;
    }

    public void setFechaFacturacionAnterior(Date fechaFacturacionAnterior) {
        this.fechaFacturacionAnterior = fechaFacturacionAnterior;
    }

    public Date getFechaFacturacionNuevo() {
        return fechaFacturacionNuevo;
    }

    public void setFechaFacturacionNuevo(Date fechaFacturacionNuevo) {
        this.fechaFacturacionNuevo = fechaFacturacionNuevo;
    }

    public String getEstadoFacturaAnterior() {
        return estadoFacturaAnterior;
    }

    public void setEstadoFacturaAnterior(String estadoFacturaAnterior) {
        this.estadoFacturaAnterior = estadoFacturaAnterior;
    }

    public String getEstadoFacturaRfsNuevo() {
        return estadoFacturaNuevo;
    }

    public void setEstadoFacturaNuevo(String estadoFacturaNuevo) {
        this.estadoFacturaNuevo = estadoFacturaNuevo;
    }

    public Integer getEncargadoIdAnterior() {
        return encargadoIdAnterior;
    }

    public void setEncargadoIdAnterior(Integer encargadoIdAnterior) {
        this.encargadoIdAnterior = encargadoIdAnterior;
    }

    public Integer getEncargadoIdNuevo() {
        return encargadoIdNuevo;
    }

    public void setEncargadoIdNuevo(Integer encargadoIdNuevo) {
        this.encargadoIdNuevo = encargadoIdNuevo;
    }

    public Integer getUltimoCambioPorAnterior() {
        return ultimoCambioPorAnterior;
    }

    public void setUltimoCambioPorAnterior(Integer ultimoCambioPorAnterior) {
        this.ultimoCambioPorAnterior = ultimoCambioPorAnterior;
    }

    public Integer getUltimoCambioPorNuevo() {
        return ultimoCambioPorNuevo;
    }

    public void setUltimoCambioPorNuevo(Integer ultimoCambioPorNuevo) {
        this.ultimoCambioPorNuevo = ultimoCambioPorNuevo;
    }

    public Date getFechaUltimoCambioAnterior() {
        return fechaUltimoCambioAnterior;
    }

    public void setFechaUltimoCambioAnterior(Date fechaUltimoCambioPorAnterior) {
        this.fechaUltimoCambioAnterior = fechaUltimoCambioPorAnterior;
    }

    public Date getFechaUltimoCambioNuevo() {
        return fechaUltimoCambioNuevo;
    }

    public void setFechaUltimoCambioNuevo(Date fechaUltimoCambioPorNuevo) {
        this.fechaUltimoCambioNuevo = fechaUltimoCambioPorNuevo;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public String getEstadoFacturaNuevo() {
        return estadoFacturaNuevo;
    }
}
