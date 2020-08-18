package com.rfs.fe;

import com.rfs.domain.factura.Emisor;
import com.rfs.domain.factura.Receptor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.NONE)
public class ComprobanteElectronicoBase {

    @XmlTransient
    protected String tipoDocumento;

    @XmlTransient
    protected Integer id;

    @XmlTransient
    protected String correo;

    @XmlTransient
    protected String fechaEmisionStr;

    @XmlTransient
    protected Integer numeroConsecutivoFe;

    @XmlTransient
    protected Emisor emisor1;

    @XmlTransient
    protected Receptor receptor1;

    @XmlTransient
    protected Short esClienteInternacional;

    @XmlTransient
    protected Integer ordenServicioId;

    @XmlTransient
    private String numeroConsecutivoComprobante;

    @XmlTransient
    private Integer facturaId;

    @XmlTransient
    private Integer empresaId;


    public Short getEsClienteInternacional() {
        return esClienteInternacional;
    }

    public void setEsClienteInternacional(Short esClienteInternacional) {
        this.esClienteInternacional = esClienteInternacional;
    }


    public void setEmisor1(Emisor emisor) {
        this.emisor1 = emisor;
    }

    public void setReceptor1(Receptor receptor) {
        this.receptor1 = receptor;
    }

    public Emisor getEmisor1() {
        return emisor1;
    }

    public Receptor getReceptor1() {
        return receptor1;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaEmisionStr() {
        return fechaEmisionStr;
    }

    public void setFechaEmisionStr(String fechaEmisionStr) {

        this.fechaEmisionStr = fechaEmisionStr;
    }

    public Integer getNumeroConsecutivoFe() {
        return numeroConsecutivoFe;
    }

    public void setNumeroConsecutivoFe(Integer numeroConsecutivoFe) {
        this.numeroConsecutivoFe = numeroConsecutivoFe;
    }

    public Integer getOrdenServicioId() {
        return ordenServicioId;
    }

    public void setOrdenServicioId(Integer ordenServicioId) {

        this.ordenServicioId = ordenServicioId;
    }

    public String getNumeroConsecutivoComprobante() {
        return numeroConsecutivoComprobante;
    }

    public void setNumeroConsecutivoComprobante(String numeroConsecutivoComprobante) {
        this.numeroConsecutivoComprobante = numeroConsecutivoComprobante;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getEmpresaId() {
        return this.empresaId;
    }
}
