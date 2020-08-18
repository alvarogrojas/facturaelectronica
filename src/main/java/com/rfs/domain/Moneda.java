package com.rfs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Moneda {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String nombre;

    private Short esMonedaSistema;

    private Double tipoCambioCompra;

    private Double tipoCambioVenta;

    private String simbolo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Short getEsMonedaSistema() {
        return esMonedaSistema;
    }

    public void setEsMonedaSistema(Short esMonedaSistema) {
        this.esMonedaSistema = esMonedaSistema;
    }

    public Double getTipoCambioCompra() {
        return tipoCambioCompra;
    }

    public void setTipoCambioCompra(Double tipoCambioCompra) {
        this.tipoCambioCompra = tipoCambioCompra;
    }

    public Double getTipoCambioVenta() {
        return tipoCambioVenta;
    }

    public void setTipoCambioVenta(Double tipoCambioVenta) {
        this.tipoCambioVenta = tipoCambioVenta;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}
