package com.rfs.controller;


import com.rfs.dtos.FacturaDataDTO;
import com.rfs.repository.factura.BillSenderDetailRepository;
import com.rfs.service.FacturaService;
import com.rfs.service.NotaCreditoService;
import com.rfs.service.UsuarioService;
import com.rfs.view.PdfFacturaView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path="/api/export/pdf")
public class FacturaPdfExportController {

    @Autowired
    FacturaService facturaService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UsuarioService usuarioServicio;

    @Autowired
    private BillSenderDetailRepository billSenderDetailRepository;

    @Autowired
    private NotaCreditoService notaCreditoService;

    /**
     * Handle request to download an Excel document
     */
    @RequestMapping(value = "/factura", method = RequestMethod.GET)
    public ModelAndView downloadFacturas(
            @RequestParam (value="facturaId",required=false) Integer facturaId
            ) throws Exception{
        Map<String, Object> model1 = new HashMap<String, Object>();
        FacturaDataDTO facturaDTO = facturaService.getFacturaData(facturaId);
        model1.put("facturaDataDTO",facturaDTO);
        String[] data = facturaService.getFacturaElectronicaData(facturaId, facturaDTO.getEnviadaHacienda());
        model1.put("clave",data[0]);
        model1.put("numeroFacturaElectronica",data[1]);
        model1.put("empresa",usuarioServicio.getCurrentLoggedUser().getEmpresa());

        PdfFacturaView view = new PdfFacturaView();

        view.setApplicationContext(applicationContext);

        return new ModelAndView(view, model1);
    }





}