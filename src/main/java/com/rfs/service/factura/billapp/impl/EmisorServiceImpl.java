package com.rfs.service.factura.billapp.impl;

import com.rfs.domain.Empresa;
import com.rfs.service.UsuarioService;
import com.rfs.service.factura.billapp.EmisorService;


public class EmisorServiceImpl implements EmisorService {


    private UsuarioService service;
    private Empresa empresa;

    public EmisorServiceImpl(Empresa e) {
        this.empresa = e;
    }

    @Override
    public Integer getEmpresaId() {
        return this.empresa.getId();
    }

    @Override
    public String getTipo() {
        return empresa.getTipo();
    }

    @Override
    public String getProvincia() {
        return empresa.getProvincia();
    }

    @Override
    public String getCanton() {
        return empresa.getCanton();
    }

    @Override
    public String getDistrito() {
        return empresa.getDistrito();
    }

    @Override
    public String getDireccion() {
        return empresa.getDireccion();
    }

    @Override
    public String getNombre() {
        return empresa.getNombre();
    }

    @Override
    public String getNumero() {
        return empresa.getCedula();
    }

    @Override
    public String getCorreo() {
        return empresa.getCorreo1();
    }

    @Override
    public String getUnidadMedida() {
        return empresa.getTipoMedida();
    }

    @Override
    public Integer getTelefono() {
        String t =  empresa.getTelefono();
        if (t==null || t!="") {
            return 85695050;
        }
        t = t.trim();
        t = t.replace(" ","");
        t = t.replace("-","");
        t = t.replace("(","");
        t = t.replace(")","");

        return Integer.valueOf(t);
    }


}
