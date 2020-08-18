package com.rfs.controller;

import com.rfs.domain.TipoCambio;
import com.rfs.service.TipoCambioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api/tipo-cambio")
public class TipoCambioController {

    @Autowired
    private TipoCambioService tipoCambioService;

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
    @PostMapping(path="/create")
    public @ResponseBody
    String createRecibo(@RequestBody TipoCambio c) {
        TipoCambio r = tipoCambioService.save(c);
        return r.getNombre();
    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
    @GetMapping(path="/data")
    public @ResponseBody
    TipoCambio getCliente(@RequestParam Integer id) {

        return tipoCambioService.getTipoCambio(id);
    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	@GetMapping(path="/data/list")
    public @ResponseBody
    Iterable<TipoCambio> getTipoCambioList() {
        return tipoCambioService.getTiposCambio();
    }
}
