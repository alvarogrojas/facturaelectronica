package com.rfs.service.factura.billapp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rfs.controller.ConfirmacionFacturaElectronicaController;
import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.fe.v43.mr.MensajeReceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FacturaSerializer {

    @Autowired
    private BillConfigService billConfigService;

    private XmlMapper xmlMapper;

    public FacturaSerializer() {
        xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);

        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

//    public String serialize(FacturaElectronica fe) {
//        String fileName = null;
//        try {
//            fileName = fe.getClave() + ".xml";
//            String fullFileName = billConfigService.getCompletePath() + fileName;
//            xmlMapper.writeValue(new File(fullFileName), fe);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return fileName;
//    }
//
//    public String serialize(MensajeReceptor fe) {
//        String fileName = null;
//        try {
//            fileName = fe.getClave() + ".xml";
//            String fullFileName = billConfigService.getCompletePath() + fileName;
//            xmlMapper.writeValue(new File(fullFileName), fe);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return fileName;
//    }


}
