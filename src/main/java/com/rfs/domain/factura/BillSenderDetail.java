package com.rfs.domain.factura;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class BillSenderDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idSendDetail;

    private Integer idSend;

    private Date dateSent;

    private String status;

    private Integer billId;
    private Integer enviadaHacienda;
    private Integer validada;
    private Integer enviadaCliente;

    private Integer ingresadoPor;

    private Date fechaIngreso;

    private Integer ultimoCambioId;

    private Date fechaUltimoCambio;

    private String path;

    private String clave;

    private String consecutivo;

    private String tipo;

    private Integer empresaId;

    private String observaciones;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIngresadoPor() {
        return ingresadoPor;
    }

    public void setIngresadoPor(Integer ingresadoPor) {
        this.ingresadoPor = ingresadoPor;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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



    public Integer getEnviadaHacienda() {
        return enviadaHacienda;
    }

    public void setEnviadaHacienda(Integer enviadaHacienda) {
        this.enviadaHacienda = enviadaHacienda;
    }

    public Integer getValidada() {
        return validada;
    }

    public void setValidada(Integer validada) {
        this.validada = validada;
    }

    public Integer getEnviadaCliente() {
        return enviadaCliente;
    }

    public void setEnviadaCliente(Integer enviadaCliente) {
        this.enviadaCliente = enviadaCliente;
    }

    public Integer getIdSendDetail() {
        return idSendDetail;
    }

    public void setIdSendDetail(Integer idSendDetail) {
        this.idSendDetail = idSendDetail;
    }

    public Integer getIdSend() {
        return idSend;
    }

    public void setIdSend(Integer idSend) {
        this.idSend = idSend;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
