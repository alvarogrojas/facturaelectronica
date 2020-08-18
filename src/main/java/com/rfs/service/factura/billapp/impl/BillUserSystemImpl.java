package com.rfs.service.factura.billapp.impl;

import com.rfs.service.UsuarioService;
import com.rfs.service.factura.billapp.BillUserSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillUserSystemImpl implements BillUserSystem{

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Integer getCurrentLoggedUser() {
        return usuarioService.getCurrentLoggedUserId();
    }
}
