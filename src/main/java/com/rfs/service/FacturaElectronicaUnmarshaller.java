package com.rfs.service;

import com.rfs.domain.NotaCredito;
import com.rfs.fe.v43.*;
import com.rfs.fe.v43.mh.MensajeHacienda;
import com.rfs.fe.v43.mr.MensajeReceptor;
import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.fe.v43.nc.NotaCreditoElectronica;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FacturaElectronicaUnmarshaller {

    private Logger logger = Logger.getLogger("FacturaElectronicaUnmarshaller");


    public com.rfs.fe.v43.FacturaElectronica readXMLFile(String fileName) throws JAXBException {


        com.rfs.fe.v43.FacturaElectronica fe = null;
        File file = new File(fileName);
        try {

            JAXBContext jContext = JAXBContext.newInstance(com.rfs.fe.v43.FacturaElectronica.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            fe = (com.rfs.fe.v43.FacturaElectronica) unmarshallerObj.unmarshal(file);
            return fe;
        } catch (JAXBException e){
            fe = manageUnmarshalFE42(file);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            throw e;
        }
        if (fe.getResumenFactura().getCodigoTipoMoneda()==null) {
            CodigoMonedaType c = new CodigoMonedaType();
            c.setCodigoMoneda("CRC");
            fe.getResumenFactura().setCodigoTipoMoneda(c);
        }
        return fe;
    }

    public NotaCreditoElectronica readNCXMLFile(String fileName) throws JAXBException {
        NotaCreditoElectronica fe = null;
        try {
            File file = new File(fileName);
            JAXBContext jContext = JAXBContext.newInstance(NotaCreditoElectronica.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            fe = (NotaCreditoElectronica) unmarshallerObj.unmarshal(file);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            //throw e;
        }
        return fe;
    }

    public MensajeHacienda mensajeHaciendaXMLFile(String fileName) throws JAXBException {
        MensajeHacienda fe = null;
        try {
            File file = new File(fileName);
            JAXBContext jContext = JAXBContext.newInstance(com.rfs.fe.v43.mh.MensajeHacienda.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            fe = (MensajeHacienda) unmarshallerObj.unmarshal(file);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            //throw e;
        }
        return fe;
    }

    public SignatureType readSignedTypeXMLFile(String fileName) throws JAXBException {
        SignatureType fe = null;
        try {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(com.rfs.fe.v43.SignatureType.class);
        Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
        fe = (SignatureType) unmarshallerObj.unmarshal(file);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            throw e;
        }
        return fe;
    }

    public MensajeReceptor readMensajeReceptorXMLFile(String fileName) throws JAXBException {
        MensajeReceptor fe = null;
        try {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(com.rfs.fe.v43.mr.MensajeReceptor.class);
        Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
        fe = (MensajeReceptor) unmarshallerObj.unmarshal(file);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            throw e;
        }
            return fe;
    }

    public MensajeHacienda createMensajeHacienda(String data) throws JAXBException {
        MensajeHacienda mensajeHacienda = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MensajeHacienda.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            mensajeHacienda = (MensajeHacienda) unmarshaller.unmarshal(new StringReader(data));
        } catch (JAXBException e){

            mensajeHacienda = manageMensaje42(data);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            throw e;
        }
        return mensajeHacienda;
    }

    private MensajeHacienda manageMensaje42(String data) throws JAXBException {
        MensajeHacienda mensajeHacienda;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(com.rfs.fe.MensajeHacienda.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            com.rfs.fe.MensajeHacienda mensajeHacienda1 = (com.rfs.fe.MensajeHacienda) unmarshaller.unmarshal(new StringReader(data));
            mensajeHacienda = new MensajeHacienda();
            BeanUtils.copyProperties(mensajeHacienda1, mensajeHacienda);
        } catch (JAXBException e){

            mensajeHacienda = manageMensaje41(data);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            throw e;
        }
        return mensajeHacienda;
    }

    private MensajeHacienda manageMensaje41(String data) throws JAXBException {
        MensajeHacienda mensajeHacienda;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(com.rfs.fe.v41.MensajeHacienda.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            com.rfs.fe.v41.MensajeHacienda mensajeHacienda1 = (com.rfs.fe.v41.MensajeHacienda) unmarshaller.unmarshal(new StringReader(data));
            mensajeHacienda = new MensajeHacienda();
            BeanUtils.copyProperties(mensajeHacienda1, mensajeHacienda);
        } catch (JAXBException e){

            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            throw e;
        }
        return mensajeHacienda;
    }

    private FacturaElectronica manageUnmarshalFE42(File file) throws JAXBException {
        FacturaElectronica fe;
        JAXBContext jaxbContext = JAXBContext.newInstance(com.rfs.fe.MensajeHacienda.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        com.rfs.fe.FacturaElectronica facturaElectronica = (com.rfs.fe.FacturaElectronica) unmarshaller.unmarshal(file);
        fe = new FacturaElectronica();
        BeanUtils.copyProperties(facturaElectronica, fe);
        if (facturaElectronica.getEmisor()!=null) {
            fe.setEmisor(new com.rfs.fe.v43.EmisorType());
            BeanUtils.copyProperties(facturaElectronica.getEmisor(), fe.getEmisor());
            fe.getEmisor().setIdentificacion(new IdentificacionType());
            BeanUtils.copyProperties(facturaElectronica.getEmisor().getIdentificacion(), fe.getEmisor().getIdentificacion());

        }
        if (facturaElectronica.getReceptor()!=null) {
            fe.setReceptor(new com.rfs.fe.v43.ReceptorType());
            BeanUtils.copyProperties(facturaElectronica.getReceptor(), fe.getReceptor());
            fe.getReceptor().setIdentificacion(new IdentificacionType());
            BeanUtils.copyProperties(facturaElectronica.getReceptor().getIdentificacion(), fe.getReceptor().getIdentificacion());
        }
        if (facturaElectronica.getDetalleServicio()!=null) {
            fe.setDetalleServicio(new FacturaElectronica.DetalleServicio());

            if (facturaElectronica.getDetalleServicio().getLineaDetalle() != null) {
                FacturaElectronica.DetalleServicio.LineaDetalle ld1;
                for (com.rfs.fe.FacturaElectronica.DetalleServicio.LineaDetalle ld : facturaElectronica.getDetalleServicio().getLineaDetalle()) {
                    ld1 = new FacturaElectronica.DetalleServicio.LineaDetalle();
                    BeanUtils.copyProperties(ld, ld1);
                    fe.getDetalleServicio().getLineaDetalle().add(ld1);
                }
            }
        }
        if (facturaElectronica.getResumenFactura()!=null) {
            fe.setResumenFactura(new FacturaElectronica.ResumenFactura());
            BeanUtils.copyProperties(facturaElectronica.getResumenFactura(), fe.getResumenFactura());

            fe.getResumenFactura().setCodigoTipoMoneda(new CodigoMonedaType());
            if (facturaElectronica.getResumenFactura().getCodigoMoneda()!=null) {
                fe.getResumenFactura().getCodigoTipoMoneda().setCodigoMoneda(facturaElectronica.getResumenFactura().getCodigoMoneda());
            } else {
                fe.getResumenFactura().getCodigoTipoMoneda().setCodigoMoneda("CRC");
            }
        }
        return fe;
    }


}

