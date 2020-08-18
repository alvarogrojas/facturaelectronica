package com.rfs;

public class ComprobanteElectronicoCR {
    private String clave;
    private String fecha;
    private ObligadoTributario emisor;
    private ObligadoTributario receptor;

    private String consecutivoReceptor;

    private String comprobanteXml;
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public ObligadoTributario getEmisor() {
        return emisor;
    }
    public void setEmisor(ObligadoTributario emisor) {
        this.emisor = emisor;
    }
    public ObligadoTributario getReceptor() {
        return receptor;
    }
    public void setReceptor(ObligadoTributario receptor) {
        this.receptor = receptor;
    }
    public String getComprobanteXml() {
        return comprobanteXml;
    }
    public void setComprobanteXml(String comprobanteXml) {
        this.comprobanteXml = comprobanteXml;
    }

    public void setConsecutivoReceptor(String consecutivoReceptor) {
        this.consecutivoReceptor = consecutivoReceptor;
    }

    public String getConsecutivoReceptor() {
        return consecutivoReceptor;
    }
}
