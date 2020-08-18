package com.rfs.domain;

import javax.persistence.*;

/**
 * Created by alvaro on 10/17/17.
 */
@Entity
public class Tarifa {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String nombre;

    @OneToOne
    @JoinColumn(name="servicio_correduria_aduanera_id", referencedColumnName="id")
    private ServicioCorreduriaAduanera servicioCorreduriaAduanera;

    private Double monto;

    private String observacion;

    private Integer clienteId;

    @OneToOne
    @JoinColumn(name="tipo_cambio_id", referencedColumnName="id")
    private TipoCambio tipoCambio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public TipoCambio getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(TipoCambio tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public ServicioCorreduriaAduanera getServicioCorreduriaAduanera() {
        return servicioCorreduriaAduanera;
    }

    public void setServicioCorreduriaAduanera(ServicioCorreduriaAduanera servicioCorreduriaAduanera) {
        this.servicioCorreduriaAduanera = servicioCorreduriaAduanera;
    }
}
