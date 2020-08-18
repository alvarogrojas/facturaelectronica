package com.rfs.controller;


import com.rfs.dtos.FacturaDataDTO;
import com.rfs.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path="/api/export/excel")
public class FacturaExcelExportController {

    @Autowired
    FacturaService facturaService;

    /**
     * Handle request to download an Excel document
     */
    @RequestMapping(value = "/factura", method = RequestMethod.GET)
    public ModelAndView downloadFacturas(
            @RequestParam (value="reciboId",required=false) Integer reciboId,
            @RequestParam (value="facturaId",required=false) Integer facturaId
            ) throws Exception{
        Map<String, Object> model1 = new HashMap<String, Object>();

        FacturaDataDTO facturaDTO = facturaService.getFacturaData(facturaId);
        model1.put("facturaDataDTO",facturaDTO);
//        return new ModelAndView(new ExcelFacturaView("factura.xls"), model1);
//        return new ModelAndView(new ExcelFacturaView("factura.xls"), model1);

        return new ModelAndView("excelFacturaView", "facturaDataDTO", facturaDTO);
    }





}