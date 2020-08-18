package com.rfs.dtos;

import java.util.List;

/**
 * Created by alvaro on 10/17/17.
 */
public class ClienteDTO {
    private Integer id;

    private String nombre;

    private List<TarifaDTO> tarifas;

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

    public List<TarifaDTO> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<TarifaDTO> tarifas) {
        this.tarifas = tarifas;
    }
}
