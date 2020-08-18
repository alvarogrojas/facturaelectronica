package com.rfs.service.factura.billapp;

public interface BillConfigData {

    public String getBasedPath();
    public String getIdpClientId();
    public String getIdpUri();
    public String getCedula();
    public String getCorreo();
    public void setCorreo(String correo);

    public String getApiUri();
    public String getUsuario();
    public String getClave();
    public String getPin();
    public String getCertificateFileName();
    public void setCertificateFileName(String cert);

    public void setIdpUri(String uri);
    public void setIdpClientId(String id);
    public void setApiUri(String uri);
    public void setUsuario(String usuario);
    public void setClave(String clave);
    public void setPin(String pin);
    public void setFileNamePath(String fileNamePath);
    public void setCedula(String cedula);

    public void setRecepcionCorreos(String recepcionCorreos);
    public String getRecepcionCorreos();


    void setEmpresaId(Integer id);
    Integer getEmpresaId();
}
