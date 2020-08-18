package com.rfs.service;


/**
 * Created by alvaro on 10/17/17.
 */
//@Service
public class ReciboService {

//    private static final String PRE_RECIBO = "R";
//
//    @Autowired
//    private AduanaRepository aduanaRepository;
//
//    @Autowired
//    private TipoTramiteRepository tipoTramiteRepository;
//
//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    @Autowired
//    private EstadoRepository estadoRepository;
//
//    @Autowired
//    private ReciboRepository reciboRepository;
//
//    @Autowired
//    private UsuarioService usuarioService;
//
//    @Autowired
//    private FacturaService facturaService;
//
//    @Autowired
//    private FacturaRepository facturaRepository;
//
//    @Autowired
//    private GlobalDataManager globalDataManager;
//
//    @Autowired
//    private BitacoraReciboService bitacoraReciboService;
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @Autowired
//    private FacturaCorelacionadoRepository facturaCorelacionadoRepository;
//    private Logger log = Logger.getLogger(ReciboService.class);
//
//
//    public List<CorelacionadosDTO> getCorelacionados(Integer clienteId, String estado) {
//        CorelacionadosDTO crdto = new CorelacionadosDTO();
//        crdto.setRecibo("NA");
//        crdto.setId(0);
//        List<CorelacionadosDTO> dtos = new ArrayList<>();
//        dtos.add(crdto);
//
//        getCorelacionados(clienteId, estado, dtos);
//
//        return dtos;
//    }
//
//
//
//    public ReciboDataDTO reciboDataList(Integer id) {
//        ReciboDataDTO dto = new ReciboDataDTO();
//        //boolean found = false;
//        dto.setRecibo(reciboRepository.findById(id)); //TODO check if ID is null
//        Iterable<Aduana> aduanas = aduanaRepository.findAll();
//        List<Cliente> clientes = clienteRepository.findByEstadoOrderByNombre("ACTIVO");
//        if (dto.getRecibo()!= null && dto.getRecibo().getCliente()!=null && dto.getRecibo().getCliente().getId()!=null) {
//            if (!clientes.contains(dto.getRecibo().getCliente())) {
//                clientes.add(dto.getRecibo().getCliente());
//            }
//
//        }
//        Iterable<TipoTramite> tipoTramites = tipoTramiteRepository.findAll();
//        Iterable<Estado> estados = estadoRepository.findAll();
//
//        Usuario u = this.usuarioService.getCurrentLoggedUser();
//        if (Helper.isAdminOrCoordinador(u.getRoles())) {
//            List<Usuario> usuarios = usuarioRepository.findByEstado("ACTIVO");
//            dto.setUsuarios(usuarios);
//        }
//        dto.setAduanas(aduanas);
//        dto.setClientes(clientes);
//        dto.setTiposTramites(tipoTramites);
//        dto.setEstados(estados);
//        loadCorelacionados(dto, clientes);
//        loadFacturas(id, dto.getRecibo(), dto.getFacturas());
//
//        return dto;
//    }
//
//    @Transactional
//    public Recibo save(Recibo reciboObj) {
//        Date currentDate = new Date();
//        Recibo rdb = null;
//        Recibo lastRecibo = new Recibo();
//        if (Strings.isNullOrEmpty(reciboObj.getRecibo())) {
//            initNewReciboData(reciboObj, currentDate);
//        } else {
//            rdb = this.reciboRepository.findById(reciboObj.getId());
//            setReciboNewData(reciboObj, currentDate, rdb, lastRecibo);
//        }
//        reciboObj.setFechaUltimoCambio(currentDate);
//        reciboObj.setUltimoCambioId(usuarioService.getCurrentLoggedUserId());
//        reciboRepository.save(reciboObj);
//        if (rdb!=null) {
//            this.bitacoraReciboService.saveBitaraRecibo(lastRecibo,reciboObj);;
//        }
//        return reciboObj;
//
//    }
//
//
//
//    public RecibosDataTableDTO getRecibos(Integer encargadoId, Integer clienteId ,String estado, Date fechaInicio, Date fechaFinal, Boolean cargados, Integer pageNumber, Integer pageSize) {
//        System.out.println("fechaInicio " + fechaInicio + " fechaFinal " + fechaFinal + " encargadoId " + encargadoId);
//
//        Integer total =  this.reciboRepository.countRecibosFiltrados(encargadoId, clienteId, estado,fechaInicio,fechaFinal, pageNumber, pageSize).intValue();
//        List<ReciboDTO> lr = this.reciboRepository.getRecibosFiltrados(encargadoId, clienteId, estado, fechaInicio, fechaFinal, pageNumber, pageSize);
//        List<FacturaReciboDTO> facturas;
//        ReciboDTO dto = null;
//        for (ReciboDTO r: lr) {
//            facturas = facturaRepository.findFacturasByReciboAsc(r.getId());
//            r.addFacturas(facturas);
//            r.addFacturas(facturaCorelacionadoRepository.findFacturasByReciboAsc(r.getId()));
//
//            //lr.add(dto);
//        }
//        RecibosDataTableDTO result = null;
//        if (pageSize!=null) {
//            result = initRecibosDataTable(pageSize, lr, total);
//        } else {
//            result = initRecibosDataTable(lr);
//        }
//
//        if (!cargados) {
//            result.setEncargados(this.usuarioRepository.findEncargadosByEstadoActivo());
//            result.setClientes(this.clienteRepository.findAll());
//        }
//
//        return result;
//
//    }
//
//    public ResumenTramiteEncargadoDTO getEncargadosTramites(Integer encargadoId, Integer clienteId, Date fechaInicio, Date fechaFinal, Boolean cargados, Integer pageNumber, Integer pageSize) {
//        if (fechaFinal==null || fechaInicio==null) {
//            Date[] dates = initDateRango(fechaInicio,fechaFinal);
//            fechaFinal = dates[1];
//            fechaInicio = dates[0];
//        }
//        ResumenTramiteEncargadoDTO result = new ResumenTramiteEncargadoDTO();
//        initTramitesEncargados(encargadoId,clienteId,fechaInicio,fechaFinal, pageNumber, pageSize, result);
//
//        if (!cargados) {
//            result.setClientes(this.clienteRepository.findAll());
//            result.setEncargados(this.usuarioRepository.findByEstado("ACTIVO"));
//
//        }
//
//        result.setFechaFinal(fechaFinal);
//        result.setFechaInicio(fechaInicio);
//
//        return result;
//    }
//
//    private Date[] initDateRango(Date fechaInicio, Date fechaFinal) {
//
//        if (fechaInicio==null || fechaFinal==null) {
//            fechaFinal = new Date();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(fechaFinal);
//
//            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//
//            fechaInicio = cal.getTime();
//
//        }
//        return new Date[]{fechaInicio,fechaFinal};
//    }
//
//    private void initTramitesEncargados(Integer encargadoId, Integer clienteId, Date fechaInicio, Date fechaFinal,Integer pageNumber, Integer pageSize, ResumenTramiteEncargadoDTO resumen) {
//        PageRequest request = new PageRequest(pageNumber,pageSize);
//        List<TramiteEncargadoDTO> l = null;
//
//        if ((encargadoId!=null && encargadoId!=0) && (clienteId!=null && clienteId!=0)) {
//            resumen.setTramites(this.reciboRepository.findTramitesByEncargados(encargadoId, fechaInicio,fechaFinal,clienteId,request));
//            l = this.reciboRepository.findTramitesByEncargados(encargadoId, fechaInicio,fechaFinal,clienteId);
//        }
//        else if ((encargadoId==null || encargadoId==0) && (clienteId==null || clienteId==0)) {
//            resumen.setTramites(this.reciboRepository.findTramitesByEncargados(fechaInicio,fechaFinal,request));
//            l = this.reciboRepository.findTramitesByEncargados(fechaInicio,fechaFinal);
//        } else if ((encargadoId==null || encargadoId==0)) {
//            resumen.setTramites(this.reciboRepository.findTramitesByEncargadosClientes(clienteId,fechaInicio,fechaFinal,request));
//            l = this.reciboRepository.findTramitesByEncargadosClientes(clienteId,fechaInicio,fechaFinal);
//        } else if (encargadoId!=null && encargadoId!=0) {
//            resumen.setTramites(this.reciboRepository.findTramitesByEncargados(encargadoId,fechaInicio,fechaFinal,request));
//            l = this.reciboRepository.findTramitesByEncargados(encargadoId,fechaInicio,fechaFinal);
//        } else {
//            l = new ArrayList<>();
//        }
//        initTotalPages(pageSize, resumen, l);
//    }
//
//    public RecibosDataTableDTO getRecibos(Integer encargadoId, String estado, Integer pageNumber, Integer pageSize, String filter) {
//        List<ReciboDTO> recibos =  null;
//        Integer total = 0;
//        Usuario u = this.usuarioService.getCurrentLoggedUser();
//        if (Strings.isNullOrEmpty(filter)) {
//            recibos = this.reciboRepository.findByEncargadoIdAndEstadoOrderByFechaAsc1(encargadoId, estado, new PageRequest(pageNumber, pageSize));
//            total = this.reciboRepository.countByEncargadoIdAndEstado(encargadoId, estado);
//
//        } else {
//            recibos = this.reciboRepository.findByEncargadoIdAndEstadoOrderWithFilterByFechaAsc(encargadoId, estado, filter, new PageRequest(pageNumber, pageSize));
//            total = this.reciboRepository.getCountByEncargadoIdAndEstadoWithFilter(encargadoId, estado, filter);
//
//        }
//        RecibosDataTableDTO result = initRecibosDataTable(pageSize, recibos, total);
//
//        return result;
//
//    }
//
//
//    public RecibosDataTableDTO getRecibos(String estado, Integer pageNumber, Integer pageSize, String filter) {
//        List<ReciboDTO> recibos =  null;
//        Integer total = 0;
//        Usuario u = this.usuarioService.getCurrentLoggedUser();
//        if (Strings.isNullOrEmpty(filter)) {
//            recibos = this.reciboRepository.findByEstadoOrderByFechaAsc(estado, new PageRequest(pageNumber, pageSize));
//            total = this.reciboRepository.countByEstado(estado);
//
//        } else {
//            recibos = this.reciboRepository.findByEstadoOrderWithFilterByFechaAsc(estado, filter, new PageRequest(pageNumber, pageSize));
//            total = this.reciboRepository.getCountByEstadoWithFilter( estado, filter);
//        }
//
//        RecibosDataTableDTO result = initRecibosDataTable(pageSize, recibos, total);
//
//        return result;
//
//    }
//
//    @Transactional
//    public FacturarResult updateFacturado(Integer id) {
////        Date currentDate = new Date();
////        Recibo lastRecibo = new Recibo();
////        Integer idEncargado = usuarioService.getCurrentLoggedUserId();
////        Recibo r = this.reciboRepository.findById(id);
////        updateFacturado(currentDate, lastRecibo, idEncargado, r);
////        //facturar
////        Factura f = facturaService.save(id, idEncargado,currentDate);
////        this.bitacoraReciboService.saveBitaraRecibo(lastRecibo,r);
////
////        updateFacturado(r,f);
//        FacturarResult result = new FacturarResult();
////        result.setFacturaId(f.getNumeroFactura());
////        loadFacturas(id, r, result.getFacturas());
//        return result;
//    }
//
//    @Transactional
//    public FacturarResult updateFacturado(Recibo r,Factura f, Date currentDate) {
//
//        Recibo lastRecibo = new Recibo();
//        Integer idEncargado = usuarioService.getCurrentLoggedUserId();
//
//        updateFacturado(currentDate, lastRecibo, idEncargado, r);
//
//        this.bitacoraReciboService.saveBitaraRecibo(lastRecibo,r);
//
//        updateFacturado(r,f);
//        FacturarResult result = new FacturarResult();
//        result.setFacturaId(f.getNumeroFactura());
//        loadFacturas(r.getId(), r, result.getFacturas());
//        return result;
//    }
//
//    private void buscarCorelacionadoOIncluirlo(ReciboDataDTO dto, List<CorelacionadosDTO> dtos) {
//        boolean found = false;
//        if (dto.getRecibo()!= null && dto.getRecibo().getCorelacionId()!=null) {
//            //if (dto.getRecibo()!= null) {
//            for (CorelacionadosDTO corelacionado: dtos) {
//                if (corelacionado.getId()==dto.getRecibo().getCorelacionId()) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                Recibo r = reciboRepository.findById(dto.getRecibo().getCorelacionId());
//                dtos.add(createCorelacionado(r)); //CORELACIONADO FINALIZADO
//            }
//        }
//    }
//
//    private void createFacturaCorelacionado(Recibo co, Factura f) {
//        FacturaCorelacionado facturaCorelacionado = new FacturaCorelacionado();
//        facturaCorelacionado.setFechaUltimoCambio(f.getFechaUltimoCambio());
//        facturaCorelacionado.setFechaFacturacion(f.getFechaFacturacion());
//        facturaCorelacionado.setEncargadoId(co.getEncargado().getId());
////        facturaCorelacionado.setEncargadoId(co.getEncargadoId());
//
//        facturaCorelacionado.setFacturaId(f.getId());
//        facturaCorelacionado.setReciboId(co.getId());
//        facturaCorelacionado.setUltimoCambioPor(co.getUltimoCambioId());
//
//        this.facturaCorelacionadoRepository.save(facturaCorelacionado);
//    }
//
//    public List<PendientesFacturarDTO> getRecibosPendientesFacturar() {
//        return this.reciboRepository.findPendientesFacturar();
//    }
//
//
//    public synchronized Recibo getNextRecibo() {
//        return reciboRepository.findTopByOrderByIdDesc();
//    }
//
//    private CorelacionadosDTO createCorelacionado(Recibo r) {
//        CorelacionadosDTO crdto = new CorelacionadosDTO();
//        crdto.setRecibo(r.getRecibo());
//        crdto.setId(r.getId());
//        return crdto;
//    }
//
//    private void initTotalPages(Integer pageSize, ResumenTramiteEncargadoDTO resumen, List<TramiteEncargadoDTO> l) {
//        if (l!=null) {
//            resumen.setTotal(l.size());
//            Integer totalPages = 1;
//
//            if (resumen.getTotal() >0) {
//                totalPages = resumen.getTotal() / pageSize;
//            }
//            resumen.setPagesTotal(totalPages);
//        }
//    }
//
//    private String formatRecibo(Integer value) {
//        String valueFormatted = Integer.toString(value);
//        return PRE_RECIBO + valueFormatted;
//    }
//
//    private void getCorelacionados(Integer clienteId, String estado, List<CorelacionadosDTO> dtos) {
//        CorelacionadosDTO crdto;
//        List<Recibo> recibosCorelacionados = reciboRepository.findByClienteId(clienteId);
////        List<Recibo> recibosCorelacionados = reciboRepository.findByClienteIdAndEstado(clienteId, estado);
//        for(Recibo r:recibosCorelacionados) {
//            dtos.add(createCorelacionado(r));
//        }
//    }
//
//    private void initNewReciboData(Recibo reciboObj, Date currentDate) {
//        Integer currentTopId = null;
//        Integer id = globalDataManager.getReciboNext();
//
//        System.out.println("ID RECIBO " + currentTopId);
//        reciboObj.setFecha(currentDate);
//        String reciboNumber = formatRecibo(id);
//        reciboObj.setId(id.intValue());
//        reciboObj.setRecibo(reciboNumber);
//        if (!Strings.isNullOrEmpty(reciboObj.getDua())) {
//            reciboObj.setFechaDua(currentDate);
//        }
//    }
//
//    private void loadFacturas(Integer id, Recibo r, List<FacturaReciboDTO> list) {
//        if (id!=null) {
//            list.addAll(this.facturaRepository.findFacturasByReciboAsc(r.getId()));
//            list.addAll(this.facturaCorelacionadoRepository.findFacturasByReciboAsc(r.getId()));
//        }
//    }
//
//    private void loadCorelacionados(ReciboDataDTO dto, Iterable<Cliente> clientes) {
//        Cliente c = null;
//        if (dto.getRecibo()!=null) {
//            c = dto.getRecibo().getCliente();
//        } else {
//            c = clientes.iterator().next();
//        }
//        List<CorelacionadosDTO> dtos = getCorelacionados(c.getId(), EstadosEnum.PENDIENTE.toString());
//        buscarCorelacionadoOIncluirlo(dto, dtos);
//        dto.setCorelacionados(dtos);
//
//    }
//
//    private RecibosDataTableDTO initRecibosDataTable(Integer pageSize, List<ReciboDTO> recibos, Integer total) {
//        RecibosDataTableDTO result = new RecibosDataTableDTO();
//
//        result.setRecibos(recibos);
//        Integer totalPages = 1;
//        result.setTotal(total);
//        if (total >0) {
//            totalPages = total / pageSize;
//        }
//        result.setPagesTotal(totalPages);
//        return result;
//    }
//
//    private RecibosDataTableDTO initRecibosDataTable(List<ReciboDTO> recibos) {
//        RecibosDataTableDTO result = new RecibosDataTableDTO();
//
//        result.setRecibos(recibos);
//        return result;
//    }
//
//    private void updateFacturado(Recibo r, Factura f) {
//        List<Recibo> corelacionados = this.reciboRepository.findByCorelacionId(r.getId());
//        Recibo anterior ;
//        for (Recibo co: corelacionados) {
//            anterior= new Recibo();
//            updateFacturado(r.getFechaUltimoCambio(),anterior,r.getId(),co);
////            updateFacturado(r.getFechaUltimoCambio(),anterior,r.getEncargadoId(),co);
//            createFacturaCorelacionado(co,f);
//            this.bitacoraReciboService.saveBitaraRecibo(anterior,co);
//        }
//    }
//
//    private void updateFacturado(Date currentDate, Recibo anterior, Integer idEncargado, Recibo paraActualizar) {
//        try {
//            BeanUtils.copyProperties(anterior, paraActualizar);
//
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        paraActualizar.setEstado(EstadosEnum.FINALIZADO.toString());
//        paraActualizar.setEstaFacturado(1);
//        paraActualizar.setFechaUltimoCambio(currentDate);
//        paraActualizar.setFechaFin(currentDate);
//        paraActualizar.setUltimoCambioId(idEncargado);
//        this.reciboRepository.save(paraActualizar);
//    }
//
//    private void setReciboNewData(Recibo reciboObj, Date currentDate, Recibo rdb, Recibo lastRecibo) {
//        try {
//            BeanUtils.copyProperties(lastRecibo,rdb);
//            if (rdb.getDua()!=null && reciboObj.getDua()!=null &&
//                    !rdb.getDua().equals(reciboObj.getDua()) && !Strings.isNullOrEmpty(reciboObj.getDua())) {
//                reciboObj.setFechaDua(currentDate);
//            }
//            reciboObj.setFecha(rdb.getFecha());
//            reciboObj.setFechaFin(rdb.getFechaFin());
//            reciboObj.setTicaEstado(rdb.getTicaEstado());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void actualizarJustificacion(Integer id, String estado) {
//        Recibo r = reciboRepository.findById(id);
//        if (r==null) {
//            return;
//        }
//
//        r.setTicaEstado(estado);
//        Date currentDate = new Date();
//        Usuario u = usuarioService.getCurrentLoggedUser();
//
//        r.setUltimoCambioId(u.getId());
//        r.setFechaUltimoCambio(currentDate);
//
//        this.reciboRepository.save(r);
//
//    }
//
//
//    public ReciboFacturacionDTO getReciboFacturacion(Integer id) {
//
//        ReciboFacturacionDTO result = this.reciboRepository.findReciboByReciboId(id);
//        result.setFacturas(this.facturaRepository.findFacturasByRecibo(result.getCorelacionId()));
//        return result;
//    }
//
//    public ReciboFacturacionDTO updateReciboFacturacion(ReciboFacturacionDTO recibo) {
//        Recibo r = reciboRepository.findById(recibo.getId());
//        Factura f = facturaRepository.findById(recibo.getFacturaId());
//
//        Recibo anterior= new Recibo();
//        updateFacturado(r.getFechaUltimoCambio(),anterior,r.getId(),r);
//        createFacturaCorelacionado(r,f);
//        this.bitacoraReciboService.saveBitaraRecibo(anterior,r);
//        return recibo;
//    }
//
//    @Transactional
//    public ReciboDTO clonar(ReciboDTO recibo) {
//        ReciboDTO result = new ReciboDTO();
//        Recibo r = this.reciboRepository.findById(recibo.getId());
//        Recibo newRecibo = new Recibo();
//        try {
//            Date currentDate = new Date();
//            BeanUtils.copyProperties(newRecibo,r);
//            newRecibo.setId(null);
//            initNewReciboData(newRecibo, currentDate);
//            newRecibo.setDua("");
//            newRecibo.setCorelacionId(r.getId());
//            newRecibo.setFechaUltimoCambio(currentDate);
//            newRecibo.setUltimoCambioId(usuarioService.getCurrentLoggedUserId());
//            Recibo r1 = reciboRepository.save(newRecibo);
//            copyRecibo(r1,result);
//        } catch (IllegalAccessException e) {
//            log.debug("Error copiando recibos " + r.getId() + " : " +e.getMessage());
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            log.debug("Error copiando recibos " + r.getId() + " : " +e.getMessage());
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    private void copyRecibo(Recibo r1, ReciboDTO result) {
//        result.setAduana(r1.getAduana().getNombre());
//        result.setAntiguedad(0);
//        result.setBl(r1.getBl());
//        result.setCliente(r1.getCliente().getNombre());
//        result.setConsignatario(r1.getConsignatario());
//        result.setCorelacionId(r1.getCorelacionId());
//        result.setDua(r1.getDua());
//        result.setEncargado(r1.getEncargado().getNombre());
//        result.setEstado(r1.getEstado());
//        result.setFacturas(r1.getFactura());
//        result.setFecha(r1.getFecha());
//        result.setId(r1.getId());
//        result.setProveedor(r1.getProveedor());
//        result.setRecibo(r1.getRecibo());
//        result.setTipo(r1.getTipo().getNombre());
//    }


}
