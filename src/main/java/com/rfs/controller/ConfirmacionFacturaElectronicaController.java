package com.rfs.controller;

import com.rfs.domain.*;
import com.rfs.dtos.ResultadoConfirmacionRechazoDTO;
import com.rfs.repository.*;
import com.rfs.fe.v43.mh.MensajeHacienda;
import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.dtos.FacturaElectronicaDTO;
import com.rfs.service.FacturaElectronicaService;
import com.rfs.service.factura.billapp.Result;
//import com.rfs.service.tica.StorageService;
import com.rfs.view.ExcelGastosRegistroView;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.*;

@Controller
@RequestMapping(path="/api/fe/confirmacion")
public class ConfirmacionFacturaElectronicaController {

    @Autowired
    private FacturaElectronicaService facturaElectronicaService;

    @Autowired
    private ApplicationContext applicationContext;

    List<String> files = new ArrayList<String>();
    org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @PostMapping("/upload")
    public @ResponseBody
    String handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        //System.out.println("CONTENT TYPE " + file.getContentType());
//        if(!file.getContentType().equalsIgnoreCase("text/xml")){
//            throw new Exception("El archivo no tiene el formato esperado, unicamente debe subir archivos XML");
//        }
        String message = "";
        log.debug("INGRESA handleFileUpload");
        String result = "";
        try {
            result = facturaElectronicaService.loadDataFromFile(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK " + result;
    }

    @GetMapping(path="/data")
    public @ResponseBody
    FacturaElectronicaDTO getFacturaElectronica(@RequestParam String fileName) {
        try{
            return facturaElectronicaService.getMensajeHacienda(fileName);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new FacturaElectronicaDTO();
    }

    @GetMapping(path="/ok")
    public @ResponseBody
    Result confirmarDE(@RequestParam String fileName,
                          @RequestParam String mensaje) throws JAXBException, DatatypeConfigurationException {

        //facturaService.testing();
        return facturaElectronicaService.confirmFacturaElectronica(fileName, mensaje);
    }

    @GetMapping(path="/rechazo")
    public @ResponseBody
    Result rechazoDE(@RequestParam String fileName,
                     @RequestParam String mensaje) throws JAXBException, DatatypeConfigurationException {

        //facturaService.testing();
        return facturaElectronicaService.rechazarFacturaElectronica(fileName, mensaje);
    }

    @GetMapping(path="/rechazoDoc")
    public @ResponseBody
    ConfirmaRechazaDocumento getConfirmacion(@RequestParam String clave){

        return facturaElectronicaService.getConfirmacion(clave);
    }

    @GetMapping(path="/confirmacion-rechazo/list")
    public @ResponseBody
    ResultadoConfirmacionRechazoDTO getConfimaRechazoDocList(
            @RequestParam String filter,
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaInicio,
            @RequestParam(value = "fechaFinal", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal) {
        return facturaElectronicaService.getConfirmacionRechazosList(filter, fechaInicio, fechaFinal);

    }

    @RequestMapping(value = "/download/gastos", method = RequestMethod.GET)
    public ModelAndView downloadGastos(
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaInicio,
            @RequestParam(value = "fechaFinal", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal,
            @RequestParam(value = "filter", required = false) String filter
    ) throws Exception{
        Map<String, Object> model1 = new HashMap<String, Object>();

        ResultadoConfirmacionRechazoDTO dto = facturaElectronicaService.getConfirmacionRechazosList(filter, fechaInicio, fechaFinal);
        model1.put("dto",dto);

        ExcelGastosRegistroView view = new ExcelGastosRegistroView();

        //view.setUrl("factura");
        view.setApplicationContext(applicationContext);
        return new ModelAndView(view, model1);


       // return new ModelAndView("excelGastosView", "dto", dto);
    }



}
