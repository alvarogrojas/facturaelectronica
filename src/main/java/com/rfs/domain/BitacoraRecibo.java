package com.rfs.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alvaro on 11/3/17.
 */
@Entity
public class BitacoraRecibo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer reciboId;

    private Integer aduanaIdNuevo;

    private Integer aduanaIdAnterior;

    @OneToOne
    @JoinColumn(name="tipo_id_nuevo", referencedColumnName="id")
    private TipoTramite tipoNuevo;
    @OneToOne
    @JoinColumn(name="tipo_id_anterior", referencedColumnName="id")
    private TipoTramite tipoAnterior;

    private String consignatarioAnterior;

    private String consignatarioNuevo;

    private String proveedorAnterior;

    private String proveedorNuevo;

    private String blAnterior;

    private String blNuevo;

    private String facturaAnterior;

    private String facturaNuevo;

    private String duaAnterior;

    private String duaNuevo;

    private String observacionesAnterior;

    private String observacionesNuevo;

    private String estadoAnterior;

    private String estadoNuevo;

    private Integer encargadoIdAnterior;

    private Integer encargadoIdNuevo;

    private Integer corelacionIdAnterior;

    private Integer corelacionIdNuevo;

    @OneToOne
    @JoinColumn(name="cliente_id_anterior", referencedColumnName="id")
    private Cliente clienteAnterior;

    @OneToOne
    @JoinColumn(name="cliente_id_nuevo", referencedColumnName="id")
    private Cliente clienteNuevo;

    private Integer ultimoCambioId;

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

    public Integer getAduanaIdNuevo() {
        return aduanaIdNuevo;
    }

    public void setAduanaIdNuevo(Integer aduanaIdNuevo) {
        this.aduanaIdNuevo = aduanaIdNuevo;
    }

    public Integer getAduanaIdAnterior() {
        return aduanaIdAnterior;
    }

    public void setAduanaIdAnterior(Integer aduanaIdAnterior) {
        this.aduanaIdAnterior = aduanaIdAnterior;
    }

    public TipoTramite getTipoNuevo() {
        return tipoNuevo;
    }

    public void setTipoNuevo(TipoTramite tipoNuevo) {
        this.tipoNuevo = tipoNuevo;
    }

    public TipoTramite getTipoAnterior() {
        return tipoAnterior;
    }

    public void setTipoAnterior(TipoTramite tipoAnterior) {
        this.tipoAnterior = tipoAnterior;
    }

    public String getConsignatarioAnterior() {
        return consignatarioAnterior;
    }

    public void setConsignatarioAnterior(String consignatarioAnterior) {
        this.consignatarioAnterior = consignatarioAnterior;
    }

    public String getConsignatarioNuevo() {
        return consignatarioNuevo;
    }

    public void setConsignatarioNuevo(String consignatarioNuevo) {
        this.consignatarioNuevo = consignatarioNuevo;
    }

    public String getProveedorAnterior() {
        return proveedorAnterior;
    }

    public void setProveedorAnterior(String proveedorAnterior) {
        this.proveedorAnterior = proveedorAnterior;
    }

    public String getProveedorNuevo() {
        return proveedorNuevo;
    }

    public void setProveedorNuevo(String proveedorNuevo) {
        this.proveedorNuevo = proveedorNuevo;
    }

    public String getBlAnterior() {
        return blAnterior;
    }

    public void setBlAnterior(String blAnterior) {
        this.blAnterior = blAnterior;
    }

    public String getBlNuevo() {
        return blNuevo;
    }

    public void setBlNuevo(String blNuevo) {
        this.blNuevo = blNuevo;
    }

    public String getFacturaAnterior() {
        return facturaAnterior;
    }

    public void setFacturaAnterior(String facturaAnterior) {
        this.facturaAnterior = facturaAnterior;
    }

    public String getFacturaNuevo() {
        return facturaNuevo;
    }

    public void setFacturaNuevo(String facturaNuevo) {
        this.facturaNuevo = facturaNuevo;
    }

    public String getDuaAnterior() {
        return duaAnterior;
    }

    public void setDuaAnterior(String duaAnterior) {
        this.duaAnterior = duaAnterior;
    }

    public String getDuaNuevo() {
        return duaNuevo;
    }

    public void setDuaNuevo(String duaNuevo) {
        this.duaNuevo = duaNuevo;
    }

    public String getObservacionesAnterior() {
        return observacionesAnterior;
    }

    public void setObservacionesAnterior(String observacionesAnterior) {
        this.observacionesAnterior = observacionesAnterior;
    }

    public String getObservacionesNuevo() {
        return observacionesNuevo;
    }

    public void setObservacionesNuevo(String observacionesNuevo) {
        this.observacionesNuevo = observacionesNuevo;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
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

    public Integer getCorelacionIdAnterior() {
        return corelacionIdAnterior;
    }

    public void setCorelacionIdAnterior(Integer corelacionIdAnterior) {
        this.corelacionIdAnterior = corelacionIdAnterior;
    }

    public Integer getCorelacionIdNuevo() {
        return corelacionIdNuevo;
    }

    public void setCorelacionIdNuevo(Integer corelacionIdNuevo) {
        this.corelacionIdNuevo = corelacionIdNuevo;
    }

    public Cliente getClienteAnterior() {
        return clienteAnterior;
    }

    public void setClienteAnterior(Cliente clienteAnterior) {
        this.clienteAnterior = clienteAnterior;
    }

    public Cliente getClienteNuevo() {
        return clienteNuevo;
    }

    public void setClienteNuevo(Cliente clienteNuevo) {
        this.clienteNuevo = clienteNuevo;
    }

    public Integer getUltimoCambioId() {
        return ultimoCambioId;
    }

    public void setUltimoCambioId(Integer ultimoCambioId) {
        this.ultimoCambioId = ultimoCambioId;
    }

    public Date getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(Date fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }

    private Date fechaUltimoCambio;
}
