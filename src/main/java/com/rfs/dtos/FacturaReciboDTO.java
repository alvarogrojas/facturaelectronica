package com.rfs.dtos;

import java.util.Date;

/**
 * Created by alvaro on 11/10/17.
 */
public class FacturaReciboDTO {

    private static final String ENVIADA = "Enviada";
    private static final String NO_ENVIADA = "No Enviada";
    private static final String ANULADA = "ANULADA";

    private Integer facturaId;

    private Integer reciboId;

    private Date fechaFacturacion;

    private String encargado;

    private Boolean reversible = true;

    private String cliente;

    private Integer enviadaHacienda;

    private String hacienda;

    private Boolean isUpdatable = true;

    public FacturaReciboDTO(Integer facturaId, Integer reciboId, Date fechaFacturacion, String usuario, Integer enviadaHacienda) {
        this(facturaId,reciboId,fechaFacturacion,usuario,true, enviadaHacienda);
    }

    public FacturaReciboDTO(Integer facturaId, Integer reciboId, Date fechaFacturacion, String usuario, String cliente, Integer enviadaHacienda) {
        this.cliente = cliente;
//        this(facturaId,reciboId,fechaFacturacion,usuario,true);
        this.facturaId = facturaId;
        this.reciboId = reciboId;
        this.fechaFacturacion = fechaFacturacion;
        this.encargado = usuario;
        this.reversible = reversible;
        this.enviadaHacienda = enviadaHacienda;
        if (enviadaHacienda!=0) {
            this.isUpdatable = false;
        }
        setHacienda("");

    }

    public FacturaReciboDTO(Integer facturaId, Integer reciboId, Date fechaFacturacion, String usuario, Boolean reversible,Integer enviadaHacienda) {
        this.facturaId = facturaId;
        this.reciboId = reciboId;
        this.fechaFacturacion = fechaFacturacion;
        this.encargado = usuario;
        this.reversible = reversible;
        this.enviadaHacienda = enviadaHacienda;
        if (enviadaHacienda!=0) {
            this.isUpdatable = false;
        }
        setHacienda("");
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
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

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Boolean getReversible() {
        return reversible;
    }

    public void setReversible(Boolean reversible) {
        this.reversible = reversible;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getEnviadaHacienda() {
        return enviadaHacienda;
    }

    public void setEnviadaHacienda(Integer enviadaHacienda) {
        this.enviadaHacienda = enviadaHacienda;
        this.setHacienda("");
    }

    public void setHacienda(String hacienda) {
        if (enviadaHacienda==0){
            this.hacienda = NO_ENVIADA;
            this.reversible = true;
        } else if (enviadaHacienda==1){
            this.hacienda = ENVIADA;
            this.reversible = false;
        } else if (enviadaHacienda==2){
            this.hacienda = ANULADA;
            this.reversible = false;
        } else {
            this.hacienda = "";
        }
    }

    public String getHacienda() {
        return hacienda;
    }

    public Boolean getUpdatable() {
        return isUpdatable;
    }

    public void setUpdatable(Boolean updatable) {
        isUpdatable = updatable;
    }
}
