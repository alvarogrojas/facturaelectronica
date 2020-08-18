package com.rfs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by alvaro on 10/17/17.
 */
@Entity
public class ConfirmaRechazaDocumento {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String clave;

    private String consecutivo;

    private String emisor;

    private Integer empresaId;

    private String identificacionEmisor;

    private Date fechaEmision;

    private Double totalVenta;

    private Double totalImpuesto;

    private Double totalComprobante;

    private String estado;

    private String estadoEnviadoHacienda;

    private String pathOriginalFile;

    private Integer ultimoCambioPor;

    private Date fechaUltimoCambio;

    private String tipo;
    private Integer billSenderId;
    private String numeroConsecutivoReceptor;
    private String mensaje;
    private String moneda;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer id) {
        this.empresaId = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getIdentificacionEmisor() {
        return identificacionEmisor;
    }

    public void setIdentificacionEmisor(String identificacionEmisor) {
        this.identificacionEmisor = identificacionEmisor;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Double getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(Double totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    public Double getTotalComprobante() {
        return totalComprobante;
    }

    public void setTotalComprobante(Double totalComprobante) {
        this.totalComprobante = totalComprobante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getEstadoEnviadoHacienda() {
        return estadoEnviadoHacienda;
    }

    public void setEstadoEnviadoHacienda(String estadoEnviadoHacienda) {
        this.estadoEnviadoHacienda = estadoEnviadoHacienda;
    }

    public String getPathOriginalFile() {
        return pathOriginalFile;
    }

    public void setPathOriginalFile(String pathOriginalFile) {
        this.pathOriginalFile = pathOriginalFile;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getBillSenderId() {
        return billSenderId;
    }

    public void setBillSenderId(Integer billSenderId) {
        this.billSenderId = billSenderId;
    }

    public String getNumeroConsecutivoReceptor() {
        return numeroConsecutivoReceptor;
    }

    public void setNumeroConsecutivoReceptor(String numeroConsecutivoReceptor) {
        this.numeroConsecutivoReceptor = numeroConsecutivoReceptor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfirmaRechazaDocumento aduana = (ConfirmaRechazaDocumento) o;

        return id != null ? id.equals(aduana.id) : aduana.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}