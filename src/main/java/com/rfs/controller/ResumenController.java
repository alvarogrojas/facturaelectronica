package com.rfs.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/api/resumen")
public class ResumenController {

//    @Autowired
//    private ReciboService reciboService;
//
//    @Autowired
//    private TicaService ticaService;
//
//    @Autowired
//    private FacturaService facturaService;
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping(path="/tramite-encargados/list")
//    public @ResponseBody
//    ResumenTramiteEncargadoDTO getTramitesList(
//            @RequestParam(value="encargadoId",required=false) Integer encargadoId,
//            @RequestParam (value="clienteId",required=false) Integer clienteId,
//            @RequestParam (value="fechaInicio",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
//                    Date fechaInicio,
//            @RequestParam (value="fechaFinal",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal,
//            @RequestParam (value="cargados",required=true) Boolean cargados,
//            @RequestParam Integer pageNumber,
//            @RequestParam Integer pageSize) {
//        //return reciboService.getEncargadosTramites(encargadoId,clienteId,fechaInicio, fechaFinal, cargados,pageNumber,pageSize);
//        return new ResumenTramiteEncargadoDTO();
//    }
//
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping(path="/tica-encargados/list")
//    public @ResponseBody
//    ResumenTramiteEncargadoDTO getTicaEncargadosList(
//            @RequestParam(value="encargadoId",required=false) Integer encargadoId,
//            @RequestParam (value="regimenId",required=false) Integer regimenId,
//            @RequestParam (value="fechaInicio",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
//                    Date fechaInicio,
//            @RequestParam (value="fechaFinal",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal,
//            @RequestParam (value="cargados",required=true) Boolean cargados,
//            @RequestParam Integer pageNumber,
//            @RequestParam Integer pageSize) {
//        return ticaService.getTicaEncargados(encargadoId,regimenId,fechaInicio, fechaFinal, cargados,pageNumber,pageSize);
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping(path="/factura/list")
//    public @ResponseBody
//    ResumenTramiteEncargadoDTO getFacturaList(
//            @RequestParam (value="clienteId",required=false) Integer clienteId,
//            @RequestParam (value="tipoId",required=false) Integer tipoId,
//            @RequestParam (value="fechaInicio",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
//                    Date fechaInicio,
//            @RequestParam (value="fechaFinal",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal,
//            @RequestParam (value="cargados",required=true) Boolean cargados,
//            @RequestParam Integer pageNumber,
//            @RequestParam Integer pageSize) {
//        return facturaService.getFacturasTramites(clienteId,tipoId, fechaInicio, fechaFinal, cargados,pageNumber,pageSize);
//    }
}
