package com.rfs.dtos;

import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.fe.v43.mh.MensajeHacienda;

import java.util.Date;

public class FacturaElectronicaDTO {

    private String fullPathFileName;

    private FacturaElectronica facturaElectronica;

    private MensajeHacienda mensajeHacienda;

    private String clave;

    private String numeroConsecutivo;

    private String emisorNombre;

    private Date fechaEmision;

    private String numeroCedulaEmisor;

    private String nombreReceptor;

    private String receptorNombre;

    private String numeroCedulaReceptor;

    private Double totalFactura;

    private Double montoTotalImpuesto;
    private Double totalDescuentos;
    private Double totalVentaNeta;
    private Double totalVenta;
    private boolean isFacturaComprobante;

    public String getFullPathFileName() {
        return fullPathFileName;
    }

    public void setFullPathFileName(String fullPathFileName) {
        this.fullPathFileName = fullPathFileName;
    }

    public FacturaElectronica getFacturaElectronica() {
        return facturaElectronica;
    }

    public void setFacturaElectronica(FacturaElectronica facturaElectronica) {
        this.facturaElectronica = facturaElectronica;
    }

    public MensajeHacienda getMensajeHacienda() {
        return mensajeHacienda;
    }

    public void setMensajeHacienda(MensajeHacienda mensajeHacienda) {
        this.mensajeHacienda = mensajeHacienda;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNumeroConsecutivo() {
        return numeroConsecutivo;
    }

    public void setNumeroConsecutivo(String numeroConsecutivo) {
        this.numeroConsecutivo = numeroConsecutivo;
    }

    public String getEmisorNombre() {
        return emisorNombre;
    }

    public void setEmisorNombre(String emisorNombre) {
        this.emisorNombre = emisorNombre;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNumeroCedulaEmisor() {
        return numeroCedulaEmisor;
    }

    public void setNumeroCedulaEmisor(String numeroCedulaEmisor) {
        this.numeroCedulaEmisor = numeroCedulaEmisor;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getReceptorNombre() {
        return receptorNombre;
    }

    public void setReceptorNombre(String receptorNombre) {
        this.receptorNombre = receptorNombre;
    }

    public String getNumeroCedulaReceptor() {
        return numeroCedulaReceptor;
    }

    public void setNumeroCedulaReceptor(String numeroCedulaReceptor) {
        this.numeroCedulaReceptor = numeroCedulaReceptor;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public Double getMontoTotalImpuesto() {
        return montoTotalImpuesto;
    }

    public void setMontoTotalImpuesto(Double montoTotalImpuesto) {
        this.montoTotalImpuesto = montoTotalImpuesto;
    }

    public void setIsFacturaComprobante(boolean isFacturaComprobante) {
        this.isFacturaComprobante = isFacturaComprobante;
    }

    public boolean getIsFacturaComprobante() {
        return isFacturaComprobante;
    }

    public void setFacturaComprobante(boolean isFacturaComprobante) {
        this.isFacturaComprobante = isFacturaComprobante;
    }

    public Double getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(Double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public Double getTotalVentaNeta() {
        return totalVentaNeta;
    }

    public void setTotalVentaNeta(Double totalVentaNeta) {
        this.totalVentaNeta = totalVentaNeta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }
}
