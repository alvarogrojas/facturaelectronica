package com.rfs.service.factura.billapp;

import com.google.common.base.Strings;
import com.rfs.domain.*;
import com.rfs.domain.factura.BillSender;
import com.rfs.domain.factura.BillSenderDetail;
import com.rfs.domain.factura.OptionSet;
import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.fe.v43.mr.MensajeReceptor;
import com.rfs.fe.v43.nc.NotaCreditoElectronica;
import com.rfs.repository.ConfirmaRechazoDocumentoRepository;
import com.rfs.repository.ErrorEnvioRepository;
import com.rfs.repository.FacturaRepository;
import com.rfs.repository.NotaCreditoRepository;
import com.rfs.repository.factura.BillSenderDetailRepository;
import com.rfs.repository.factura.BillSenderRepository;
import com.rfs.service.EmpresaService;
import com.rfs.service.FacturaElectronicaMarshaller;
import com.rfs.service.GlobalDataManager;
import com.rfs.service.factura.billapp.impl.EmisorServiceImpl;
import com.rfs.view.PdfNotaCreditoView;
import javafx.scene.control.TextInputControl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class BillTaskService {

    private Logger logger = Logger.getLogger("BillTaskService");

    private static final String PAIS_CODE = "506";
    private static final String SITUATION_FE = "1";

    @Autowired
    private GlobalDataManager globalDataManager;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Autowired
    private BillDataService billDataService;

    @Autowired
    private BillConfigService billConfigService;

    @Autowired
    private BillEmitter billEmitter;

    @Autowired
    private FacturaSerializer serializer;

    @Autowired
    private FacturaElectronicaMarshaller fem;

    @Autowired
    private BillPdfGenerator billPdfGenerator;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @Autowired
    private BillSenderRepository billSenderRepository;

    @Autowired
    private BillSenderDetailRepository billSenderDetailRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private NotaCreditoRepository notaCreditoRepository;

    @Autowired
    private ErrorEnvioRepository errorEnvioRepository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ConfirmaRechazoDocumentoRepository confirmaRechazoDocumentoRepository;


    @Transactional
    @Async
    public Result emitNotaCreditoElectronica(NotaCreditoElectronica nc, Integer id,
                                             String correoDestino, String emisor, String receptor, Empresa e) {
        BillSender pef = createProcesoEmisionFactura(nc.getTipoDocumento(), "ENVIO NOTA DE CREDITO ", nc.getEmpresaId());
        Result result = new Result();
        try {

            billEmitter.authenticate();
            result = enviarNotaCredito(nc, pef, id, correoDestino, emisor, receptor,nc.getTipoDocumento(), e);
            if (result!=null && result.getResult()==1) {
                actualizarFacturaBillSenderDetail(nc);
            }

        } catch (JAXBException e1) {
            e1.printStackTrace();
            throw new RuntimeException(e1.getMessage());
        } catch (Exception e2) {
            e2.printStackTrace();
            throw new RuntimeException(e2.getMessage());

        } finally {
            enviarCorreoProcesoNC(pef,nc, emisor, id);
            actualizarBillSender(pef, 1);
            billEmitter.desconectar();

        }
        return result;
    }

    @Transactional
    @Async
    public Result emitFactura(FacturaElectronica fe) {
        Result result = new Result();
        BillSender pef = createProcesoEmisionFactura(fe.getTipoDocumento(), "ENVIO DE FACTURAS ", fe.getEmpresaId());
        Integer totalSent = 0;

        try {
            if (!billEmitter.authenticate()) {
                this.agregarError("AUTENTICACION_HACIENDA",this.billEmitter.getMessageError(), fe.getId());
                result.setResultStr(this.billEmitter.getMessageError());
                result.setResult(-10);
                return result;
            }

            result = enviarFacturaElectronica(fe, pef, totalSent);
            System.out.println("** RESULTADO " + result.getResultStr());
            //result.setResult(totalSent);
            if (result.getResult()==0 && (result.getResultStr()==null || result.getResultStr().equals(""))) {
                result.setResultStr("Hubo un error en el proceso de envío");
            } else if (result.getResult()!=0 && (result.getResultStr()==null ||  result.getResultStr().equals(""))) {
                result.setResultStr("ENVIADA EXITOSAMENTE");
            }

        } catch (JAXBException e) {
            this.logger.info("--> ERROR " + e.getErrorCode() + " " + getExceptionMessage(e));
            e.printStackTrace();
            throw new RuntimeException(getExceptionMessage(e));
        } catch (Exception e) {
            this.logger.info("--> ERROR  " + getExceptionMessage(e));

            e.printStackTrace();
            throw new RuntimeException(getExceptionMessage(e));
        } finally {
            enviarCorreoProceso(fe,totalSent,result, result.getBillSenderDetail());
            actualizarBillSender(pef, totalSent);
            billEmitter.desconectar();
        }
        return result;
    }

    @Transactional
    @Async
    public Result emitConfirmation(MensajeReceptor fe,Integer consecutivo,
                                   String correoDestino, String emisor, String receptor, String tipo) {
        BillSender pef = createProcesoEmisionFactura(tipo, "ENVIO CONFIRMACION/ RECHAZO ", this.billConfigService.getBillConfigData().getEmpresaId());
        Result result = new Result();
        try {
            if (!billEmitter.authenticate()) {
                this.agregarError("AUTENTICACION_HACIENDA",this.billEmitter.getMessageError(), fe.getId());
                result.setResultStr(this.billEmitter.getMessageError());
                result.setResult(-1);
                return result;
            }
            result = enviarConfirmacion(fe, pef, consecutivo, correoDestino, emisor, receptor,tipo);
            System.out.println("** RESULTADO  CONFIRMACION" + result.getResultStr());

            if (result.getResult()==0 && (result.getResultStr()==null || result.getResultStr().equals(""))) {
                result.setResultStr("Hubo un error en el proceso de envío");
            } else if (result.getResult()!=0 && (result.getResultStr()==null ||  result.getResultStr().equals(""))) {
                result.setResultStr("ENVIADA EXITOSAMENTE");


            }
        } catch (JAXBException e) {
            this.logger.info("--> ERROR " + e.getErrorCode() + " " + getExceptionMessage(e));

            e.printStackTrace();
            throw new RuntimeException(getExceptionMessage(e));
        } catch (Exception e) {
            this.logger.info("--> ERROR  " + getExceptionMessage(e));

            e.printStackTrace();
            throw new RuntimeException(getExceptionMessage(e));
        } finally {
            enviarCorreoProcesoConfirmacion(result.getBillSenderDetail(),fe, emisor, consecutivo);
            actualizarBillSender(pef, 1);
            billEmitter.desconectar();
        }
        return result;
    }

    private void actualizarFacturaBillSenderDetail(NotaCreditoElectronica f) {
        List<BillSenderDetail> detalleList = this.billSenderDetailRepository.findByBillIdAndEmpresaIdAndTipoOrderByFechaIngresoDesc(f.getFacturaId(),f.getEmpresaId(), "FE");
        BillSenderDetail detalle =null;
        if (detalleList==null || detalleList.size()<=0) {
            return;
        }
        detalle = detalleList.get(0);
        detalle.setEnviadaHacienda(2);
        billSenderDetailRepository.save(detalle);
    }


    private Integer enviarCorreoProcesoNotaCredito(BillSenderDetail pef, FacturaElectronica fe) {
        String message = generarMensajeProcesoNotaCredito(pef, fe);
        String xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(fe, pef);
        String pdfFile = this.billConfigService.getPdfFileAndFullPath(fe, pef);

        return this.emailSenderService.sendMessageProcessNotaCreditoResult("RESULTADO DE ENVIO DE NOTA CREDITO", message, xmlFile, pdfFile,fe.getId().toString(),this.billConfigService.getBillConfigData().getRecepcionCorreos());
    }

    private Integer     enviarCorreoProcesoConfirmacion(BillSenderDetail pef, MensajeReceptor fe, String emisor, Integer consecutivo) {
        String mensaje = " Estimados, \n\n Se  hizo un envio de confirmacion de documento electronico recibido, y que fue emitido por " + emisor  + " el dia " + dateFormat.format(pef.getDateSent()) + ".\n\n";
        String xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(fe.getClave(), pef);
        return this.emailSenderService.sendMessageProcessConfirmacionRechazo("RESULTADO DE ENVIO CONFIRMACIÓN DOCUMENTO ELECTRÓNICO", mensaje, null,consecutivo.toString(),this.billConfigService.getBillConfigData().getRecepcionCorreos());
    }

    private Integer enviarCorreoProcesoNC(BillSender pef, NotaCreditoElectronica fe, String emisor, Integer consecutivo) {
        String mensaje = " Estimados, \n\n Se  hizo un envio de la nota de credito con el numero interno " + fe.getId() + " consecutivo para hacienda " + fe.getNumeroConsecutivo() + " el dia " + dateFormat.format(pef.getDateSent()) + " para el cliente " + fe.getReceptor().getNombre()  + ".\n\n";

        return this.emailSenderService.sendMessageProcessResult("Información de envió de NOTA CREDITO  consecutivo para hacienda " + fe.getNumeroConsecutivo(), mensaje,this.billConfigService.getBillConfigData().getRecepcionCorreos());
    }


    private Result enviarNotaCredito(NotaCreditoElectronica nce, BillSender pef, Integer id, String correoDestino, String emisor, String receptor, String tipo, Empresa empresa) throws Exception {
        String consecutive;
        String fileName;
        OptionSet options;
        Result result = new Result();
        BillSenderDetail pefd = obtenerBillSender(pef.getIdSend(), nce.getId(), BillHelper.TIPO_NOTA_CREDITO_FE, nce.getClave(), empresa.getId());




        options = billConfigService.initOptionsValues(pefd, nce.getClave() + ".xml");
//        billEmitter.sign(options, BillHelper.NOTA_CREDITO_NAMESPACE_V43);

        result = sendNC(nce, options, pefd);
        Integer validada =-1;
        if (result==null || result.getResult()==-2) {
            return result;
        } else if (result.getResult()==1) {
            validada = 1; // LOCATION
        }
        Integer enviada = result.getResult();
        String observaciones = result.getResultStr();

        String validadaLabel = " SI ";

        Result r  = result;
        String status = r.getEstado();

        Integer enviadaCliente = 0;

        logger.info("Nota Credito enviada " + nce.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ enviadaCliente);
        System.out.println("Nota Credito enviada " + nce.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ enviadaCliente);
        pefd.setConsecutivo(nce.getNumeroConsecutivo());
        createProcesoEmisionDetalle(nce.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), enviada, enviadaCliente, validada, observaciones,status, BillHelper.TIPO_NOTA_CREDITO_FE, empresa.getId());
        updateNotaCredito(nce.getId(),  nce.getFacturaId(), empresa.getId(), 1, status, nce.getClave(), nce.getNumeroConsecutivo(), null);

        return result;

    }

    private String getNCNumRef(NotaCreditoElectronica nce) {
        if (nce!=null && nce.getInformacionReferencia()!=null && nce.getInformacionReferencia().size()>0) {
            return nce.getInformacionReferencia().get(0).getNumero();
        }
        return "";
    }

    private Result enviarConfirmacion(MensajeReceptor mr, BillSender pef, Integer consecutivoDE, String correoDestino, String emisor, String receptor, String tipo) throws JAXBException {
        String consecutive;
        String fileName;
        OptionSet options;
        Result result = new Result();
        BillSenderDetail pefd = createProcesoEmisionFacturaDetalle(pef.getIdSend(), consecutivoDE, getConfirmacionRechazoCode(tipo),this.billConfigService.getBillConfigData().getEmpresaId());
        pefd.setPath(billConfigService.getInitialPath(consecutivoDE.toString(),  mr.getNumeroCedulaEmisor(),tipo));

//        pefd.setPath(billConfigService.getCompletePath());
        pefd.setClave(mr.getClave());

        fileName = getXmlFileName(mr.getClave(), pefd);
        mr = fem.writeXMLFile(mr,pefd.getPath() + fileName);
        options = billConfigService.initOptionsValues(pefd, fileName);
        if (!firmarFactura(options, BillHelper.MENSAJE_RECEPTOR_NAMESPACE_V43, mr.getId(), mr.getEmpresaId(), BillHelper.TIPO_CONFIRMACION_FE, pefd)) {
            result.setResultStr("Hubo un error al firmar la factura");
            result.setResult(-10);
            return result;
        }
        result = sendComprobante(mr, options, pefd, tipo);

        Integer enviada = result.getResult();
        Integer validada =  billEmitter.validate(mr);
        String observaciones = result.getResultStr();
        String validadaLabel = " SI ";

//        agregarPendienteMensajeHacienda(mr, pefd,tipo);

        logger.info("Factura enviada " + mr.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
        System.out.println("Factura enviada " + mr.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
        pefd.setConsecutivo(mr.getNumeroConsecutivoComprobante());
        createProcesoEmisionDetalle(mr.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), enviada, 0, validada, observaciones,"pendiente", getConfirmacionRechazoCode(tipo), pefd.getEmpresaId());

        updateConfirmacionRechazo(mr.getId(),null,"enviado", pefd.getIdSendDetail(), mr.getNumeroConsecutivoReceptor());
        result.setBillSenderDetail(pefd);
        return result;



    }


    private void actualizarBillSender(BillSender pef, Integer totalSent) {
        pef.setTotalEnviadas(totalSent);
        pef.setFechaUltimoCambio(new Date());
        pef.setUltimoCambioId(pef.getIngresadoPor());
        billSenderRepository.save(pef);
    }


    private Result enviarFacturaElectronica(FacturaElectronica fe, BillSender pef, Integer totalSent) throws JAXBException {
        String consecutive;
        String fileName;
        OptionSet options;
        Result result = new Result();
        BillSenderDetail pefd = obtenerBillSender(pef.getIdSend(), fe.getId(), BillHelper.TIPO_FACTURA_FE, fe.getClave(), fe.getEmpresaId());

        options = billConfigService.initOptionsValues(pefd, fe.getClave() + ".xml");

        result = sendFacturaElectronica(fe, options, pefd);
        Integer validada =-1;
        if (result==null || result.getResult()==-2 || result.getResult()==-3) {
            //return result;
        } else if (result.getResult()==1) {
            validada = 1; // LOCATION
        }
        Integer enviada = result.getResult();
        String observaciones = result.getResultStr();
        String status = result.getEstado();

        String validadaLabel = "";

        logger.info("Factura enviada " + fe.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
        System.out.println("Factura enviada " + fe.getId() + ": enviada=" + enviada + " validada=" + validadaLabel + " enviada cliente ="+ 0);
        pefd.setConsecutivo(fe.getNumeroConsecutivo());
        createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), enviada, 0, validada, observaciones,status, BillHelper.TIPO_FACTURA_FE, fe.getEmpresaId());
        updateFactura(fe.getId(), fe.getEmpresaId(), 1, BillHelper.INGRESADA, status,fe.getClave(),fe.getNumeroConsecutivo());

        result.setBillSenderDetail(pefd);
        return result;

    }

    private boolean manageNotResultError(FacturaElectronica fe, BillSenderDetail pefd, Result result, String observaciones, Result r) {
        if (r==null) {
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, observaciones, "NO_VALIDADA", BillHelper.TIPO_FACTURA_FE,fe.getEmpresaId());
            System.out.println("Se produjo un error al intentar obtener respuesta de  " + fe.getId() + ": enviada=" + result.getResultStr());
            return true;
        }
        return false;
    }

    private Result sendComprobante(MensajeReceptor mr, OptionSet options, BillSenderDetail pefd,String tipo) throws JAXBException {
        Result result = billEmitter.sendConfirmacionRechazo(options, BillHelper.MENSAJE_RECEPTOR_BASE_XML, mr);
        if (result.getResult()==-2) {
            agregarError("CONFIRMA_RECHAZO",billEmitter.getMessageError(),mr.getId());
            createProcesoEmisionDetalle(mr.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(),
                    1, 0, 0, result.getResultStr(),
                    "CONFIRMA_RECHAZO", tipo, pefd.getEmpresaId());
        } else if (result.getResult()==-3){
            agregarError("ENVIO", billEmitter.getMessageError(), mr.getId());
            createProcesoEmisionDetalle(mr.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(),
                    1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", tipo, pefd.getEmpresaId());
            enviarDevCorreo(mr.getClave(), mr.getNumeroConsecutivoComprobante(), "NO RESPUESTA DE HACIENDA", result.getResultStr(), pefd);
            result.setResult(-1);
        }
        if (result.getResult() == -1 || result.getResult() == 1) {

            result.setResult(-1);

            result.setEstado(BillHelper.RESPUESTA_PENDIENTE);

            result.setResultStr("El confirmación/rechazo de comprobante electrónico enviada exitósamente. La respuesta será obtenida automáticamente en unos minutos.");

            result.setEstado(BillHelper.RESPUESTA_PENDIENTE);
            agregarError("CONFIRMA_RECHAZO", result.getResultStr(), mr.getId());
            createProcesoEmisionDetalle(mr.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", tipo, pefd.getEmpresaId());


        }
        return result;
    }

    private Result sendFacturaElectronica(FacturaElectronica fe, OptionSet options, BillSenderDetail pefd) throws JAXBException {
        Result result = billEmitter.send(options, BillHelper.FACTURA_ELECTRONICA_BASE_XML,fe.getEsClienteInternacional());
        if (result.getResult()==-2) {
            agregarError("ENVIO", billEmitter.getMessageError(), fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "ENVIANDO_FACTURA", BillHelper.TIPO_FACTURA_FE, fe.getEmpresaId());
        } else if (result.getResult()==0) {
            agregarError("ENVIO", billEmitter.getMessageError(), fe.getId());
            enviarDevCorreo(fe.getClave(), fe.getNumeroConsecutivo(), "RESPUESTA DE HACIENDA ERROR", billEmitter.getMessageError(), pefd);
            logger.info(billEmitter.getMessageError());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "error_respuesta", BillHelper.TIPO_FACTURA_FE, fe.getEmpresaId());
        } else if (result.getResult()==-3){
            agregarError("ENVIO", billEmitter.getMessageError(), fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", BillHelper.TIPO_FACTURA_FE, fe.getEmpresaId());
            enviarDevCorreo(fe.getClave(), fe.getNumeroConsecutivo(), "NO RESPUESTA DE HACIENDA", billEmitter.getMessageError(), pefd);
            result.setResult(-1);
        }

        if (result.getResult() == -1 || result.getResult() == 1) {

            result.setResult(-1);
            result.setResultStr("Factura electrónica enviada exitósamente" + fe.getClave() + ".La respuesta será obtenida automáticamente en unos minutos.");
            result.setEstado(BillHelper.RESPUESTA_PENDIENTE);


            agregarError("ENVIANDO_FACTURA", BillHelper.RESPUESTA_PENDIENTE, fe.getId());
            createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", BillHelper.TIPO_FACTURA_FE, fe.getEmpresaId());

        }

        return result;
    }

    private Result sendNC(NotaCreditoElectronica nc, OptionSet options, BillSenderDetail pefd) throws JAXBException {
        Result result = billEmitter.send(options, BillHelper.NOTA_CREDITO_ELECTRONICA_BASE_XML, nc.getEsClienteInternacional());


        if (result.getResult()==-2) {
            agregarError("ENVIO",billEmitter.getMessageError(),nc.getId());
            createProcesoEmisionDetalle(nc.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "ENVIANDO_NC", BillHelper.TIPO_NOTA_CREDITO_FE, nc.getEmpresaId());
        } else if (result.getResult()==-3){
            agregarError("ENVIO", billEmitter.getMessageError(), nc.getId());
            createProcesoEmisionDetalle(nc.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "no_aceptado_rechazado", BillHelper.TIPO_NOTA_CREDITO_FE, nc.getEmpresaId());
            enviarDevCorreo(nc.getClave(), nc.getNumeroConsecutivo(), "NO RESPUESTA DE HACIENDA", result.getResultStr(), pefd);
            result.setResult(-1);
        }
        if (result.getResult() == -1 || result.getResult() == 1) {

            result.setResult(-1);
            result.setEstado(BillHelper.RESPUESTA_PENDIENTE);

            result.setResultStr("Nota de Crédito enviada exitósamente con la clave " + nc.getClave() + ". La respuesta será obtenida automáticamente en unos minutos.");
            agregarError("ENVIANDO_NC", result.getResultStr(), nc.getId());
            createProcesoEmisionDetalle(nc.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(), 1, 0, 0, result.getResultStr(), "ENVIANDO_NC", BillHelper.TIPO_NOTA_CREDITO_FE, nc.getEmpresaId());
        }

        return result;
    }

    private boolean firmarFactura(OptionSet options, String facturaNamespaceV42, Integer id, Integer empresaId, String tipo, BillSenderDetail pefd) {
        if (!billEmitter.sign(options, facturaNamespaceV42)) {
            agregarError("FIRMA", billEmitter.getMessageError(), id);
            createProcesoEmisionDetalle(id, new Date(), pefd, this.billConfigService.getCurrentUser(), 0, 0, 0, billEmitter.getMessageError(), "FIRMA", tipo, empresaId);

            return false;
        }
        return true;
    }


    private Result obtenerEstadoComprobante(String clave, Integer validada, Integer facturaId, String path) throws JAXBException {
        Integer trying = 1;
        Result vr = new Result();
        if (clave==null) {
            vr.setResult(-2);
            vr.setResultStr("NO TIENE UNA CLAVE LA FACTURA ELECTRONICA");
            return vr;
        }

        vr = this.billEmitter.getComprobante(clave, path);
        if (vr==null || vr.getResult()==null) {
            validada = -1;
        } else {
            validada = vr.getResult();
        }

        if (validada==-1) {
            vr.setResult(-1);
            vr.setResultStr("Mensaje Hacienda no obtenido, se esta procesando la solicitud");
            agregarError("OBTENIENDO_MENSAJE",vr.getResultStr(), facturaId);
        } else if (vr.getResult()==-99) {
            System.out.println(vr.getResultStr());
            logger.info(vr.getResultStr());
            if (vr.getResultStr()==null) {
                vr.setResultStr("No fue recibido el comprobante");
            }
            agregarError("VALIDA_MENSAJE", vr.getResultStr(), facturaId);

        }
        System.out.println(" RESPUESTA de get comprobante " + validada);
        return vr;
    }

    private Integer enviarCorreoProceso(FacturaElectronica e, Integer totalSent, Result r, BillSenderDetail pefd) {

        try {
            String message = generarMensajeProceso(e, totalSent, r) ;
            String xmlFile = null;
            String pdfFile = null;
            String respuestaFile = null;
            if (r.getEstado()!=null && r.getEstado().equals(BillHelper.RESPUESTA_ACEPTADA)) {
                xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(e.getClave(), pefd);
                pdfFile = this.billConfigService.getPdfFileAndFullPath(e.getClave(), pefd);
                respuestaFile = this.billConfigService.getRespuestaHaciendaPath(e.getClave(), pefd);
            }

            return this.emailSenderService.sendMessageProcessResultWithAttachments("ENVIO DE FACTURA ELECTRONICA A HACIENDA", message,
                this.billConfigService.getBillConfigData().getRecepcionCorreos(), xmlFile, pdfFile, respuestaFile);
        } catch (Exception e1) {
            String errMsg = "Error al enviar Correo al cliente";
            if (e1!=null && e1.getMessage()!=null) {
                errMsg = e1.getMessage();
            }
            Integer id =null;
            if (pefd!=null) {
                id = pefd.getBillId();
            }
            agregarError("ENVIO_CORREO", errMsg, id);
        }
            return 0;

    }

    public Integer enviarDevCorreo(String clave, String consecutive, String subject, String message, BillSenderDetail b) {
        //correo = "facturacion@ingpro-tec.com";

        String xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(clave, b);
        String pdfFile = this.billConfigService.getPdfFileAndFullPath(clave, b);
        String respuestaFile = this.billConfigService.getRespuestaHaciendaPath(clave, b);

        return this.emailSenderService.sendMessageWithAttachment("aagonzalezrojas@gmail.com",subject, message, null, null, null, consecutive, null, "");

    }

    private String generarMensajeProceso(FacturaElectronica e, Integer totalSent, Result mensaje1) {
        if (mensaje1.getEstado()==null || !mensaje1.getEstado().equals(BillHelper.RESPUESTA_ACEPTADA)) {
            return "Estimados, \n\n No fue posible completar el proceso de envio para la factura "+ e.getId() + " a hacienda el dia "  + dateFormat.format(new Date()) + ". " + mensaje1.getResultStr();
        }

        String facturas = "";

        String mensaje = " Estimados, \n\n Se  hizo un envio de la Facturas Electronica " + e.getId() + " a Hacienda, el dia " + dateFormat.format(new Date()) + ".\n\n " + ". " + mensaje1.getResultStr();
        mensaje = mensaje + facturas;
        mensaje = mensaje + "\n\nSaludos Cordiales";
        return mensaje;
    }

    private String getValidadaLabel(BillSenderDetail d) {
        return d.getValidada()!=null && d.getValidada()==1?" SI VALIDA | ": " NO VALIDADA | ";
    }

    private String getEnviadaClienteLabel(BillSenderDetail d) {
        return d.getEnviadaCliente()!=null && d.getEnviadaCliente()==1?" SI ENVIADA CLIENTE | ": " NO ENVIADA CLIENTE | ";
    }

    private String getEnviadaHaciendaLabel(BillSenderDetail d) {
        return d.getEnviadaHacienda()!=null && d.getEnviadaHacienda()==1?" SI ENVIADA HACIENDA | ": " NO ENVIADA HACIENDA | ";
    }

    private String generarMensajeProcesoNotaCredito(BillSenderDetail pef, FacturaElectronica fe) {
        String facturas = "";

        String mensaje = " Estimados, \n\n Se  hizo un envio de Nota de Credito, para el cliente " + fe.getReceptor().getNombre()  + " el dia " + dateFormat.format(pef.getDateSent()) + ".\n\n Adjunto encontrará el detalle de la nota de crédito\n";

        return mensaje;
    }

    private Integer enviarCorreo(String clave, String correo, String consecutive, String subject, String tipo,
                                 String receptorName, String emisorName, String message, String destinoEmpresa,
            BillSenderDetail b) {
        try {
            correo = getValidCorreoForClient(correo);
            String xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(clave, b);
            String pdfFile = this.billConfigService.getPdfFileAndFullPath(clave, b);
            String respuestaFile = this.billConfigService.getRespuestaHaciendaPath(clave, b);
            if (pdfFile!=null) {

                boolean existsFile = false;
                try {
                    existsFile = new File(pdfFile).isFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!existsFile)
                    pdfFile = null;
            }

            return this.emailSenderService.sendMessageWithAttachment("aagonzalezrojas@gmail.com", subject, message,
                    xmlFile, pdfFile,
                    respuestaFile, consecutive,tipo, destinoEmpresa);

            //return this.emailSenderService.sendMessageWithAttachment(correo,subject, message, xmlFile, pdfFile, respuestaFile, consecutive,tipo, destinoEmpresa);
        } catch (Exception e) {
            String errMsg = "Error al enviar Correo al cliente";
            if (e != null && e.getMessage() != null) {
                errMsg = e.getMessage();
            }
            agregarError("ENVIO_CORREO", errMsg, b.getBillId());
        }
            return 0;

    }


    private void enviarCorreoRechazadaToDEV(String clave, String consecutive, String tipoDocumento, String nombre, String numero, String emisor, BillSenderDetail pefd) {
        try {
            String correo = "aagonzalearojas@gmail.com";
            String respuestaFile = this.billConfigService.getRespuestaHaciendaPath(clave, pefd);
            String xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(clave, pefd);
            String message = " Hacienda no ha aceptado:\n\r tipo de documento: " + tipoDocumento + "\n\rclave: " + clave  + "\n\rconsecutivo: " + consecutive + "\n\rEmisor:" + emisor + "\n\rReceptor:"  + nombre + "cedula "  + numero;

            this.emailSenderService.sendMessageWithAttachment(correo,"DOCUMENTO ELECTRONICO RECHAZADO POR HACIENDA y ENVIADO POR " + emisor, message, xmlFile, null, respuestaFile,consecutive.toString(),tipoDocumento,null);
        } catch (Exception e) {
            String errMsg = "Error al enviar Correo al cliente";
            if (e!=null && e.getMessage()!=null) {
                errMsg = e.getMessage();
            }
            agregarError("ENVIO_CORREO", errMsg, pefd.getBillId());
        }

    }

//    private Integer enviarCorreoProveedor(String correoDestino, String clave,Integer consecutive, String tipo, String emisor, String receptor) {
//        String subject = getSubjectByTipoConfirmacionORechazo(tipo);
//        String xmlFile = this.billConfigService.getSignedXmlFileAndFullPath(clave);
//        //String pdfFile = this.billConfigService.getPdfFileAndFullPath(clave);
//        String message = String.format(simpleMailMessage.getText(), receptor,emisor);
//        return this.emailSenderService.sendMessageWithAttachment(correoDestino,subject, message, xmlFile, null, null,consecutive.toString(),tipo,null);
//        //return 0;
//    }

    private String getSubjectByTipoConfirmacionORechazo(String tipo) {
        if (tipo.equals(BillHelper.CONFIRMACION_ACEPTACION_COMPROBANTE_TIPO)) {
            return "Comprobante Confirmacion Documento Electrónico";
        } else if (tipo.equals(BillHelper.CONFIRMACION_RECHAZO_COMPROBANTE)) {
            return "Comprobante Rechazo Documento Electrónico";

        }
        return "";
    }

    private String getValidCorreoForClient(String correo) {
        //return "aagonzalezrojas@gmail.com";

        if (Strings.isNullOrEmpty(correo)) {
            return "aagonzalezrojas@gmail.com";
        }
        String[] correos = correo.split(";");
        String validCorreo = "";
        String correo1="";
        boolean isInitialized = false;
        boolean isValid = false;
        for (int i = 0;i < correos.length; i++) {
            isValid = false;
            if(Strings.isNullOrEmpty(correos[i]) || !isValidEmailAddress(correos[i])) {
                correo1 = "aagonzalezrojas@gmail.com";

            } else {
                correo1 = correos[i].trim().toLowerCase();
                isValid = true;

            }
            if (isInitialized && isValid) {
                validCorreo = validCorreo + "," +correo1;
            } else if (isValid) {

                validCorreo = correo1;
                isInitialized = true;
            }
        }

        return validCorreo;
    }

    public boolean isValidEmailAddress(String email) {
        if (Strings.isNullOrEmpty(email)) {
            return false;
        }
        email = email.trim().toLowerCase();
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public BillDataService getBillDataService() {
        return billDataService;
    }

    public void setBillDataService(BillDataService billDataService) {
        this.billDataService = billDataService;
    }

    public void generarClave(FacturaElectronica fe) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(fe.getNumeroConsecutivo(), fe.getTipoDocumento()));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);

    }

    public void generarClave(NotaCreditoElectronica fe) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(fe.getNumeroConsecutivo(), fe.getTipoDocumento()));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);

    }

    public void generarConsecutivo(MensajeReceptor fe, String tipoDocumento, Integer consecutivo) {
        //String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivoReceptor(generarConsecutivoFactura(consecutivo.toString(), tipoDocumento));
        //String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());
    }

    public void generarConsecutivo(NotaCreditoElectronica fe, String tipoDocumento, Integer consecutivo) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(consecutivo.toString(), tipoDocumento));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);
        //String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());
    }

    private String generarSeguridad() {
        Random rnd = new Random();
        Integer n = 10000000 + rnd.nextInt(90000000);
        return StringUtils.leftPad(n.toString(), 8, "0");
    }

    private String generarCedula(String cedula) {
        cedula = cedula.replaceAll("-","");

        StringBuilder stringBuilder = new StringBuilder(cedula);
        while (stringBuilder.length() < 12) {
            stringBuilder.insert(0, Integer.toString(0));
        }
        return stringBuilder.toString();
    }

    private String generarConsecutivoFactura(String consecutivoFactura, String tipoDocumento) {
        String consecutivoStr =  StringUtils.leftPad(consecutivoFactura, 10, "0");
        return  "001" + "00001" + tipoDocumento + consecutivoStr;
    }

    private String crearFechaClave() {
        Date fecha = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        Integer month = c.get(Calendar.MONTH) + 1;
        Integer ano = getAno();
        Integer day = c.get(Calendar.DAY_OF_MONTH);
        String fechaStr = (day < 10 ? "0" + day.toString(): day.toString()) + (month < 10 ? "0" + month.toString(): month.toString()) + ano.toString();
        return fechaStr;
    }

    private int getAno() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String formattedDate = df.format(Calendar.getInstance().getTime());
        return Integer.parseInt(formattedDate);
    }


    public BillSender createProcesoEmisionFactura(String documentType, String observaciones, Integer empresaId) {
        Date currentDate = new Date();
        BillSender pef = new BillSender();
        Integer usuarioId = this.billConfigService.getCurrentUser();
        pef.setDateSent(currentDate);
        pef.setFechaIngreso(currentDate);
        pef.setFechaUltimoCambio(currentDate);
        pef.setIngresadoPor(usuarioId);
        pef.setUltimoCambioId(usuarioId);
        pef.setStatus("INICIADO");
        pef.setTipoDocumento(documentType);
        pef.setEmpresaId(empresaId);
        pef.setObservaciones(observaciones + currentDate );
        pef = billSenderRepository.save(pef);

        logger.info("INICIANDO PROCESO " + currentDate);
        return pef;
    }

    public BillSenderDetail createProcesoEmisionFacturaDetalle(Integer parent, Integer facturaId, String tipo, Integer empresaId) {
        Date currentDate = new Date();
        BillSenderDetail pef = new BillSenderDetail();
        Integer usuarioId = this.billConfigService.getCurrentUser();
        pef.setDateSent(currentDate);
        pef.setFechaIngreso(currentDate);
        pef.setIngresadoPor(usuarioId);
        pef.setIdSend(parent);

        pef = createProcesoEmisionDetalle(facturaId, currentDate, pef, usuarioId,0,0,0,"","", BillHelper.TIPO_FACTURA_FE, empresaId);
        return pef;
    }

    public BillSenderDetail createProcesoEmisionDetalle(Integer facturaId, Date currentDate, BillSenderDetail pef, Integer usuarioId, Integer enviado, Integer enviadoCliente, Integer validado, String o, String status, String tipo, Integer empresaId) {
        pef.setFechaUltimoCambio(currentDate);
        pef.setUltimoCambioId(usuarioId);
        pef.setBillId(facturaId);
        pef.setEnviadaHacienda(enviado);
        pef.setEnviadaCliente(enviadoCliente);
        pef.setValidada(validado);
        if (o!=null && o.length()>256) {
            o =  o.substring(0,255);
        }
        pef.setObservaciones(o);
        pef.setStatus(status);
        pef.setTipo(tipo);
        pef.setEmpresaId(empresaId);
        pef = billSenderDetailRepository.save(pef);
        logger.info("Factura enviada " + facturaId);
        return pef;
    }

    public void updateFactura(Integer id, Integer empresaId,Integer enviadaHaciendaStatus, String estado, String estadoHacienda, String clave, String consecutivo) {
        try {
            Factura factura = this.facturaRepository.findByFacturaIdentity(new FacturaIdentity(id,empresaId));
            if (factura==null) {
                return;
            }
            if (estado!=null && !estado.equals("")) {
                factura.setEstado(estado);
            }
            if (enviadaHaciendaStatus!=null ) {
                factura.setEnviadaHacienda(enviadaHaciendaStatus);
            }

            if (estadoHacienda!=null && !estadoHacienda.equals("")) {
                factura.setEstadoHacienda(estadoHacienda);
            }

            if (clave!=null && !clave.equals("")) {
                factura.setClave(clave);
            }

            if (consecutivo!=null && !consecutivo.equals("")) {
                factura.setConsecutivo(consecutivo);

            }

            this.facturaRepository.save(factura);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void updateConfirmacionRechazo(Integer id, String estado, String estadoHacienda, Integer billSenderId, String consecutivoConfirma) {
        try {
            if (id==null) {
                return;
            }
            ConfirmaRechazaDocumento factura = this.confirmaRechazoDocumentoRepository.findById(id);
            if (estadoHacienda!=null && !estadoHacienda.equals("")) {

                factura.setEstadoEnviadoHacienda(estadoHacienda);
            }

            if (estado!=null && !estado.equals("")) {

                factura.setEstado(estado);
            }


            if (billSenderId!=null) {

                factura.setBillSenderId(billSenderId);
            }

            if (consecutivoConfirma!=null && !consecutivoConfirma.equals("")) {
                factura.setNumeroConsecutivoReceptor(consecutivoConfirma);
            }


            this.confirmaRechazoDocumentoRepository.save(factura);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void updateNotaCredito(Integer id, Integer facturaId, Integer empresaId, Integer enviadaHaciendaStatus, String estadoHacienda, String clave, String consecutivo, NotaCredito nc) {
        try {
            if (nc==null) {
                nc = this.notaCreditoRepository.findByIdAndEmpresaId(id, empresaId);
                if (nc == null) {
                    updateFactura(facturaId, empresaId, 3, BillHelper.ANULADA, estadoHacienda, clave, consecutivo);

                }
            }
            nc.setEnviadaHacienda(enviadaHaciendaStatus);
            nc.setEstado("ENVIADA");
            this.notaCreditoRepository.save(nc);

            updateFactura(nc.getFacturaId(), empresaId, 2,BillHelper.ANULADA, estadoHacienda, clave, consecutivo);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    public String getXmlFileName(String clave, BillSenderDetail b) {
        String fileName = null;
        fileName = clave + ".xml";
        String fullFileName = b.getPath() + fileName;
        return fileName;
    }

    public void setBillConfigData(BillConfigData bcd) {
        billConfigService.setBillConfigData(bcd);
    }


    public void agregarError(String proceso, String mensaje, Integer facturaId) {
        try {
            if (mensaje!=null && mensaje.length()>512) {
                mensaje = mensaje.substring(0,511);
            }
            if (proceso!=null && proceso.length()>32) {
                proceso = proceso.substring(0,31);
            }
            ErrorEnvio ev = new ErrorEnvio();
            ev.setFechaIngreso(new Date());
            ev.setMensaje(mensaje);
            ev.setFacturaId(facturaId);
            ev.setProceso(proceso);
            ev.setEmpresaId(this.billConfigService.getBillConfigData().getEmpresaId());
            errorEnvioRepository.save(ev);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.ALL, "ERROR persistiendo el error " + getExceptionMessage(e));
        }


    }

    private String getConfirmacionRechazoCode(String tipo) {
        if (tipo!=null && tipo.equals("05")) {
            return BillHelper.TIPO_CONFIRMACION_FE;
        } else {
            return BillHelper.TIPO_RECHAZO_FE;
        }
    }

    public Result getComprobanteResult(String clave, Integer id, String path) {
        try {

            billEmitter.authenticate();
            Result r = obtenerEstadoComprobante(clave,-1, id, path);
            if (r!=null) {

                return r;


            } else {
                return null;
            }
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException(getExceptionMessage(e)!=null?getExceptionMessage(e):"");
        } finally {

            billEmitter.desconectar();
        }
    }

    public void notificarClienteConFactura(FacturaElectronica fe, BillSenderDetail pefd, String status, String obs, Integer validada, Integer resultado) {

        Integer enviadaCliente = 0;
        if (resultado == 1) {
            boolean existsFile = false;
            try {
                String pdfFile = this.billConfigService.getPdfFileAndFullPath(pefd.getClave(), pefd);
                existsFile = new File(pdfFile).isFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!existsFile)
                billPdfGenerator.generatePdf(fe, pefd);
            String message = String.format(simpleMailMessage.getText(), fe.getReceptor().getNombre(),
                    "FACTURA ELECTRONICA", fe.getNumeroConsecutivo(), fe.getEmisor().getNombre());
            enviadaCliente = enviarCorreo(fe.getClave(), fe.getCorreo(), pefd.getConsecutivo(),
                    "Comprobante Factura Electronica "  + pefd.getConsecutivo(), fe.getTipoDocumento(), fe.getReceptor().getNombre(),
                    fe.getEmisor().getNombre(), message, this.billConfigService.getBillConfigData().getCorreo(), pefd);
            logger.info("Factura enviada " + fe.getId() + " enviada cliente ="+ enviadaCliente);
            System.out.println("Factura enviada " + " enviada cliente ="+ enviadaCliente);
        } else if (resultado == 3 && status!=null && status.equals(BillHelper.RESPUESTA_RECHAZADO)) {
            this.enviarCorreoRechazadaToDEV(fe.getClave(), fe.getNumeroConsecutivo(), fe.getTipoDocumento(), fe.getReceptor().getNombre(), fe.getNumeroConsecutivo(), fe.getEmisor().getNombre(), pefd);

        }

        createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(),
                pefd.getEnviadaHacienda(), enviadaCliente, pefd.getValidada(), obs, status,
                BillHelper.TIPO_FACTURA_FE,fe.getEmpresaId());
        if (validada==1 && status!=null && status.equals(BillHelper.RESPUESTA_ACEPTADA)) {
            updateFactura(fe.getId(), fe.getEmpresaId(), 1, BillHelper.FINALIZADA, status, fe.getClave(), fe.getNumeroConsecutivo());

        } else if (status!=null && status.equals(BillHelper.RESPUESTA_RECHAZADO)) {
            updateFactura(fe.getId(), fe.getEmpresaId(), 0, BillHelper.INGRESADA, status, fe.getClave(), fe.getNumeroConsecutivo());


        } else {
            updateFactura(fe.getId(), fe.getEmpresaId(), 1, BillHelper.INGRESADA, status, fe.getClave(), fe.getNumeroConsecutivo());

        }

    }
    public void notificarClienteConNotaCredito(NotaCreditoElectronica fe, BillSenderDetail pefd, String status, String obs, Integer validada, Integer resultado, Empresa e, NotaCredito nc) throws Exception {

        Integer enviadaCliente = 0;
        if (resultado == 1) {
            boolean existsFile = false;
            try {
                String pdfFile = this.billConfigService.getPdfFileAndFullPath(pefd.getClave(), pefd);
                existsFile = new File(pdfFile).isFile();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if (!existsFile) {
                PdfNotaCreditoView pdfNotaCreditoView = new PdfNotaCreditoView();
                pdfNotaCreditoView.generatePdfFile(fe,this.billConfigService.getPdfFileAndFullPath(fe.getClave(), pefd),fe.getInformacionReferencia().get(0).getNumero(), this.billDataService,fe.getInformacionReferencia().get(0).getNumero(),e);


            }
            String message = String.format(simpleMailMessage.getText(), fe.getReceptor().getNombre(), "NOTA CREDITO ELECTRONICA", fe.getNumeroConsecutivo(), fe.getEmisor().getNombre());
            enviadaCliente = enviarCorreo(fe.getClave(), fe.getCorreo(), pefd.getConsecutivo(), "Comprobante Nota Credito Electronica " + pefd.getConsecutivo(), fe.getTipoDocumento(), fe.getReceptor().getNombre(), fe.getEmisor().getNombre(), message, this.billConfigService.getBillConfigData().getCorreo(), pefd);
            logger.info("Nota Credito enviada " + fe.getId() + " enviada cliente ="+ enviadaCliente);
            System.out.println("Nota Credito enviada " + " enviada cliente ="+ enviadaCliente);
        } else if (resultado == 3 && status!=null && status.equals(BillHelper.RESPUESTA_RECHAZADO)) {
            this.enviarCorreoRechazadaToDEV(fe.getClave(), fe.getNumeroConsecutivo(), fe.getTipoDocumento(), fe.getReceptor().getNombre(), fe.getNumeroConsecutivo(), fe.getEmisor().getNombre(), pefd);

        }

        createProcesoEmisionDetalle(fe.getId(), new Date(), pefd, this.billConfigService.getCurrentUser(),
                pefd.getEnviadaHacienda(), enviadaCliente, pefd.getValidada(), obs, status,
                BillHelper.TIPO_FACTURA_FE,fe.getEmpresaId());
//        if (validada==1) {
//            updateNotaCredito(fe.getId(), fe.getFacturaId(), e.getId(), 1, status, fe.getClave(), fe.getNumeroConsecutivo(), nc);
//
//
//        } else {
//            updateNotaCredito(fe.getId(), fe.getFacturaId(), e.getId(), 1, status, fe.getClave(), fe.getNumeroConsecutivo(), nc);
//
//
//        }

        if (validada==1 && status!=null && status.equals(BillHelper.RESPUESTA_ACEPTADA)) {
            updateNotaCredito(fe.getId(), fe.getFacturaId(), e.getId(), 1, status, fe.getClave(), fe.getNumeroConsecutivo(), nc);

            //updateFactura(fe.getId(), fe.getEmpresaId(), 1, BillHelper.FINALIZADA, status, fe.getClave(), fe.getNumeroConsecutivo());

        } else if (status!=null && status.equals(BillHelper.RESPUESTA_RECHAZADO)) {
            updateNotaCredito(fe.getId(), fe.getFacturaId(), e.getId(), 0, status, fe.getClave(), fe.getNumeroConsecutivo(), nc);

            //updateFactura(fe.getId(), fe.getEmpresaId(), 0, BillHelper.INGRESADA, status, fe.getClave(), fe.getNumeroConsecutivo());


        } else {
            //updateFactura(fe.getId(), fe.getEmpresaId(), 1, BillHelper.INGRESADA, status, fe.getClave(), fe.getNumeroConsecutivo());
            updateNotaCredito(fe.getId(), fe.getFacturaId(), e.getId(), 1, status, fe.getClave(), fe.getNumeroConsecutivo(), nc);

        }

    }
    
    public String getExceptionMessage(Exception e) {
        String message = "";
        if (e!=null && e.getMessage()!=null) {
            return e.getMessage();
        }
        return message;
    }

    private BillSenderDetail obtenerBillSender(Integer parent, Integer id, String tipo, String clave, Integer empresaId) {
        List<BillSenderDetail> l = this.billSenderDetailRepository.findByClave(clave);
        if (l!=null && l.size()>0) {
            return l.get(0);
        }
        return createProcesoEmisionFacturaDetalle(parent, id, BillHelper.TIPO_FACTURA_FE, empresaId);
    }


}
