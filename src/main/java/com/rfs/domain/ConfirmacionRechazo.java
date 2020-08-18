package com.rfs.domain;
import java.util.Date;
import javax.persistence.*;

@Entity
public class ConfirmacionRechazo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer id;

    private String clave;

    private String fileName;

    private String identificadorEmisor;

    private Date fechaEmision;

    public Double montoTotal;

    private Date fechaConfirmacionRechazo;

    private String estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIdentificadorEmisor() {
        return identificadorEmisor;
    }

    public void setIdentificadorEmisor(String identificadorEmisor) {
        this.identificadorEmisor = identificadorEmisor;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Date getFechaConfirmacionRechazo() {
        return fechaConfirmacionRechazo;
    }

    public void setFechaConfirmacionRechazo(Date fechaConfirmacionRechazo) {
        this.fechaConfirmacionRechazo = fechaConfirmacionRechazo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
