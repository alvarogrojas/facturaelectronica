package com.rfs.service.factura.billapp;

import com.rfs.domain.*;
import com.rfs.domain.factura.BillSenderDetail;
import com.rfs.domain.factura.Emisor;
import com.rfs.domain.factura.OptionSet;
import com.rfs.domain.factura.Receptor;
import com.rfs.dtos.NotaCreditoDataDTO;
import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.fe.v43.mr.MensajeReceptor;
import com.rfs.fe.v43.nc.NotaCreditoElectronica;
import com.rfs.repository.*;
import com.rfs.repository.factura.BillSenderDetailRepository;
import com.rfs.service.*;
import com.rfs.service.factura.billapp.impl.BillDataServiceImpl;
import com.rfs.service.factura.billapp.impl.EmisorServiceImpl;
import com.rfs.service.factura.billapp.impl.FacturaElectronicaV43Mapper;
import com.rfs.view.PdfNotaCreditoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//import org.slf4j.Logger;

@Service
public class BillManagerService {

    private Logger logger = Logger.getLogger("BillManagerService");

    private static final String PAIS_CODE = "506";
    private static final String SITUATION_FE = "1";

    @Autowired
    private BillEmitter billEmitter;

    @Autowired
    private FacturaElectronicaMarshaller fem;

    @Autowired
    private BillPdfGenerator billPdfGenerator;


    @Autowired
    private BillDataService billDataService;

    @Autowired
    private BillConfigService billConfigService;

    @Autowired
    private BillDataServiceImpl billDataServiceImpl;

    @Autowired
    private BillTaskService billTaskService;

    @Autowired
    public BillConfigData billConfigData;

    @Autowired
    public UsuarioService usuarioService;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private GlobalDataManager globalDataManager;

    @Autowired
    private BillSenderDetailRepository billSenderDetailRepository;

    @Autowired
    private  NotaCreditoService notaCreditoService;

    @Autowired
    private ConfirmacionRechazoRepository confirmacionRechazoRepository;

    @Autowired
    private FacturaElectronicaUnmarshaller feu;

    @Autowired
    private NotaCreditoRepository notaCreditoRepository;

    @Autowired
    private TipoActividadEconomicaRepository tipoActividadEconomicaRepository;

    @Transactional
    public synchronized Result sendBill(Integer facturaId, Boolean esRenvio) throws JAXBException {
        OptionSet options;
        Result result = new Result();
        Usuario u = usuarioService.getCurrentLoggedUser();
        EmisorService emisorService = new EmisorServiceImpl(this.usuarioService.getCurrentLoggedUser().getEmpresa());
        FacturaElectronica fe = this.billDataService.getFacturaElectronicaById(facturaId, emisorService);

        if (fe == null) {
            logger.info("FACTURA NO ENCONTRADA ");
            result = new Result();
            result.setResultStr("FACTURA NO ENCONTRADA ");
            return result;
        }
        BillSenderDetail pefd = this.billTaskService.createProcesoEmisionFacturaDetalle(null, facturaId, BillHelper.TIPO_FACTURA_FE, u.getEmpresa().getId());

        this.initializeBillDataWithCurrentTenant();

        Factura f = this.facturaRepository.findByFacturaIdentity(new FacturaIdentity(facturaId,u.getEmpresa().getId()));
        String claveActual = f.getClave();


        fe.setNumeroConsecutivoFe(globalDataManager.getFacturaElectronicaNext(u.getEmpresa().getId()));
        fe.setNumeroConsecutivo(fe.getNumeroConsecutivoFe().toString());

        BillHelper.generarClave(fe);
        f.setClave(fe.getClave());
        f.setConsecutivo(fe.getNumeroConsecutivo());
        f.setEstadoHacienda(BillHelper.ENVIANDO);

        this.facturaRepository.save(f);

        billTaskService.setBillConfigData(this.billConfigData);
        pefd.setPath(billConfigService.getInitialPath(fe.getNumeroConsecutivo(),fe.getEmisor().getIdentificacion().getNumero(),fe.getTipoDocumento()));

        initReenvioInfo(esRenvio, fe, facturaId, claveActual);


        String fileName = getXmlFileName(fe.getClave(), pefd);
        this.fem.writeXMLFile(fe,pefd.getPath() + fileName);
        options = billConfigService.initOptionsValues(pefd, fileName);
        pefd.setClave(fe.getClave());
        pefd.setStatus(BillHelper.NO_ENVIADA);
        pefd.setConsecutivo(fe.getNumeroConsecutivo());

        if (!firmarFactura(options, BillHelper.FACTURA_NAMESPACE_V43, fe.getId(), BillHelper.TIPO_FACTURA_FE, pefd, u.getEmpresa().getId())) {
            result.setResultStr("Hubo un error al firmar la factura");
            result.setResult(-1);
            return result;
        }

        billPdfGenerator.generatePdf(fe, pefd);

        result = billTaskService.emitFactura(fe);
        result = new Result();
        result.setResult(-1);
        result.setResultStr("Factura Electronica se esta enviando. El proceso de obtener respuesta se hará automaticamente.");
        result.setEstado(BillHelper.ENVIANDO);

        return result;
    }

    public Result sendNotaCreditoHacienda(NotaCreditoDataDTO dto) throws Exception {
        OptionSet options;
        Result result = new Result();

        Empresa e = this.usuarioService.getCurrentLoggedUser().getEmpresa();
        EmisorService emisorService = new EmisorServiceImpl(e);
        logger.info(" ENVIANDO NOTA DE CREDITO");

        NotaCredito f = this.notaCreditoRepository.findByIdAndEmpresaId(dto.getNotaCreditoId(), dto.getEmpresaId());

        NotaCreditoElectronica fe = billDataService.getNotaCreditoElectronica(dto,emisorService);
        if (fe == null) {
            logger.info("NOTA CREDITO NO ENCONTRADA ");

            return new Result();
        }
        fe.setNumeroConsecutivoFe(globalDataManager.getFacturaElectronicaNext(e.getId()));
        fe.setNumeroConsecutivo(fe.getNumeroConsecutivoFe().toString());
        BillHelper.generarClave(fe);

        f.setClave(fe.getClave());
        f.setConsecutivo(fe.getNumeroConsecutivo());
        f.setEstadoHacienda(BillHelper.ENVIANDO);

        BillSenderDetail bsd = this.getBillSenderDetail(dto.getFacturaId());
        if (bsd==null) {
            throw new RuntimeException("NO FUE ENCONTRADO EL REGISTRO ANTERIOR DEL ENVIO DE FACTURA CUANDO SE IBA A ENVIAR LA NOTA DE CREDITO");
        }
        fe.getInformacionReferencia().get(0).setNumero(bsd.getConsecutivo());


        BillSenderDetail pefd = this.billTaskService.createProcesoEmisionFacturaDetalle(null, dto.getNotaCreditoId(), BillHelper.TIPO_NOTA_CREDITO_FE, e.getId());
        pefd.setPath(billConfigService.getInitialPath(fe.getNumeroConsecutivo(),fe.getEmisor().getIdentificacion().getNumero(),fe.getTipoDocumento()));

        pefd.setConsecutivo(fe.getNumeroConsecutivo());
        pefd.setClave(fe.getClave());

        this.notaCreditoRepository.save(f);

        Factura factura = this.facturaRepository.findByFacturaIdentity(new FacturaIdentity(dto.getFacturaId(), e.getId()));
        factura.setEstado("Anulada");
        factura.setEnviadaHacienda(-5);
        this.facturaRepository.save(factura);

        this.initializeBillDataWithCurrentTenant();
        billTaskService.setBillConfigData(this.billConfigData);
        pefd.setPath(billConfigService.getInitialPath(fe.getNumeroConsecutivo(),fe.getEmisor().getIdentificacion().getNumero(),fe.getTipoDocumento()));


        String fileName = getXmlFileName(fe.getClave(), pefd);
        this.fem.writeXMLFile(fe, pefd.getPath() + fileName);
        options = billConfigService.initOptionsValues(pefd, fileName);
        pefd.setClave(fe.getClave());
        pefd.setStatus(BillHelper.NO_ENVIADA);
        pefd.setConsecutivo(fe.getNumeroConsecutivo());

        this.billSenderDetailRepository.save(pefd);
        if (!firmarFactura(options, BillHelper.NOTA_CREDITO_NAMESPACE_V43, dto.getNotaCreditoId(), BillHelper.TIPO_NOTA_CREDITO_FE, pefd, e.getId())) {
            result.setResultStr("Hubo un error al firmar la factura");
            result.setResult(-1);
            return result;
        }
        PdfNotaCreditoView pdfNotaCreditoView = new PdfNotaCreditoView();
        pdfNotaCreditoView.generatePdfFile(fe,this.billConfigService.getPdfFileAndFullPath(fe.getClave(), pefd),fe.getInformacionReferencia().get(0).getNumero(), this.billDataService,fe.getInformacionReferencia().get(0).getNumero(),e);



        billTaskService.setBillConfigData(this.billConfigData);
//        billTaskService.emitNotaCreditoElectronica(nc, nc.getId(),nc.getEmisor().getCorreoElectronico(),nc.getEmisor().getNombre(), nc.getReceptor().getNombre(), e);
        billTaskService.emitNotaCreditoElectronica(fe, fe.getId(), fe.getCorreo(), fe.getEmisor().getNombre(), fe.getReceptor().getNombre(), e);
        Result r = new Result();
        r.setResult(1);
        r.setResultStr("INICIO proceso de envio de NOTA DE CREDITO");
        return r;
    }


    public synchronized String sendNotaCredito(Integer facturaId) throws Exception {
        Usuario u = usuarioService.getCurrentLoggedUser();
        Factura e = this.facturaRepository.findByFacturaIdentity(new FacturaIdentity(facturaId,u.getEmpresa().getId()));

        if (e.getTipoActividadEconomica()==null) {
            List<TipoActividadEconomica> lta = this.tipoActividadEconomicaRepository.findByEmpresaId(u.getEmpresa().getId());
            if (lta==null || lta.size()==0) {
                throw new  Exception("NO HAY TIPOS DE ACTIVIDAD. DEBE AGREGAR TIPOS DE ACTIVIDADES PARA PODER ANULAR LA FACTURA");
            }
            e.setTipoActividadEconomica(lta.get(0));
        }


        if (e.getEstado().equals(BillHelper.INGRESADA)) {
            updateFacturaToAnulada(u, e);
            logger.info(" SOLO SE ACTUALIZA LA FACTURA");
            return "Factura anulada localmente";
        } else if (e.getEstado().equals(BillHelper.ANULADA)){
            logger.info(" LA FACTURA YA HABIA SIDO ANULADA");
            return "Ya anulada previamente";
        }
        NotaCreditoDataDTO dto = this.notaCreditoService.getNotaCreditoData(null,facturaId);
        dto.setEstado(BillHelper.ACTIVA);

        dto.setCodigo("01");
        dto.setRazon("ANULANDO UNA FACTURA QUE NO SE DEBIO CREAR");
        Integer id  = this.notaCreditoService.save(dto);
        dto.setNotaCreditoId(id);
        //Result r = this.notaCreditoService.enviarNCAHacienda(dto);
        Result r = sendNotaCreditoHacienda(dto);
        return r.getResultStr();
    }

    private void updateFacturaToAnulada(Usuario u, Factura e) {
        e.setEstado(BillHelper.ANULADA);
        e.setUltimoCambioPor(u.getId());
        e.setFechaUltimoCambio(new Date());
        //e.setEstado(BillHelper.ANULADA);
        this.facturaRepository.save(e);
    }

    public BillDataService getBillDataService() {
        return billDataService;
    }

    public void setBillDataService(BillDataService billDataService) {
        this.billDataService = billDataService;
    }

    public Result sendConfirmDE(FacturaElectronica fe,
                                String mensaje, Integer id) throws DatatypeConfigurationException {
        MensajeReceptor mensajeReceptor = new MensajeReceptor();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        mensajeReceptor.setFechaEmisionDoc(date2);
        mensajeReceptor.setClave(fe.getClave());
        if (fe.getReceptor()!=null && fe.getReceptor().getIdentificacion()!=null) {
            mensajeReceptor.setNumeroCedulaReceptor(fe.getReceptor().getIdentificacion().getNumero());
        } else {
            mensajeReceptor.setNumeroCedulaReceptor("");
        }
        if (fe.getEmisor()!=null && fe.getEmisor().getIdentificacion()!=null) {
            mensajeReceptor.setNumeroCedulaEmisor(fe.getEmisor().getIdentificacion().getNumero());
        } else {
            mensajeReceptor.setNumeroCedulaEmisor("");

        }
        mensajeReceptor.setMensaje(BigInteger.valueOf(BillHelper.CONFIRMACION_ACEPTADO));
        mensajeReceptor.setDetalleMensaje(mensaje);
        mensajeReceptor.setMontoTotalImpuesto(fe.getResumenFactura().getTotalImpuesto());
        mensajeReceptor.setTotalFactura(fe.getResumenFactura().getTotalVentaNeta());
        mensajeReceptor.setEmpresaId(this.usuarioService.getCurrentLoggedUser().getEmpresa().getId());
        mensajeReceptor.setNumeroConsecutivoFe(globalDataManager.getConfirmacionNext(this.usuarioService.getCurrentLoggedUser().getEmpresa().getId()));
        mensajeReceptor.setEmisor(new Emisor(fe.getEmisor()));
        mensajeReceptor.setReceptor(new Receptor(fe.getReceptor()));
        mensajeReceptor.setNumeroConsecutivoReceptor(fe.getNumeroConsecutivo());
        mensajeReceptor.setNumeroConsecutivoComprobante(fe.getNumeroConsecutivo());
        mensajeReceptor.setId(id);
        this.initializeBillDataWithCurrentTenant();
        billTaskService.setBillConfigData(this.billConfigData);
        BillHelper.generarConsecutivo(mensajeReceptor,BillHelper.CONFIRMACION_ACEPTACION_COMPROBANTE_TIPO,mensajeReceptor.getNumeroConsecutivoFe());
        return billTaskService.emitConfirmation(mensajeReceptor,mensajeReceptor.getNumeroConsecutivoFe(),fe.getEmisor().getCorreoElectronico(),fe.getEmisor().getNombre(), fe.getReceptor().getNombre(), BillHelper.CONFIRMACION_ACEPTACION_COMPROBANTE_TIPO);
    }

    public Result sendRejectDE(FacturaElectronica fe, String mensaje, Integer id) throws DatatypeConfigurationException {
        MensajeReceptor mensajeReceptor = new MensajeReceptor();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        mensajeReceptor.setFechaEmisionDoc(date2);
        mensajeReceptor.setClave(fe.getClave());
        mensajeReceptor.setId(id);
        if (fe.getReceptor()!=null && fe.getReceptor().getIdentificacion()!=null) {
            mensajeReceptor.setNumeroCedulaReceptor(fe.getReceptor().getIdentificacion().getNumero());
        } else {
            mensajeReceptor.setNumeroCedulaReceptor("");
        }
        if (fe.getEmisor()!=null && fe.getEmisor().getIdentificacion()!=null) {
            mensajeReceptor.setNumeroCedulaEmisor(fe.getEmisor().getIdentificacion().getNumero());
        } else {
            mensajeReceptor.setNumeroCedulaEmisor("");

        }
        mensajeReceptor.setMensaje(BigInteger.valueOf(BillHelper.CONFIRMACION_RECHAZADO));
        mensajeReceptor.setDetalleMensaje(mensaje);
        mensajeReceptor.setMontoTotalImpuesto(fe.getResumenFactura().getTotalImpuesto());
        mensajeReceptor.setTotalFactura(fe.getResumenFactura().getTotalVentaNeta());
        mensajeReceptor.setEmpresaId(this.usuarioService.getCurrentLoggedUser().getEmpresa().getId());
        mensajeReceptor.setNumeroConsecutivoFe(globalDataManager.getConfirmacionRechazoNext(this.usuarioService.getCurrentLoggedUser().getEmpresa().getId()));
        mensajeReceptor.setEmisor(new Emisor(fe.getEmisor()));
        mensajeReceptor.setReceptor(new Receptor(fe.getReceptor()));
        mensajeReceptor.setNumeroConsecutivoReceptor(fe.getNumeroConsecutivo());
        mensajeReceptor.setNumeroConsecutivoComprobante(fe.getNumeroConsecutivo());
        mensajeReceptor.setId(id);
        this.initializeBillDataWithCurrentTenant();
        billTaskService.setBillConfigData(this.billConfigData);
        BillHelper.generarConsecutivo(mensajeReceptor,BillHelper.CONFIRMACION_RECHAZO_COMPROBANTE,mensajeReceptor.getNumeroConsecutivoFe());
        return billTaskService.emitConfirmation(mensajeReceptor,mensajeReceptor.getNumeroConsecutivoFe(),fe.getEmisor().getCorreoElectronico(),fe.getEmisor().getNombre(), fe.getReceptor().getNombre(), BillHelper.CONFIRMACION_RECHAZO_COMPROBANTE);
    }

    private void initializeBillDataWithCurrentTenant() {
        Empresa empresa = this.usuarioService.getCurrentLoggedUser().getEmpresa();
        //this.billConfigData.setApiUri();
        this.billConfigData.setUsuario(empresa.getUsuario());
        this.billConfigData.setClave(empresa.getClave());
        this.billConfigData.setCedula(empresa.getCedula());
        this.billConfigData.setRecepcionCorreos(empresa.getRecepcionCorreos());

        this.billConfigData.setIdpUri(empresa.getIdpUri());
        this.billConfigData.setApiUri(empresa.getApiUri());
        this.billConfigData.setIdpClientId(empresa.getIdpClientId());

        this.billConfigData.setPin(empresa.getPin());
        this.billConfigData.setFileNamePath(empresa.getKeyPath());
        this.billConfigData.setCertificateFileName(empresa.getKeyPath());
        this.billConfigData.setEmpresaId(empresa.getId());

    }

    public BillSenderDetail getBillSenderDetail(Integer facturaId) {
        Usuario u = usuarioService.getCurrentLoggedUser();
        BillSenderDetail result = null;
        try {
            List<BillSenderDetail> resultList = billSenderDetailRepository.findByBillIdAndEmpresaIdAndTipoOrderByFechaIngresoDesc(facturaId, u.getEmpresa().getId(),"FE");
            if (resultList!=null) {
                for (BillSenderDetail result1: resultList) {
                    if(result1.getStatus()!=null && result1.getStatus().equals("aceptado") || result1.getStatus().equals("OK")) {
                        result = result1;
                        break;
                    }
                }
            }
//            result = billSenderDetailRepository.findByBillIdAndEmpresaIdAndTipo(facturaId, u.getEmpresa().getId(),"FE");
        } catch (Exception e) {
            System.out.println(e.getMessage());


        }
        if (result==null) {
            try {
                List<BillSenderDetail> resultList = billSenderDetailRepository.findByBillIdAndPathLikeCedula(facturaId, u.getEmpresa().getCedula(),  BillHelper.transformCedula(u.getEmpresa().getCedula(),u.getEmpresa().getTipo()));
                if (resultList!=null && resultList.size()>0) {
                    result = resultList.get(0);
                }
            } catch (Exception e) {

                System.out.println(e.getMessage());


            }
        }

        return result;
    }

    private void saveConfirmacionRechazo(MensajeReceptor mensajeReceptor,String status, String filename) {
        ConfirmacionRechazo confirmacionRechazo = new ConfirmacionRechazo();
        confirmacionRechazo.setClave(mensajeReceptor.getClave());
        confirmacionRechazo.setEstado(status);
        confirmacionRechazo.setFechaConfirmacionRechazo(new Date());
        confirmacionRechazo.setFileName(filename);
        confirmacionRechazo.setIdentificadorEmisor(mensajeReceptor.getNumeroCedulaEmisor());
        confirmacionRechazo.setFechaEmision(mensajeReceptor.getFechaEmisionDoc().toGregorianCalendar().getGregorianChange());
        confirmacionRechazo.setMontoTotal(mensajeReceptor.getTotalFactura().doubleValue());
        confirmacionRechazoRepository.save(confirmacionRechazo);

    }

    public Result obtenerMensajeHaciendaNC(Integer id) {
        this.initializeBillDataWithCurrentTenant();

        BillSenderDetail bsd  = null;
        Usuario u = usuarioService.getCurrentLoggedUser();
        NotaCredito f = null;
        List<NotaCredito> fl = this.notaCreditoRepository.findByFacturaIdAndEmpresaIdOrderByFechaNotaCreditocionDesc(id,u.getEmpresa().getId());
        if (fl==null || fl.size()<=0) {
            Result r = new Result();
            r.setResult(-2);
            r.setEstado(BillHelper.RESPUESTA_NO_ACEPTADO_NO_RECHAZADO);
            r.setMensaje("No se encontro la nota de credito es necesario volverla a enviar");
            return r;
        } else {
            f = fl.get(0);
        }
        NotaCreditoDataDTO dto = this.notaCreditoRepository.findByIdToNotaCreditoDTO(f.getId());
        String correos = createCorreoTo(dto);
        List<BillSenderDetail> bsdl = billSenderDetailRepository.findByClave(f.getClave());

        if (bsdl==null || bsdl.size()==0) {
                throw new RuntimeException("La NOTA DE CREDITO enviada previamente no tiene un entrada registrada en el log sea enviada a hacienda");
            }

        EmisorService emisorService = new EmisorServiceImpl(this.usuarioService.getCurrentLoggedUser().getEmpresa());
        BillSenderDetail bsd1 = bsdl.get(0);
        String basePath = bsd1.getPath().substring(billConfigService.getDirSystemPath().length());
//        billConfigService.setBasePath(basePath);
        Result result = this.billTaskService.getComprobanteResult(bsd1.getClave(),bsd1.getBillId(), bsd1.getPath());
        String statusActual = bsd1.getStatus();
        Integer validada = bsd1.getValidada();
        if (result!=null && result.getEstado()!=null && !result.getEstado().equals("")) {
            statusActual = result.getEstado();
        }
        if (result.getResult()==1 || result.getResult()==3) {
            validada = 1;
        }
        if (result.getResult()==1 || result.getResult()==3) {
            String xmlFileName = bsd1.getPath()  +  bsd1.getClave() + ".xml";
            try {

                NotaCreditoElectronica fe = this.feu.readNCXMLFile(xmlFileName);
                fe.setId(bsd1.getBillId());
                fe.setTipoDocumento(BillHelper.NOTA_CREDITO_TIPO);
                fe.setEmpresaId(u.getEmpresa().getId());

                fe.setCorreo(correos);
                fe.setId(id);
                this.billTaskService.notificarClienteConNotaCredito(fe, bsd1, statusActual, result.getResultStr(), validada, result.getResult(), u.getEmpresa(), f);
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else if (result.getResult()==-99){
            this.billTaskService.updateNotaCredito(bsd1.getBillId(), f.getFacturaId(), u.getEmpresa().getId(), 0, "no_enviada", bsd1.getClave(), bsd1.getConsecutivo(), f);
            this.billTaskService.enviarDevCorreo(bsd1.getClave(), bsd1.getConsecutivo(), "Comprobante no recibido por hacienda", result.getResultStr(), bsd);

        } else {
            this.billTaskService.enviarDevCorreo(bsd1.getClave(), bsd1.getConsecutivo(), "No se obtuvo respuesta", result.getResultStr(), bsd);
        }

//        }

        return result;

    }

    public Result obtenerMensajeHacienda(Integer id) {

        this.initializeBillDataWithCurrentTenant();

        BillSenderDetail bsd  = null;
        Usuario u = usuarioService.getCurrentLoggedUser();
        Factura f =this.facturaRepository.findByFacturaIdentity(new FacturaIdentity(id,u.getEmpresa().getId()));
        String correos = createCorreoTo(f.getCliente());

        List<BillSenderDetail> bsdl = billSenderDetailRepository.findByBillIdAndEmpresaIdAndTipoAndStatusOrderByDateSentDesc(id,u.getEmpresa().getId(),"FE","OK");

        if (bsdl==null || bsdl.size()==0) {
            bsdl = billSenderDetailRepository.findByBillIdAndEmpresaIdAndTipoAndStatusOrderByDateSentDesc(id, u.getEmpresa().getId(), "FE", BillHelper.RESPUESTA_PENDIENTE);
        }
        Result result = null;
        if (bsdl==null || bsdl.size()==0) {
            logger.log(Level.ALL, "La factura enviada previamente no tiene un entrada registrada en el log");

            bsdl = billSenderDetailRepository.findByBillIdAndEmpresaIdAndTipoOrderByDateSentDesc(id, u.getEmpresa().getId(),"FE");
            if (bsdl==null || bsdl.size()==0) {
                throw new RuntimeException("La factura enviada previamente no tiene un entrada registrada en el log sea enviada a hacienda");
            }
        }
        EmisorService emisorService = new EmisorServiceImpl(this.usuarioService.getCurrentLoggedUser().getEmpresa());
        BillSenderDetail bsd1 = bsdl.get(0);
        String basePath = bsd1.getPath().substring(billConfigService.getDirSystemPath().length());
//        billConfigService.setBasePath(basePath);
        result = this.billTaskService.getComprobanteResult(bsd1.getClave(),bsd1.getBillId(), bsd1.getPath());
        String statusActual = bsd1.getStatus();
        Integer validada = bsd1.getValidada();
        if (result!=null && result.getEstado()!=null && !result.getEstado().equals("")) {
            statusActual = result.getEstado();
        }
        if (result.getResult()==1 || result.getResult()==3) {
            validada = 1;
        }
        if (result.getResult()==1 || result.getResult()==3) {
            String xmlFileName = bsd1.getPath()  +  bsd1.getClave() + ".xml";
            try {

                FacturaElectronica fe = this.feu.readXMLFile(xmlFileName);
                fe.setId(bsd1.getBillId());
                fe.setTipoDocumento(BillHelper.FACTURA_ELECTRONICA_TIPO);
                fe.setEmpresaId(u.getEmpresa().getId());

                fe.setCorreo(correos);
                fe.setId(id);
                this.billTaskService.notificarClienteConFactura(fe, bsd1, statusActual, result.getResultStr(), validada, result.getResult());
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else if (result.getResult()==-99 ) {
            this.billTaskService.updateFactura(bsd1.getBillId(), u.getEmpresa().getId(), 0, BillHelper.INGRESADA, BillHelper.RESPUESTA_NO_ENVIADA,
                    bsd1.getClave(), bsd1.getConsecutivo());


        } else {
            this.billTaskService.enviarDevCorreo(bsd1.getClave(), bsd1.getConsecutivo(), "No se obtuvo respuesta", result.getResultStr(), bsd);
        }

//        }

        return result;


    }

    public Result getStatusHacienda(Integer id) {

        this.initializeBillDataWithCurrentTenant();

        BillSenderDetail bsd  = null;
        Usuario u = usuarioService.getCurrentLoggedUser();
        List<BillSenderDetail> bsdl = billSenderDetailRepository.findByBillIdAndEmpresaIdAndTipoAndStatusOrderByDateSentDesc(id,u.getEmpresa().getId(),"FE","OK");
        Result result = null;
        if (bsdl==null || bsdl.size()==0) {
            logger.log(Level.ALL, "La factura enviada previamente no tiene un entrada registrada en el log");

            bsdl = billSenderDetailRepository.findByBillIdAndEmpresaIdAndTipoOrderByDateSentDesc(id, u.getEmpresa().getId(),"FE");
            if (bsdl==null || bsdl.size()==0) {
                throw new RuntimeException("La factura enviada previamente no tiene un entrada registrada en el log sea enviada a hacienda");
            }
        }

        for (BillSenderDetail bsd1: bsdl) {

            if (bsd1.getClave()==null || bsd1.getClave().equals("")) {
                continue;
            }
            String basePath = bsd1.getPath().substring(billConfigService.getDirSystemPath().length());
//            billConfigService.setBasePath(basePath);

            result = this.billTaskService.getComprobanteResult(bsd1.getClave(), id, bsd1.getPath());
            if (result.getResult()==1 && bsd1.getStatus().equals(BillHelper.RESPUESTA_NO_ACEPTADO_NO_RECHAZADO)) {

            }

        }
        return result;
    }

    private String createCorreoTo(Cliente data) {
        String email = "";
        Boolean isInitialized = false;
        if (!StringUtils.isEmpty(data.getCorreo())) {
            email = data.getCorreo();
            isInitialized = true;
        }

        if (!StringUtils.isEmpty(data.getContacto1Correo()) && !BillHelper.isNAEmail(data.getContacto1Correo())) {
            if (isInitialized) {
                email = email + ";" + data.getContacto1Correo();
            } else {
                isInitialized = true;
                email = data.getContacto1Correo();
            }

        }

        if (!StringUtils.isEmpty(data.getContacto2Correo()) && !BillHelper.isNAEmail(data.getContacto2Correo())) {
            if (isInitialized) {
                email = email + ";" + data.getContacto2Correo();
            } else {
                isInitialized = true;
                email = data.getContacto2Correo();
            }

        }

        if (!StringUtils.isEmpty(data.getContacto3Correo()) && !BillHelper.isNAEmail(data.getContacto3Correo())) {
            if (isInitialized) {
                email = email + ";" + data.getContacto3Correo();
            } else {
                email = data.getContacto3Correo();
            }

        }

        if (!StringUtils.isEmpty(data.getContacto4Correo()) && !BillHelper.isNAEmail(data.getContacto4Correo())) {
            if (isInitialized) {
                email = email + ";" + data.getContacto4Correo();
            } else {
                email = data.getContacto4Correo();
            }

        }


        return email;
    }

    private String createCorreoTo(NotaCreditoDataDTO data) {
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
        return email;
    }

    public String getXmlFileName(String clave, BillSenderDetail b) {
        String fileName = null;
        fileName = clave + ".xml";
        String fullFileName = b.getPath() + fileName;
        return fileName;
    }

    private boolean firmarFactura(OptionSet options, String facturaNamespaceV42, Integer id, String tipo, BillSenderDetail pefd, Integer empresaId) {
        if (!billEmitter.sign(options, facturaNamespaceV42)) {
            this.billTaskService.agregarError("FIRMA", billEmitter.getMessageError(), id);
            this.billTaskService.createProcesoEmisionDetalle(id, new Date(), pefd, this.billConfigService.getCurrentUser(), 0, 0, 0, billEmitter.getMessageError(), "FIRMA", tipo, empresaId);

            return false;
        }
        return true;
    }

    private void initReenvioInfo(Boolean esReenvio, FacturaElectronica fe, Integer id, String oldClave) {
        if (!esReenvio) {
            return;
        }

        BillSenderDetail bsd = null;
        List<BillSenderDetail> l = this.billSenderDetailRepository.findByClave(oldClave);
        if (l!=null && l.size()>0) {
            FacturaElectronicaV43Mapper mapper = new FacturaElectronicaV43Mapper();
            bsd = l.get(0);
            try {
                FacturaElectronica feOld = feu.readXMLFile(bsd.getPath() + bsd.getClave() + ".xml");
                FacturaElectronica.InformacionReferencia ir = mapper.getInformacionReferenciaSustituyePorRechazoFe(feOld);
                fe.getInformacionReferencia().add(ir);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }





}
