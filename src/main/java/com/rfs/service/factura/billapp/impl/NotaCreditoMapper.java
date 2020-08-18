package com.rfs.service.factura.billapp.impl;


import com.rfs.dtos.*;
import com.rfs.fe.nce.*;
import com.rfs.service.factura.billapp.BillHelper;
import com.rfs.service.factura.billapp.EmisorService;
import com.rfs.service.factura.billapp.Mapper;
import org.apache.commons.lang.StringUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class NotaCreditoMapper implements Mapper {


    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    private Double totalImpuestos = 0d;

    private EmisorService emisorService;
    private Double totalServiciosGrabados;
    private Double totalServiciosExcento;

    public Object mapFacturaElectronica(Object o, String tipoDocumento) throws DatatypeConfigurationException {
        NotaCreditoDataDTO data = (NotaCreditoDataDTO) o;
        NotaCreditoElectronica fe = new NotaCreditoElectronica();
        fe.setTipoDocumento(tipoDocumento);

        fe.setNumeroConsecutivo(data.getNotaCreditoId().toString());

        fe.setCondicionVenta(getCondicionVenta(data.getCredito()));
        fe.setPlazoCredito(getPlazoCredito(data));
        fe.getMedioPago().add(getMedioPago());
        fe.setEmisor(getEmisor());

        fe.setReceptor(getReceptor(data.getCliente(),data.getCedula(),data.getFisicaOJuridica(),data.getTelefono()));
        fe.setEsClienteInternacional(data.getEsClienteInternacional());

        Date emisionDate = new Date();
        fe.setFechaEmision(getFechaEmision(emisionDate));
        fe.setFechaEmisionStr(getFechaEmisionStr(emisionDate));

        agregarInformacionReferencia(fe.getInformacionReferencia(),data);
        fe.setNormativa(createNormativa());

        fe.setDetalleServicio(createDetalleServicio(data));
        fe.setResumenFactura(getResumen(data));
        fe.setId(data.getNotaCreditoId());
        fe.setFacturaId(data.getFacturaId());
        setCorreoTo(fe, data);
        return fe;
    }

    private  void agregarInformacionReferencia(List<NotaCreditoElectronica.InformacionReferencia> refl, NotaCreditoDataDTO data) {
        NotaCreditoElectronica.InformacionReferencia ref = new NotaCreditoElectronica.InformacionReferencia();
        ref.setTipoDoc("01");
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(data.getFechaNotaCreditocion());

        try {
            ref.setFechaEmision(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        //ref.setNumero(data.g);
        ref.setCodigo(data.getCodigo());
        ref.setRazon(data.getRazon());
        refl.add(ref);

    }

    private void setCorreoTo(NotaCreditoElectronica fe, NotaCreditoDataDTO data) {
        String email = "";
        Boolean isInitialized = false;
        if (!StringUtils.isEmpty(data.getCorreoCliente())) {
            email = data.getCorreoCliente();
            isInitialized = true;
        }

        if (!StringUtils.isEmpty(data.getCorreoCliente2() ) && !BillHelper.isNAEmail(data.getCorreoCliente2())) {
            if (isInitialized) {
                email = email + ";" +data.getCorreoCliente2();
            } else {
                isInitialized = true;
                email = data.getCorreoCliente2();
            }

        }

        if (!StringUtils.isEmpty(data.getCorreoCliente3()) && !BillHelper.isNAEmail(data.getCorreoCliente3())) {
            if (isInitialized) {
                email = email + ";" +data.getCorreoCliente3();
            } else {
                isInitialized = true;
                email = data.getCorreoCliente3();
            }

        }

        if (!StringUtils.isEmpty(data.getCorreoCliente4()) &&  !BillHelper.isNAEmail(data.getCorreoCliente4())) {
            if (isInitialized) {
                email = email + ";" +data.getCorreoCliente4();
            } else {
                email = data.getCorreoCliente4();
            }

        }

        if (!StringUtils.isEmpty(data.getCorreoCliente5()) &&  !BillHelper.isNAEmail(data.getCorreoCliente5())) {
            if (isInitialized) {
                email = email + ";" +data.getCorreoCliente4();
            } else {
                email = data.getCorreoCliente4();
            }

        }
        fe.setCorreo(email);
    }



    private NotaCreditoElectronica.DetalleServicio createDetalleServicio(NotaCreditoDataDTO data) {
        NotaCreditoElectronica.DetalleServicio detalleServicio = new NotaCreditoElectronica.DetalleServicio();
        //detalleServicio.setDetalleServicio(new ArrayList<LineaDetalle>());
        createDetalle(data.getDetallesDTO(),detalleServicio);
//        createDetalleServicioForServices(data.getNotaCreditoServiciosDTO(),detalleServicio);
//        createDetalleServicioForTerceros(data.getNotaCreditoTercerosDTO(),detalleServicio);
        return detalleServicio;

//        FacturaElectronica.DetalleServicio detalleServicio = new FacturaElectronica.DetalleServicio();
//        createDetalle(data.getDetallesDTO(),detalleServicio);
//        // this.totalImpuestos = createDetalleServicioForServices(data.getFacturaServiciosDTO(),detalleServicio);
//        //createDetalleServicioForTerceros(data.getFacturaTercerosDTO(),detalleServicio);
//        return detalleServicio;
    }

    private void createDetalle(List<NotaCreditoDetalleDTO> impuestos, NotaCreditoElectronica.DetalleServicio detalleServicio) {
        if (impuestos==null) {
            return;
        }
        this.totalServiciosExcento = 0d;
        this.totalServiciosGrabados = 0d;
        this.totalImpuestos = 0d;

        Integer linea = detalleServicio.getLineaDetalle().size() + 1;
        NotaCreditoElectronica.DetalleServicio.LineaDetalle ld;
        for (NotaCreditoDetalleDTO fd: impuestos) {
            ld = new NotaCreditoElectronica.DetalleServicio.LineaDetalle();
            this.totalImpuestos = totalImpuestos + createLineaDetalle(linea, ld, fd);
            detalleServicio.getLineaDetalle().add(ld);
            linea++;
        }
    }

    private void createDetalleServicioForServices(List<NotaCreditoServicioDetalleDTO> impuestos, NotaCreditoElectronica.DetalleServicio detalleServicio) {
        if (impuestos==null) {
            return;
        }

        Integer linea = detalleServicio.getLineaDetalle().size() + 1;
        NotaCreditoElectronica.DetalleServicio.LineaDetalle ld;
        for (NotaCreditoServicioDetalleDTO fd: impuestos) {
            ld = new NotaCreditoElectronica.DetalleServicio.LineaDetalle();
            createLineaDetalleFromServices(linea, ld, fd);
            detalleServicio.getLineaDetalle().add(ld);
            linea++;
        }
    }

    private void createDetalleServicioForTerceros(List<NotaCreditoTercerosDTO> impuestos, NotaCreditoElectronica.DetalleServicio detalleServicio) {
        if (impuestos==null) {
            return;
        }

        Integer linea = detalleServicio.getLineaDetalle().size() + 1;
        NotaCreditoElectronica.DetalleServicio.LineaDetalle ld;
        for (NotaCreditoTercerosDTO fd: impuestos) {
            ld = new NotaCreditoElectronica.DetalleServicio.LineaDetalle();
            createLineaDetalleFromTerceros(linea, ld, fd);
            detalleServicio.getLineaDetalle().add(ld);
            linea++;
        }
    }

    private Double createLineaDetalle(Integer linea, NotaCreditoElectronica.DetalleServicio.LineaDetalle ld, NotaCreditoDetalleDTO fd) {
        ld.setNumeroLinea(BigInteger.valueOf(linea));
        ld.setCantidad(BigDecimal.valueOf(fd.getCantidad()));
        ld.setUnidadMedida(this.emisorService.getUnidadMedida());
        ld.setDetalle(fd.getDetalle());
        Double montoImpuesto = 0d;
        BigDecimal i = null;
        Double montoUnitarioAntesImpuestos = fd.getMontoNeto() / fd.getCantidad();
        if (BillHelper.aplicaImpuestoVentas(fd.getImpuestos())) {
            this.totalServiciosGrabados = this.totalServiciosGrabados + fd.getMontoNeto();
            montoImpuesto = fd.getImpuestosMonto();
            i = createDinero(montoImpuesto);
            //montoUnitarioAntesImpuestos = montoUnitarioAntesImpuestos / fd.getCantidad(); //Incluye impuesto
            com.rfs.fe.nce.ImpuestoType it = new com.rfs.fe.nce.ImpuestoType();
            it.setCodigo("01");
            it.setTarifa(createDinero(fd.getImpuestos()));
            it.setMonto(i);
//            it.setMonto(createDinero(montoImpuesto));
            ld.getImpuesto().add(it);

        } else {
            this.totalServiciosExcento = fd.getMontoColones() + this.totalServiciosExcento;
        }

        ld.setPrecioUnitario(createDinero(createPrecioUnitario(fd,fd.getMontoNeto())));
        ld.setMontoTotal(createDinero(fd.getMontoNeto()));
        ld.setSubTotal(createDinero(fd.getMontoNeto()));
        Double totalColones = (ld.getPrecioUnitario().doubleValue() * fd.getCantidad()) + montoImpuesto;
        ld.setMontoTotalLinea(createDinero((totalColones)));
        if (i==null) {
            return 0d;
        }
        return i.doubleValue();

    }

    private Double createLineaDetalleFromServices(Integer linea, NotaCreditoElectronica.DetalleServicio.LineaDetalle ld, NotaCreditoServicioDetalleDTO fd) {
        ld.setNumeroLinea(BigInteger.valueOf(linea));
        ld.setCantidad(BigDecimal.valueOf(fd.getCantidad()));
        ld.setDetalle(fd.getServicio().getNombre() + " " +fd.getDetalle());
        ld.setPrecioUnitario(createDinero(createPrecioUnitario(fd)));
        ld.setMontoTotal(createDinero(fd.getMontoColones()));
        ld.setSubTotal(createDinero(fd.getMontoColones()));
        Double totalColones = fd.getMontoColones();
        Double montoImpuesto = 0d;
        //  if (BillHelper.aplicaImpuestoVentas(fd)) {
//            ImpuestoType tax = new ImpuestoType();
//            tax.setCodigo("01");
//            tax.setTarifa(BigDecimal.valueOf(BillHelper.TARIFA_IMPUESTO_VENTAS));
//            montoImpuesto = fd.getMontoColones() * BillHelper.IMPUESTO_VENTAS_PORCENTAJE;
//            tax.setMonto(BigDecimal.valueOf(montoImpuesto));
//            ld.getImpuesto().add(tax);
//            totalColones = totalColones + montoImpuesto;

        //       }

        ld.setMontoTotalLinea(BigDecimal.valueOf(totalColones));
        //ld.setUnidadMedida(BillHelper.SERVICIOS_PROFESIONALES);
        ld.setUnidadMedida(this.emisorService.getUnidadMedida());

        return montoImpuesto;
    }

    private void createLineaDetalleFromTerceros(Integer linea, NotaCreditoElectronica.DetalleServicio.LineaDetalle ld, NotaCreditoTercerosDTO fd) {
        ld.setNumeroLinea(BigInteger.valueOf(linea));
        ld.setCantidad(BigDecimal.valueOf(fd.getCantidad()));

        ld.setDetalle(fd.getTerceros().getNombre() + " " +fd.getDetalle());
        ld.setPrecioUnitario(createDinero(createPrecioUnitario(fd)));
        ld.setMontoTotal(createDinero(fd.getMontoColones()));
        ld.setMontoTotalLinea(createDinero(fd.getMontoColones()));
        ld.setSubTotal(createDinero(fd.getMontoColones()));
        //ld.setUnidadMedida(BillHelper.SERVICIOS_PROFESIONALES);
        ld.setUnidadMedida(this.emisorService.getUnidadMedida());

    }

    private Double createPrecioUnitario(NotaCreditoServicioDetalleDTO fd) {
        Double price = 0d;
        try {
            if (fd.getTipoCambio().getEsDefault()==1) {
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

    private Double createPrecioUnitario(NotaCreditoDetalleDTO fd, Double montoAntesImpuestos) {
        Double price = 0d;
        try {
            if (fd.getTipoCambio().getEsDefault()==1) {
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

    private Double createPrecioUnitario(NotaCreditoTercerosDTO fd) {
        Double price = 0d;
        try {
            if (fd.getTipoCambio().getEsDefault()==1) {
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

    /*    private NotaCreditoElectronica.ResumenFactura getResumen(NotaCreditoDataDTO data) {
            NotaCreditoElectronica.ResumenFactura resumenFactura = new NotaCreditoElectronica.ResumenFactura();

            resumenFactura.setCodigoMoneda(getMoneda(data));
            Double exentos = data.getTotal() - data.getImpuestoVentas();

            resumenFactura.setTotalMercanciasGravadas(BigDecimal.valueOf(0));
            resumenFactura.setTotalMercanciasExentas(BigDecimal.valueOf(0));

            resumenFactura.setTotalServExentos(createDinero(exentos));
            resumenFactura.setTotalServGravados(createDinero(data.getImpuestoVentas()));
            resumenFactura.setTotalGravado(createDinero(data.getImpuestoVentas()));
            resumenFactura.setTotalExento(createDinero(exentos));

            BigDecimal ventaTotal = resumenFactura.getTotalGravado().add(resumenFactura.getTotalExento());
            ventaTotal = ventaTotal.setScale(5,BigDecimal.ROUND_DOWN);
            resumenFactura.setTotalVenta(ventaTotal);
            resumenFactura.setTotalDescuentos(BigDecimal.valueOf(0));
            resumenFactura.setTotalVentaNeta(ventaTotal);

            resumenFactura.setTotalImpuesto(createDinero(this.totalImpuestos));
            BigDecimal totalComprobante = resumenFactura.getTotalVenta().add(resumenFactura.getTotalImpuesto());
            totalComprobante = totalComprobante.setScale(5,BigDecimal.ROUND_DOWN);

            resumenFactura.setTotalComprobante(totalComprobante);
            return resumenFactura;
        }*/
    private NotaCreditoElectronica.ResumenFactura getResumen(NotaCreditoDataDTO data) {
        NotaCreditoElectronica.ResumenFactura resumenFactura = new NotaCreditoElectronica.ResumenFactura();

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

        //Double total = this.totalServiciosExcento + this.totalServiciosGrabados;
        resumenFactura.setTotalVenta(totalBD);
        resumenFactura.setTotalVentaNeta(totalBD);
        BigDecimal bdImpuestos = createDinero(this.totalImpuestos);
        resumenFactura.setTotalImpuesto(bdImpuestos);

        BigDecimal totalComprobanteBD = totalBD.add(bdImpuestos);
        resumenFactura.setTotalComprobante(totalComprobanteBD);
        return resumenFactura;
    }

    private String getMoneda(NotaCreditoDataDTO data) {
        if (data.getTipoCambio().getNombre().equals(BillHelper.COLON)) {
            return "CRC";
        } else {
            return "USD";
        }
    }

    private XMLGregorianCalendar getFechaEmision(Date fechaFacturacion) throws DatatypeConfigurationException {
        if (fechaFacturacion==null) {
            return null;
        }
        //return df.format(fechaFacturacion);

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    }

//    private BigDecimal createDinero(Double value) {
//        BigDecimal bd = BigDecimal.valueOf(value);
//
//        return bd.setScale(5, RoundingMode.FLOOR);
//    }

    private BigDecimal createDinero(Double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN);
    }

    private String getFechaEmisionStr(Date fechaFacturacion) {
        if (fechaFacturacion==null) {
            return null;
        }
        return df.format(fechaFacturacion);

    }

    private String getCondicionVenta(Integer credito) {
        String result = BillHelper.CONTADO;

        if (credito==1) {
            result = BillHelper.CREDITO;
        }

        return result;
    }

    public String getMedioPago() {
        return BillHelper.MEDIO_TRANSERENCIA_BANCARIA;
    }

    private String getPlazoCredito(NotaCreditoDataDTO data) {
        if (data.getCredito()==1 && data.getDiasCredito()!=null) {
            return data.getDiasCredito().toString();
        }
        return "";
    }

//    public EmisorType getEmisor() {
//        EmisorType e = new EmisorType();
//        UbicacionType u = new UbicacionType();
//
//        u.setProvincia("4");
//        u.setCanton("08");
//        u.setDistrito("01");
//        u.setBarrio("01");
//        u.setOtrasSenas("Heredia, Flores, Llorente");
//
//        e.setNombre("RFS LOGISTICA INTEGRADA SOCIEDAD ANONIMA");
//        IdentificacionType i = new IdentificacionType();
//        i.setNumero("3101654879");
//
//        i.setTipo("02");
//        e.setIdentificacion(i);
//        e.setUbicacion(u);
//        e.setCorreoElectronico("rfonseca@rfslogistica.com");
//        TelefonoType t = new TelefonoType();
//        t.setCodigoPais(BigInteger.valueOf(506));
//        t.setNumTelefono(BigInteger.valueOf(40363725));
//        e.getTelefono();
//
//        return e;
//    }

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
        com.rfs.fe.TelefonoType t = new com.rfs.fe.TelefonoType();
        t.setCodigoPais(BigInteger.valueOf(506));
        t.setNumTelefono(BigInteger.valueOf(emisorService.getTelefono()));
        return e;
    }

    private NotaCreditoElectronica.Normativa createNormativa() {
        NotaCreditoElectronica.Normativa fn = new NotaCreditoElectronica.Normativa();
        fn.setFechaResolucion("20-02-2017 13:22:22");
        fn.setNumeroResolucion("DGT-R-48-2016");
        return fn;
    }


    public ReceptorType getReceptor(String cliente, String cedula, String tipo, String telefono) {
        ReceptorType r = new ReceptorType();
        r.setNombre(cliente);
        IdentificacionType i = new IdentificacionType();
        i.setTipo(tipo);
        cedula = transformCedula(cedula, tipo);
        i.setNumero(cedula);

        r.setIdentificacion(i);
        return r;
    }

    private String transformCedula(String cedula, String tipo) {
        if (cedula!=null) {
            cedula = cedula.replace(" ","");
            cedula = cedula.replace("-","");
            if (tipo!=null && tipo.equals("02") && cedula.length()>9 && (cedula.startsWith("30101") || cedula.startsWith("3101"))) {
                if (cedula.startsWith("30101")) {
                    cedula = "3" + cedula.substring(2);

                }

                if (cedula.startsWith("3101") && cedula.length()>9) {
                    cedula = cedula.substring(0,10);
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

}
