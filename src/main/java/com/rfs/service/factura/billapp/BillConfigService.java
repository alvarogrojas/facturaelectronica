package com.rfs.service.factura.billapp;

import com.rfs.domain.factura.BillSenderDetail;
import com.rfs.domain.factura.OptionSet;
import com.rfs.fe.v43.FacturaElectronica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BillConfigService {

    public static final String INPUT_FILE = "input";

    public static final String OUTPUT_FILE = "output";
    
    private static final String PREFIX_FILE = "signed";
    public static final String PASSWORD = "password";
    public static final String CERTIFICATE = "certificate";

//    private String certificateFileName = "310165487908.p12";

    @Autowired
    public BillConfigData billConfigData;


    @Autowired
    private BillUserSystem billUserSystem;

    private SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

    //private String dirSystemPath;

//    private String basePath;

    public BillConfigService() {

    }

    public String getDirSystemPath() {
        return billConfigData.getBasedPath();
    }

//    public String getBasePath() {
//        return basePath;
//    }
//
//    public void setBasePath(String basePath) {
//        this.basePath = basePath;
//    }

//    public void initBasePath(String consecutivo, String empresa, String tipo) {
//        this.basePath = empresa + File.separator + tipo + File.separator + sdf.format(new Date()) + File.separator + consecutivo + File.separator;
//
//        String path = getCompletePath();
//        File directory = new File(path);
//        if (!directory.exists()){
//            directory.mkdirs();
//
//        }
//    }

    public String getInitialPath(String consecutivo,  String empresa, String tipo) {
        String basePath = empresa + File.separator + tipo + File.separator + sdf.format(new Date()) + File.separator + consecutivo + File.separator;
        //String basePath  = sdf.format(new Date())+ File.separator + tipo + File.separator + consecutivo + File.separator;

        String path = billConfigData.getBasedPath() + basePath;
        File directory = new File(path);
        if (!directory.exists()){
            directory.mkdirs();

        }
        return path;
    }


//    public String getCompletePath() {
//        return billConfigData.getBasedPath() + this.basePath;
//    }

    public OptionSet initOptionsValues(BillSenderDetail bsd, String fileName) {
        
        OptionSet options = new OptionSet();
        
        options.setValue(INPUT_FILE,bsd.getPath() + fileName);

        options.setValue(OUTPUT_FILE,bsd.getPath() + PREFIX_FILE + fileName);

        options.setValue(PASSWORD,billConfigData.getPin());

        options.setValue(CERTIFICATE, billConfigData.getBasedPath() + billConfigData.getCertificateFileName());

        return options;
    }

//    public OptionSet initOptionsValues(String fileName, OptionSet options) {
//
//        options.setValue(INPUT_FILE,getCompletePath() + fileName);
//
//        options.setValue(OUTPUT_FILE,getCompletePath() + PREFIX_FILE + fileName);
//
//        return options;
//    }

    public String getCertificateFileName() {
        return billConfigData.getCertificateFileName();
    }

//    public void setCertificateFileName(String certificateFileName) {
//        this.billConfigData. = certificateFileName;
//    }

    public String getPin() {
        return billConfigData.getPin();
    }

//    public void setPin(String pin) {
//        this.pin = pin;
//    }

    public String getUsuario() {
        return billConfigData.getUsuario();
    }

    public String getPassword() {
        return billConfigData.getClave();
    }

    public String getXmlFileAndFullPath(FacturaElectronica fe, BillSenderDetail b) {
        return b.getPath() + fe.getClave() + ".xml";
    }


    public String getSignedXmlFileAndFullPath(FacturaElectronica fe, BillSenderDetail b) {
        return b.getPath() + this.PREFIX_FILE + fe.getClave() + ".xml";
    }

    public String getSignedXmlFileAndFullPath(String clave, BillSenderDetail b) {
        return b.getPath() + this.PREFIX_FILE + clave + ".xml";
    }

    public String getPdfFileAndFullPath(String clave, BillSenderDetail b) {
        return b.getPath() + clave + ".pdf";
    }

    public String getRespuestaHaciendaPath(String clave, BillSenderDetail b) {
        return b.getPath() + BillHelper.PREFIX_MENSAJE_HACIENDA_FILE + clave + ".xml";

    }


    public Integer getCurrentUser() {
        return this.billUserSystem.getCurrentLoggedUser();
    }

    public String getPdfFileAndFullPath(FacturaElectronica fe, BillSenderDetail b) {
        return b.getPath() + fe.getClave() + ".pdf";
    }

    public String getIdpClientId() {
        return this.billConfigData.getIdpClientId();
    }

    public String getBasedPath() {
        return  this.billConfigData.getBasedPath();
    }
    public String getIdpUri() {
        return this.billConfigData.getIdpUri();
    }

    public String getApiUri() {
        return billConfigData.getApiUri();
    }

    public void setBillConfigData(BillConfigData billConfigData) {
        this.billConfigData = billConfigData;
    }

    public BillConfigData getBillConfigData() {
        return billConfigData;
    }
}
