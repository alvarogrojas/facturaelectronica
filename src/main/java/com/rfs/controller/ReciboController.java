package com.rfs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/api/recibo")
public class ReciboController {

//    @Autowired
//    private ReciboService reciboService;
//
//    @Autowired
//    private ReciboRepository reciboRepository;
//
////    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
//    @PostMapping(path="/create") //
//    public @ResponseBody Recibo createRecibo (@RequestBody Recibo recibo) {
//        Recibo r = reciboService.save(recibo);
//        return r;
//    }
//
//    @PostMapping(path="/clonar") //
//    public @ResponseBody ReciboDTO clonarRecibo (@RequestBody ReciboDTO recibo) {
//        return reciboService.clonar(recibo);
//
//    }
//
////    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
//    @PutMapping(path="/facturado") //
//    public @ResponseBody
//                        FacturarResult updateFacturado(@RequestBody Recibo recibo) {
//        return reciboService.updateFacturado(recibo.getId());
//
//    }
//
//    @PutMapping(path="/update-justificacion")
//    public @ResponseBody
//    String deleteTarifa(
//            @RequestParam Integer id,
//            @RequestParam String estado
//                        ) {
//        reciboService.actualizarJustificacion(id, estado);
//        return "OK";
//    }
//
////    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
//	@GetMapping(path="/data")
//	public @ResponseBody
//                ReciboDataDTO getReciboData(@RequestParam(required = false) Integer id) {
//        System.out.println(" EN GET DATA");
//		return reciboService.reciboDataList(id);
//	}
//
//    @PutMapping(path="/data/facturacion") //
//    public @ResponseBody
//    ReciboFacturacionDTO updateReciboFacturado(@RequestBody ReciboFacturacionDTO recibo) {
//        return reciboService.updateReciboFacturacion(recibo);
//
//    }
//
//    @GetMapping(path="/facturacion")
//    public @ResponseBody
//    ReciboFacturacionDTO getReciboFacturacion(@RequestParam(required = false) Integer id) {
//        System.out.println(" EN GET DATA");
//        return reciboService.getReciboFacturacion(id);
//    }
//
////    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
//    @GetMapping(path="/data/corelacionados")
//    public @ResponseBody List<CorelacionadosDTO> getCorelacionados(@RequestParam Integer clienteId,
//                                                                     @RequestParam String estado) {
//        return reciboService.getCorelacionados(clienteId,estado);
//    }
//
////    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
//    @GetMapping(path="/data/list")
//    public @ResponseBody
//    RecibosDataTableDTO getReciboList(@RequestParam Integer encargadoId,
//                                                       @RequestParam String estado,
//                                                       @RequestParam Integer pageNumber,
//                                                       @RequestParam Integer pageSize,
//                                                       @RequestParam String filter) {
//        return reciboService.getRecibos(encargadoId,estado, pageNumber, pageSize,filter);
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COORDINADOR')")
//    @GetMapping(path="/data/consultas")
//    public @ResponseBody
//                        RecibosDataTableDTO getReciboList(
//                                                         @RequestParam (value="encargadoId",required=false) Integer encargadoId,
//                                                         @RequestParam (value="clienteId",required=false) Integer clienteId,
//                                                          @RequestParam (value="estado",required=true) String estado,
//                                                          @RequestParam (value="fechaInicio",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaInicio,
//                                                          @RequestParam (value="fechaFinal",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fechaFinal,
//                                                          @RequestParam (value="encargadosCargado",required=true) Boolean cargarEncargados,
//                                                          @RequestParam Integer pageNumber,
//                                                          @RequestParam Integer pageSize) {
//        return reciboService.getRecibos(encargadoId, clienteId, estado, fechaInicio, fechaFinal, cargarEncargados, pageNumber, pageSize);
//    }
//
//
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COORDINADOR')")
//    @GetMapping(path="/data/list/admin")
//    public @ResponseBody
//    RecibosDataTableDTO getReciboListForAdmin(
//                                      @RequestParam String estado,
//                                      @RequestParam Integer pageNumber,
//                                      @RequestParam Integer pageSize,
//                                      @RequestParam String filter) {
//        return reciboService.getRecibos(estado, pageNumber, pageSize,filter);
//    }
}
