package com.rfs.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by alvaro on 11/2/17.
 */
@Entity
public class Parametro {
    @Id
    private Integer id;

    private String nombre;

    private String tipo;

    private String valor;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String value) {
        this.valor = value;
    }
}
