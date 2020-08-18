package com.rfs.controller;

import com.rfs.domain.Tarifa;
import com.rfs.dtos.ClienteTarifasDTO;
import com.rfs.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api/cliente/tarifa")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path="/create")
    public @ResponseBody
    Tarifa createTarifa(@RequestBody Tarifa t) {
        t = tarifaService.save(t);
        return t;
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path="/create")
    public @ResponseBody
    Tarifa updateTarifa(@RequestBody Tarifa t) {
        t = tarifaService.save(t);
        return t;
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path="/delete")
    public @ResponseBody
    String deleteTarifa(@RequestParam Long id) {
        tarifaService.delete(id);
        return "OK";
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path="/data/list")
    public @ResponseBody
    ClienteTarifasDTO getTarifasList(@RequestParam Integer clienteId) {
        return tarifaService.getTarifas(clienteId);
    }
}
