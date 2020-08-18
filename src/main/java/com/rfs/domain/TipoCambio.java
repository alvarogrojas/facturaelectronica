package com.rfs.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alvaro on 10/24/17.
 */
@Entity
public class TipoCambio {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String nombre;

    private Double venta;

    private Double compra;

    private String descripcion;

    private Integer esDefault;

    private String simbol;

    private Integer ultimoCambioId;
    private Date fechaUltimoCambio;

    @OneToOne
    @JoinColumn(name="empresa_id", referencedColumnName="id")
    private Empresa empresa;

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

    public Double getVenta() {
        return venta;
    }

    public void setVenta(Double venta) {
        this.venta = venta;
    }

    public Double getCompra() {
        return compra;
    }

    public void setCompra(Double compra) {
        this.compra = compra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEsDefault() {
        return esDefault;
    }

    public void setEsDefault(Integer esDefault) {
        this.esDefault = esDefault;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
