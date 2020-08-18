package com.rfs.domain.factura;

import com.rfs.fe.v43.ReceptorType;

public class Receptor {

    private String tipoIdentificacion = "02";

    private String numeroIdentificacion = "310120365421";
    private String nombre;

    public Receptor() {}

    public Receptor(ReceptorType rt) {
        this.tipoIdentificacion = rt.getIdentificacion().getTipo();
        this.numeroIdentificacion = rt.getIdentificacion().getNumero();
        this.nombre = rt.getNombre();
    }

//    public Receptor(NotaCreditoElectronica.ReceptorType rt) {
//        this.tipoIdentificacion = rt.getIdentificacion().getTipo();
//        this.numeroIdentificacion = rt.getIdentificacion().getNumero();
//        this.nombre = rt.getNombre();
//    }


    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
