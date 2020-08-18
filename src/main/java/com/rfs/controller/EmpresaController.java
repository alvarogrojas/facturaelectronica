package com.rfs.controller;

import com.rfs.domain.*;
import com.rfs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping(path="/api/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    //    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
    @PostMapping(path="/create")
    public @ResponseBody
    String createEmpresa(@RequestBody Empresa e) throws NoSuchAlgorithmException {
        Empresa r = empresaService.save(e);
        return r.getNombre();
    }

    //    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
    @GetMapping(path="/data")
    public @ResponseBody
    Empresa getEmpresa(@RequestParam Integer id) {
        return empresaService.getEmpresa(id);
    }

    //    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
    @GetMapping(path="/data/list")
    public @ResponseBody
    Iterable<Empresa> getEmpresaList() {
        return empresaService.getEmpresas();
    }
}
