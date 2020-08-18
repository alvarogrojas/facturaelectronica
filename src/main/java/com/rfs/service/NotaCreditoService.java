package com.rfs.service;

import com.rfs.domain.*;
import com.rfs.domain.factura.BillSenderDetail;
import com.rfs.dtos.*;
import com.rfs.repository.*;
import com.rfs.repository.factura.BillSenderDetailRepository;
import com.rfs.service.factura.billapp.BillHelper;
import com.rfs.service.factura.billapp.BillManagerService;
import com.rfs.service.factura.billapp.Result;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by alvaro on 10/17/17.
 */
@Service
public class NotaCreditoService {

    @Autowired
    private NotaCreditoRepository notaCreditoRepository;

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

    private Logger logger = Logger.getLogger(NotaCreditoService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoTramiteRepository tipoTramiteRepository;

    @Autowired
    private BillSenderDetailRepository billSenderDetailRepository;

    @Autowired
    private BillManagerService billManagerService;
    @Autowired
    private TipoCambioRepository tipoCambioRepository;


    @Transactional
    public NotaCredito save(Integer idRecibo, Integer idEncargado, Date currentDate) {
        NotaCredito f = new NotaCredito();
        f.setReciboId(idRecibo);
        f.setFechaNotaCreditocion(currentDate);
        f.setEncargadoId(idEncargado);

        f.setUltimoCambioPor(idEncargado);
        f.setFechaUltimoCambio(currentDate);
        //f.setEstadoFacturaRfs(FacturaEstadosRfsEnum.ACTIVA.toString());
        initNewNotaCreditoData(f);
        f = this.notaCreditoRepository.save(f);
        this.save(f,true);
        return f;
    }

    @Transactional
    public Integer save(NotaCreditoDataDTO data) throws  Exception {
        NotaCredito f = null;
        //data.calculateComisionFinanciamiento();
            try {
                Usuario u = usuarioService.getCurrentLoggedUser();
                Integer idEncargado = usuarioService.getCurrentLoggedUserId();
                Boolean isEditing = false;
                data.recalculateDiasCredito();
                Date currentDate = new Date();
                if (data.getNotaCreditoId() == null) {
                    f = new NotaCredito();
                    f.setEmpresaId(u.getEmpresa().getId());
                    idEncargado = usuarioService.getCurrentLoggedUserId();
                    f.setEncargadoId(idEncargado);
                   // f.setEstadoFacturaRfs(FacturaEstadosRfsEnum.ACTIVA.toString());
                    initNewNotaCreditoData(f);
                } else {
                    try {
                        f = this.notaCreditoRepository.findByIdAndEmpresaId(data.getNotaCreditoId(), u.getEmpresa().getId());
                        isEditing = true;
                    } catch(Exception e) {
                        e.printStackTrace();
                        logger.error("ERROR recuperando notaCredito " + data.getNotaCreditoId());
                        throw e;
                    }
                }
                cambiarHoraNotaCredito(data, currentDate);
                BeanUtils.copyProperties(f, data);
                f.setNotaCreditoDetalleDTO(data.getDetallesDTO(),f);
                f.setEncargadoId(idEncargado);

//                copyNotaCreditoDetalleDTO(data.getDetallesDTO(),f);
//                copyNotaCreditoTercerosDTO(data.getNotaCreditoTercerosDTO(),f);
//                copyNotaCreditoServicioDetalleDTO(data.getNotaCreditoServiciosDTO(),f);
                f.setUltimoCambioPor(idEncargado);
                f.setFechaUltimoCambio(currentDate);


                f = saveNotaCredito(f, isEditing, currentDate);

                if (f !=null) {
                    Factura factura = this.facturaRepository.findByFacturaIdentity(new FacturaIdentity(f.getFacturaId(), u.getEmpresa().getId()));
                    factura.setEstado(BillHelper.ACTIVA_NC);
                    this.facturaRepository.save(factura);
                }


            } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error(" ** Error guardando la notaCredito del recibo " + data.getReciboId() + " : " + e.getMessage());
                throw e;
            } catch (InvocationTargetException e) {
                logger.error(" ** Error guardando la notaCredito del recibo " + data.getReciboId() + " : " + e.getMessage());

                e.printStackTrace();
                throw e;
            } finally {
            }
        return f.getId();
    }

    private void copyNotaCreditoDetalleDTO(List<NotaCreditoDetalleDTO> detallesDTO, NotaCredito f) {
        NotaCreditoDetalleDTO dto1;
        List<NotaCreditoDetalleDTO> toUpdateList = new ArrayList<>();
        List<NotaCreditoDetalle> toRemoveList = new ArrayList<>();
        for (NotaCreditoDetalle dt: f.getNotaCreditoDetalle()) {
            if (!detallesDTO.contains(dt)) {
                toRemoveList.add(dt);
            } else {
                toUpdateList.add(detallesDTO.get(dt.getId()));
            }
        }

        for(NotaCreditoDetalleDTO dto: toUpdateList) {
                try {
                    NotaCreditoDetalle dt = f.getNotaCreditoDetalle().get(dto.getId());
                    BeanUtils.copyProperties(dt,dto);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
        }

        for(NotaCreditoDetalle dt: toRemoveList) {
            f.removeNotaCreditoDetalle(dt);
        }

        for (NotaCreditoDetalleDTO dto: detallesDTO) {

            if (!f.getNotaCreditoDetalle().contains(dto)) {
                NotaCreditoDetalle fdt = new NotaCreditoDetalle();
                try {
                    BeanUtils.copyProperties(fdt,dto);
                    f.addNotaCreditoDetalle(fdt);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private NotaCredito saveNotaCredito(NotaCredito f, Boolean isEditing, Date currentDate) throws Exception {
        try {
            if (isEditing) {
                f = this.notaCreditoRepository.saveAndFlush(f);

            } else {
                f = this.notaCreditoRepository.save(f);
            }

//            if (!isEditing) {
//                Recibo r = this.reciboRepository.findById(f.getReciboId());
//                this.reciboService.updateNotaCreditodo(r, f, currentDate);
//            }
        } catch (Exception e) {
            throw e;
        } finally {
            //globalDataManager.goBackNotaCreditoId();
        }
        return f;
    }


    private void cambiarHoraNotaCredito(NotaCreditoDataDTO data, Date currentDate) {
        if (data.getFechaNotaCreditocion()==null) {
            data.setFechaNotaCreditocion(currentDate);
            return;
        }
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(data.getFechaNotaCreditocion());
        cal.set(Calendar.HOUR, currentCal.get(Calendar.HOUR));
        cal.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, currentCal.get(Calendar.SECOND));
        data.setFechaNotaCreditocion(cal.getTime());
    }

    @Transactional
    public NotaCredito save(NotaCredito f, Boolean nueva) {
        NotaCredito current  = null;
        if (!nueva) {
            Usuario u = this.usuarioService.getCurrentLoggedUser();
            current = this.notaCreditoRepository.findByIdAndEmpresaId(f.getId(),u.getEmpresa().getId());
        } else {
            current = new NotaCredito();
        }
        f = this.notaCreditoRepository.save(f);
        
        return f;
    }

    private NotaCredito initNewNotaCreditoData(NotaCredito fObj) {
        Integer currentTopId = null;

        Integer id = globalDataManager.getNotaCreditoNext(fObj.getEmpresaId());
        System.out.println("ID FACTURA " + id);

        String fNumber = formatFactura(id);
        fObj.setId(id);

        return fObj;
    }

    public synchronized NotaCredito getNexFactura() {
        return notaCreditoRepository.findTopByOrderByIdDesc();
    }

    private String formatFactura(Integer id) {
        return id.toString();
    }

    public NotaCreditoDataDTO getNotaCreditoData(Integer id, Integer facturaId) {
        NotaCreditoDataDTO result = null;
        Usuario u = this.usuarioService.getCurrentLoggedUser();
        boolean estaEditando = false;
        if (id==null) {

           result = this.facturaRepository.findByIdToNotaCreditoDTO(facturaId, u.getEmpresa().getId());
            result.setEncargado(this.usuarioService.getCurrentLoggedUser().getNombre());
        } else  {
            estaEditando = true;
            result = this.notaCreditoRepository.findByIdToNotaCreditoDTO(id);
            if (result.getEncargadoId()!=null)
                result.setEncargado(this.usuarioRepository.findById(result.getEncargadoId()).getNombre());
            else {
                result.setEncargado(this.usuarioService.getCurrentLoggedUser().getNombre());
            }
        }
        if (!estaEditando) {
            result.initPorcentajeComision();
        }

        result.setMonedas(this.tipoCambioRepository.findByEmpresaId(u.getEmpresa().getId()));

//        if (!estaEditando) {
//            result.setTipoCambio(result.getMonedas().get(0));
//            result.setTipoCambioMonto(result.getMonedas().get(1).getVenta());
//        }
        return result;
    }

    private FacturaTercerosDTO createFacturaTerceros(Terceros t, TipoCambio tc) {
        FacturaTercerosDTO facturaTercerosDTO = new FacturaTercerosDTO();
        facturaTercerosDTO.setCantidad(0);
        facturaTercerosDTO.setTerceros(t);
        facturaTercerosDTO.setTipoDetalle("TERCEROS");
        facturaTercerosDTO.getId();
        facturaTercerosDTO.setMonto(0d);
        facturaTercerosDTO.setMontoColones(0d);
        facturaTercerosDTO.setDetalle("");
        facturaTercerosDTO.setTipoCambio(tc);
        return facturaTercerosDTO;
    }


    public ResumenTramiteEncargadoDTO getFacturasTramites(Integer clienteId, Integer tipoId, Date fechaInicio, Date fechaFinal, Boolean cargados, Integer pageNumber, Integer pageSize) {
        ResumenTramiteEncargadoDTO result = new ResumenTramiteEncargadoDTO();
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

    public String[] getNotaCreditoElectronicaData(Integer ncId) {
        String[] result = new String[2];
        try {
            Usuario u = usuarioService.getCurrentLoggedUser();
            List<BillSenderDetail> bsdList = this.billSenderDetailRepository.findByBillIdAndEmpresaIdAndTipoOrderByFechaIngresoDesc(ncId, u.getEmpresa().getId(), BillHelper.TIPO_NOTA_CREDITO_FE);
            BillSenderDetail bsd = null;
            if (bsdList!=null && bsdList.size()>0) {
                bsd = bsdList.get(0);
                result[1] = bsd.getConsecutivo();
                result[0] = bsd.getClave();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    public String cambiarEstadoFactura(Integer facturaId,String estado) {
        Usuario u = this.usuarioService.getCurrentLoggedUser();
        Factura factura = this.facturaRepository.findByFacturaIdentity(new FacturaIdentity(facturaId,u.getEmpresa().getId()));
        factura.setEstado(estado);
        Date currentDate =  new Date();
        Integer idEncargado = this.usuarioService.getCurrentLoggedUserId();
        factura.setUltimoCambioPor(idEncargado);
        factura.setFechaUltimoCambio(currentDate);
        this.facturaRepository.saveAndFlush(factura);
        return "Ok";
    }

    @Transactional
    public Result saveAnSend(NotaCreditoDataDTO f) throws Exception {
        return this.billManagerService.sendNotaCreditoHacienda(f);
    }

    public Result enviarNCAHacienda(NotaCreditoDataDTO f) throws Exception {
        return this.billManagerService.sendNotaCreditoHacienda(f);
    }

    @Transactional
    public Result noAprobarNotaCredito(Integer id) {
        Date currentDate =  new Date();
        Usuario u = this.usuarioService.getCurrentLoggedUser();
        NotaCredito nc = this.notaCreditoRepository.findByIdAndEmpresaId(id, u.getEmpresa().getId());
        Factura f = this.facturaRepository.findByFacturaIdentity(new FacturaIdentity(nc.getFacturaId(),u.getEmpresa().getId()));
        f.setEstado("ACTIVA");
        f.setUltimoCambioPor(u.getId());
        f.setFechaUltimoCambio(currentDate);
        this.facturaRepository.save(f);
        nc.setEstado("NO_APROBADA");

        nc.setUltimoCambioPor(u.getId());
        nc.setFechaUltimoCambio(currentDate);
        this.notaCreditoRepository.save(nc);
        Result result = new Result();
        result.setResultStr("OK");
        result.setResult(1);
        return result;

    }

    public NotaCreditoStatusDTO getNotaCreditoFirstNotaCreditoNoRechazadaAndSent(List<NotaCreditoStatusDTO> l) {
        NotaCreditoStatusDTO found = null;
        Integer id = -1;
        Integer pos = -1;
        Integer idx = 0;
        for (NotaCreditoStatusDTO dto: l) {
//            if (dto.getEstado().equals("ENVIADA")) {
//                found = dto;
//                break;
//            }
            if (dto.getNotaCreditoId()>id) {
                id = dto.getNotaCreditoId();
                pos = idx;
            }

            idx ++;
        }
        if (pos >= 0 && l.get(pos)!=null) {
            found = l.get(pos);

        }


        return found;
    }

}
