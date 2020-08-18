package com.rfs.service;

import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.fe.v43.mh.MensajeHacienda;
import com.rfs.fe.v43.mr.MensajeReceptor;
import com.rfs.fe.v43.nc.NotaCreditoElectronica;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

@Service
public class FacturaElectronicaMarshaller {

    public FacturaElectronica writeXMLFile(FacturaElectronica fe, String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(FacturaElectronica.class);
        Marshaller marshallerObj = jContext.createMarshaller();
        marshallerObj.marshal(fe,file);
        return fe;
    }

    public MensajeReceptor writeXMLFile(MensajeReceptor mr, String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(MensajeReceptor.class);
        Marshaller marshallerObj = jContext.createMarshaller();
        marshallerObj.marshal(mr,file);
        return mr;
    }

    public NotaCreditoElectronica writeXMLFile(NotaCreditoElectronica mr, String fileName) throws JAXBException {
        File file = new File(fileName);
        JAXBContext jContext = JAXBContext.newInstance(NotaCreditoElectronica.class);
        Marshaller marshallerObj = jContext.createMarshaller();
        marshallerObj.marshal(mr,file);
        return mr;
    }

    public MensajeHacienda writeXMLFile(MensajeHacienda mr, String fileName) throws JAXBException {
        try {
            File file = new File(fileName);
            JAXBContext jContext = JAXBContext.newInstance(MensajeHacienda.class);
            Marshaller marshallerObj = jContext.createMarshaller();
            marshallerObj.marshal(mr, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mr;
    }


}

