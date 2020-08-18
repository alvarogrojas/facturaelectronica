package com.rfs.dtos;

import com.rfs.domain.factura.BillSenderDetail;
import com.rfs.fe.v43.mh.MensajeHacienda;
import com.rfs.service.FacturaElectronicaUnmarshaller;

import javax.xml.bind.JAXBException;
import java.util.Date;
import java.util.List;

public class RespuestaHaciendaDTO {

    private Integer id;

    private String clave;

    private Integer identificacion;

    private Date fechaEnvio;

    private String estado;

    private String detalle;

    private Date dateSent;

    private Integer billId;

    private String consecutivo;

    public RespuestaHaciendaDTO() {}

    public RespuestaHaciendaDTO(BillSenderDetail bsd) {
        this.clave = bsd.getClave();
        this.id = bsd.getBillId();
        this.estado = bsd.getStatus();
        this.consecutivo = bsd.getConsecutivo();
        this.detalle = bsd.getObservaciones();
        this.dateSent = bsd.getDateSent();
        if (estado==null || estado.toLowerCase().equals("rechazado")) {
            getMessageFromSystem(bsd);
        }
    }

    private void getMessageFromSystem(BillSenderDetail bsd) {

        try {
            String fileName = bsd.getPath() + "mensajehacienda_" + this.clave + ".xml";
            FacturaElectronicaUnmarshaller facturaElectronicaUnmarshaller = new FacturaElectronicaUnmarshaller();

            MensajeHacienda mh = facturaElectronicaUnmarshaller.mensajeHaciendaXMLFile(fileName);
            this.detalle = mh.getDetalleMensaje();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Integer getBillId() {
        return billId;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

}
//package com.rfs.dtos;
//
//import com.rfs.domain.ResultadoEnvioLog;
//
//import java.util.List;
//
//public class RespuestaHaciendaDTO {
//
//    private Integer facturaId;
//
//    private List<ResultadoEnvioLog> respuestas;
//
//    public Integer getFacturaId() {
//        return facturaId;
//    }
//
//    public void setFacturaId(Integer facturaId) {
//        this.facturaId = facturaId;
//    }
//
//    public List<ResultadoEnvioLog> getRespuestas() {
//        return respuestas;
//    }
//
//    public void setRespuestas(List<ResultadoEnvioLog> respuestas) {
//        this.respuestas = respuestas;
//    }
//}
