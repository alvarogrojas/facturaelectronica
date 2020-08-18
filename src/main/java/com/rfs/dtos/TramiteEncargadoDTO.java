package com.rfs.dtos;

public class TramiteEncargadoDTO {

    private Double total;

    private String encargado;

    private String tipo;

    private String cliente;

    private Long cantidad;

    private String aduana;

    private String regimen;

    public TramiteEncargadoDTO() {}

    public TramiteEncargadoDTO(Double total, Long cantidad,String encargado, String cliente, String tipo, String aduana) {
        this.encargado = encargado;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.cliente = cliente;
        this.total = total;
        this.aduana = aduana;
    }

    public TramiteEncargadoDTO(Long cantidad, String encargado, String cliente, String tipo, String aduana) {
        this.encargado = encargado;
        this.tipo = tipo;
        this.cliente = cliente;
        this.cantidad = cantidad;
        this.aduana = aduana;
    }

    public TramiteEncargadoDTO(Long cantidad, String encargado, String regimen, String aduana) {
        this.encargado = encargado;
        this.regimen = regimen;
        this.cantidad = cantidad;
        this.aduana = aduana;
    }


    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public String getAduana() {
        return aduana;
    }

    public void setAduana(String aduana) {
        this.aduana = aduana;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public Double getTotal() {
        return total;
    }
}

