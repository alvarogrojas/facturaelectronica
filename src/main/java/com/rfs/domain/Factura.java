package com.rfs.domain;

import com.rfs.dtos.FacturaDetalleDTO;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by alvaro on 10/30/17.
 */
@Entity
public class Factura {

    @EmbeddedId
    private FacturaIdentity facturaIdentity;

    private String numeroFactura;

    private Date fechaFacturacion;

    private Integer ultimoCambioPor;

    private Date fechaUltimoCambio;

    @OneToOne
    @JoinColumn(name="cliente_id", referencedColumnName="id")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name="tipo_actividad_economica_id", referencedColumnName="id")
    private TipoActividadEconomica tipoActividadEconomica;

    private String observaciones;


    private String estado = "Activa"; // ACTIVA | APROBADA | ANULADA

    private String estadoPago = "Pendiente"; // ACTIVA | APROBADA | ANULADA

    private Integer credito;

    private Integer diasCredito;

    private Integer enviadaHacienda;

    @OneToOne
    @JoinColumn(name="tipo_cambio_id", referencedColumnName="id")
    private TipoCambio tipoCambio;

    private Double subtotal = 0d;


    private Double total = 0d;

    private Double montoAnticipado = 0d;

    private Double saldoPendiente = 0d;

    private Double impuestoVentas = 0d;
    private Double exonerado = 0d;

    private Double tipoCambioMonto = 0d;

    private Double totalTerceros = 0d;

    private Date fechaVencimiento;

    private String clave;

    private String consecutivo;

    private String estadoHacienda;
    //////////////////////////////////////////////////////

    @OneToOne
    @JoinColumn(name="encargado_id", referencedColumnName="id")
    private Usuario encargado;


    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    @OrderBy("id")
    private SortedSet<FacturaDetalle> facturaDetalle;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }


    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public Integer getUltimoCambioPor() {
        return ultimoCambioPor;
    }

    public void setUltimoCambioPor(Integer ultimoCambioPor) {
        this.ultimoCambioPor = ultimoCambioPor;
    }

    public Date getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(Date fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }
    

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getCredito() {
        return credito;
    }

    public void setCredito(Integer credito) {
        this.credito = credito;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }


    public TipoCambio getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(TipoCambio tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
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


    public SortedSet<FacturaDetalle> getFacturaDetalle() {
        return facturaDetalle;
    }

    public void setFacturaDetalle(SortedSet<FacturaDetalle> facturaDetalles, Factura id) {
        if (facturaDetalle!=null) {
            this.facturaDetalle.clear();

        } else {
            this.facturaDetalle = new TreeSet<>();
        }
        if (facturaDetalles != null) {
            this.facturaDetalle.addAll(facturaDetalles);
        }
        //this.facturaDetalle = facturaDetalles;
        if (this.facturaDetalle!=null && this.facturaDetalle.size() > 0) {
            for (FacturaDetalle fd: facturaDetalle) {
                fd.setFactura(id);
            }
        }
    }

    public void setFacturaDetalleDTO(List<FacturaDetalleDTO> facturaDetallesDTO, Factura id) {
        
        if (facturaDetallesDTO!=null && facturaDetallesDTO.size() > 0) {
            this.facturaDetalle = new TreeSet<FacturaDetalle>();
            FacturaDetalle d;
            
            for (FacturaDetalleDTO fd:facturaDetallesDTO) {
                d = new FacturaDetalle();
                d.setFactura(id);
                try {
                    BeanUtils.copyProperties(d,fd);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                this.facturaDetalle.add(d);
            }
        }
    }


    public Double getTipoCambioMonto() {
        return tipoCambioMonto;
    }

    public void setTipoCambioMonto(Double tipoCambioMonto) {
        this.tipoCambioMonto = tipoCambioMonto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    public Integer getEnviadaHacienda() {
        return enviadaHacienda;
    }

    public void setEnviadaHacienda(Integer enviadaHacienda) {
        this.enviadaHacienda = enviadaHacienda;
    }

    public Double getTotalInmuestos() {
        return impuestoVentas;
    }

    public void setTotalInmuestos(Double totalInmuestos) {
        this.impuestoVentas = totalInmuestos;
    }

    public Usuario getEncargado() {
        return encargado;
    }

    public void setEncargado(Usuario encargado) {

        this.encargado = encargado;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public FacturaIdentity getFacturaIdentity() {
        return facturaIdentity;
    }

    public void setFacturaIdentity(FacturaIdentity facturaIdentity) {
        this.facturaIdentity = facturaIdentity;
    }

    public Double getTotalTerceros() {
        return totalTerceros;
    }

    public void setTotalTerceros(Double totalTerceros) {
        this.totalTerceros = totalTerceros;
    }

    public Double getImpuestoVentas() {
        return impuestoVentas;
    }

    public void setImpuestoVentas(Double impuestoVentas) {
        this.impuestoVentas = impuestoVentas;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getEstadoHacienda() {
        return estadoHacienda;
    }

    public void setEstadoHacienda(String estadoHacienda) {
        this.estadoHacienda = estadoHacienda;
    }

    public TipoActividadEconomica getTipoActividadEconomica() {
        return tipoActividadEconomica;
    }

    public void setTipoActividadEconomica(TipoActividadEconomica tipoActividadEconomica) {
        this.tipoActividadEconomica = tipoActividadEconomica;
    }

    public Double getExonerado() {
        return exonerado;
    }

    public void setExonerado(Double exonerado) {
        this.exonerado = exonerado;
    }
}
