package com.rfs.dtos;

import com.rfs.domain.Cliente;
import com.rfs.domain.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacturaTableDTO {

    private Integer total;

    private Double montoTotal;

    private Integer pagesTotal;

    private List<FacturaRegistroDTO> facturas = new ArrayList<>();

    private Iterable<Cliente> clientes;
    private Iterable<Usuario> encargados;

    private Date fechaInicio;

    private Date fechaFinal;

//    private List<ReciboIdDTO> recibos;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    public List<FacturaRegistroDTO> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaRegistroDTO> facturas) {
        this.facturas = facturas;
    }

    public Iterable<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Iterable<Cliente> clientes) {
        this.clientes = clientes;
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


    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }
}
