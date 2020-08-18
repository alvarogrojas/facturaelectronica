package com.rfs.domain;
import java.util.Date;
import javax.persistence.*;

@Entity
public class ResultadoEnvioLog {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer id;

    private Integer facturaId;

    private Integer empresaId;

    private Date fechaHora;

    public String estado;

    private Integer enviadoPor;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getEnviadoPor() {
        return enviadoPor;
    }

    public void setEnviadoPor(Integer enviadoPor) {
        this.enviadoPor = enviadoPor;
    }
}
