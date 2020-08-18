package com.rfs.dtos;

import com.rfs.domain.Factura;
import com.rfs.service.factura.billapp.BillHelper;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

//import com.rfs.domain.FacturaServicioDetalle;

public class FacturaRegistroDTO {

    private static final String ENVIADA = "Enviada";
    private static final String ENVIANDO= "Enviando";
    private static final String NO_ENVIADA = "No Enviada";
    private static final String ACEPTADO = "Aceptado";
    private static final String RECHAZADO = "Rechazado";
    private static final String PROCESANDO = "procesando";
    private static final String PENDIENTE_RESPUESTA = "Pendiente Respuesta";
    private static final String ANULADA = "Anulada";
    private static final String PENDIENTE_HACIENDA = "Pendiente Hacienda";
    private String consecutivo;
    private String clave;

    private String tipoCedula;
    private String encargado;
    private boolean isUpdatable = true;
    private boolean anulable = true;

    private Integer facturaId;
    private Integer factura;

    private String estadoPago;
    private String pago;

    private String estado;

    private Integer reciboId;

    private Date fechaFacturacion;
    private Date fecha;
    private Date fechaRecibo;

    private String aduana;

    private String cliente;

    private String tramite;

    private String dua;

    private Double totalInmuestos;

    private Double totalTerceros;

    private Double totalServicios;

    private Double nacionalizacion;

    private Double documentacion;

    private double otros;

    private Double comisionFinanciamiento;

    private Double impuestoVentas;

    private Double total;

    private Double montoAnticipado;
    private Double subtotal = 0d;


    private String cedula;

    private Boolean esJuridico;

    private Double saldoPendiente;

    private Integer enviadaHacienda;

    private Boolean reversible = true;

    private String hacienda;

    private String estadoHacienda;

    private String cedulaJuridica;


    public FacturaRegistroDTO(Factura f) {

        try {
            BeanUtils.copyProperties(this,f);

            this.encargado = f.getEncargado().getNombre();
            this.facturaId = f.getFacturaIdentity().getFacturaId();
            this.factura = f.getFacturaIdentity().getFacturaId();
            this.pago = f.getEstadoPago();
            this.fecha = f.getFechaFacturacion();
            clave = f.getClave();
            consecutivo = f.getConsecutivo();


            this.enviadaHacienda = f.getEnviadaHacienda();

            this.cedula = f.getCliente().getCedulaJuridica();
            this.esJuridico = (f.getCliente().getEsJuridico()==null || f.getCliente().getEsJuridico()==0?false:true);
            this.tipoCedula = (f.getCliente().getEsJuridico()==null || f.getCliente().getEsJuridico()==0?"ES FISICO":"ES JURIDICO");
            setHacienda("");

            this.estadoHacienda = f.getEstadoHacienda();

            if (enviadaHacienda!=0 || this.getEstado().equals(BillHelper.INGRESADA)) {
                this.isUpdatable = false;
            } else {
                this.isUpdatable = true;
            }

            if (enviadaHacienda==2 || this.getEstado().equals(BillHelper.ANULADA)) {
                this.anulable = false;
            } else {
                this.anulable = true;
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public Integer getReciboId() {
        return reciboId;
    }

    public void setReciboId(Integer reciboId) {
        this.reciboId = reciboId;
    }

    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public String getAduana() {
        return aduana;
    }

    public void setAduana(String aduana) {
        this.aduana = aduana;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getDua() {
        return dua;
    }

    public void setDua(String dua) {
        this.dua = dua;
    }

    public Double getTotalInmuestos() {
        return totalInmuestos;
    }

    public void setTotalInmuestos(Double totalInmuestos) {
        this.totalInmuestos = totalInmuestos;
    }

    public Double getTotalTerceros() {
        return totalTerceros;
    }

    public void setTotalTerceros(Double totalTerceros) {
        this.totalTerceros = totalTerceros;
    }

    public Double getNacionalizacion() {
        return nacionalizacion;
    }

    public void setNacionalizacion(Double nacionalizacion) {
        this.nacionalizacion = nacionalizacion;
    }

    public Double getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(Double documentacion) {
        this.documentacion = documentacion;
    }

    public Double getOtros() {
        return otros;
    }

    public void setOtros(Double otros) {
        this.otros = otros;
    }

    public Double getComisionFinanciamiento() {
        return comisionFinanciamiento;
    }

    public void setComisionFinanciamiento(Double comisionFinanciamiento) {
        this.comisionFinanciamiento = comisionFinanciamiento;
    }

    public Double getImpuestoVentas() {
        return impuestoVentas;
    }

    public void setImpuestoVentas(Double impuestoVentas) {
        this.impuestoVentas = impuestoVentas;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMontoAnticipado() {
        return montoAnticipado;
    }

    public void setMontoAnticipado(Double montoAnticipado) {
        this.montoAnticipado = montoAnticipado;
    }

    public Double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(Double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public void setOtros(double otros) {
        this.otros = otros;
    }

    public Double getTotalServicios() {
        return totalServicios;
    }

    public void setTotalServicios(Double totalServicios) {
        this.totalServicios = totalServicios;
    }


    public Date getFechaRecibo() {
        return fechaRecibo;
    }

    public void setFechaRecibo(Date fechaRecibo) {
        this.fechaRecibo = fechaRecibo;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setHacienda(String hacienda) {

        if (estadoHacienda==null || estadoHacienda.equals("")){
            this.hacienda = NO_ENVIADA;
            this.reversible = true;
        } else if (enviadaHacienda==1 && estadoHacienda.equals(BillHelper.RESPUESTA_ACEPTADA)){
            this.hacienda = ACEPTADO;
            this.reversible = false;
        } else if (estadoHacienda.equals(BillHelper.RESPUESTA_RECHAZADO)){
            this.hacienda = RECHAZADO;
            this.reversible = false;
        } else if (estadoHacienda.equals(BillHelper.RESPUESTA_NO_ACEPTADO_NO_RECHAZADO)){
            this.hacienda = PENDIENTE_RESPUESTA;
            this.reversible = false;
        } else if (estadoHacienda.equals(BillHelper.RESPUESTA_PROCESANDO)){
            this.hacienda = PENDIENTE_RESPUESTA;
            this.reversible = false;
        } else if (estadoHacienda.equals(BillHelper.RESPUESTA_NO_ENVIADA)){
            this.hacienda = NO_ENVIADA;
            this.reversible = false;
        } else if (estadoHacienda.equals(BillHelper.RESPUESTA_ENVIANDO)){
            this.hacienda = ENVIANDO;
            this.reversible = false;
        } else if (enviadaHacienda==2){
            this.hacienda = ANULADA;
            this.reversible = false;
        } else if (enviadaHacienda==-1){
            this.hacienda = PENDIENTE_HACIENDA;
            this.reversible = false;
        } else {
            this.hacienda = "";
        }
    }

    public Integer getEnviadaHacienda() {
        return enviadaHacienda;
    }

    public void setEnviadaHacienda(Integer enviadaHacienda) {
        this.enviadaHacienda = enviadaHacienda;
    }

    public Boolean getReversible() {
        return reversible;
    }

    public void setReversible(Boolean reversible) {
        this.reversible = reversible;
    }

    public String getHacienda() {
        return hacienda;
    }

    public Integer getFactura() {
        return factura;
    }

    public void setFactura(Integer factura) {
        this.factura = factura;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isUpdatable() {
        return isUpdatable;
    }

    public void setUpdatable(boolean updatable) {
        isUpdatable = updatable;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipoCedula() {

        return tipoCedula;
    }

    public void setTipoCedula(String tipoCedula) {
        this.tipoCedula = tipoCedula;
    }

    public String getEstadoHacienda() {
        return estadoHacienda;
    }

    public void setEstadoHacienda(String estadoHacienda) {
        this.estadoHacienda = estadoHacienda;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
