package com.rfs.controller;


import com.rfs.dtos.PendientesFacturarDTO;
import com.rfs.dtos.RecibosDataTableDTO;
import com.rfs.view.ExcelReciboSinFacturarView;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
            @RequestMapping(path="/api/export")
public class ReciboExportController {

//    @Autowired
//    ReciboService reciboService;

    /**
     * Handle request to download an Excel document
                                                                                                                                                                       */
    @RequestMapping(value = "/recibos", method = RequestMethod.GET)
    public ModelAndView download(
            @RequestParam(value="encargadoId",required=false) Integer encargadoId,
            @RequestParam (value="clienteId",required=false) Integer clienteId,
            @RequestParam (value="estado",required=true) String estado,
            @RequestParam (value="fechaInicio",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaInicio,
            @RequestParam (value="fechaFinal",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal,
            Model model) {
        RecibosDataTableDTO ld = new RecibosDataTableDTO();
//        RecibosDataTableDTO ld = reciboService.getRecibos(encargadoId,clienteId,estado,fechaInicio,fechaFinal,true,null,null);
        return new ModelAndView("excelView", "recibosDataTableDTO", ld);
    }

    @RequestMapping(value = "/recibos/nofacturados", method = RequestMethod.GET)
    public ModelAndView download() {
        Map<String, Object> model1 = new HashMap<String, Object>();

//        return new ModelAndView("excelTicaView", "ticaTableDTO", dto);
        //return new ModelAndView(new ExcelTicaView(), model1);
        List<PendientesFacturarDTO> lpf = new ArrayList<>();
        //List<PendientesFacturarDTO> lpf = reciboService.getRecibosPendientesFacturar();
        model1.put("recibosPendientes",lpf);
//        return new ModelAndView("excelTicaView", "ticaTableDTO", dto);
        return new ModelAndView(new ExcelReciboSinFacturarView(), model1);

    }
}