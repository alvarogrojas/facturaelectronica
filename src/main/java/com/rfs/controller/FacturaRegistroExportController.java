package com.rfs.controller;


import com.rfs.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/api/export")
public class FacturaRegistroExportController {

    @Autowired
    FacturaService facturaService;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Handle request to download an Excel document
     */
//    @RequestMapping(value = "/facturacion-registro", method = RequestMethod.GET)
//    public ModelAndView downloadFacturas(
//            @RequestParam (value="fechaInicio",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
//                    Date fechaInicio,
//            @RequestParam (value="fechaFinal",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal) {
//        Map<String, Object> model1 = new HashMap<String, Object>();
//        List<FacturaRegistroDTO> facturaTableDTO = facturaService.getFacturasByFechas(fechaInicio, fechaFinal,null);
//        model1.put("facturaRegistroDTO",facturaTableDTO);
//        ExcelFacturacionRegistroView view = new ExcelFacturacionRegistroView();
//
//        //view.setUrl("factura");
//        view.setApplicationContext(applicationContext);
//        return new ModelAndView(view, model1);
//    }


}