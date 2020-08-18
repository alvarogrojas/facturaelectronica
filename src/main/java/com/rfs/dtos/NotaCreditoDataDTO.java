package com.rfs.dtos;

import com.rfs.domain.*;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotaCreditoDataDTO {

    private static final String COMISION_FINANCIAMIENTO = "COMISION POR FINANCIAMIENTO";
    private Integer empresaId;
    private Date fechaNotaCreditocion = new Date();

    private Integer clienteId;

    private String correoCliente;
    private String correoCliente2;
    private String correoCliente3;
    private String correoCliente4;
    private String correoCliente5;
    private String clave;
    private String cedula;
    private String fisicaOJuridica = "02";

    private String razon;

    private String codigo = "01";

    private Integer notaCreditoElectronicaConsecutivo;

    private Short esClienteInternacional = 0;

    private String cliente;

    private Cliente clienteObject;


    private String telefono;

    private String direccion;

    private String contacto;

    private String cobroPorCuentaDe;

    private String observaciones;

    private Double valorCif;

    private Double peso;

    private Double volumen;

    private Double cantidadBultos;

    private String estado = "EDICION";
//    private String estado = "ACTIVA";

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

    private String encargado;

    private Integer encargadoId;

    private Integer facturaId;

    private TipoActividadEconomica tipoActividadEconomica;

    private List<NotaCreditoDetalleDTO> detallesDTO;

    private List<NotaCreditoTercerosDTO> notaCreditoTercerosDTO;

    private List<NotaCreditoServicioDetalleDTO> notaCreditoServiciosDTO;

    /*******************************************************************************************************************/
    //UI

    private List<TipoCambio> monedas;

    private List<Terceros> terceros;

    private List<Impuesto> impuestos;

    private List<Tarifa> tarifas;

    private Iterable<ServicioCorreduriaAduanera> servicios;

    /****************************************************************************************************************/

    private String bl;

    private String proveedor;

    private Date fechaVencimiento = new Date();

    private String tipoTramite;
    private String dua;

    private String aduana;


    private Integer reciboId;

    private Integer notaCreditoId;

    private Double totalInmuestos = 0d;

    private Double totalTerceros = 0d;

    private Double totalServicios = 0d;

    private Double tipoCambioMonto = 0d;

    private Integer forzarCalculoComision = 0;

    private Integer liberarComision = 0;

    Short previoExamen = 0, aforoFisico = 0, permisos = 0;

    private Integer enviadaHacienda = 0;
    private String consecutivo;
    private String estadoHacienda;


    public NotaCreditoDataDTO() {

    }

    public NotaCreditoDataDTO(Integer reciboId,
                              Integer clienteId,
                              String cliente,
                              String telefono,
                              String direccion,
                              String contacto,
                              Integer credito,
                              Integer diasCredito, String tipoTramite, String aduana, String bl, String proveedor, String dua, Short previoExamen, Short aforoFisico, Short permisos, String correo, String correo2, String correo3, String correo4, Integer enviadaHacienda) {
        this.reciboId = reciboId;
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

        if (this.credito!=null && this.credito==1 && this.diasCredito!=null && this.diasCredito>0 ) {

            Calendar c = Calendar.getInstance();
                c.setTime(fechaNotaCreditocion);
            c.add(Calendar.DAY_OF_MONTH, this.diasCredito);
            this.fechaVencimiento = c.getTime();
        }

        detallesDTO =  new ArrayList<NotaCreditoDetalleDTO>();
        notaCreditoTercerosDTO =  new ArrayList<NotaCreditoTercerosDTO>();
        this.facturaId = facturaId;
    }



    public NotaCreditoDataDTO(
                          Factura f) {

        this.enviadaHacienda = f.getEnviadaHacienda();
        this.correoCliente = f.getCliente().getCorreo();
        this.correoCliente5 = f.getCliente().getContacto1Correo();
        this.correoCliente2 = f.getCliente().getContacto2Correo();
        this.correoCliente3 = f.getCliente().getContacto3Correo();
        this.correoCliente4 = f.getCliente().getContacto4Correo();

        this.cedula = f.getCliente().getCedulaJuridica();


        this.clienteId = f.getCliente().getId();
        this.cliente = f.getCliente().getNombre();
        this.clienteObject = f.getCliente();
        this.telefono = f.getCliente().getTelefono();
        this.direccion = f.getCliente().getDireccion();
        this.contacto = f.getCliente().getContacto1();
        this.credito = f.getCliente().getTieneCredito();
        this.diasCredito = f.getCliente().getDiasCredito();

        try {

            BeanUtils.copyProperties(this,f);

            this.clienteObject = f.getCliente();
            mapTipoCedula(f);
            initNotaCreditoList(f);
            this.tipoCambio = f.getTipoCambio();
            this.tipoCambioMonto = f.getTipoCambioMonto();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        this.cedula = cedula;
        this.facturaId = f.getFacturaIdentity().getFacturaId();
        this.empresaId = f.getFacturaIdentity().getEmpresaId();
    }

    public NotaCreditoDataDTO(
            NotaCredito f, Factura factura) {

        this.enviadaHacienda = f.getEnviadaHacienda();
        mapTipoCedula(factura);

        try {
            //his.notaCreditoId = notaCredito.getId();

            BeanUtils.copyProperties(this,f);


            this.clienteObject = factura.getCliente();
            //calculateComisionFinanciamiento();
            initNotaCreditoList(f);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        this.cedula = cedula;
        this.facturaId = f.getFacturaId();
        this.notaCreditoId = f.getId();
        this.correoCliente = factura.getCliente().getCorreo();

        this.correoCliente2 = factura.getCliente().getContacto1Correo();
        this.correoCliente3 = factura.getCliente().getContacto2Correo();
        this.correoCliente4 = factura.getCliente().getContacto3Correo();
        this.correoCliente5 = factura.getCliente().getContacto4Correo();
        this.clienteId = clienteId;
    }


    public void calcularTotal() {
        if (subtotal==null) {
            subtotal = 0d;
        }

//        if (this.comisionFinanciamiento==null) {
//            this.comisionFinanciamiento = 0d;
//        }

        if (impuestoVentas==0) {
            impuestoVentas = 0d;
        }
//        this.total = subtotal + comisionFinanciamiento + impuestoVentas;
        calcularSaldoPendiente();
    }

    public void calcularSaldoPendiente() {
        this.saldoPendiente = this.total - this.montoAnticipado;
    }

    public void recalculateDiasCredito() {
        if (this.fechaVencimiento==null || this.fechaNotaCreditocion==null) {
            this.diasCredito = 0;
            return;
        }
        long difference = this.fechaVencimiento.getTime() - this.fechaNotaCreditocion.getTime();
        this.diasCredito = (int) (difference / (1000*60*60*24));
    }

    private void initNotaCreditoList(Factura f) {
//        this.notaCreditoTercerosDTO = new ArrayList<>();
//        NotaCreditoTercerosDTO t;
//        for (FacturaTerceros ft:f.getFacturaTerceros()) {
//            t = new NotaCreditoTercerosDTO();
//            try {
//                BeanUtils.copyProperties(t,ft);
//
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            this.notaCreditoTercerosDTO.add(t);
//        }
//
//
//        this.notaCreditoServiciosDTO = new ArrayList<>();
//        NotaCreditoServicioDetalleDTO sd;
//        for (NotaCreditoServicioDetalle fsd:f.getNotaCreditoServicioDetalle()) {
//            sd = new NotaCreditoServicioDetalleDTO();
//            try {
//                BeanUtils.copyProperties(sd,fsd);
//
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            this.notaCreditoServiciosDTO.add(sd);
//
//        }

        this.detallesDTO = new ArrayList<>();
        NotaCreditoDetalleDTO d;
        for (FacturaDetalle fd:f.getFacturaDetalle()) {
            d = new NotaCreditoDetalleDTO();
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

    private void initNotaCreditoList(NotaCredito f) {
//        this.notaCreditoTercerosDTO = new ArrayList<>();
//        NotaCreditoTercerosDTO t;
//        for (NotaCreditoTerceros ft:f.getNotaCreditoTerceros()) {
//            t = new NotaCreditoTercerosDTO();
//            try {
//                BeanUtils.copyProperties(t,ft);
//
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            this.notaCreditoTercerosDTO.add(t);
//        }
//
//
//        this.notaCreditoServiciosDTO = new ArrayList<>();
//        NotaCreditoServicioDetalleDTO sd;
//        for (NotaCreditoServicioDetalle fsd:f.getNotaCreditoServicioDetalle()) {
//            sd = new NotaCreditoServicioDetalleDTO();
//            try {
//                BeanUtils.copyProperties(sd,fsd);
//
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            this.notaCreditoServiciosDTO.add(sd);
//
//        }

        this.detallesDTO = new ArrayList<>();
        NotaCreditoDetalleDTO d;
        for (NotaCreditoDetalle fd:f.getNotaCreditoDetalle()) {
            d = new NotaCreditoDetalleDTO();
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

    public Integer getReciboId() {
        return reciboId;
    }

    public void setReciboId(Integer reciboId) {
        this.reciboId = reciboId;
    }

    public Date getFechaNotaCreditocion() {
        return fechaNotaCreditocion;
    }

    public void setFechaNotaCreditocion(Date fechaNotaCreditocion) {
        this.fechaNotaCreditocion = fechaNotaCreditocion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
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

    public List<NotaCreditoDetalleDTO> getDetallesDTO() {
        return detallesDTO;
    }

    public void setDetallesDTO(List<NotaCreditoDetalleDTO> detalles) {
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

    public List<NotaCreditoTercerosDTO> getNotaCreditoTercerosDTO() {
        return notaCreditoTercerosDTO;
    }

    public void setNotaCreditoTercerosDTO(List<NotaCreditoTercerosDTO> notaCreditoTerceros) {
        this.notaCreditoTercerosDTO = notaCreditoTerceros;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer id) {
        this.clienteId = id;
    }

    public Iterable<ServicioCorreduriaAduanera> getServicios() {
        return servicios;
    }

    public void setServicios(Iterable<ServicioCorreduriaAduanera> servicios) {
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


    public List<NotaCreditoServicioDetalleDTO> getNotaCreditoServiciosDTO() {
        return notaCreditoServiciosDTO;
    }

    public void setNotaCreditoServiciosDTO(List<NotaCreditoServicioDetalleDTO> notaCreditoServicios) {
        this.notaCreditoServiciosDTO = notaCreditoServicios;
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

    public Integer getNotaCreditoId() {
        return notaCreditoId;
    }

    public void setNotaCreditoId(Integer notaCreditoId) {
        this.notaCreditoId = notaCreditoId;
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
//        if (comisionFinanciamiento==null) {
//            comisionFinanciamiento = 0d;
//        }
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

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
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

    public Integer getNotaCreditoElectronicaConsecutivo() {
        return notaCreditoElectronicaConsecutivo;
    }

    public void setNotaCreditoElectronicaConsecutivo(Integer notaCreditoElectronicaConsecutivo) {
        this.notaCreditoElectronicaConsecutivo = notaCreditoElectronicaConsecutivo;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
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

    public Cliente getClienteObject() {
        return clienteObject;
    }

    public void setClienteObject(Cliente clienteObject) {
        this.clienteObject = clienteObject;
    }
}
