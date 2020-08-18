package com.rfs.domain.factura;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class BillSender {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idSend;

    private Date dateSent;

    private String status;

    private String observaciones;

    private Integer ingresadoPor;

    private Date fechaIngreso;

    private Integer ultimoCambioId;

    private Date fechaUltimoCambio;
    private Integer totalEnviadas;

    private String tipoDocumento;

    private Integer empresaId;

    public Integer getIdSend() {
        return idSend;
    }

    public void setIdSend(Integer id) {
        this.idSend = id;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date fecha) {
        this.dateSent = fecha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public Integer getTotalEnviadas() {
        return totalEnviadas;
    }

    public void setTotalEnviadas(Integer totalEnviadas) {
        this.totalEnviadas = totalEnviadas;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
}
