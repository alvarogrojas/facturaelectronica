package com.rfs.dtos;

/**
 * Created by alvaro on 11/7/17.
 */
public class PendienteDTO {
    private Integer id;
    private String recibo;
    private String cliente;
    private String observaciones;
    private String actualizado = "NO";
    private String diasNoActualizado = "NA";

    public PendienteDTO(Integer id, String recibo, String cliente, String observaciones, Boolean actualizado, Integer diasNoActualizado) {
        this.id = id;
        this.recibo = recibo;
        this.cliente = cliente;
        this.observaciones = observaciones;
        if (diasNoActualizado!=null) {
            this.diasNoActualizado = diasNoActualizado.toString() + " dias";
        }
        if (actualizado!=null && actualizado) {
            this.actualizado = "SI";
        }
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getActualizado() {
        return actualizado;
    }

    public void setActualizado(String actualizado) {
        this.actualizado = actualizado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiasNoActualizado() {
        return diasNoActualizado;
    }

    public void setDiasNoActualizado(String diasNoActualizado) {
        this.diasNoActualizado = diasNoActualizado;
    }
}
