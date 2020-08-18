package com.rfs.dtos;

import java.util.Date;

/**
 * Created by alvaro on 11/9/17.
 */
public class PendientesFacturarDTO {

    private Integer id;

    private String recibo;

    private String cliente;

    private String dua;

    private String encargado;

    private String ticaEstado = "NA";

    private Date fecha;

    private Integer corelacionId;

    public PendientesFacturarDTO(Integer id, String recibo, String cliente, String dua, String encargado, Date fecha, String ticaEstado, Integer corelacionId) {
        this.id = id;
        this.recibo = recibo;
        this.cliente = cliente;
        this.dua = dua;
        this.encargado = encargado;
        this.fecha = fecha;
        this.ticaEstado = ticaEstado;
        this.corelacionId = corelacionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDua() {
        return dua;
    }

    public void setDua(String dua) {
        this.dua = dua;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fechaDua) {
        this.fecha = fechaDua;
    }

    public String getTicaEstado() {
        return ticaEstado;
    }

    public void setTicaEstado(String ticaEstado) {
        this.ticaEstado = ticaEstado;
    }

    public Integer getCorelacionId() {
        return corelacionId;
    }

    public void setCorelacionId(Integer corelacionId) {
        this.corelacionId = corelacionId;
    }
}
