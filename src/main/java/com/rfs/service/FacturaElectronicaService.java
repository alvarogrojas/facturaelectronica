package com.rfs.service;

import com.rfs.domain.ConfirmaRechazaDocumento;
import com.rfs.domain.factura.BillSenderDetail;
import com.rfs.dtos.FacturaElectronicaDTO;
import com.rfs.dtos.ResultadoConfirmacionRechazoDTO;
import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.fe.v43.mh.MensajeHacienda;
import com.rfs.repository.ConfirmaRechazoDocumentoRepository;
import com.rfs.repository.factura.BillSenderDetailRepository;
import com.rfs.service.factura.billapp.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FacturaElectronicaService {


    private Path rootConfirmationLocation = null;

    //Logger log = LoggerFactory.getLogger(FacturaElectronicaService.class);

    @Autowired
    private FacturaDeserializer facturaDeserializer;

    @Autowired
    private FacturaElectronicaUnmarshaller feu;

    @Autowired
    private FacturaElectronicaMarshaller fem;

    @Autowired
    private BillManagerService billManagerService;

    @Autowired
    private ConfirmaRechazoDocumentoRepository confirmaRechazoDocumentoRepository;

    @Autowired
    private UsuarioService usuarioService;

    private Path fileStorageLocation;

    @Autowired
    public BillConfigData billConfigData;

    @Autowired
    private BillSenderDetailRepository billSenderDetailRepository;

    public String loadDataFromFile(MultipartFile file) throws JAXBException {
        String fileName = this.storeConfirmation(file);
        return fileName;
    }

    public String storeConfirmation(MultipartFile file) {
        String fullFileName = "";
        this.rootConfirmationLocation =  Paths.get(this.billConfigData.getBasedPath());
        try {
            try {
                if (!Files.exists(rootConfirmationLocation)) {
                    Files.createDirectory(rootConfirmationLocation);
                }
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize storage!");
            }
            if (Files.exists(this.rootConfirmationLocation.resolve(file.getOriginalFilename()))) {
                FileSystemUtils.deleteRecursively(new File(this.rootConfirmationLocation.toString(),file.getOriginalFilename()));
            }
            Files.copy(file.getInputStream(), this.rootConfirmationLocation.resolve(file.getOriginalFilename()));
            fullFileName = this.rootConfirmationLocation.toString() + File.separator + file.getOriginalFilename();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("FAIL! " + e.getMessage());
        }
        return fullFileName;
    }

    public FacturaElectronicaDTO getMensajeHacienda(String fileName) throws JAXBException {
        FacturaElectronicaDTO result = new FacturaElectronicaDTO();
        this.rootConfirmationLocation =  Paths.get(this.billConfigData.getBasedPath());
        String fullFileName = this.rootConfirmationLocation.toString() + File.separator + fileName;
        FacturaElectronica fe = feu.readXMLFile(fullFileName);
        if (fe!=null) {
            //result.setFacturaElectronica(feu.readXMLFile(fullFileName));
            mapMensajeToDTO(result,fe);
        } else {
            MensajeHacienda mh = feu.mensajeHaciendaXMLFile(fullFileName);
            mapMensajeToDTO(result,mh);
            //result.setMensajeHacienda(feu.mensajeHaciendaXMLFile(fullFileName));
        }
        result.setFullPathFileName(fileName);
        return result;
    }

    private void mapMensajeToDTO(FacturaElectronicaDTO result, MensajeHacienda mh) {
        if (mh==null) {
            return;
        }
        result.setClave(mh.getClave());
        //result.setNumeroConsecutivo(mh.getN);
        result.setEmisorNombre(mh.getNombreEmisor());
        result.setMontoTotalImpuesto(mh.getMontoTotalImpuesto().doubleValue());
        result.setTotalFactura(getValue(mh.getTotalFactura()));
        result.setMontoTotalImpuesto(getValue(mh.getMontoTotalImpuesto()));
        result.setNumeroCedulaEmisor(mh.getNumeroCedulaEmisor());
        result.setNombreReceptor(mh.getNombreReceptor());
        if (mh.getNumeroCedulaReceptor()!=null && mh.getNumeroCedulaReceptor().getValue()!=null) {

            result.setNumeroCedulaReceptor(mh.getNumeroCedulaReceptor().getValue());
        }

        result.setIsFacturaComprobante(false);

    }

    private void mapMensajeToDTO(FacturaElectronicaDTO result, FacturaElectronica mh) {
        if (mh==null) {
            return;
        }
        result.setClave(mh.getClave());
        //result.setNumeroConsecutivo(mh.getN);
        result.setEmisorNombre(mh.getEmisor().getNombre());
        result.setMontoTotalImpuesto(getValue(mh.getResumenFactura().getTotalImpuesto()));
        result.setTotalFactura(getValue(mh.getResumenFactura().getTotalComprobante()));
        result.setTotalDescuentos(getValue(mh.getResumenFactura().getTotalDescuentos()));
        result.setTotalVentaNeta(getValue(mh.getResumenFactura().getTotalVentaNeta()));
        result.setTotalVenta(getValue(mh.getResumenFactura().getTotalVenta()));

        result.setNumeroCedulaEmisor(mh.getEmisor().getIdentificacion().getNumero());

        result.setNombreReceptor(mh.getReceptor().getNombre());
        result.setNumeroCedulaReceptor(mh.getReceptor().getIdentificacion().getNumero());
        result.setIsFacturaComprobante(true);
        result.setFacturaElectronica(mh);


    }

    public Result confirmFacturaElectronica(String fileName, String mensaje) throws JAXBException, DatatypeConfigurationException {
        FacturaElectronicaDTO fedto = getFacturaElectronica(fileName);
        Result r = null;

        ConfirmaRechazaDocumento c = saveConfirmacionRechazo(fileName, fedto, "CONFIRMADO");

        if (c!=null) {
           // fedto.set
            r =  billManagerService.sendConfirmDE(fedto.getFacturaElectronica(),mensaje, c.getId());
            r = new Result();
            r.setResult(-1);
            r.setResultStr("Factura Electronica se esta enviando. El proceso de obtener respuesta se hará automaticamente.");
            r.setEstado(BillHelper.ENVIANDO);

        } else {
            r = new Result();
            r.setResult(0);
            r.setResultStr("Comprobante no fue enviado. Error al intentar guardar en la base de datos la confirmacion");
            r.setEstado(BillHelper.RESPUESTA_NO_ENVIADA);
        }

        return r;

    }

    public Result rechazarFacturaElectronica(String fileName, String mensaje) throws JAXBException, DatatypeConfigurationException {

        FacturaElectronicaDTO fedto = getFacturaElectronica(fileName);
        Result r = null;

        ConfirmaRechazaDocumento c = saveConfirmacionRechazo(fileName, fedto,"RECHAZADO");
        if (c!=null) {
            // fedto.set
            r =  billManagerService.sendRejectDE(fedto.getFacturaElectronica(),mensaje, c.getId());
            r = new Result();
            r.setResult(-1);
            r.setResultStr("Factura Electronica se esta enviando. El proceso de obtener respuesta se hará automaticamente.");
            r.setEstado(BillHelper.ENVIANDO);
        } else {
            r = new Result();
            r.setResult(0);
            r.setResultStr("Comprobante no fue enviado. Error al intentar guardar en la base de datos la confirmacion");
            r.setEstado(BillHelper.RESPUESTA_NO_ENVIADA);
        }



        return r;
    }



    public ResultadoConfirmacionRechazoDTO getConfirmacionRechazosList(String filter, Date fechaInicio, Date fechaFinal) {
        ResultadoConfirmacionRechazoDTO result = new ResultadoConfirmacionRechazoDTO();
        if (fechaInicio == null && fechaFinal == null) {
            Date[] dates = initDateRango(fechaInicio, fechaFinal);
            fechaFinal = dates[1];
            fechaInicio = dates[0];
        }

        Integer empresaId = this.usuarioService.getCurrentLoggedUser().getEmpresa().getId();
        if (filter==null || filter.equals("")) {
            List<ConfirmaRechazaDocumento> crd = (List<ConfirmaRechazaDocumento>) this.confirmaRechazoDocumentoRepository.getConfirmaRechazaDocumentoByFilters(fechaInicio, fechaFinal,empresaId);
            result.setConfirmRechazos(crd);
        } else {

            List<ConfirmaRechazaDocumento> list = this.confirmaRechazoDocumentoRepository.getConfirmaRechazaDocumentoByFilters(filter, fechaInicio, fechaFinal,empresaId);
            result.setConfirmRechazos(list);
        }
        result.setFechaInicio(fechaInicio);
        result.setFechaFinal(fechaFinal);

        return result;
    }

    public ConfirmaRechazaDocumento getConfirmacion(String clave) {
        return this.confirmaRechazoDocumentoRepository.findByClave(clave);
    }

    private Date[] initDateRango(Date fechaInicio, Date fechaFinal) {

        if (fechaInicio == null || fechaFinal == null) {
            fechaFinal = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaFinal);

            cal.add(Calendar.WEEK_OF_YEAR, -1); // Fecha semana pasada
            fechaInicio = cal.getTime();
        }

        return new Date[]{fechaInicio, fechaFinal};
    }

    public FacturaElectronicaDTO getFacturaElectronica(String fileName) throws JAXBException {
        FacturaElectronicaDTO result = new FacturaElectronicaDTO();
        this.rootConfirmationLocation =  Paths.get(this.billConfigData.getBasedPath());
        String fullFileName = this.rootConfirmationLocation.toString() + File.separator + fileName;
        result.setFacturaElectronica(feu.readXMLFile(fullFileName));
        result.setFullPathFileName(fileName);
        return result;
    }

    public Resource loadFileAsResource(Integer id, String fileName) throws Exception {
        try {
            //String fileName = getXmlFileName(id);
            initPath(id, usuarioService.getCurrentLoggedUser().getEmpresa().getId());
            if (fileStorageLocation==null){
                throw new Exception("Error al obtener el archivo XML");
            }
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + id, ex);
        }
    }



    private ConfirmaRechazaDocumento saveConfirmacionRechazo(String fileName, FacturaElectronicaDTO fedto, String estado) {
        this.rootConfirmationLocation =  Paths.get(this.billConfigData.getBasedPath());
        ConfirmaRechazaDocumento cd = null;
        try {
            cd = this.confirmaRechazoDocumentoRepository.findByClave(fedto.getFacturaElectronica().getClave());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cd==null)
            cd = new ConfirmaRechazaDocumento();
        cd.setClave(fedto.getFacturaElectronica().getClave());
        cd.setFechaEmision(fedto.getFacturaElectronica().getFechaEmision().toGregorianCalendar().getTime());
        cd.setConsecutivo(fedto.getFacturaElectronica().getNumeroConsecutivo());
        cd.setEmisor(fedto.getFacturaElectronica().getEmisor().getNombre());
        cd.setIdentificacionEmisor(fedto.getFacturaElectronica().getEmisor().getIdentificacion().getNumero());
        cd.setTotalVenta(getValue(fedto.getFacturaElectronica().getResumenFactura().getTotalVenta()));
        cd.setTotalImpuesto(getValue(fedto.getFacturaElectronica().getResumenFactura().getTotalImpuesto()));
        cd.setTotalComprobante(getValue(fedto.getFacturaElectronica().getResumenFactura().getTotalComprobante()));
        cd.setPathOriginalFile(this.rootConfirmationLocation.toString() + File.separator + fileName);
        cd.setEstado(estado);

        cd.setTipo("FE");

        if (fedto.getFacturaElectronica().getResumenFactura().getCodigoTipoMoneda()==null) {
            cd.setMoneda("CRC");
        } else {
            cd.setMoneda(fedto.getFacturaElectronica().getResumenFactura().getCodigoTipoMoneda().getCodigoMoneda());

        }

        cd.setEmpresaId(usuarioService.getCurrentLoggedUser().getEmpresa().getId());
        cd.setUltimoCambioPor(usuarioService.getCurrentLoggedUserId());

        cd.setFechaUltimoCambio(new Date());


        cd.setEstadoEnviadoHacienda("enviando");

        cd = this.confirmaRechazoDocumentoRepository.save(cd);
        return cd;
    }

    private double getValue(BigDecimal v) {
        if (v==null){
            return 0d;
        }
        return v.doubleValue();
    }

    public void initPath(Integer id, Integer empresaId) {
        List<BillSenderDetail> l = this.billSenderDetailRepository.findByBillIdAndTipoAndStatusAndEmpresaIdOrderByDateSentDesc(id,"FE", BillHelper.RESPUESTA_ACEPTADA, empresaId);
        if (l!=null && l.size()>0) {
            BillSenderDetail bsd = l.get(0);
            this.fileStorageLocation = Paths.get(bsd.getPath())
                    .toAbsolutePath().normalize();
            //return bsd.getPath() +   "signed" + bsd.getClave() + ".xml";

        }
        // return null;
    }

}
