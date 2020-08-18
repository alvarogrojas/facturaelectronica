package com.rfs.service.factura.billapp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rfs.fe.FacturaElectronica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FacturaDeserializer {

    @Autowired
    private BillConfigService billConfigService;

    private XmlMapper xmlMapper;

    public FacturaDeserializer() {
        xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);

        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public FacturaElectronica deserialize(String xmlFullPath) {
        FacturaElectronica fe = new FacturaElectronica();
        try {
            File file = new File(xmlFullPath);
            String xml = inputStreamToString(new FileInputStream(file));
            fe = xmlMapper.readValue(xml, FacturaElectronica.class);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return fe;
    }

    public String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }


}
