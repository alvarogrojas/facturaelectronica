package com.rfs.domain;

import com.rfs.dtos.NotaCreditoDetalleDTO;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alvaro on 10/30/17.
 */
@Entity
public class NotaCredito {

    private Integer facturaId;
    private Integer empresaId;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "notaCredito", orphanRemoval = true)
    private List<NotaCreditoDetalle> notaCreditoDetalle = new ArrayList<>();

    private String clave;

    private String consecutivo;

    private String estadoHacienda;

    @Id
    private Integer id;

    private Integer reciboId;

    private Date fechaNotaCreditocion;

    private Integer encargadoId;

    private Integer ultimoCambioPor;

    private Date fechaUltimoCambio;

    private String estadoFacturaRfs;

    private String cliente;

//    @OneToOne
//    @JoinColumn(name="cliente_id", referencedColumnName="id")
//    private Cliente cliente;

    private String cobroPorCuentaDe;

    private String observaciones;

    private Double valorCif;

    private Double peso;

    private Double volumen;

    private Double cantidadBultos;

    private String estado = "ACTIVA";

    private Integer credito;

    private Integer diasCredito;

    private Double porcentajeComision = 3d;

    private Integer forzarCalculoComision;

    private Integer liberarComision;

    private Integer enviadaHacienda;


    @OneToOne
    @JoinColumn(name="tipo_actividad_economica_id", referencedColumnName="id")
    private TipoActividadEconomica tipoActividadEconomica;


    @OneToOne
    @JoinColumn(name="tipo_cambio_id", referencedColumnName="id")
    private TipoCambio tipoCambio;

    private Double subtotal = 0d;

    private Double comisionFinanciamiento = 0d;

    private Double impuestoVentas = 0d;

    private Double total = 0d;

    private Double montoAnticipado = 0d;

    private Double saldoPendiente = 0d;

    private Double saldoPendienteMoneda = 0d;

    private Double totalInmuestos = 0d;

    private Double totalTerceros = 0d;

    private Double totalServicios = 0d;

    private Double tipoCambioMonto = 0d;

    private Date fechaVencimiento;

    private String razon;

    private String codigo;

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReciboId() {
        return reciboId;
    }

    public void setReciboId(Integer reciboId) {
        this.reciboId = reciboId;
    }

    public Date getFechaNotaCreditocion() {
        return this.fechaNotaCreditocion;
    }

    public void setFechaNotaCreditocion(Date fechaFacturacion) {
        this.fechaNotaCreditocion = fechaFacturacion;
    }

    public Integer getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(Integer encargadoId) {
        this.encargadoId = encargadoId;
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

    public String getEstadoFacturaRfs() {
        return estadoFacturaRfs;
    }

    public void setEstadoFacturaRfs(String estadoFacturaRfs) {
        this.estadoFacturaRfs = estadoFacturaRfs;
    }

    public String getCobroPorCuentaDe() {
        return cobroPorCuentaDe;
    }

    public void setCobroPorCuentaDe(String cobroPorCuentaDe) {
        this.cobroPorCuentaDe = cobroPorCuentaDe;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getValorCif() {
        return valorCif;
    }

    public void setValorCif(Double valorCif) {
        this.valorCif = valorCif;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getVolumen() {
        return volumen;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public Double getCantidadBultos() {
        return cantidadBultos;
    }

    public void setCantidadBultos(Double cantidadBultos) {
        this.cantidadBultos = cantidadBultos;
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

    public Double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
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

    public Double getSaldoPendienteMoneda() {
        return saldoPendienteMoneda;
    }

    public void setSaldoPendienteMoneda(Double saldoPendienteMoneda) {
        this.saldoPendienteMoneda = saldoPendienteMoneda;
    }

    public List<NotaCreditoDetalle> getNotaCreditoDetalle() {
        return notaCreditoDetalle;
    }

    public void addNotaCreditoDetalle(NotaCreditoDetalle fd) {
        notaCreditoDetalle.add(fd);
        fd.setNotaCredito(this);
    }

    public void removeNotaCreditoDetalle(NotaCreditoDetalle fd) {
        fd.setNotaCredito(null);
        this.notaCreditoDetalle.remove(fd);
    }

//    public List<NotaCreditoTerceros> getNotaCreditoTerceros() {
//        return notaCreditoTerceros;
//    }
//
//    public void addNotaCreditoTerceros(NotaCreditoTerceros fd) {
//        notaCreditoTerceros.add(fd);
//        fd.setNotaCredito(this);
//    }
//
//    public void removeNotaCreditoTerceros(NotaCreditoTerceros fd) {
//        fd.setNotaCredito(null);
//        this.notaCreditoTerceros.remove(fd);
//    }
//
//    public List<NotaCreditoServicioDetalle> getNotaCreditoServicioDetalle() {
//        return notaCreditoServicioDetalle;
//    }
//
//    public void addNotaCreditoServicioDetalle(NotaCreditoServicioDetalle fd) {
//        notaCreditoServicioDetalle.add(fd);
//        fd.setNotaCredito(this);
//    }

//    public void removeNotaCreditoServicioDetalle(NotaCreditoServicioDetalle fd) {
//        fd.setNotaCredito(null);
//        this.notaCreditoServicioDetalle.remove(fd);
//    }

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

    public Double getTotalServicios() {
        return totalServicios;
    }

    public void setTotalServicios(Double totalServicios) {
        this.totalServicios = totalServicios;
    }

    public Double getTipoCambioMonto() {
        return tipoCambioMonto;
    }

    public void setTipoCambioMonto(Double tipoCambioMonto) {
        this.tipoCambioMonto = tipoCambioMonto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getForzarCalculoComision() {
        return forzarCalculoComision;
    }

    public void setForzarCalculoComision(Integer forzarCalculoComision) {
        this.forzarCalculoComision = forzarCalculoComision;
    }

    public Integer getLiberarComision() {
        return liberarComision;
    }

    public void setLiberarComision(Integer liberarComision) {

        this.liberarComision = liberarComision;
    }

    public Integer getEnviadaHacienda() {
        return enviadaHacienda;
    }

    public void setEnviadaHacienda(Integer enviadaHacienda) {
        this.enviadaHacienda = enviadaHacienda;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public TipoActividadEconomica getTipoActividadEconomica() {
        return tipoActividadEconomica;
    }

    public void setTipoActividadEconomica(TipoActividadEconomica tipoActividadEconomica) {
        this.tipoActividadEconomica = tipoActividadEconomica;
    }

    public void setNotaCreditoDetalleDTO(List<NotaCreditoDetalleDTO> notaCreditoDetallesDTO, NotaCredito id) {

        if (notaCreditoDetallesDTO!=null && notaCreditoDetallesDTO.size() > 0) {
            this.notaCreditoDetalle = new ArrayList<>();
            NotaCreditoDetalle d;

            for (NotaCreditoDetalleDTO fd:notaCreditoDetallesDTO) {
                d = new NotaCreditoDetalle();
                d.setNotaCredito(id);
                try {
                    BeanUtils.copyProperties(d,fd);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                this.notaCreditoDetalle.add(d);

            }
        }
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
}
