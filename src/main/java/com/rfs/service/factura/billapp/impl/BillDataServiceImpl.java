package com.rfs.service.factura.billapp.impl;

import com.rfs.domain.Factura;
import com.rfs.domain.FacturaIdentity;
import com.rfs.domain.Usuario;
import com.rfs.dtos.FacturaDataDTO;
import com.rfs.dtos.NotaCreditoDataDTO;
import com.rfs.fe.v43.FacturaElectronica;
import com.rfs.fe.v43.nc.NotaCreditoElectronica;
import com.rfs.repository.FacturaRepository;
import com.rfs.service.FacturaService;
import com.rfs.service.GlobalDataManager;
import com.rfs.service.NotaCreditoService;
import com.rfs.service.UsuarioService;
import com.rfs.service.factura.billapp.BillDataService;
import com.rfs.service.factura.billapp.BillHelper;
import com.rfs.service.factura.billapp.EmisorService;
import com.rfs.service.factura.billapp.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class BillDataServiceImpl implements BillDataService {

    @Autowired
    private FacturaService facturaService;

    private Mapper mapper;

    private Date fechaDesde;

    private Date fechaHasta;
    @Autowired
    private FacturaRepository facturaRepository;


    private Map<Integer,FacturaDataDTO> originalData;

    private Map<Integer,NotaCreditoDataDTO> ncData;

    @Autowired
    private GlobalDataManager globalDataManager;

    @Autowired
    private NotaCreditoService notaCreditoService;

    @Autowired
    private UsuarioService usuarioService;

    public BillDataServiceImpl() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        fechaDesde = cal.getTime();
        fechaHasta = new Date();
    }

    public  Map<Integer,NotaCreditoDataDTO> getNcData() {
        return this.ncData;
    }


    @Override
    public List<FacturaElectronica> getFacturas(Date fechaDesde, Date fechaHasta, EmisorService e) {
          this.mapper = new FacturaElectronicaMapper();
        ((FacturaElectronicaMapper)mapper).setEmisorService(e);
        List<FacturaDataDTO> dl = facturaService.getFacturaDataDTO(fechaDesde, fechaHasta,0);
        List<FacturaElectronica> fes = new ArrayList<>();
        if (dl==null) {
            return fes;
        }
        return mapFacturas(dl, fes,this.mapper);
    }

    @Transactional
    public List<FacturaElectronica> mapFacturas(List<FacturaDataDTO> dl, List<FacturaElectronica> fes, Mapper map) {
        FacturaElectronica fe;
        originalData = new HashMap<>();

        for(FacturaDataDTO dto: dl) {
            mapFactura(fes, dto, map);
        }

        return fes;
    }

    private void mapFactura(List<FacturaElectronica> fes, FacturaDataDTO dto, Mapper fem) {
        try {
            FacturaElectronica fe = (FacturaElectronica)fem.mapFacturaElectronica(dto, BillHelper.FACTURA_ELECTRONICA_TIPO);
            if (fes!=null) {
                fes.add(fe);
            }
            originalData.put(dto.getFacturaId(),dto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<FacturaElectronica> getFacturas(EmisorService e) {
        return getFacturas(this.fechaDesde,fechaHasta, e);
    }

    @Override
    public Map getData() {
        return this.originalData;
    }

//    @Override
//    public List<FacturaElectronica> getFacturasElectronica(FacturaDataDTO fdd, EmisorService e) {
//        originalData = new HashMap<>();
//        this.mapper = new FacturaElectronicaMapper();
//        ((FacturaElectronicaMapper)mapper).setEmisorService(e);
//        List<FacturaElectronica> list = new ArrayList<>();
//        mapFactura(list,fdd,this.mapper);
//    }

    @Override
    public List<FacturaElectronica> getFacturaElectronica1(Integer id, EmisorService e) {
        FacturaDataDTO dto = this.facturaService.getFacturaData(id);
        //return mapFacturaForNotaCredito(dto);
        originalData = new HashMap<>();
        this.mapper = new FacturaElectronicaMapper();
        ((FacturaElectronicaMapper)mapper).setEmisorService(e);
        List<FacturaElectronica> list = new ArrayList<>();
        mapFactura(list,dto,this.mapper);
        return list;
    }

    @Override
    public NotaCreditoElectronica getNotaCreditoElectronica(Integer id,EmisorService emisorService) {
        NotaCreditoDataDTO dto = this.notaCreditoService.getNotaCreditoData(id,null);
        return mapNotaCreditoForNotaCreditoElectronica(dto, emisorService);
    }

    @Override
    public NotaCreditoElectronica getNotaCreditoElectronica(NotaCreditoDataDTO dto, EmisorService emisorService) {

        NotaCreditoElectronica nce =  mapNotaCreditoForNotaCreditoElectronica(dto, emisorService);
        Usuario u = usuarioService.getCurrentLoggedUser();
        nce.setEmpresaId(u.getEmpresa().getId());
        ncData = new HashMap<>();
        ncData.put(dto.getNotaCreditoId(), dto);
        return nce;
    }


    private FacturaElectronica mapFacturaForNotaCredito(FacturaDataDTO dto) {
        try {
            FacturaElectronica fe = (FacturaElectronica)mapper.mapFacturaElectronica(dto, BillHelper.NOTA_CREDITO_TIPO);
            fe.setNumeroConsecutivoFe(fe.getEmpresaId());
//            fe.setNumeroConsecutivoFe(globalDataManager.getFacturaElectronicaNext(fe.getEmpresaId()));

            ncData = new HashMap<>();
            originalData.put(dto.getFacturaId(), dto);
            return fe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }


    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @Override
    public void updateFactura(Integer id, Integer enviadaHaciendaStatus) {
        try {
            Usuario u = this.usuarioService.getCurrentLoggedUser();
            Factura factura = this.facturaRepository.findByFacturaIdentity(new FacturaIdentity(id,u.getEmpresa().getId()));
            factura.setEnviadaHacienda(enviadaHaciendaStatus);
            this.facturaRepository.save(factura);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private NotaCreditoElectronica mapNotaCreditoForNotaCreditoElectronica(NotaCreditoDataDTO dto, EmisorService emisorService) {
        try {
//            Mapper mnc = new NotaCreditoMapper();
            Mapper mnc = new NotaCreditoV43Mapper();
            ((NotaCreditoV43Mapper)mnc).setEmisorService(emisorService);
            NotaCreditoElectronica fe = (NotaCreditoElectronica)mnc.mapFacturaElectronica(dto, BillHelper.NOTA_CREDITO_TIPO);


            ncData = new HashMap<>();
            ncData.put(dto.getNotaCreditoId(), dto);
            return fe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public FacturaElectronica getFacturaElectronicaById(Integer id, EmisorService e) {
        FacturaDataDTO dto = this.facturaService.getFacturaData(id);
        return mapFacturaElectronica(dto, e);
    }

    private FacturaElectronica mapFacturaElectronica(FacturaDataDTO dto, EmisorService e) {
        try {
            FacturaElectronicaV43Mapper map = new FacturaElectronicaV43Mapper();
            map.setEmisorService(e);
            FacturaElectronica fe = (FacturaElectronica)map.mapFacturaElectronica(dto, BillHelper.FACTURA_ELECTRONICA_TIPO);

            originalData = new HashMap<>();

            originalData.put(dto.getFacturaId(), dto);
            return fe;
        } catch (Exception e1) {
            e1.printStackTrace();
            throw new RuntimeException(e1.getMessage());
        }

    }
}
