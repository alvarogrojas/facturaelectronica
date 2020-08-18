package com.rfs.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alvaro on 10/17/17.
 */
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String nombre;

    private String correo ;
    private String telefono ;
     private String cedulaJuridica;
     private String direccion;
     private String categoria;
     private String representanteLegal;
     private String representanteCedula;
     private String contacto1;
     private String contacto1Puesto;
     private String contacto1Correo;
     private String contacto1Telefono;
     private String contacto2;
     private String contacto2Puesto;
     private String contacto2Correo;
     private String contacto2Telefono;
     private String contacto3;
     private String contacto3Puesto;
     private String contacto3Correo;
     private String contacto3Telefono;
     private Short esInternacional;

    private Short esJuridico;

    private String contacto4;
    private String contacto4Puesto;
    private String contacto4Correo;
    private String contacto4Telefono;
     private String agente;
     private String ejecutivo;
     private String requerimientosOperativos;
     private String requerimientosFacturacion;
     private String observaciones;
    private Integer ultimoCambioId;
    private Date fechaUltimoCambio;

    private Integer tieneCredito;
    private Integer diasCredito;

    private String estado;

    @OneToOne
    @JoinColumn(name="empresa_id", referencedColumnName="id")
    private Empresa empresa;
    private Integer esExonerado = 0;


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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCedulaJuridica() {
        return cedulaJuridica;
    }

    public void setCedulaJuridica(String cedulaJuridica) {
        this.cedulaJuridica = cedulaJuridica;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getRepresentanteCedula() {
        return representanteCedula;
    }

    public void setRepresentanteCedula(String representanteCedula) {
        this.representanteCedula = representanteCedula;
    }

    public String getContacto1() {
        return contacto1;
    }

    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    public String getContacto1Puesto() {
        return contacto1Puesto;
    }

    public void setContacto1Puesto(String contacto1Puesto) {
        this.contacto1Puesto = contacto1Puesto;
    }

    public String getContacto1Correo() {
        return contacto1Correo;
    }

    public void setContacto1Correo(String contacto1Correo) {
        this.contacto1Correo = contacto1Correo;
    }

    public String getContacto1Telefono() {
        return contacto1Telefono;
    }

    public void setContacto1Telefono(String contacto1Telefono) {
        this.contacto1Telefono = contacto1Telefono;
    }

    public String getContacto2() {
        return contacto2;
    }

    public void setContacto2(String contacto2) {
        this.contacto2 = contacto2;
    }

    public String getContacto2Puesto() {
        return contacto2Puesto;
    }

    public void setContacto2Puesto(String contacto2Puesto) {
        this.contacto2Puesto = contacto2Puesto;
    }

    public String getContacto2Correo() {
        return contacto2Correo;
    }

    public void setContacto2Correo(String contacto2Correo) {
        this.contacto2Correo = contacto2Correo;
    }

    public String getContacto2Telefono() {
        return contacto2Telefono;
    }

    public void setContacto2Telefono(String contacto2Telefono) {
        this.contacto2Telefono = contacto2Telefono;
    }

    public String getContacto3() {
        return contacto3;
    }

    public void setContacto3(String contacto3) {
        this.contacto3 = contacto3;
    }

    public String getContacto3Puesto() {
        return contacto3Puesto;
    }

    public void setContacto3Puesto(String contacto3Puesto) {
        this.contacto3Puesto = contacto3Puesto;
    }

    public String getContacto3Correo() {
        return contacto3Correo;
    }

    public void setContacto3Correo(String contacto3Correo) {
        this.contacto3Correo = contacto3Correo;
    }

    public String getContacto3Telefono() {
        return contacto3Telefono;
    }

    public void setContacto3Telefono(String contacto3Telefono) {
        this.contacto3Telefono = contacto3Telefono;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public String getEjecutivo() {
        return ejecutivo;
    }

    public void setEjecutivo(String ejecutivo) {
        this.ejecutivo = ejecutivo;
    }

    public String getRequerimientosOperativos() {
        return requerimientosOperativos;
    }

    public void setRequerimientosOperativos(String requerimientosOperativos) {
        this.requerimientosOperativos = requerimientosOperativos;
    }

    public String getRequerimientosFacturacion() {
        return requerimientosFacturacion;
    }

    public void setRequerimientosFacturacion(String requerimientosFacturacion) {
        this.requerimientosFacturacion = requerimientosFacturacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;

    }

    public Integer getTieneCredito() {
        return tieneCredito;
    }

    public void setTieneCredito(Integer tieneCredito) {
        this.tieneCredito = tieneCredito;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {

        this.diasCredito = diasCredito;
    }

    public String getContacto4() {
        return contacto4;
    }

    public void setContacto4(String contacto4) {
        this.contacto4 = contacto4;
    }

    public String getContacto4Puesto() {
        return contacto4Puesto;
    }

    public void setContacto4Puesto(String contacto4Puesto) {
        this.contacto4Puesto = contacto4Puesto;
    }

    public String getContacto4Correo() {
        return contacto4Correo;
    }

    public void setContacto4Correo(String contacto4Correo) {
        this.contacto4Correo = contacto4Correo;
    }

    public String getContacto4Telefono() {
        return contacto4Telefono;
    }

    public void setContacto4Telefono(String contacto4Telefono) {
        this.contacto4Telefono = contacto4Telefono;
    }

    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;

        Cliente cliente = (Cliente) o;

        if (id != null ? !id.equals(cliente.id) : cliente.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Short getEsJuridico() {
        return esJuridico;
    }

    public void setEsJuridico(Short esJuridico) {
        this.esJuridico = esJuridico;
    }

    public Short getEsInternacional() {
        return esInternacional;
    }

    public void setEsInternacional(Short esInternacional) {
        this.esInternacional = esInternacional;
    }

    public Integer getEsExonerado() {
        return esExonerado;
    }

    public void setEsExonerado(Integer esExonerado) {
        this.esExonerado = esExonerado;
    }
}
