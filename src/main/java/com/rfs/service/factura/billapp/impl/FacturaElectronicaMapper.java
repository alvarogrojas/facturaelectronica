package com.rfs.service.factura.billapp.impl;

import com.rfs.dtos.FacturaDataDTO;
import com.rfs.dtos.FacturaDetalleDTO;
import com.rfs.dtos.FacturaServicioDetalleDTO;
import com.rfs.dtos.FacturaTercerosDTO;
import com.rfs.fe.FacturaElectronica;
import com.rfs.fe.IdentificacionType;
import com.rfs.fe.ImpuestoType;
import com.rfs.fe.ReceptorType;
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

public class FacturaElectronicaMapper implements Mapper {


    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private Double totalImpuestos = 0d;

    private EmisorService emisorService;
    private Double totalServiciosGrabados;
    private Double totalServiciosExcento;


    public FacturaElectronica mapFacturaElectronica(Object o, String tipoDocumento) throws DatatypeConfigurationException {
        FacturaElectronica fe = new FacturaElectronica();
        try {
            FacturaDataDTO data = (FacturaDataDTO) o;

            fe.setTipoDocumento(tipoDocumento);

            fe.setNumeroConsecutivo(data.getFacturaId().toString());

            fe.setCondicionVenta(getCondicionVenta(data.getCredito()));
            fe.setPlazoCredito(getPlazoCredito(data));

            fe.getMedioPago().add(getMedioPago());

            fe.setEmisor(getEmisor());

            fe.setReceptor(getReceptor(data.getCliente().getNombre(), data.getCedula(), data.getFisicaOJuridica(), data.getTelefono(), data.getEsClienteInternacional()));
            fe.setEsClienteInternacional(data.getEsClienteInternacional());

            Date emisionDate = new Date();
            fe.setFechaEmision(getFechaEmision(emisionDate));
            fe.setFechaEmisionStr(getFechaEmisionStr(emisionDate));
            fe.setDetalleServicio(createDetalles(data));
            fe.setResumenFactura(getResumen(data));

            fe.setNormativa(createNormativa());

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

    private FacturaElectronica.Normativa createNormativa() {
        FacturaElectronica.Normativa fn = new FacturaElectronica.Normativa();
        fn.setFechaResolucion("20-02-2017 13:22:22");
        fn.setNumeroResolucion("DGT-R-48-2016");
        return fn;
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


    private FacturaElectronica.DetalleServicio createDetalles(FacturaDataDTO data) {
        FacturaElectronica.DetalleServicio detalleServicio = new FacturaElectronica.DetalleServicio();
        createDetalle(data.getDetallesDTO(), detalleServicio);
        // this.totalImpuestos = createDetalleServicioForServices(data.getFacturaServiciosDTO(),detalleServicio);
        //createDetalleServicioForTerceros(data.getFacturaTercerosDTO(),detalleServicio);
        return detalleServicio;
    }

    private void createDetalle(List<FacturaDetalleDTO> detalles, FacturaElectronica.DetalleServicio detalleServicio) {
        if (detalles == null) {
            return;
        }
        this.totalServiciosExcento = 0d;
        this.totalServiciosGrabados = 0d;
        this.totalImpuestos = 0d;
        Integer linea = detalleServicio.getLineaDetalle().size() + 1;
        FacturaElectronica.DetalleServicio.LineaDetalle ld;
        for (FacturaDetalleDTO fd : detalles) {
            ld = new FacturaElectronica.DetalleServicio.LineaDetalle();
            this.totalImpuestos = totalImpuestos + createLineaDetalle(linea, ld, fd);
            detalleServicio.getLineaDetalle().add(ld);
            linea++;
        }
    }

    private Double createLineaDetalle(Integer linea, FacturaElectronica.DetalleServicio.LineaDetalle ld, FacturaDetalleDTO fd) {
        ld.setNumeroLinea(BigInteger.valueOf(linea));
        ld.setCantidad(BigDecimal.valueOf(fd.getCantidad()));
        ld.setUnidadMedida(this.emisorService.getUnidadMedida());
        ld.setDetalle(fd.getDetalle());
        Double montoImpuesto = 0d;
        Double montoUnitarioAntesImpuestos = fd.getMontoNeto() / fd.getCantidad();
        BigDecimal i = null;
        if (BillHelper.aplicaImpuestoVentas(fd.getImpuestos())) {
            this.totalServiciosGrabados = this.totalServiciosGrabados + fd.getMontoNeto();
            montoImpuesto = fd.getImpuestosMonto();
            i = createDinero(montoImpuesto);
            //montoUnitarioAntesImpuestos = montoUnitarioAntesImpuestos / fd.getCantidad(); //Incluye impuesto
            ImpuestoType it = new ImpuestoType();
            it.setCodigo("01");
            it.setTarifa(createDinero(fd.getImpuestos()));
            it.setMonto(i);
//            it.setMonto(createDinero(montoImpuesto));
            ld.getImpuesto().add(it);

        } else {
            this.totalServiciosExcento = fd.getMontoColones() + this.totalServiciosExcento;
        }

        ld.setPrecioUnitario(createDinero(createPrecioUnitario(fd, fd.getMontoNeto())));
        ld.setMontoTotal(createDinero(fd.getMontoNeto()));
        ld.setSubTotal(createDinero(fd.getMontoNeto()));
        Double totalColones = (ld.getPrecioUnitario().doubleValue() * fd.getCantidad()) + montoImpuesto;
        ld.setMontoTotalLinea(createDinero((totalColones)));
        if (i==null) {
            return 0d;
        }
        return i.doubleValue();
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

    private FacturaElectronica.ResumenFactura getResumen(FacturaDataDTO data) {
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
    }
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


    public com.rfs.fe.EmisorType getEmisor() {
        com.rfs.fe.EmisorType e = new com.rfs.fe.EmisorType();
        com.rfs.fe.UbicacionType u = new com.rfs.fe.UbicacionType();

        u.setProvincia(emisorService.getProvincia());
        u.setCanton(emisorService.getCanton());
        u.setDistrito(emisorService.getDistrito());
        u.setBarrio("01");
        u.setOtrasSenas(emisorService.getDireccion());

        e.setNombre(emisorService.getNombre());
        com.rfs.fe.IdentificacionType i = new com.rfs.fe.IdentificacionType();

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

    public ReceptorType getReceptor(String cliente, String cedula, String tipo, String telefono, Short esInternacional) {
        ReceptorType r = new ReceptorType();
        r.setNombre(cliente);
        if (esInternacional != null && esInternacional == 1) {
            r.setIdentificacionExtranjero(cedula);
//                r.setIdentificacionExtranjero(cliente);
        } else {
            IdentificacionType i = new IdentificacionType();

            i.setTipo(tipo);
            cedula = transformCedula(cedula, tipo);
            i.setNumero(cedula);

            r.setIdentificacion(i);
        }
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
}
