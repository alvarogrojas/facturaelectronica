package com.rfs.dtos;

import com.rfs.domain.Role;

import java.util.List;

/**
 * Created by alvaro on 10/31/17.
 */
public class EstadisticaDTO {

    private Integer totalRecibosPendientes;

    private Integer totalRecibosFinalizados;

    private Integer totalRecibosPendientesConDua;

    private Integer totalRecibosHoy;
    private Long totalFacturasHoy = 0l;
    private Long totalFacturasMes = 0l;

    private String nombreEmpresa;

    private String logoUrl;

    private List<EncargadoDTO> encargados;

    private List<ReciboDTO> recibosHoy;



    private Integer isAdmin = 0;

    private Boolean estadisticasCargadas = true;

    private List<PendientesFacturarDTO> pendientesFacturar;

    private List<PendientesFacturarDTO> pendientesFacturarEnAforo;
    private List<PendientesFacturarDTO> pendientesFacturarInmediata;
    private List<PendientesFacturarDTO> pendientesFacturarDuaAnticipado;


    private List<ReciboPendientesAnualDTO> pendientesPormes;
    private Long totalFacturasPendientes = 0l;
    private Long totalFacturasVencidas = 0l;

    public Integer getTotalRecibosPendientes() {
        return totalRecibosPendientes;
    }

    public void setTotalRecibosPendientes(Integer totalRecibosPendientes) {
        this.totalRecibosPendientes = totalRecibosPendientes;
    }

    public Integer getTotalRecibosFinalizados() {
        return totalRecibosFinalizados;
    }

    public void setTotalRecibosFinalizados(Integer totalRecibosFinalizados) {
        this.totalRecibosFinalizados = totalRecibosFinalizados;
    }

    public List<EncargadoDTO> getEncargados() {
        return encargados;
    }

    public void setEncargados(List<EncargadoDTO> encargados) {
        this.encargados = encargados;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public Integer isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setAdmin(List<Role> roles) {
        this.isAdmin = 0;
        for (Role r: roles) {
            if (r.getNombre().equals("ADMIN")) {
                this.isAdmin = 1;
                break;
            }
        }
    }

    public void setPendientesPorMes(List<ReciboPendientesAnualDTO> pendientesPorMes) {
        this.pendientesPormes = pendientesPorMes;
    }

    public List<ReciboPendientesAnualDTO> getPendientesPormes() {

        return pendientesPormes;
    }

    public Boolean getEstadisticasCargadas() {
        return estadisticasCargadas;
    }

    public void setEstadisticasCargadas(Boolean estadisticasCargadas) {
        this.estadisticasCargadas = estadisticasCargadas;
    }

    public List<PendientesFacturarDTO> getPendientesFacturar() {
        return pendientesFacturar;
    }

    public void setPendientesFacturar(List<PendientesFacturarDTO> pendientesFacturar) {
        this.pendientesFacturar = pendientesFacturar;
    }

    public List<ReciboDTO> getRecibosHoy() {
        return recibosHoy;
    }

    public void setRecibosHoy(List<ReciboDTO> recibosHoy) {
        this.recibosHoy = recibosHoy;
    }


    public Integer getTotalRecibosPendientesConDua() {
        return totalRecibosPendientesConDua;
    }

    public void setTotalRecibosPendientesConDua(Integer totalRecibosPendientesConDua) {
        this.totalRecibosPendientesConDua = totalRecibosPendientesConDua;
    }

    public List<PendientesFacturarDTO> getPendientesFacturarEnAforo() {
        return pendientesFacturarEnAforo;
    }

    public void setPendientesFacturarEnAforo(List<PendientesFacturarDTO> pendientesFacturarEnAforo) {
        this.pendientesFacturarEnAforo = pendientesFacturarEnAforo;
    }

    public List<PendientesFacturarDTO> getPendientesFacturarInmediata() {
        return pendientesFacturarInmediata;
    }

    public void setPendientesFacturarInmediata(List<PendientesFacturarDTO> pendientesFacturarInmediata) {
        this.pendientesFacturarInmediata = pendientesFacturarInmediata;
    }

    public List<PendientesFacturarDTO> getPendientesFacturarDuaAnticipado() {
        return pendientesFacturarDuaAnticipado;
    }

    public void setPendientesFacturarDuaAnticipado(List<PendientesFacturarDTO> pendientesFacturarDuaAnticipado) {
        this.pendientesFacturarDuaAnticipado = pendientesFacturarDuaAnticipado;
    }

    public Integer getTotalRecibosHoy() {
        return totalRecibosHoy;
    }

    public void setTotalRecibosHoy(Integer totalRecibosHoy) {
        this.totalRecibosHoy = totalRecibosHoy;
    }

    public Long getTotalFacturasHoy() {
        return totalFacturasHoy;
    }

    public void setTotalFacturasHoy(Long totalFacturasHoy) {
        if (totalFacturasHoy==null) {
            return;
        }
        this.totalFacturasHoy = totalFacturasHoy;
    }

    public Long getTotalFacturasMes() {
        return totalFacturasMes;
    }

    public void setTotalFacturasMes(Long totalFacturasMes) {
        if (totalFacturasMes==null) {
            return;
        }
        this.totalFacturasMes = totalFacturasMes;
    }

    public void setTotalFacturasPendientes(Long totalFacturasPendientes) {
        if (totalFacturasPendientes==null) {
            return;
        }
        this.totalFacturasPendientes = totalFacturasPendientes;
    }


    public Long getTotalFacturasPendientes() {
        return totalFacturasPendientes;
    }

    public void setTotalFacturasVencidas(Long totalFacturasVencidas) {
        if (totalFacturasVencidas==null) {
            return;
        }
        this.totalFacturasVencidas = totalFacturasVencidas;
    }


    public Long getTotalFacturasVencidas() {
        return totalFacturasVencidas;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
