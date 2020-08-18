package com.rfs.controller;

import com.rfs.dtos.NotaCreditoDataDTO;
import com.rfs.service.NotaCreditoService;
import com.rfs.service.factura.billapp.BillManagerService;
import com.rfs.service.factura.billapp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alvaro on 10/29/17.
 */
@Controller
@RequestMapping(path="/api/nota-credito")
public class NotaCreditoController {
    @Autowired
    private NotaCreditoService notaCreditoService;

    @Autowired
    private BillManagerService billManagerService;


//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path="/nueva-nota-credito")
    public @ResponseBody
    Integer add (@RequestBody NotaCreditoDataDTO f) throws  Exception {
        return notaCreditoService.save(f);

    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path="/send-save-nota-credito")
    public @ResponseBody
    Result saveAndSend (@RequestBody NotaCreditoDataDTO f) throws  Exception {
        return notaCreditoService.enviarNCAHacienda(f);

    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path="/no-aprobada-nota-credito")
    public @ResponseBody
    Result saveAndSend (@RequestParam(required = false) Integer id) throws  Exception {
        return notaCreditoService.noAprobarNotaCredito(id);

    }


    @GetMapping(path="/data")
    public @ResponseBody
    NotaCreditoDataDTO getFacturaData(@RequestParam(required = false) Integer id,
                                  @RequestParam(required = false) Integer facturaId) {

        return notaCreditoService.getNotaCreditoData(id,facturaId);
    }

    @GetMapping(path="/cambiar-estado")
    public @ResponseBody
    String aprobarFactura(@RequestParam(required = false) Integer facturaId,
                          @RequestParam(required = false) String estado
                                  ) {
        return notaCreditoService.cambiarEstadoFactura(facturaId, estado);
    }



}
