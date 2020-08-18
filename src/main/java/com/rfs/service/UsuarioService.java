package com.rfs.service;


import com.rfs.domain.Empresa;
import com.rfs.domain.Usuario;
import com.rfs.dtos.EncargadoDTO;
import com.rfs.dtos.EstadisticaDTO;
import com.rfs.dtos.UsuarioDTO;
import com.rfs.repository.FacturaRepository;
import com.rfs.repository.UsuarioRepository;
import com.rfs.service.factura.billapp.BillHelper;
import com.rfs.utils.Helper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by alvaro on 10/29/17.
 */
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

//    @Autowired
//    private ReciboRepository reciboRepository;

    @Autowired
    private FacturaRepository facturaRepository;

//    @Autowired
//    private TicaEstadoReciboRepository ticaEstadoReciboRepository;

    public Usuario getCurrentLoggedUser() {
        Usuario u = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null || authentication.getPrincipal()==null) {
            return null;
        }
        Object obj = authentication.getPrincipal();
        if (obj instanceof  String) {
            String usuario = (String) obj;
            u = usuarioRepository.findByUsuario(usuario);
        } else {
            throw new RuntimeException("Principal type unexpected " + authentication.getPrincipal());
        }
        //u.setClave("");
        return u;
    }

    public Integer getCurrentLoggedUserId() {
        Usuario user = getCurrentLoggedUser();
        if (user!=null) {
            return user.getId();
        } else {
            //throw new RuntimeException("Unknow username");
            return 1;
        }
    }

    public UsuarioDTO getUsuarioData() {
        Usuario u = getCurrentLoggedUser();
        UsuarioDTO dto  = new UsuarioDTO();
        try {
            BeanUtils.copyProperties(dto, u);
            dto.setClave("");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return dto;
    }

    public EstadisticaDTO getUsuarioEstadisticas() {
        Usuario u = getCurrentLoggedUser();
        EstadisticaDTO dto = new EstadisticaDTO();
        dto.setAdmin(u.getRoles());
        //if (dto.isAdmin()==1) {
        if (Helper.isAdminOrCoordinador(u.getRoles())) {
            getAdminEstadisticas(dto);

            //dto.setPendientesPorMes(this.reciboRepository.findPendientesPorMes());
        } else {
            getStandardUsuarioEstadisticas(u, dto);
        }
        return dto;


    }

    private void getStandardUsuarioEstadisticas(Usuario u, EstadisticaDTO dto) {
        dto.setTotalRecibosFinalizados(0);
        dto.setTotalRecibosPendientes(0);
        Empresa e = u.getEmpresa();

        dto.setPendientesFacturarInmediata(new ArrayList<>());
        dto.setPendientesFacturarEnAforo(new ArrayList<>());
        dto.setPendientesFacturarDuaAnticipado(new ArrayList<>());

        Date[] dates = initDateRango();
        Date fechaFinal = dates[1];
        Date fechaInicio = dates[0];

        dto.setTotalFacturasMes(this.facturaRepository.countFacturas(null,null,null,fechaInicio,fechaFinal,null,e.getId()));
        dto.setTotalFacturasHoy(this.facturaRepository.countFacturas(null,null,null,fechaInicio,fechaInicio,null,e.getId()));

//        dto.setTotalFacturasMes(this.facturaRepository.getCountFacturasDelMes(fechaInicio,fechaFinal));
//        dto.setTotalFacturasPendientes(this.facturaRepository.getCountFacturasEstadoPago(BillHelper.PENDIENTE_PAGO,e.getId()));
        dto.setTotalFacturasPendientes(this.facturaRepository.countFacturas(null,null,BillHelper.PENDIENTE_PAGO,null,null,null,e.getId()));

        dto.setTotalRecibosHoy(0);

//        dto.setTotalRecibosFinalizados(this.reciboRepository.countByEncargadoIdAndEstado(u.getId(),"FINALIZADO"));
//        dto.setTotalRecibosPendientes(this.reciboRepository.countByEncargadoIdAndEstado(u.getId(),"PENDIENTE"));
//
//        dto.setPendientesFacturarInmediata(this.reciboRepository.findPendientesFacturarEnInmediata());
//        dto.setPendientesFacturarEnAforo(this.reciboRepository.findPendientesFacturarEnAforo());
//        dto.setPendientesFacturarDuaAnticipado(this.reciboRepository.findPendientesFacturarDuaAnticipado());
//
//        dto.setTotalFacturasHoy(this.facturaRepository.getCountFacturasHoy());
//        dto.setTotalRecibosHoy(this.reciboRepository.getCountRecibosHoy());


    }

    private void getAdminEstadisticas(EstadisticaDTO dto) {
        List<EncargadoDTO> encargados = new ArrayList<>();
        includeRecibos(encargados);
        dto.setEncargados(encargados);
        dto.setPendientesFacturar(new ArrayList<>());
        dto.setTotalRecibosFinalizados(0);
        dto.setTotalRecibosPendientes(0);
        dto.setTotalRecibosPendientesConDua(0);
        dto.setRecibosHoy(new ArrayList<>());
        Empresa e = getCurrentLoggedUser().getEmpresa();

        dto.setNombreEmpresa(e.getNombre());
        dto.setLogoUrl(BillHelper.createLogoUrl(e.getLogoPath()));

        Date[] dates = initDateRango();
        Date fechaFinal = dates[1];
        Date fechaInicio = dates[0];
        dto.setTotalFacturasMes(this.facturaRepository.countFacturas(null,null,null,fechaInicio,fechaFinal,null,e.getId()));
        dto.setTotalFacturasHoy(this.facturaRepository.countFacturas(null,null,null,fechaFinal,fechaFinal,null,e.getId()));

//        dto.setTotalFacturasMes(this.facturaRepository.getCountFacturasDelMes(fechaInicio,fechaFinal));
        dto.setTotalFacturasPendientes(this.facturaRepository.countFacturas(null,null,BillHelper.PENDIENTE_PAGO,null,null,null,e.getId()));
        dto.setTotalFacturasVencidas(this.facturaRepository.countFacturas(null,null,BillHelper.VENCIDAS,null,null,null,e.getId()));
//        dto.setTotalFacturasPendientes(this.facturaRepository.getCountFacturasEstadoPago(BillHelper.PENDIENTE_PAGO,e.getId()));

        dto.setTotalRecibosHoy(0);


    }

    private void includeRecibos(List<EncargadoDTO> encargados) {
//        for (EncargadoDTO e: encargados) {
//            List<PendienteDTO> pendienteDTOs = this.reciboRepository.findPendientesByFechaAsc(e.getId(), EstadosEnum.PENDIENTE.toString());
//            e.setPendientes(pendienteDTOs);
//        }
    }

    private Date[] initDateRango() {

        Date fechaFinal = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaFinal);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        Date fechaInicio = cal.getTime();

        return new Date[]{fechaInicio,fechaFinal};
    }


    public void updateUsuario(Usuario u) {
        try {
            this.setPassword(u);
            this.usuarioRepository.save(u);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public String getEncriptPwd(String clave) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] hash = sha256.digest(clave.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private void setPassword(Usuario u) throws NoSuchAlgorithmException {
        u.setClave(this.getEncriptPwd(u.getClave()));
    }
}
