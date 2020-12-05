package com.rfs.dtos;

import com.rfs.domain.*;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class  FacturaDataDTO {

    private static final String COMISION_FINANCIAMIENTO = "COMISION POR FINANCIAMIENTO";

    private String cedula;
    private String clave;
    private boolean errorTarifa = false;
    private Date fechaFacturacion = new Date();

    private Integer clienteId;

    private Boolean esClienteNuevo = false;

    private String correoCliente;
    private String correoCliente2;
    private String correoCliente3;
    private String correoCliente4;
    private String correoCliente5;

    private TipoActividadEconomica tipoActividadEconomica;

    private Cliente cliente;

    private String telefono;

    private String direccion;

    private String contacto;

    private String cobroPorCuentaDe;

    private String observaciones;

    private Double valorCif;

    private Double peso;

    private Double volumen;

    private Double cantidadBultos;

    private String estado = "Ingresada";

    private String estadoPago = "Pendiente Pago";

    private Integer credito;

    private Integer diasCredito;

    private Double porcentajeComision = 3d;

    private TipoCambio tipoCambio;

    private Double subtotal = 0d;

//    private Double comisionFinanciamiento = 0d;

    private Double impuestoVentas = 0d;

    private Double total = 0d;

    private Double montoAnticipado = 0d;

    private Double saldoPendiente = 0d;

    private Double saldoPendienteMoneda = 0d;

    private Usuario encargado;

    private Integer encargadoId;

    private List<FacturaDetalleDTO> detallesDTO;

    private List<FacturaTercerosDTO> facturaTercerosDTO;

    private List<FacturaServicioDetalleDTO> facturaServiciosDTO;

    private String fisicaOJuridica = "02";

    private List<NotaCreditoStatusDTO> notaCreditoStatusDTOS;

    private Integer facturaElectronicaConsecutivo;

    private Double exonerado;

    /*******************************************************************************************************************/
    //UI

    private List<TipoCambio> monedas;
    private List<Cliente> clientes;

    private List<Terceros> terceros;

    private List<TipoActividadEconomica> tipoActividadEconomicas;
    private List<TarifaIva> tarifaIvas;

    private List<Impuesto> impuestos;
    private List<ServicioCabys> servicios;

    private List<Tarifa> tarifas;
    private List<Medida> medidas;

//    private Iterable<ServicioCorreduriaAduanera> servicios;

    /****************************************************************************************************************/

    private String bl;

    private String proveedor;

    private Date fechaVencimiento = new Date();

    private String tipoTramite;
    private String dua;

    private String aduana;

    private Integer facturaId;

    //private Double totalInmuestos = 0d;

    private Double totalTerceros = 0d;

    private Double totalServicios = 0d;

    private Double tipoCambioMonto = 0d;

    private Integer forzarCalculoComision = 0;

    private Integer liberarComision = 0;

    Short previoExamen = 0, aforoFisico = 0, permisos = 0;

    private Integer enviadaHacienda = 0;
    private Short esClienteInternacional = 0;


    //    private String
    public FacturaDataDTO(boolean errorTarifa) {
        this.errorTarifa = errorTarifa;
    }

    public FacturaDataDTO() {
        detallesDTO =  new ArrayList<FacturaDetalleDTO>();
        facturaTercerosDTO =  new ArrayList<FacturaTercerosDTO>();
        facturaServiciosDTO =  new ArrayList<FacturaServicioDetalleDTO>();
        esClienteNuevo = true;

    }

    public FacturaDataDTO(
                          Integer clienteId,
                          String cliente,
                          String telefono,
                          String direccion,
                          String contacto,
                          Integer credito,
                          Integer diasCredito, String tipoTramite, String aduana, String bl, String proveedor, String dua, Short previoExamen, Short aforoFisico, Short permisos, String correo, String correo2, String correo3, String correo4, Integer enviadaHacienda) {

        this.enviadaHacienda = enviadaHacienda;
        this.correoCliente = correo;
        this.correoCliente2 = correo2;
        this.correoCliente3 = correo3;
        this.correoCliente4 = correo4;
        this.clienteId = clienteId;
        //this.cliente = cliente;
        this.telefono = telefono;
        this.direccion = direccion;
        this.contacto = contacto;
        this.credito = credito;
        this.diasCredito = diasCredito;
        this.tipoTramite = tipoTramite;
        this.aduana = aduana;
        this.bl = bl;
        this.proveedor = proveedor;
        this.dua = dua;

        this.aforoFisico = aforoFisico;
        this.permisos = permisos;
        this.previoExamen = previoExamen;
        esClienteNuevo = false;

        if (this.credito!=null && this.credito==1 && this.diasCredito!=null && this.diasCredito>0 ) {

            Calendar c = Calendar.getInstance();
                c.setTime(fechaFacturacion);
            c.add(Calendar.DAY_OF_MONTH, this.diasCredito);
            this.fechaVencimiento = c.getTime();
        }

        detallesDTO =  new ArrayList<FacturaDetalleDTO>();
        facturaTercerosDTO =  new ArrayList<FacturaTercerosDTO>();
    }



    public FacturaDataDTO(
                          Factura factura
                          ) {

        this.enviadaHacienda = factura.getEnviadaHacienda();
        this.correoCliente = factura.getCliente().getCorreo();
        this.correoCliente5 = factura.getCliente().getContacto1Correo();
        this.correoCliente2 = factura.getCliente().getContacto2Correo();
        this.correoCliente3 = factura.getCliente().getContacto3Correo();
        this.correoCliente4 = factura.getCliente().getContacto4Correo();
        this.cedula = factura.getCliente().getCedulaJuridica();


        this.clienteId = factura.getCliente().getId();
        this.cliente = factura.getCliente();
        this.tipoActividadEconomica = factura.getTipoActividadEconomica();
        this.telefono = factura.getCliente().getTelefono();
        this.direccion = factura.getCliente().getDireccion();
        this.contacto = factura.getCliente().getContacto1();
        this.credito = factura.getCliente().getTieneCredito();
        this.diasCredito = factura.getCliente().getDiasCredito();
        this.tipoTramite = "";
        this.aduana = "";
        this.bl = "";
        this.proveedor = "";
        this.dua = "";

        this.aforoFisico = 0;
        this.permisos = 0;
        this.previoExamen = 0;
        mapTipoCedula(factura);

        try {
            this.facturaId = factura.getFacturaIdentity().getFacturaId();

            BeanUtils.copyProperties(this,factura);
            this.impuestoVentas = factura.getImpuestoVentas();
            //this.totalInmuestos = factura.getTotalInmuestos();
            initFacturaList(factura);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void mapTipoCedula(Factura factura) {
        if (factura.getCliente().getEsJuridico()!=null && factura.getCliente().getEsJuridico()==0) {
            this.fisicaOJuridica = "01";
        } else if (factura.getCliente().getEsJuridico()!=null && factura.getCliente().getEsJuridico()==4) {
            this.fisicaOJuridica = "04";
        } else if (factura.getCliente().getEsJuridico()!=null && factura.getCliente().getEsJuridico()==3) {
            this.fisicaOJuridica = "03";

        } else if (factura.getCliente().getEsJuridico()!=null && factura.getCliente().getEsJuridico()==1) {
            this.fisicaOJuridica = "02";

        }
        this.esClienteInternacional = factura.getCliente().getEsInternacional();

    }

    public void recalculateDiasCredito() {
        if (this.fechaVencimiento==null || this.fechaFacturacion==null) {
            this.diasCredito = 0;
            return;
        }
        long difference = this.fechaVencimiento.getTime() - this.fechaFacturacion.getTime();
        this.diasCredito = (int) (difference / (1000*60*60*24));
    }

    private void initFacturaList(Factura factura) {
        this.facturaTercerosDTO = new ArrayList<>();
//        FacturaTercerosDTO t;
//        for (FacturaDetalle ft:factura.getFacturaTerceros()) {
//            t = new FacturaTercerosDTO();
//            try {
//                BeanUtils.copyProperties(t,ft);
//
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            this.facturaTercerosDTO.add(t);
//        }
//
//
//        this.facturaServiciosDTO = new ArrayList<>();
//        FacturaServicioDetalleDTO sd;
//        for (FacturaServicioDetalle fsd:factura.getFacturaServicioDetalle()) {
//            sd = new FacturaServicioDetalleDTO();
//            try {
//                BeanUtils.copyProperties(sd,fsd);
//
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            this.facturaServiciosDTO.add(sd);
//
//        }

        this.detallesDTO = new ArrayList<>();
        FacturaDetalleDTO d;
        for (FacturaDetalle fd:factura.getFacturaDetalle()) {
            d = new FacturaDetalleDTO();
            try {
                BeanUtils.copyProperties(d,fd);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            this.detallesDTO.add(d);

        }
    }


    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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

    public List<FacturaDetalleDTO> getDetallesDTO() {
        return detallesDTO;
    }

    public void setDetallesDTO(List<FacturaDetalleDTO> detalles) {
        this.detallesDTO = detalles;
    }

    public void setMonedas(List<TipoCambio> monedas) {
        this.monedas = monedas;
    }

    public List<TipoCambio> getMonedas() {
        return monedas;
    }

    public TipoCambio getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(TipoCambio tipoCambio) {
        this.tipoCambio = tipoCambio;

    }

    public List<Terceros> getTerceros() {
        return terceros;
    }

    public void setTerceros(List<Terceros> terceros) {
        this.terceros = terceros;
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

    public void setValorCif(Double valorCIF) {
        this.valorCif = valorCIF;
    }

    public List<FacturaTercerosDTO> getFacturaTercerosDTO() {
        return facturaTercerosDTO;
    }

    public void setFacturaTercerosDTO(List<FacturaTercerosDTO> facturaTerceros) {
        this.facturaTercerosDTO = facturaTerceros;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer id) {
        this.clienteId = id;
    }

//    public Iterable<ServicioCorreduriaAduanera> getServicios() {
//        return servicios;
//    }
//
//    public void setServicios(Iterable<ServicioCorreduriaAduanera> servicios) {
//        this.servicios = servicios;
//    }


    public List<ServicioCabys> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioCabys> servicios) {
        this.servicios = servicios;
    }

    public List<Impuesto> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }

    public Iterable<Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<Tarifa> tarifas) {
        this.tarifas = tarifas;
        //initComisionFinanciamiento();
    }

    public List<Medida> getMedidas() {
        return medidas;
    }

    public void setMedidas(List<Medida> medidas) {
        this.medidas = medidas;
    }

    public void initPorcentajeComision() {
        if (this.tarifas==null || this.tarifas.size() <= 0) {
            this.porcentajeComision = 3d;
            return;
        }
        for (Tarifa t: tarifas) {
            if (t.getNombre().toUpperCase().equals(COMISION_FINANCIAMIENTO)) {
                this.porcentajeComision =  t.getMonto();
                break;
            }
        }
    }


    public List<FacturaServicioDetalleDTO> getFacturaServiciosDTO() {
        return facturaServiciosDTO;
    }

    public void setFacturaServiciosDTO(List<FacturaServicioDetalleDTO> facturaServicios) {
        this.facturaServiciosDTO = facturaServicios;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getDua() {
        return dua;
    }

    public void setDua(String dua) {
        this.dua = dua;
    }

    public Double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public String getAduana() {
        return aduana;
    }

    public void setAduana(String aduana) {
        this.aduana = aduana;
    }

    public Double getCantidadBultos() {
        return cantidadBultos;
    }

    public void setCantidadBultos(Double cantidadBultos) {
        this.cantidadBultos = cantidadBultos;
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

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

//    public Double getComisionFinanciamiento() {
//        return comisionFinanciamiento;
//    }
//
//    public void setComisionFinanciamiento(Double comisionFinanciamiento) {
//        this.comisionFinanciamiento = comisionFinanciamiento;
//    }

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

//    public Double getTotalInmuestos() {
//        return totalInmuestos;
//    }
//
//    public void setTotalInmuestos(Double totalInmuestos) {
//        this.totalInmuestos = totalInmuestos;
//        this.impuestoVentas = totalInmuestos;
//    }

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

    public Usuario getEncargado() {
        return encargado;
    }

    public void setEncargado(Usuario encargado) {
        this.encargado = encargado;
    }

    public Integer getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(Integer encargadoId) {
        this.encargadoId = encargadoId;
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

    public Short getPrevioExamen() {
        return previoExamen;
    }

    public void setPrevioExamen(Short previoExamen) {
        this.previoExamen = previoExamen;
    }

    public Short getAforoFisico() {
        return aforoFisico;
    }

    public void setAforoFisico(Short aforoFisico) {
        this.aforoFisico = aforoFisico;
    }

    public Short getPermisos() {
        return permisos;
    }

    public void setPermisos(Short permisos) {
        this.permisos = permisos;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public Integer getEnviadaHacienda() {
        return enviadaHacienda;
    }

    public void setEnviadaHacienda(Integer enviadaHacienda) {
        this.enviadaHacienda = enviadaHacienda;
    }

    public String getCorreoCliente2() {
        return correoCliente2;
    }

    public void setCorreoCliente2(String correoCliente2) {
        this.correoCliente2 = correoCliente2;
    }

    public String getCorreoCliente3() {
        return correoCliente3;
    }

    public void setCorreoCliente3(String correoCliente3) {
        this.correoCliente3 = correoCliente3;
    }

    public String getCorreoCliente4() {
        return correoCliente4;
    }

    public void setCorreoCliente4(String correoCliente4) {
        this.correoCliente4 = correoCliente4;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Boolean getEsClienteNuevo() {
        return esClienteNuevo;
    }

    public void setEsClienteNuevo(Boolean esClienteNuevo) {
        this.esClienteNuevo = esClienteNuevo;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public boolean getErrorTarifa() {
        return errorTarifa;
    }

    public void setErrorTarifa(boolean errorTarifa) {
        this.errorTarifa = errorTarifa;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }


    public String getFisicaOJuridica() {
        return fisicaOJuridica;
    }

    public void setFisicaOJuridica(String fisicaOJuridica) {
        this.fisicaOJuridica = fisicaOJuridica;
    }

    public Integer getFacturaElectronicaConsecutivo() {
        return facturaElectronicaConsecutivo;
    }

    public void setFacturaElectronicaConsecutivo(Integer facturaElectronicaConsecutivo) {
        this.facturaElectronicaConsecutivo = facturaElectronicaConsecutivo;
    }

    public List<NotaCreditoStatusDTO> getNotaCreditoStatusDTOS() {
        if (notaCreditoStatusDTOS==null) {
            this.notaCreditoStatusDTOS = new ArrayList<>();
        }
        return notaCreditoStatusDTOS;
    }

    public void setNotaCreditoStatusDTOS(List<NotaCreditoStatusDTO> notaCreditoStatusDTOS) {
        this.notaCreditoStatusDTOS = notaCreditoStatusDTOS;
    }

    public Short getEsClienteInternacional() {
        return esClienteInternacional;
    }

    public void setEsClienteInternacional(Short esClienteInternacional) {
        this.esClienteInternacional = esClienteInternacional;
    }

    public String getCorreoCliente5() {
        return correoCliente5;
    }

    public void setCorreoCliente5(String correoCliente5) {
        this.correoCliente5 = correoCliente5;
    }

    public TipoActividadEconomica getTipoActividadEconomica() {
        return tipoActividadEconomica;
    }

    public void setTipoActividadEconomica(TipoActividadEconomica tipoActividadEconomica) {
        this.tipoActividadEconomica = tipoActividadEconomica;
    }

    public List<TipoActividadEconomica> getTipoActividadEconomicas() {
        return tipoActividadEconomicas;
    }

    public void setTipoActividadEconomicas(List<TipoActividadEconomica> tipoActividadEconomicas) {
        this.tipoActividadEconomicas = tipoActividadEconomicas;
    }

    public List<TarifaIva> getTarifaIvas() {
        return tarifaIvas;
    }

    public void setTarifaIvas(List<TarifaIva> tarifaIvas) {
        this.tarifaIvas = tarifaIvas;
    }

    public Double getExonerado() {
        return exonerado;
    }

    public void setExonerado(Double exonerado) {
        this.exonerado = exonerado;
    }
}
