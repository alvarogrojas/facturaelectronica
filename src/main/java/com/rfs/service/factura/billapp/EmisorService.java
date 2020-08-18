package com.rfs.service.factura.billapp;

public interface EmisorService {
    Integer getEmpresaId();
    String getTipo();
    String getProvincia();
    String getCanton();
    String getDistrito();

    String getDireccion();

    String getNombre();

    String getNumero();

    String getCorreo();
    String getUnidadMedida();
    Integer getTelefono();

}
