package com.rfs.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alvaro on 10/28/17.
 */
@Entity
public class Empresa {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String nombre;

    private String iniciales;

    private String correo1;
    private String correo2;

    private String telefono;

    private String direccion;

    private String usuario;

    private String clave;

    private String pin;

    @Column(unique = true)
    private String cedula;

    private String keyPath;
    private String logoPath;

    private String provincia;
    private String canton;
    private String distrito;
    private String tipo;

    private String recepcionCorreos;

    private Integer cantidad;

    private String idpUri;

    private String apiUri;

    private String idpClientId;

    private String estado;

    private String tipoMedida;

    private Integer ultimoCambioId;

    private Date fechaUltimoCambio;

    private Integer agregadoPorId;

    private Date fechaAgregado;

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

    public String getIniciales() {
        return iniciales;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    public String getCorreo1() {
        return correo1;
    }

    public void setCorreo1(String correo) {
        this.correo1 = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String toString() {
        return nombre;
    }

    public String getCorreo2() {
        return correo2;
    }

    public void setCorreo2(String correo2) {
        this.correo2 = correo2;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getRecepcionCorreos() {
        return recepcionCorreos;
    }

    public void setRecepcionCorreos(String recepcionCorreos) {
        this.recepcionCorreos = recepcionCorreos;
    }

    public String getIdpUri() {
        return idpUri;
    }

    public void setIdpUri(String idpUri) {
        this.idpUri = idpUri;
    }

    public String getApiUri() {
        return apiUri;
    }

    public void setApiUri(String apiUri) {
        this.apiUri = apiUri;
    }

    public String getIdpClientId() {
        return idpClientId;
    }

    public void setIdpClientId(String idpClientId) {
        this.idpClientId = idpClientId;
    }

    public String getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(String tipoMedida) {
        this.tipoMedida = tipoMedida;
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

    public Integer getAgregadoPorId() {
        return agregadoPorId;
    }

    public void setAgregadoPorId(Integer agregadoPorId) {
        this.agregadoPorId = agregadoPorId;
    }

    public Date getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(Date fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
}
