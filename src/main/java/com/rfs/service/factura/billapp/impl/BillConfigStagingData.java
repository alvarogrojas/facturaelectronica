package com.rfs.service.factura.billapp.impl;

import com.rfs.service.factura.billapp.BillConfigData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillConfigStagingData implements BillConfigData {

   // @Value( "${bill.usuario}" )
    private String usuario;
   // @Value( "${bill.password}" )
    private String password ;
   /// @Value( "${bill.pin}" )
    private String pin ;
    @Value( "${bill.base.path}" )
    private String basePath;

    @Value( "${bill.idp.uri.system}" )
    private String idpUriSystem;
    private String idpUri;

    @Value( "${bill.api.uri.system}" )
    private String apiUriSystem;
    private String apiUri;

    @Value( "${bill.client.id.system}" )
    private String idpClientIdSystem;


    private String idpClientId;

    //@Value( "${bill.certificate.file.name}" )
    private String certificateFile;

    private String fileNamePath;

    private String cedula;

    public String recepcionCorreos;

    public Integer empresaId;

    private String correo;

    public String getBasedPath() {
        return basePath;
    }
    public String getIdpUri() {
        return idpUri;
    }

    public String getIdpClientId() {
        return idpClientId;
    }
    public String getApiUri() {
        return apiUri;
    }
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public void setClave(String clave) {
        this.password = clave;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public void setFileNamePath(String fileNamePath) {
        this.fileNamePath = fileNamePath;
    }


    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setIdpUri(String idpUri) {
        if (StringUtils.isEmpty(idpUri)) {
            idpUri = this.idpUriSystem;
        }
        this.idpUri = idpUri;
    }

    public void setApiUri(String apiUri) {
        if (StringUtils.isEmpty(apiUri)) {
            apiUri = this.apiUriSystem;
        }
        this.apiUri = apiUri;
    }

    public void setIdpClientId(String idpClientId) {
        this.idpClientId = idpClientId;
    }

    public void setCertificateFileName(String certificateFile) {
        this.certificateFile = certificateFile;
    }

    public String getClave() {
        return password;
    }
    public String getPin() {
        return pin;
    }

    public String getCertificateFileName() {return this.certificateFile;}

    public String getPassword() {
        return password;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getCertificateFile() {
        return certificateFile;
    }

    public String getFileNamePath() {
        return fileNamePath;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCedula() {
        return this.cedula;
    }

    @Override
    public String getRecepcionCorreos() {
        return recepcionCorreos;
    }

    @Override
    public void setRecepcionCorreos(String recepcionCorreos) {
        this.recepcionCorreos = recepcionCorreos;
    }

    public String getIdpUriSystem() {
        return idpUriSystem;
    }

    public void setIdpUriSystem(String idpUriSystem) {
        this.idpUriSystem = idpUriSystem;
    }

    public String getApiUriSystem() {
        return apiUriSystem;
    }

    public void setApiUriSystem(String apiUriSystem) {
        this.apiUriSystem = apiUriSystem;
    }

    public String getIdpClientIdSystem() {
        return idpClientIdSystem;
    }

    public void setIdpClientIdSystem(String idpClientIdSystem) {
        this.idpClientIdSystem = idpClientIdSystem;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    @Override
    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    @Override
    public String getCorreo() {
        return correo;
    }

    @Override
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
