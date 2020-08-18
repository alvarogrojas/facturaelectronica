package com.rfs.controller;

import com.rfs.dtos.*;
import com.rfs.service.FacturaElectronicaService;
import com.rfs.service.FacturaService;
import com.rfs.service.factura.billapp.BillManagerService;
import com.rfs.service.factura.billapp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;


import java.io.IOException;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created by alvaro on 10/29/17.
 */
@Controller
@RequestMapping(path="/api/factura")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @Autowired
    private FacturaElectronicaService facturaElectronicaService;

    private static final Logger logger = LoggerFactory.getLogger(FacturaController.class);


    @Autowired
    private BillManagerService billManagerService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COORDINADOR')")
    @GetMapping(path="/revertir")
    public @ResponseBody FacturarResult getUsuarioData(@RequestParam(required = false) Integer id) {
       return this.facturaService.revertirFactura(id);

    }

    @PostMapping(path="/nueva-factura") //
    public @ResponseBody
    Integer add (@RequestBody FacturaDataDTO f) throws  Exception {
        //return reciboService.clonar(recibo);

        return facturaService.save(f);

    }


    @GetMapping(path="/data/list")
    public @ResponseBody
    FacturaTableDTO getFacturaList(
            @RequestParam (value="facturaId",required=false) Integer facturaId,
            @RequestParam (value="fechaInicio",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date fechaInicio,
            @RequestParam (value="fechaFinal",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal,
            @RequestParam (value="cargados",required=true) Boolean cargados,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize) {
        return facturaService.getFacturasFiltrados(facturaId,fechaInicio, fechaFinal, cargados,pageNumber,pageSize);
    }


    @GetMapping(path="/get")
    public @ResponseBody
    FacturaTableDTO getFacturas(
            @RequestParam (value="clienteId",required=false) Integer clienteId,
            @RequestParam (value="estado",required=false) String estado,
            @RequestParam (value="estadoPago",required=false) String estadoPago,
            @RequestParam (value="fechaInicio",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date fechaInicio,
            @RequestParam (value="fechaFinal",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal,
            @RequestParam (value="cargados",required=true) Boolean cargados,
            @RequestParam (value="enviadas",required=true) Integer enviados,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize) {
        return facturaService.getFacturas(clienteId, estado, estadoPago, fechaInicio, fechaFinal, cargados,pageNumber,pageSize,enviados);
    }

    @GetMapping(path="/data")
    public @ResponseBody
    FacturaDataDTO getFacturacion(@RequestParam(required = false) Integer id) throws Exception {
        return facturaService.getFacturaData(id);
    }


    @GetMapping(path="/get-status-hacienda")
    public @ResponseBody
    Result getFacturaStatus(@RequestParam(required = false) Integer id
    ) {


        return facturaService.getStatusHacienda(id);
    }

    @GetMapping(path="/check-status-hacienda")
    public @ResponseBody
    Result checkHaciendaStatus(@RequestParam(required = false) Integer id
    ) {


        return facturaService.checkHaciendaStatus(id);
    }

    @GetMapping(path="/check-status-nc-hacienda")
    public @ResponseBody
    Result checkHaciendaNcStatus(@RequestParam(required = false) Integer id
    ) {


        return facturaService.checkHaciendaNCStatus(id);
    }

    @GetMapping(path="/enviar-factura")
    public @ResponseBody
    Result saveAndSend (@RequestParam(required = true) Integer id) throws  Exception {
        return billManagerService.sendBill(id, false);

    }

    @GetMapping(path="/nota-credito-hacienda")
    public @ResponseBody
    String getFacturaData(@RequestParam (required = false) Integer id) throws Exception {

        return billManagerService.sendNotaCredito(id);
    }

    @GetMapping(path="/cambiar-estado-pago")
    public @ResponseBody
    String changeFacturaEstado(@RequestParam (value="id",required = false) Integer id,
                               @RequestParam (value="estadoPago",required=false) String estadoPago) {

        return facturaService.changeFacturaEstado(id,estadoPago);
    }

    @GetMapping(path="/respuestas-factura")
    public @ResponseBody
    RespuestaEnvioDTO getRespuestasHacienda(@RequestParam (value="id",required = false) Integer id) {
        return facturaService.getRespuestasHacienda(id);
    }

    @GetMapping("/download-xml")
    public ResponseEntity<Resource> downloadFile(@RequestParam(required = true) Integer id, HttpServletRequest request) throws Exception {
        // Load file as Resource
        String fileName = this.facturaService.getXmlFileName(id);
        if (fileName==null) {
            logger.error("FACTURA NO ENCONTRADA");
            throw new Exception("Not found bill id");
        }
        Resource resource = this.facturaElectronicaService.loadFileAsResource(id,fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
            //throw new Exception("Could not determine file type.);

        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
