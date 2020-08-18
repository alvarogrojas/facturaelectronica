package com.rfs.dtos;

import com.google.common.base.Strings;

import java.util.Date;
import java.util.List;

/**
 * Created by alvaro on 10/31/17.
 */
public class ReciboDTO {

    private static final String SI = "SI";

    private static final String NO = "NO";

    private Integer id;

    private String recibo;

    private String aduana;

    private String dua;

    private String encargado;

    private Integer corelacionId;

    private String consignatario;

    private String proveedor;

    private String bl;

    private String estado;

    private Integer antiguedad;

    private Date fecha;

    private String cliente;

    private String tipo;

    private String facturas = "";

    private Short previoExamen = 0, aforoFisico = 0, permisos = 0, pedimentados = 0;

    public ReciboDTO() {}


    public ReciboDTO(Integer id,
                     String recibo,
                     String aduana,
                     String consignatario,
                     String dua,
                     String encargado,
                     Integer corelacionId,
                     String estado,
                     String tipo,
                     String cliente,
                     Date fecha) {
        this.id = id;
        this.recibo = recibo;
        this.aduana = aduana;
        this.consignatario = consignatario;
        this.dua = dua;
        this.encargado = encargado;
        this.corelacionId = corelacionId;
        this.estado = estado;

        this.cliente = cliente;

        this.tipo =tipo;
        this.fecha = fecha;
        Date currentDate = new Date();
        long diff = currentDate.getTime() - fecha.getTime();



        this.antiguedad = (int) (diff / (24 * 60 * 60 * 1000));
    }

    public ReciboDTO(Integer id,
                     String recibo,
                     String aduana,
                     String consignatario,
                     String dua,
                     String encargado,
                     Integer corelacionId,
                     String estado,
                     String tipo,
                     String cliente,
                     Date fecha,
                     Short previoExamen,
                     Short aforoFisico,
                     Short permisos,
                     Short pedimentados) {
        this.id = id;
        this.recibo = recibo;
        this.aduana = aduana;
        this.consignatario = consignatario;
        this.dua = dua;
        this.encargado = encargado;
        this.corelacionId = corelacionId;
        this.estado = estado;

        this.cliente = cliente;

        this.tipo =tipo;
        this.fecha = fecha;
        Date currentDate = new Date();
        long diff = currentDate.getTime() - fecha.getTime();

        this.aforoFisico = aforoFisico;
        this.pedimentados = pedimentados;
        this.permisos = permisos;
        this.previoExamen = previoExamen;


        this.antiguedad = (int) (diff / (24 * 60 * 60 * 1000));
    }

    public ReciboDTO(Integer id,
                     String recibo,
                     String consignatario,
                     String proveedor,
                     String bl,
                     String estado,
                     String tipo,
                     String cliente,
                     Date fecha,
                     Integer antiguedad, Integer corelacionId) {
        this.id = id;
        this.recibo = recibo;
        this.tipo =tipo;
        this.consignatario = consignatario;
        this.proveedor = proveedor;
        this.bl = bl;
        this.cliente = cliente;
        this.estado = estado;
        this.fecha = fecha;
        this.antiguedad = antiguedad;
        this.corelacionId = corelacionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConsignatario() {
        return consignatario;
    }

    public void setConsignatario(String consignatario) {
        this.consignatario = consignatario;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAduana() {
        return aduana;
    }

    public void setAduana(String aduana) {
        this.aduana = aduana;
    }

    public String getDua() {
        return dua;
    }

    public void setDua(String dua) {
        this.dua = dua;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Integer getCorelacionId() {
        return corelacionId;
    }

    public void setCorelacionId(Integer corelacionId) {
        this.corelacionId = corelacionId;
    }

    public String getFacturas() {
        return facturas;
    }

    public void setFacturas(String facturas) {
        this.facturas = facturas;
    }

    public void addFacturas(List<FacturaReciboDTO> facturaList) {
        if (facturaList==null || facturaList.size()==0) {
            return;
        }
        String facturacion = "";
        for (FacturaReciboDTO f: facturaList) {
            if (!Strings.isNullOrEmpty(facturacion)) {
                facturacion = facturacion + " / " + f.getFacturaId() ;
            } else {
                facturacion = facturacion + f.getFacturaId();
            }
        }
        if (!Strings.isNullOrEmpty(this.facturas)) {
            this.facturas = this.facturas + " / " + facturacion ;
        } else {
            this.facturas = this.facturas + facturacion;

        }

    }

    public String getExamenPrevioStr() {
        if (this.previoExamen==null || this.previoExamen!=1) {
            return NO;
        } else {
            return SI;
        }

    }

    public String getAforoFisicoStr() {
        if (this.aforoFisico==null || this.aforoFisico!=1) {
            return NO;
        } else {
            return SI;
        }
    }

    public String getPermisosStr() {
        if (this.permisos==null || this.permisos!=1) {
            return NO;
        } else {
            return SI;
        }
    }

    public String getPedimentadosStr() {
        if (this.pedimentados==null || this.pedimentados!=1) {
            return NO;
        } else {
            return SI;
        }
    }
}
