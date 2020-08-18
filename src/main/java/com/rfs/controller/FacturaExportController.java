package com.rfs.controller;


import com.rfs.dtos.FacturaTableDTO;
import com.rfs.service.FacturaService;
import com.rfs.view.ExcelFacturasView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path="/api/export")
public class FacturaExportController {

    @Autowired
    FacturaService facturaService;

    /**
     * Handle request to download an Excel document
     */
    @RequestMapping(value = "/facturas", method = RequestMethod.GET)
    public ModelAndView downloadFacturas(
            @RequestParam (value="fechaInicio",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date fechaInicio,
            @RequestParam (value="fechaFinal",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal) {
        Map<String, Object> model1 = new HashMap<String, Object>();
        FacturaTableDTO facturaTableDTO = facturaService.getFacturasFiltrados(null,fechaInicio, fechaFinal, true,null,null);
        model1.put("facturaTableDTO",facturaTableDTO);
        return new ModelAndView(new ExcelFacturasView(), model1);
    }


}