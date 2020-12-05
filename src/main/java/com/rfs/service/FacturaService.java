package com.rfs.service;

import com.rfs.domain.*;
import com.rfs.domain.factura.BillSenderDetail;
import com.rfs.dtos.*;
import com.rfs.repository.*;
import com.rfs.repository.factura.BillSenderDetailRepository;
import com.rfs.service.factura.billapp.BillHelper;
import com.rfs.service.factura.billapp.BillManagerService;
import com.rfs.service.factura.billapp.Result;
import com.rfs.service.factura.billapp.impl.BillDataServiceImpl;
import com.rfs.utils.EstadosEnum;
import com.rfs.utils.Helper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by alvaro on 10/17/17.
 */
@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private GlobalDataManager globalDataManager;

    @Autowired
    private BitacoraFacturaService bitacoraFacturaService;

    @Autowired
    private FacturaCorelacionadoRepository facturaCorelacionadoRepository;

    @Autowired
    private UsuarioService usuarioService;

//    @Autowired
//    private ReciboService reciboService;

//    @Autowired
//    private ReciboRepository reciboRepository;


    @Autowired
    private FacturaHuerfanaRepository facturaHuerfanaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MonedaRepository monedaRepository;

    @Autowired
    private TipoCambioRepository tipoCambioRepository;

    @Autowired
    private ServicioCabysRepository servicioCabysRepository;

    @Autowired
    private TercerosRepository tercerosRepository;

    @Autowired
    private ServicioCorreduriaAduaneraRepository servicioCorreduriaAduaneraRepository;

    @Autowired
    private ImpuestoRepository impuestoRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;

    private Logger logger = Logger.getLogger(FacturaService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BillManagerService billManagerService;

    @Autowired
    private BillDataServiceImpl billDataService;

    @Autowired
    private TipoTramiteRepository tipoTramiteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private BillSenderDetailRepository billSenderDetailRepository;

    @Autowired
    private EmpresaTarifaRepository empresaTarifaRepository;

    private SimpleDateFormat tarifaMesAnoFormat = new SimpleDateFormat("MM-yyyy");

    @Autowired
    private NotaCreditoRepository notaCreditoRepository;

    @Autowired
    private ResultadoEnvioLogRepository resultadoEnvioLogRepository;

    @Autowired
    private TipoActividadEconomicaRepository tipoActividadEconomicaRepository;

    @Autowired
    private TarifaIvaRepository tarifaIvaRepository;

    @Autowired MedidaRepository medidaRepository;

//    @Autowired
//    private ConfirmacionRechazoRepository confirmacionRechazoRepository;


    @Transactional
    public Integer save(FacturaDataDTO data) throws  Exception {
        Factura f = null;
//        synchronized (this) {
            try {
                Integer idEncargado = usuarioService.getCurrentLoggedUserId();
                Boolean isEditing = false;
                data.recalculateDiasCredito();
                Date currentDate = new Date();
                Usuario u = usuarioService.getCurrentLoggedUser();
                manejarAgregarNuevoCliente(data,u);


                if (data.getFacturaId() == null) {
                    f = new Factura();
                    f.setEncargado(u);
                    f.setFacturaIdentity(new FacturaIdentity(0,u.getEmpresa().getId()));
                    initNewFacturaData(f);
                } else {
                    try {
                        f = this.facturaRepository.findByFacturaIdentity(new FacturaIdentity(data.getFacturaId(),u.getEmpresa().getId()));
                        isEditing = true;
                    } catch(Exception e) {
                        e.printStackTrace();
                        logger.error("ERROR recuperando factura " + data.getFacturaId());
                        throw e;
                    }
                }
                if (isEditing) {
                    removeDetalles(f);
                }
                cambiarHoraFactura(data, currentDate);
                BeanUtils.copyProperties(f, data);
                f.setFacturaDetalleDTO(data.getDetallesDTO(),f);

                f.setUltimoCambioPor(idEncargado);
                f.setFechaUltimoCambio(currentDate);
                f = saveFactura(f, isEditing, currentDate);
                if (!isEditing) {
                    this.updateFacturaFee(u.getEmpresa());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error(" ** Error guardando la factura del recibo " + data.getFacturaId() + " : " + e.getMessage());
                throw e;
            } catch (InvocationTargetException e) {
                logger.error(" ** Error guardando la factura del recibo " + data.getFacturaId() + " : " + e.getMessage());

                e.printStackTrace();
                throw e;
            } finally {
               // this.globalDataManager.goBackFacturaId();
            }
//        }
        return f.getFacturaIdentity().getFacturaId();
    }

    private void manejarAgregarNuevoCliente(FacturaDataDTO data, Usuario u) {

        if (!data.getEsClienteNuevo()) {
            //return;

        }
        data.getCliente().setFechaUltimoCambio(new Date());
        data.getCliente().setUltimoCambioId(u.getId());
        data.getCliente().setEstado(Helper.CLIENTE_ACTIVO);
        data.getCliente().setEmpresa(u.getEmpresa());
        Cliente c = this.clienteService.save(data.getCliente());
        data.setCliente(c);
    }

    private Factura saveFactura(Factura f, Boolean isEditing, Date currentDate) throws Exception {
        try {
            f = this.facturaRepository.save(f);

        } catch (Exception e) {
            throw e;
        } finally {
            globalDataManager.goBackFacturaId();
        }
        return f;
    }


    private void cambiarHoraFactura(FacturaDataDTO data, Date currentDate) {
        if (data.getFechaFacturacion()==null) {
            data.setFechaFacturacion(currentDate);
            return;
        }
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(data.getFechaFacturacion());
        cal.set(Calendar.HOUR, currentCal.get(Calendar.HOUR));
        cal.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, currentCal.get(Calendar.SECOND));
        data.setFechaFacturacion(cal.getTime());
    }

    @Transactional
    public Factura save(Factura f, Boolean nueva) {
        Factura current  = null;
        if (!nueva) {
            current = this.facturaRepository.findByFacturaIdentity(f.getFacturaIdentity());
//            current = this.facturaRepository.findById(f.getId());
        } else {
            current = new Factura();
        }
        f = this.facturaRepository.save(f);
        if (!nueva) {
            this.bitacoraFacturaService.saveBitacoraFactura(current, f);
        }
        return f;
    }

    private Factura initNewFacturaData(Factura fObj) {
        Integer currentTopId = null;

        Integer id = globalDataManager.getFacturaNext(fObj.getFacturaIdentity().getEmpresaId());
        System.out.println("ID FACTURA " + id);

        String fNumber = formatFactura(id);
        fObj.getFacturaIdentity().setFacturaId(id);
        fObj.setNumeroFactura(fNumber);
        return fObj;
    }

    public synchronized  Factura getNexFactura() {
        return facturaRepository.findTopByOrderByFechaFacturacionDesc();
    }

    private String formatFactura(Integer id) {
        return id.toString();
    }

    @Transactional
    public FacturarResult revertirFactura(Integer facturaId) {
        FacturarResult result = new FacturarResult();

        return result;
    }

    public FacturaTableDTO getFacturas(Integer clienteId, String estado, String estadoPago, Date fechaInicio, Date fechaFinal, Boolean cargados, Integer pageNumber, Integer pageSize, Integer enviadas) {
        if (fechaFinal==null || fechaInicio==null) {
            Date[] dates = initDateRango(fechaInicio,fechaFinal);
            fechaFinal = dates[1];
            fechaInicio = dates[0];
        }
        Usuario currentLoggedInUser = usuarioService.getCurrentLoggedUser();
        Integer total =  this.facturaRepository.countFacturas(clienteId, estado, estadoPago,fechaInicio, fechaFinal, enviadas, currentLoggedInUser.getEmpresa().getId()).intValue();
        List<FacturaRegistroDTO> lr;
        Double totalMonto = 0d;
        if (total>0) {
            lr = this.facturaRepository.getFacturas(clienteId, estado, estadoPago, fechaInicio, fechaFinal, pageNumber, pageSize, enviadas,currentLoggedInUser.getEmpresa().getId());
            totalMonto = this.facturaRepository.sumTotalesFacturas(clienteId, estado, estadoPago,fechaInicio, fechaFinal, pageNumber, pageSize, enviadas,currentLoggedInUser.getEmpresa().getId());
        } else {
            lr = new ArrayList<>();
        }

        FacturaTableDTO result = null;
        if (pageSize!=null) {
            result = initFacturaDataTable(pageSize, lr, total);
        }
        else {
            result = initFacturaDataTable(lr);
        }
        result.setMontoTotal(totalMonto);

        if (!cargados) {
//            result.setClientes(this.clienteRepository.findAll());
            result.setClientes(this.clienteRepository.findByEmpresaIdOrderByNombre(currentLoggedInUser.getEmpresa().getId()));
            result.setEncargados(this.usuarioRepository.findByEstado("Activo"));

        }
        result.setFechaFinal(fechaFinal);
        result.setFechaInicio(fechaInicio);

        return result;
    }

    public List<FacturaRegistroDTO> getFacturasByFechas(Date fechaInicio, Date fechaFinal, Integer enviadas) {
        logger.debug("VA A OBTENER LAS FACTURAS A ENVIAR DESDE " + fechaInicio + " HASTA " + fechaFinal + " ENVIADAS?: " + enviadas + " EMPRESA ID " + usuarioService.getCurrentLoggedUser().getEmpresa().getId());
        List<FacturaRegistroDTO> lf = this.facturaRepository.getFacturaFiltrados(fechaInicio, fechaFinal, enviadas, usuarioService.getCurrentLoggedUser().getEmpresa().getId(), BillHelper.INGRESADA);
        logger.debug("TOTAL A ENVIAR " + lf.size());
        return lf;

    }

    public List<FacturaDataDTO> getFacturaDataDTO(Date fechaInicio, Date fechaHasta) {
        return getFacturaDataDTO(fechaInicio, fechaHasta, null);
    }

    public List<FacturaDataDTO> getFacturaDataDTO(Date fechaInicio, Date fechaHasta, Integer enviadaHacienda) {
        List<FacturaRegistroDTO> ld = getFacturasByFechas(fechaInicio,fechaHasta, enviadaHacienda);
        List<FacturaDataDTO> result = new ArrayList<>();
        FacturaDataDTO current;
        if (ld==null || ld.size() <= 0 ) {

            return result;
        }
        current = null;
        for (FacturaRegistroDTO c: ld) {
            try {
                current = getFacturaData(c.getFacturaId());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            if (current!=null) {
                result.add(current);
            }
        }
        return result;
    }

    public FacturaDataDTO getFacturaData(Integer id) {
        FacturaDataDTO result = null;
        boolean estaEditando = false;
        Usuario u = this.usuarioService.getCurrentLoggedUser();
        if (id==null) {
            if (!isIntoFee()) {
                return new FacturaDataDTO(true);
            }
            result = new FacturaDataDTO();
            result.setEncargado(u);
        } else  {
            estaEditando = true;
            result = this.facturaRepository.findByFacturaIdentityToFacturaDTO(id,u.getEmpresa().getId());
            if (result.getEncargadoId()!=null)
                result.setEncargado(this.usuarioRepository.findById(result.getEncargadoId()));
            else {
                result.setEncargado(this.usuarioService.getCurrentLoggedUser());
            }
        }
        result.setClientes(this.clienteRepository.findByEstadoAndEmpresaIdOrderByNombre("ACTIVO",u.getEmpresa().getId()));
        if (!estaEditando) {
            result.initPorcentajeComision();
        }
        result.setMonedas(this.tipoCambioRepository.findByEmpresaId(u.getEmpresa().getId()));

        if (!estaEditando) {
            result.setTipoCambio(result.getMonedas().get(0));
            result.setTipoCambioMonto(result.getMonedas().get(1).getVenta());
        }
        result.setTipoActividadEconomicas(this.tipoActividadEconomicaRepository.findByEmpresaId(u.getEmpresa().getId()));

        if (!estaEditando) {
            if(result.getTipoActividadEconomicas()!=null && result.getTipoActividadEconomicas().size() > 0)
                result.setTipoActividadEconomica(result.getTipoActividadEconomicas().get(0));
        }
        result.setTarifaIvas(this.tarifaIvaRepository.findAll());

        //Obtiene las medidas
        result.setMedidas(this.medidaRepository.findAll());

        result.setServicios(this.servicioCabysRepository.findByEmpresaIdOrderByDescripcion(u.getEmpresa().getId()));

//        if (!estaEditando) {
//            result.setTipoActividadEconomica(result.getTipoActividadEconomicas().get(0));
//        }

        if (id!=null) {
            result.setNotaCreditoStatusDTOS(this.notaCreditoRepository.findByFacturaIdForStatus(id, u.getEmpresa().getId()));
        }



        return result;
    }

    public FacturaDataDTO getFacturaToExport(Integer reciboId, Integer facturaId) {
        Usuario u = usuarioService.getCurrentLoggedUser();
        FacturaDataDTO dto = this.facturaRepository.findByFacturaIdentityToFacturaDTO(facturaId,u.getEmpresa().getId());
        return dto;
    }



    public ResumenTramiteEncargadoDTO getFacturasTramites(Integer clienteId, Integer tipoId, Date fechaInicio, Date fechaFinal, Boolean cargados, Integer pageNumber, Integer pageSize) {
        if (fechaFinal==null || fechaInicio==null) {
            Date[] dates = initDateRango(fechaInicio,fechaFinal);
            fechaFinal = dates[1];
            fechaInicio = dates[0];
        }
        ResumenTramiteEncargadoDTO result = new ResumenTramiteEncargadoDTO();
        initTramites(tipoId,clienteId,fechaInicio,fechaFinal, pageNumber, pageSize, result);

        if (!cargados) {
            result.setClientes(this.clienteRepository.findAll());
            result.setTipos(this.tipoTramiteRepository.findAll());

        }

        result.setFechaFinal(fechaFinal);
        result.setFechaInicio(fechaInicio);

        return result;
    }

    public String changeFacturaEstado(Integer id, String estadoPago) {
        Factura f = this.facturaRepository.getOne(new FacturaIdentity(id,this.usuarioService.getCurrentLoggedUser().getEmpresa().getId()));
        f.setEstadoPago(estadoPago);
        f.setFechaUltimoCambio(new Date());
        f.setUltimoCambioPor(usuarioService.getCurrentLoggedUserId());
        this.facturaRepository.save(f);
        return "OK";
    }

    private void setFacturaHuerfana(Factura current) {
        FacturaHuerfana h = new FacturaHuerfana();
        h.setFacturaId(current.getFacturaIdentity().getFacturaId());
        this.facturaHuerfanaRepository.save(h);
    }

    private void cambiarEstadoRecibosNotFacturados(List<Integer> facturasRecibosCorrelacionados, Integer currentFacturaId, Date currentDate) {
        if (facturasRecibosCorrelacionados==null) {
            facturasRecibosCorrelacionados = new ArrayList<>();
        }
        facturasRecibosCorrelacionados.add(currentFacturaId);
        List<FacturaReciboDTO> frl;
        List<FacturaReciboDTO> frlCorelacionados;
        Recibo r;
        for (Integer reciboId: facturasRecibosCorrelacionados) {
            frl = facturaRepository.findFacturasByReciboAsc(reciboId);
            frlCorelacionados = facturaCorelacionadoRepository.findFacturasByReciboAsc(reciboId);
            if ((frl==null || frl.size() == 0) && (frlCorelacionados==null || frlCorelacionados.size()==0)) {
                updateRecibo(currentDate, reciboId);
            } else {
                System.out.println("NO PUEDE SER ACTUALIZADO EL RECIBO a PENDIENTE");
            }
        }
    }

    private void updateRecibo(Date currentDate, Integer reciboId) {
//        Recibo r;
//        r = reciboRepository.findById(reciboId);
//        if (r!=null && r.getId()!=null) {
//            updateReciboEstado(r, currentDate, EstadosEnum.PENDIENTE);
//        }
    }

    private void updateReciboEstado(Recibo reciboObj, Date currentDate, EstadosEnum estadoNuevo) {



    }

    private List<Integer> deleteFacturaCorrelacionados(Factura current) {
        List<Integer> corelacionados = null;

        List<FacturaCorelacionado> fcbs= this.facturaCorelacionadoRepository.findByFacturaId(current.getFacturaIdentity().getFacturaId());
        for (FacturaCorelacionado c:fcbs) {
            facturaCorelacionadoRepository.delete(c.getId());
            if (corelacionados==null)
                corelacionados = new ArrayList<>();
            corelacionados.add(c.getReciboId());
        }
        return corelacionados;
    }

    private void revertirFactura(Date currentDay, Factura prevFactura, Factura current, Usuario u) {
        try {
            removeDetalles(current);

            BeanUtils.copyProperties(prevFactura, current);
            current.setEncargado(null);

            current.setFechaUltimoCambio(currentDay);

            current.setUltimoCambioPor(u.getId());

            //current.setFacturaDetalle(null,null);
            this.facturaRepository.save(current);

            ;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void removeDetalles(Factura current) {

        removeDetallesImpuestos(current);
        removeDetallesTerceros(current);
        removeDetallesServicios(current);
    }

    private void removeDetallesServicios(Factura current) {

    }

    private void removeDetallesTerceros(Factura current) {

    }

    private void removeDetallesImpuestos(Factura current) {
        if (current==null || current.getFacturaDetalle()==null || current.getFacturaDetalle().size()==0) {
            return;
        }
        this.facturaDetalleRepository.delete(current.getFacturaDetalle());
//        for (FacturaDetalle fsd: current.getFacturaDetalle()) {
//            this.facturaDetalleRepository.delete(fsd);
//        }
    }

    private FacturaTableDTO initFacturaDataTable(Integer pageSize, List<FacturaRegistroDTO> ticas, Integer total) {
        FacturaTableDTO result = new FacturaTableDTO();

        result.setFacturas(ticas);
        Integer totalPages = 1;
        result.setTotal(total);
        if (total >0) {
            totalPages = total / pageSize;
        }
        result.setPagesTotal(totalPages);
        return result;
    }

    private FacturaTableDTO initFacturaDataTable(List<FacturaRegistroDTO> t) {
        FacturaTableDTO result = new FacturaTableDTO();

        result.setFacturas(t);
        return result;
    }

    private Date[] initDateRango(Date fechaInicio, Date fechaFinal) {

        if (fechaInicio==null || fechaFinal==null) {
            fechaFinal = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaFinal);

            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

            fechaInicio = cal.getTime();

        }
        return new Date[]{fechaInicio,fechaFinal};
    }

    private void initTramites(Integer tramiteId, Integer clienteId, Date fechaInicio, Date fechaFinal,Integer pageNumber, Integer pageSize, ResumenTramiteEncargadoDTO resumen) {

    }

    private void initMonto(ResumenTramiteEncargadoDTO resumen) {

        if (resumen==null) {
            return;
        }

        if (resumen.getTramites()==null || resumen.getTramites().size()==0) {
            resumen.setMonto(0d);
        }
        Double montoTotal = 0d;
        for (TramiteEncargadoDTO t: resumen.getTramites()) {
            if (t.getTotal()!=null && t.getTotal()>=0) {
                montoTotal = montoTotal + t.getTotal();
            }
        }
        resumen.setMonto(montoTotal);
    }

    private void initTotalPages(Integer pageSize, ResumenTramiteEncargadoDTO resumen, List<TramiteEncargadoDTO> l) {
        if (l!=null) {
            resumen.setTotal(l.size());
            Integer totalPages = 1;

            if (resumen.getTotal() >0) {
                totalPages = resumen.getTotal() / pageSize;
            }
            resumen.setPagesTotal(totalPages);
        }
    }


    public String[] getFacturaElectronicaData(Integer facturaId, Integer enviada) {
        String[] result = new String[] {"",""};
        Usuario u = this.usuarioService.getCurrentLoggedUser();
        Factura f = facturaRepository.findByFacturaIdentity(new FacturaIdentity(facturaId, u.getEmpresa().getId()));
        if (!StringUtils.isEmpty(f.getClave()) && !StringUtils.isEmpty(f.getConsecutivo())) {
            result[1] = f.getConsecutivo();
            result[0] = f.getClave();
            return result;
        }
        if (enviada==0) {
            return result;
        }
        try {

            BillSenderDetail bsd = this.billManagerService.getBillSenderDetail(facturaId);
            if (bsd!=null) {
                result[1] = bsd.getConsecutivo();
                result[0] = bsd.getClave();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public boolean isIntoFee() {
        Empresa e = usuarioService.getCurrentLoggedUser().getEmpresa();
        String mesAno = tarifaMesAnoFormat.format(new Date());
        EmpresaTarifa empresaTarifa = null;
        boolean isIntoFee = false;
        try {
            empresaTarifa = this.empresaTarifaRepository.findByEmpresaIdAndMesAno(e.getId(), mesAno);
        } catch (Exception e1) {
            e1.printStackTrace();
            logger.error(e1.getMessage());
        }
        if (empresaTarifa==null) {
            this.empresaTarifaRepository.save(new EmpresaTarifa(null,e.getId(),mesAno,0));
            isIntoFee = true;

        } else {
            if (empresaTarifa.getCantidad() < e.getCantidad()) {
                isIntoFee = true;
            }
        }
        return isIntoFee;
    }

    private void updateFacturaFee(Empresa e) {
        //Empresa e = usuarioService.getCurrentLoggedUser().getEmpresa();
        String mesAno = tarifaMesAnoFormat.format(new Date());
        EmpresaTarifa empresaTarifa = null;
        boolean isIntoFee = false;
        try {
            empresaTarifa = this.empresaTarifaRepository.findByEmpresaIdAndMesAno(e.getId(), mesAno);
        } catch (Exception e1) {
            e1.printStackTrace();
            logger.error(e1.getMessage());
        }
        if (empresaTarifa!=null) {
            empresaTarifa.setCantidad(empresaTarifa.getCantidad() + 1);
            this.empresaTarifaRepository.save(empresaTarifa);

        } else {
            this.empresaTarifaRepository.save(new EmpresaTarifa(null,e.getId(),mesAno,1));
        }
    }

    public FacturaTableDTO getFacturasFiltrados(Integer facturaId, Date fechaInicio, Date fechaFinal, Boolean cargados, Integer pageNumber, Integer pageSize) {
        if (fechaFinal==null || fechaInicio==null) {
            Date[] dates = initDateRango(fechaInicio,fechaFinal);
            fechaFinal = dates[1];
            fechaInicio = dates[0];
        }
        Empresa e = usuarioService.getCurrentLoggedUser().getEmpresa();
        Integer total =  this.facturaRepository.countFacturaFiltrados(facturaId, fechaInicio, fechaFinal, e.getId()).intValue();
        List<FacturaRegistroDTO> lr = this.facturaRepository.getFacturaFiltrados(facturaId, fechaInicio, fechaFinal, e.getId());

        FacturaTableDTO result = null;
        if (pageSize!=null) {
            result = initFacturaDataTable(pageSize, lr, total);
        }
        else {
            result = initFacturaDataTable(lr);
        }

        if (!cargados) {
//            result.setClientes(this.clienteRepository.findAll());
            //result.setRecibos(this.reciboRepository.findByEstadoOrderByFechaDesc(EstadosEnum.FINALIZADO.toString()));

        }
        result.setFechaInicio(fechaInicio);
        result.setFechaFinal(fechaFinal);
        return result;
    }

    public RespuestaEnvioDTO getRespuestasHacienda(Integer facturaId) {
        RespuestaEnvioDTO dto = new RespuestaEnvioDTO();
        dto.setFacturaId(facturaId);
        Usuario u = this.usuarioService.getCurrentLoggedUser();
        List<RespuestaHaciendaDTO> lis = this.billSenderDetailRepository.getEnviosHaciendaPorFacturaId(facturaId, u.getEmpresa().getId());
        dto.setEnvios(lis);
        return dto;
    }

    public Result getStatusHacienda(Integer id) {

        Result mensaje = billManagerService.getStatusHacienda(id);
        System.out.println("MENSAJE " + (mensaje.getResultStr()!=null?mensaje.getResultStr():""));
        return mensaje;

    }

    public Result checkHaciendaStatus(Integer id) {

        Result mensaje = billManagerService.obtenerMensajeHacienda(id);
        System.out.println("MENSAJE " + (mensaje.getResultStr()!=null?mensaje.getResultStr():""));
        return mensaje;

    }

    public Result checkHaciendaNCStatus(Integer id) {

        Result mensaje = billManagerService.obtenerMensajeHaciendaNC(id);
        System.out.println("MENSAJE " + (mensaje.getResultStr()!=null?mensaje.getResultStr():""));
        return mensaje;

    }

    public String getXmlFileName(Integer id) {
        Usuario u = this.usuarioService.getCurrentLoggedUser();
        List<BillSenderDetail> l = this.billSenderDetailRepository.findByBillIdAndTipoAndStatusAndEmpresaIdOrderByDateSentDesc(id,"FE",BillHelper.RESPUESTA_ACEPTADA, u.getEmpresa().getId());
        if (l!=null && l.size()>0) {
            BillSenderDetail bsd = l.get(0);
            return bsd.getPath() +   "signed" + bsd.getClave() + ".xml";

        }
        return null;
    }
}
