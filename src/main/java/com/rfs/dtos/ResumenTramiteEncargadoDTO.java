package com.rfs.dtos;

import com.rfs.domain.Cliente;
import com.rfs.domain.RegimenAduanero;
import com.rfs.domain.TipoTramite;
import com.rfs.domain.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResumenTramiteEncargadoDTO {

    private Integer total;
    private Double monto;

    private Integer pagesTotal;

    private List<TramiteEncargadoDTO> tramites = new ArrayList<>();

    private Iterable<Cliente> clientes;
    private Iterable<TipoTramite> tipos;
    private Iterable<Usuario> encargados;
    private Iterable<RegimenAduanero> regimenAduaneros;

    private Date fechaInicio;

    private Date fechaFinal;

//    private List<ReciboIdDTO> recibos;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        if (total==null) {
            this.total = 0;
            return;
        }
        this.total = total;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    public Iterable<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Iterable<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<TramiteEncargadoDTO> getTramites() {
        return tramites;
    }

    public void setTramites(List<TramiteEncargadoDTO> tramites) {
        this.tramites = tramites;
//        if (this.tramites!=null) {
//            this.total = tramites.size();
//        }
    }

    public Iterable<Usuario> getEncargados() {
        return encargados;
    }

    public void setEncargados(Iterable<Usuario> encargados) {
        this.encargados = encargados;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Iterable<RegimenAduanero> getRegimenAduaneros() {
        return regimenAduaneros;
    }

    public void setRegimenAduaneros(Iterable<RegimenAduanero> regimenAduaneros) {
        this.regimenAduaneros = regimenAduaneros;
    }

    public Iterable<TipoTramite> getTipos() {
        return tipos;
    }

    public void setTipos(Iterable<TipoTramite> tipos) {
        this.tipos = tipos;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
