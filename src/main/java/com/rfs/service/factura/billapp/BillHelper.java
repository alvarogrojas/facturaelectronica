package com.rfs.service.factura.billapp;

import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.fe.v43.mr.MensajeReceptor;
import com.rfs.fe.v43.nc.NotaCreditoElectronica;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.apache.commons.lang.StringUtils;

public class BillHelper {

    public static final String NO_ENVIADA = "no_enviada";
    public static final String UNIT_PRODUCTO = "MERCANCIA";
    public static final String UNIT_SERVICIO = "SERVICIO";
    public static final String TIPO_DOC_AUTORIZADO_POR_LEY = "03";
    public static final String NUM_DOCUMENTO_EXONERADO = "Num 7210 - Articulo 23";
    public static final BigInteger PORCENTAJE_EXONERACION_ZONA_FRANCA = BigInteger.valueOf(100l);
    private static final String PAIS_CODE = "506";
    private static final String SITUATION_FE = "1";

    public final static Double IMPUESTO_VENTA_TARIFA = 13d;
    public final static Double IMPUESTO_VENTA_TARIFA_INCLUDED = 1.13d;
    public final static Double IMPUESTO_VENTA_TARIFA_NOINCLUDED = 0.13d;

    private static final String BILL_BASE_IMAGES_PATH = "/home/alvaro/billapp/images/";

    public static final String INGRESADA = "Ingresada";
    public static final String ANULADA = "Anulada";
    public static final String PENDIENTE_PAGO = "Pendiente Pago";
    public static final String VENCIDAS = "Vencida";

    private static final String TAX_NACIONALIZACION = "NACIONALIZACION";
    private static final String TAX_DUA_VENTA_LOCAL_ZF = "DUA VENTA LOCAL ZONA FRANCA";

    private static final String NA_EMAIL= "NA";
    public static final String ACTIVA= "ACTIVA";
    public static final String ACTIVA_NC = "ACTIVA_NC";

    public static final String SUPERVISOR = "SUPERVISOR";
    public static final String ADMIN = "ADMIN";
    public static final String COORDINADOR_LEGAL = "COORDINADOR_LEGAL";

    public static final String CALIFICACION_BUENA = "BUENA";
    public static final String CALIFICACION_MALA = "MALA";
    public static final String NO_CALIFICADA = "NO CALIFICADA";

    public static final String DOCUMENTO_FACTURA_NO = "Factura No.";
    public static final String DOCUMENTO_NOTA_CREDITO_NO = "Nota Credito.";

    public static final String FACTURA_ELECTRONICA_TIPO = "01";
    public static final String NOTA_DEBITO_TIPO = "02";
    public static final String NOTA_CREDITO_TIPO = "03";
    public static final String TIQUETE_ELECTRONICO = "04";
    public static final String CONFIRMACION_ACEPTACION_COMPROBANTE_TIPO = "05";
    public static final String CONFIRMACION_ACEPTACION_PARCIAL_COMPROBANTE = "06";
    public static final String CONFIRMACION_RECHAZO_COMPROBANTE = "07";


    public static final String CONTADO = "01";
    public static final String CREDITO = "02";

    public static final String MEDIO_TRANSERENCIA_BANCARIA = "04";
    public static final String SERVICIOS_PROFESIONALES = "Sp";
    //    public static final Integer SERVICIOS_PROFESIONALES = 1;
    public static final Integer CONFIRMACION_ACEPTADO = 1;
    public static final Integer CONFIRMACION_ACEPTADO_PARCIALMENTE = 2;
    public static final Integer CONFIRMACION_RECHAZADO = 3;
    public static final String PREFIX_MENSAJE_HACIENDA_FILE = "mensajehacienda_";

//    public static final String FACTURA_NAMESPACE_V42 = "https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/facturaElectronica";
//    public static final String NOTA_CREDITO_NAMESPACE_V42 = "https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica";
//    public static final String CONFIRMACION_NAMESPACE_V42 = "https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica";
//    public static final String MENSAJE_RECEPTOR_NAMESPACE_V42 = "https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/mensajeReceptor";


    public static final String FACTURA_NAMESPACE_V43 = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/facturaElectronica";
    public static final String NOTA_CREDITO_NAMESPACE_V43 = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/notaCreditoElectronica";
    //public static final String CONFIRMACION_NAMESPACE_V43 = "https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica";
    public static final String MENSAJE_RECEPTOR_NAMESPACE_V43 = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor";

    public static final Integer TARIFA_IMPUESTO_VENTAS = 13;
    public static final Double IMPUESTO_VENTAS_PORCENTAJE = 0.13;

    public static final String FACTURA_ELECTRONICA_BASE_XML = "/FacturaElectronica";
    public static final String MENSAJE_RECEPTOR_BASE_XML = "/MensajeReceptor";

    public static final String NOTA_CREDITO_ELECTRONICA_BASE_XML = "/NotaCreditoElectronica";
    public static final String TIPO_CONFIRMACION_FE = "CFN";
    public static final String TIPO_RECHAZO_FE = "RCH";

    public static final String TIPO_FACTURA_FE = "FE";
    public static final String TIPO_NOTA_CREDITO_FE = "NCF";
    
    public static final String RESPUESTA_ACEPTADA = "aceptado";
    public static final String RESPUESTA_RECHAZADO = "rechazado";
    public static final String RESPUESTA_NO_ACEPTADO_NO_RECHAZADO = "no_aceptado_rechazado";
    public static final String RESPUESTA_PENDIENTE = "no_aceptado_rechazado";
    public static final String RESPUESTA_NO_ENVIADA = "no_enviada";
    public static final String RESPUESTA_PROCESANDO = "procesando";

    public static final String RESPUESTA_ENVIANDO = "enviando";
    public static final String ENVIANDO = "enviando";



    public static final String FINALIZADA = "Finalizada";
    public static final String NATIONAL_CURRENCY = "CRC";


    public static boolean isNAEmail(String email) {
        if (email==null) {
            return true;
        }
        if (email.equals(NA_EMAIL)) {
            return true;
        } else {
            return false;
        }
    }

    public static String createLogoUrl(String logoPath) {

        return BILL_BASE_IMAGES_PATH + logoPath;
    }

    public static boolean aplicaImpuestoVentas(Double montoImpuestos) {

        if (montoImpuestos!=null && montoImpuestos>0d) {
            return true;
        }
        return false;
    }

    public static  String transformCedula(String cedula, String tipo) {
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

   public static final String COLON = "Colon";
   public static final String DOLAR = "Dolar";


    public static void generarClave(FacturaElectronica fe) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(fe.getNumeroConsecutivo(), fe.getTipoDocumento()));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);

    }

    public static void generarClave(NotaCreditoElectronica fe) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(fe.getNumeroConsecutivo(), fe.getTipoDocumento()));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);

    }

    public static void generarConsecutivo(MensajeReceptor fe, String tipoDocumento, Integer consecutivo) {
        //String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivoReceptor(generarConsecutivoFactura(consecutivo.toString(), tipoDocumento));
        //String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());
    }

    public static void generarConsecutivo(NotaCreditoElectronica fe, String tipoDocumento, Integer consecutivo) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(consecutivo.toString(), tipoDocumento));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);
        //String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());
    }

    private static String generarSeguridad() {
        Random rnd = new Random();
        Integer n = 10000000 + rnd.nextInt(90000000);
        return StringUtils.leftPad(n.toString(), 8, "0");
    }

    private static String generarCedula(String cedula) {
        cedula = cedula.replaceAll("-","");

        StringBuilder stringBuilder = new StringBuilder(cedula);
        while (stringBuilder.length() < 12) {
            stringBuilder.insert(0, Integer.toString(0));
        }
        return stringBuilder.toString();
    }

    private static String generarConsecutivoFactura(String consecutivoFactura, String tipoDocumento) {
        String consecutivoStr =  StringUtils.leftPad(consecutivoFactura, 10, "0");
        return  "001" + "00001" + tipoDocumento + consecutivoStr;
    }

    private static String crearFechaClave() {
        Date fecha = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        Integer month = c.get(Calendar.MONTH) + 1;
        Integer ano = getAno();
        Integer day = c.get(Calendar.DAY_OF_MONTH);
        String fechaStr = (day < 10 ? "0" + day.toString(): day.toString()) + (month < 10 ? "0" + month.toString(): month.toString()) + ano.toString();
        return fechaStr;
    }

    private static int getAno() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String formattedDate = df.format(Calendar.getInstance().getTime());
        return Integer.parseInt(formattedDate);
    }

    public static boolean isNationalCurrency(String currency) {

        return BillHelper.NATIONAL_CURRENCY.equals(currency);
    }
}

