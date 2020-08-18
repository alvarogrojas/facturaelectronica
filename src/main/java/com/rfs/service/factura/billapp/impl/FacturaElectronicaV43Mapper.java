package com.rfs.service.factura.billapp.impl;

import com.rfs.domain.Cliente;
import com.rfs.dtos.FacturaDataDTO;
import com.rfs.dtos.FacturaDetalleDTO;
import com.rfs.dtos.FacturaServicioDetalleDTO;
import com.rfs.dtos.FacturaTercerosDTO;
import com.rfs.fe.v43.*;
import com.rfs.service.factura.billapp.BillHelper;
import com.rfs.service.factura.billapp.EmisorService;
import com.rfs.service.factura.billapp.Mapper;
import org.apache.commons.lang.StringUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class FacturaElectronicaV43Mapper implements Mapper {

    private EmisorService emisorService;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private Double totalImpuestos = 0d;
    private Double totalServiciosGrabados = 0d;
    private Double totalMercGrabados = 0d;
    private Double totalServiciosExcento = 0d;
    private Double totalMercExcentos = 0d;
    private Double totalMercExoneradas = 0d;
    private Double totalServExoneradas = 0d;

    public FacturaElectronica mapFacturaElectronica(Object o, String tipoDocumento) throws DatatypeConfigurationException {
        FacturaElectronica fe = new FacturaElectronica();
        try {
            fe.setTipoDocumento(TIPO_DOCUMENTO_FACTURA_ELECTRONICA);

            FacturaDataDTO data = (FacturaDataDTO) o;

            fe.setTipoDocumento(tipoDocumento);

            fe.setNumeroConsecutivo(data.getFacturaId().toString());

            Date emisionDate = new Date();
            XMLGregorianCalendar issueDate = getFechaEmision(emisionDate);
            fe.setFechaEmision(issueDate);
            fe.setFechaEmisionStr(getFechaEmisionStr(emisionDate));

            fe.setCondicionVenta(getCondicionVenta(data.getCredito()));
            fe.setPlazoCredito(getPlazoCredito(data));

            fe.getMedioPago().add(getMedioPago());

            fe.setEmisor(getEmisor());

            fe.setReceptor(getReceptor(data.getCliente().getNombre(), data.getCedula(), data.getFisicaOJuridica(), data.getTelefono(), data.getEsClienteInternacional()));
            fe.setEsClienteInternacional(data.getEsClienteInternacional());
            fe.setCodigoActividad(data.getTipoActividadEconomica().getCodigo());

            fe.setDetalleServicio(createDetalles(data, issueDate));
            fe.setResumenFactura(getResumen(data));

            fe.setEmpresaId(this.emisorService.getEmpresaId());
            fe.setId(data.getFacturaId());

            setCorreoTo(fe, data);


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return fe;
    }

    private String getPlazoCredito(FacturaDataDTO data) {
        if (data.getCredito() == 1 && data.getDiasCredito() != null) {
            return data.getDiasCredito().toString();
        }
        return "";
    }

    private void setCorreoTo(FacturaElectronica fe, FacturaDataDTO data) {
        String email = "";
        Boolean isInitialized = false;
        if (!StringUtils.isEmpty(data.getCorreoCliente())) {
            email = data.getCorreoCliente();
            isInitialized = true;
        }

        if (!StringUtils.isEmpty(data.getCorreoCliente2()) && !BillHelper.isNAEmail(data.getCorreoCliente2())) {
            if (isInitialized) {
                email = email + ";" + data.getCorreoCliente2();
            } else {
                isInitialized = true;
                email = data.getCorreoCliente2();
            }

        }

        if (!StringUtils.isEmpty(data.getCorreoCliente3()) && !BillHelper.isNAEmail(data.getCorreoCliente3())) {
            if (isInitialized) {
                email = email + ";" + data.getCorreoCliente3();
            } else {
                isInitialized = true;
                email = data.getCorreoCliente3();
            }

        }

        if (!StringUtils.isEmpty(data.getCorreoCliente4()) && !BillHelper.isNAEmail(data.getCorreoCliente4())) {
            if (isInitialized) {
                email = email + ";" + data.getCorreoCliente4();
            } else {
                email = data.getCorreoCliente4();
            }

        }

        if (!StringUtils.isEmpty(data.getCorreoCliente5()) && !BillHelper.isNAEmail(data.getCorreoCliente5())) {
            if (isInitialized) {
                email = email + ";" + data.getCorreoCliente5();
            } else {
                email = data.getCorreoCliente5();
            }

        }


        fe.setCorreo(email);
    }


    private FacturaElectronica.DetalleServicio createDetalles(FacturaDataDTO data, XMLGregorianCalendar date) {
        FacturaElectronica.DetalleServicio detalleServicio = new FacturaElectronica.DetalleServicio();
        createDetalle(data.getDetallesDTO(), detalleServicio, data.getCliente(), date);
        return detalleServicio;
    }

    private void createDetalle(List<FacturaDetalleDTO> detalles, FacturaElectronica.DetalleServicio detalleServicio,
                               Cliente c, XMLGregorianCalendar date) {
        if (detalles == null) {
            return;
        }
        this.totalServiciosExcento = 0d;
        this.totalServiciosGrabados = 0d;
        this.totalMercExcentos = 0d;
        this.totalMercGrabados = 0d;
        this.totalImpuestos = 0d;
        totalMercExoneradas =0d;
        totalServExoneradas = 0d;
        Integer linea = 1;
        //Integer linea = detalleServicio.getLineaDetalle().size() + 1;
        FacturaElectronica.DetalleServicio.LineaDetalle ld;
        for (FacturaDetalleDTO fd : detalles) {
            ld = new FacturaElectronica.DetalleServicio.LineaDetalle();
            this.totalImpuestos = totalImpuestos + createLineaDetalle(linea, ld, fd, c, date);
            detalleServicio.getLineaDetalle().add(ld);
            linea++;
        }
    }

    private Double createLineaDetalle(Integer linea, FacturaElectronica.DetalleServicio.LineaDetalle ld, FacturaDetalleDTO fd,
                                      Cliente c, XMLGregorianCalendar date) {
        ld.setNumeroLinea(BigInteger.valueOf(linea));
        ld.setCantidad(BigDecimal.valueOf(fd.getCantidad()));
        ld.setUnidadMedida(fd.getMedida().getSimbolo());

        ld.setDetalle(fd.getDetalle());
        Double montoImpuesto = 0d;
        Double montoUnitarioAntesImpuestos = fd.getMontoNeto() / fd.getCantidad();
        BigDecimal i = null;
        montoImpuesto = fd.getImpuestosMonto();

        i = createDinero(montoImpuesto);

        ImpuestoType it = new ImpuestoType();
        it.setCodigoTarifa(fd.getTarifaIva().getCodigo()); //13%
        it.setCodigo("01");
        it.setTarifa(createDinero(new Double(fd.getTarifaIva().getPorcentaje())));
        it.setMonto(i);
        if (estaClienteExonerado(c)) {
            it.setExoneracion(createExoneracionZF(c, i, date));
            ld.setImpuestoNeto(createDinero(0d));
            if (fd.getMedida()!=null && BillHelper.UNIT_PRODUCTO.equalsIgnoreCase(fd.getMedida().getCategoria())) {
                this.totalMercExoneradas = this.totalMercExoneradas + fd.getMontoNeto();

            } else if (fd.getMedida()!=null &&  BillHelper.UNIT_SERVICIO.equalsIgnoreCase(fd.getMedida().getCategoria())) {
                this.totalServExoneradas = this.totalServExoneradas + fd.getMontoNeto();
            }
        } else {
            ld.setImpuestoNeto(i);

        }

        ld.getImpuesto().add(it);

        if (!estaClienteExonerado(c) && fd.getMedida()!=null && BillHelper.UNIT_PRODUCTO.equalsIgnoreCase(fd.getMedida().getCategoria())) {
            this.totalMercGrabados = this.totalMercGrabados + fd.getMontoNeto();

        } else if (!estaClienteExonerado(c) && fd.getMedida()!=null &&  BillHelper.UNIT_SERVICIO.equalsIgnoreCase(fd.getMedida().getCategoria())) {
            this.totalServiciosGrabados = this.totalServiciosGrabados + fd.getMontoNeto();
        }

        ld.setPrecioUnitario(createDinero(createPrecioUnitario(fd, fd.getMontoNeto())));
        ld.setMontoTotal(createDinero(fd.getMontoNeto()));
        ld.setSubTotal(createDinero(fd.getMontoNeto()));
        Double totalColones = (ld.getPrecioUnitario().doubleValue() * fd.getCantidad()) + ld.getImpuestoNeto().doubleValue();

        ld.setMontoTotalLinea(createDinero((totalColones)));
        if (i==null) {
            return 0d;
        }

        return ld.getImpuestoNeto().doubleValue();

    }

    private boolean estaClienteExonerado(Cliente c) {
        return c.getEsExonerado()!=null && c.getEsExonerado().intValue()==1;
    }

    private ExoneracionType createExoneracionZF(Cliente cliente, BigDecimal monto, XMLGregorianCalendar date) {

        ExoneracionType et = new ExoneracionType();
        et.setTipoDocumento(BillHelper.TIPO_DOC_AUTORIZADO_POR_LEY);
        et.setNumeroDocumento(BillHelper.NUM_DOCUMENTO_EXONERADO);
        et.setNombreInstitucion(cliente.getNombre());
        et.setMontoExoneracion(monto);
        et.setPorcentajeExoneracion(BillHelper.PORCENTAJE_EXONERACION_ZONA_FRANCA);
        et.setFechaEmision(date);

        return et;

    }

    private Double createPrecioUnitario(FacturaServicioDetalleDTO fd) {
        Double price = 0d;
        try {
            if (fd.getTipoCambio().getEsDefault() == 1) {
                price = fd.getMontoColones() / fd.getCantidad();

            } else {
                price = fd.getMontoColones() / fd.getCantidad();
                //price = price * fd.getTipoCambio().getVenta();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    private Double createPrecioUnitario(FacturaDetalleDTO fd, Double montoAntesImpuestos) {
        Double price = 0d;
        try {
            if (fd.getTipoCambio().getEsDefault() == 1) {
                price = montoAntesImpuestos / fd.getCantidad();

            } else {
                price = montoAntesImpuestos / fd.getCantidad();
                //price = price * fd.getTipoCambio().getVenta();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    private Double createPrecioUnitario(FacturaTercerosDTO fd) {
        Double price = 0d;
        try {
            if (fd.getTipoCambio().getEsDefault() == 1) {
                price = fd.getMontoColones() / fd.getCantidad();

            } else {
                price = fd.getMontoColones() / fd.getCantidad();
                //price = price * fd.getTipoCambio().getVenta();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    private FacturaElectronica.ResumenFactura getResumen(FacturaDataDTO invoice) {
        Double totalDescuentos = 0d;
        BigDecimal grabadoBD = createDinero(this.totalServiciosGrabados);
        BigDecimal exentoBD = createDinero(this.totalServiciosExcento);
        BigDecimal mercGrabado = createDinero(this.totalMercGrabados);
        BigDecimal totalGrabado = createDinero(this.totalMercGrabados + totalServiciosGrabados);
        BigDecimal totalExonerado = createDinero(this.totalMercExoneradas + totalServExoneradas);

        BigDecimal totalBD = totalGrabado.add(exentoBD);
        BigDecimal bdImpuestos = createDinero(this.totalImpuestos);

        BigDecimal totalComprobanteBD = totalBD.add(bdImpuestos.add(totalExonerado));

        Double totalVenta = totalGrabado.doubleValue() + totalExonerado.doubleValue() + this.totalServiciosExcento;
        Double totalVentaNeta = totalVenta - totalDescuentos;

        FacturaElectronica.ResumenFactura resumenFactura = new FacturaElectronica.ResumenFactura();

        setCodigoMonedaIfNeedIt(invoice, resumenFactura);
        resumenFactura.setTotalServGravados(createDinero(this.totalServiciosGrabados));
        resumenFactura.setTotalServExentos(createDinero(this.totalServiciosExcento));
        resumenFactura.setTotalServExonerado(createDinero(this.totalServExoneradas));

        resumenFactura.setTotalMercanciasGravadas(createDinero(this.totalMercGrabados));
        resumenFactura.setTotalMercanciasExentas(createDinero(this.totalMercExcentos));

        resumenFactura.setTotalMercExonerada(createDinero(this.totalMercExoneradas));
        resumenFactura.setTotalGravado(totalGrabado);
        resumenFactura.setTotalExento(exentoBD);

        resumenFactura.setTotalExonerado(totalExonerado);

        resumenFactura.setTotalVenta(createDinero(totalVenta));
//        resumenFactura.setTotalVenta(totalBD);
        resumenFactura.setTotalDescuentos(BigDecimal.valueOf(totalDescuentos));
        resumenFactura.setTotalVentaNeta(createDinero(totalVentaNeta));
//        resumenFactura.setTotalVentaNeta(totalBD);
        resumenFactura.setTotalImpuesto(bdImpuestos);
        resumenFactura.setTotalIVADevuelto(createDinero(0d));
        resumenFactura.setTotalOtrosCargos(createDinero(0d));
        resumenFactura.setTotalComprobante(totalComprobanteBD);

        return resumenFactura;
    }

    private void setCodigoMonedaIfNeedIt(FacturaDataDTO invoice, FacturaElectronica.ResumenFactura resumenFactura) {
        String currency = this.getMoneda(invoice);
        if (!BillHelper.isNationalCurrency(currency)) {
            CodigoMonedaType codigoMonedaType = new CodigoMonedaType();
            codigoMonedaType.setCodigoMoneda(currency);
            codigoMonedaType.setTipoCambio(createDinero(invoice.getTipoCambioMonto()));
            resumenFactura.setCodigoTipoMoneda(codigoMonedaType);

        }
    }

    /*private FacturaElectronica.ResumenFactura getResumen(FacturaDataDTO data) {
        FacturaElectronica.ResumenFactura resumenFactura = new FacturaElectronica.ResumenFactura();

        resumenFactura.setCodigoMoneda(getMoneda(data));
        resumenFactura.setTotalServGravados(createDinero(this.totalServiciosGrabados));
        resumenFactura.setTotalServExentos(createDinero(this.totalServiciosExcento));
        resumenFactura.setTotalMercanciasGravadas(BigDecimal.valueOf(0));

//        resumenFactura.setTotalGravado(createDinero(this.totalServiciosGrabados));
//        resumenFactura.setTotalExento(createDinero(totalServiciosExcento));
//
//        Double total = this.totalServiciosExcento + this.totalServiciosGrabados;
//        resumenFactura.setTotalVenta(createDinero(total));
//        resumenFactura.setTotalVentaNeta(createDinero(total));
//        resumenFactura.setTotalImpuesto(createDinero(data.getImpuestoVentas()));
//        resumenFactura.setTotalComprobante(createDinero(data.getTotal()));

        BigDecimal grabadoBD = createDinero(this.totalServiciosGrabados);
        BigDecimal exentoBD = createDinero(this.totalServiciosExcento);
        resumenFactura.setTotalGravado(grabadoBD);
        resumenFactura.setTotalExento(exentoBD);

        BigDecimal totalBD = grabadoBD.add(exentoBD);
        resumenFactura.setTotalVenta(totalBD);
        resumenFactura.setTotalVentaNeta(totalBD);
//        BigDecimal bdImpuestos = createDinero(data.getImpuestoVentas());
//        resumenFactura.setTotalImpuesto(bdImpuestos);
//        this.totalImpuestos = 71442.47787d;
        BigDecimal bdImpuestos = createDinero(this.totalImpuestos);
        resumenFactura.setTotalImpuesto(bdImpuestos);

        BigDecimal totalComprobanteBD = totalBD.add(bdImpuestos);
        resumenFactura.setTotalComprobante(totalComprobanteBD);

        return resumenFactura;
    }*/
//private FacturaElectronica.ResumenFactura getResumen(FacturaDataDTO data) {
//        FacturaElectronica.ResumenFactura resumenFactura = new FacturaElectronica.ResumenFactura();
//
//        resumenFactura.setCodigoMoneda(getMoneda(data));
//        resumenFactura.setTotalServGravados(createDinero(this.totalServiciosGrabados));
//        resumenFactura.setTotalServExentos(createDinero(this.totalServiciosExcento));
//        resumenFactura.setTotalMercanciasGravadas(BigDecimal.valueOf(0));
//
//        resumenFactura.setTotalGravado(createDinero(this.totalServiciosGrabados));
//        resumenFactura.setTotalExento(createDinero(totalServiciosExcento));
//
//        Double total = this.totalServiciosExcento + this.totalServiciosGrabados;
//        resumenFactura.setTotalVenta(createDinero(total));
//        resumenFactura.setTotalVentaNeta(createDinero(total));
//        resumenFactura.setTotalImpuesto(createDinero(data.getImpuestoVentas()));
//        resumenFactura.setTotalComprobante(createDinero(data.getTotal()));
//
//        return resumenFactura;
//    }

    private BigDecimal createDinero(Double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN);
    }

//    private BigDecimal createDinero(Double value) {
//        BigDecimal bd = BigDecimal.valueOf(value);
//        return bd.setScale(2, RoundingMode.CEILING);
////        return bd.setScale(5, RoundingMode.FLOOR);
//    }

    private String getMoneda(FacturaDataDTO data) {
        if (data.getTipoCambio().getNombre().equals(BillHelper.COLON)) {
            return "CRC";
        } else {
            return "USD";
        }
    }

    private XMLGregorianCalendar getFechaEmision(Date fechaFacturacion) throws DatatypeConfigurationException {
        if (fechaFacturacion == null) {
            return null;
        }
        //return df.format(fechaFacturacion);

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    }

    private String getFechaEmisionStr(Date fechaFacturacion) {
        if (fechaFacturacion == null) {
            return null;
        }
        return df.format(fechaFacturacion);

    }

    private String getCondicionVenta(Integer credito) {
        String result = BillHelper.CONTADO;

        if (credito == 1) {
            result = BillHelper.CREDITO;
        }

        return result;
    }

    public String getMedioPago() {
        return BillHelper.MEDIO_TRANSERENCIA_BANCARIA;
    }


    public EmisorType getEmisor() {
        EmisorType e = new EmisorType();
        UbicacionType u = new UbicacionType();

        u.setProvincia(emisorService.getProvincia());
        u.setCanton(emisorService.getCanton());
        u.setDistrito(emisorService.getDistrito());
        u.setBarrio("01");
        u.setOtrasSenas(emisorService.getDireccion());

        e.setNombre(emisorService.getNombre());
        IdentificacionType i = new IdentificacionType();

        String cedula = transformCedula(emisorService.getNumero(), this.emisorService.getTipo());
        i.setNumero(cedula);
        i.setTipo(emisorService.getTipo());
        e.setIdentificacion(i);
        e.setUbicacion(u);
        e.setCorreoElectronico(emisorService.getCorreo());
        TelefonoType t = new TelefonoType();
        t.setCodigoPais(BigInteger.valueOf(506));
        t.setNumTelefono(BigInteger.valueOf(emisorService.getTelefono()));
        return e;
    }

    public ReceptorType getReceptor(String cliente, String cedula, String tipo, String telefono, Short esInternacional) {
        ReceptorType r = new ReceptorType();
        r.setNombre(cliente);
//        if (esInternacional != null && esInternacional == 1) {
//            r.setIdentificacionExtranjero(cedula);
////                r.setIdentificacionExtranjero(cliente);
//        } else {
        IdentificacionType i = new IdentificacionType();
        i.setTipo(tipo);
        cedula = transformCedula(cedula, tipo);
        i.setNumero(cedula);

        r.setIdentificacion(i);
//        }
        return r;
    }

    private String transformCedula(String cedula, String tipo) {
        if (cedula != null) {
            cedula = cedula.replace(" ", "");
            cedula = cedula.replace("-", "");
            if (tipo != null && tipo.equals("02") && cedula.length() > 9 && (cedula.startsWith("30101") || cedula.startsWith("3101"))) {
                if (cedula.startsWith("30101")) {
                    cedula = "3" + cedula.substring(2);

                }

                if (cedula.startsWith("3101") && cedula.length() > 9) {
                    cedula = cedula.substring(0, 10);
                }
            }
        }
        return cedula;
    }

    public EmisorService getEmisorService() {
        return emisorService;
    }

    public void setEmisorService(EmisorService emisorService) {
        this.emisorService = emisorService;
    }

    public FacturaElectronica.InformacionReferencia getInformacionReferenciaSustituyePorRechazoFe(FacturaElectronica oldDocument) {
        FacturaElectronica.InformacionReferencia ir = new FacturaElectronica.InformacionReferencia();

        ir.setCodigo("04");
        ir.setFechaEmision(oldDocument.getFechaEmision());
        ir.setNumero(oldDocument.getNumeroConsecutivo()); //INVESTIGAR SI ES CLAVE O EL CONSECUTIVO
        ir.setTipoDoc("01");
        ir.setRazon("Sustituye documento por rechazo");

        return ir;
    }
}
