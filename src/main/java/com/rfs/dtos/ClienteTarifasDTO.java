package com.rfs.dtos;

import com.rfs.domain.ServicioCorreduriaAduanera;
import com.rfs.domain.Tarifa;
import com.rfs.domain.TipoCambio;

/**
 * Created by alvaro on 10/24/17.
 */
public class ClienteTarifasDTO {

    private Iterable<Tarifa> tarifas;
    private Iterable<TipoCambio> tipoCambios;
    private Iterable<ServicioCorreduriaAduanera> servicios;

    private String nombre;

    private Integer id;

    public Iterable<Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(Iterable<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Iterable<TipoCambio> getTipoCambios() {
        return tipoCambios;
    }

    public void setTipoCambios(Iterable<TipoCambio> tipoCambios) {

        this.tipoCambios = tipoCambios;
    }

    public Iterable<ServicioCorreduriaAduanera> getServicios() {
        return servicios;
    }

    public void setServicios(Iterable<ServicioCorreduriaAduanera> servicios) {
        this.servicios = servicios;
    }
}
