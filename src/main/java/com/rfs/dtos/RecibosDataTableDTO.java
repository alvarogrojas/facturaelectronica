package com.rfs.dtos;

import com.rfs.domain.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvaro on 11/1/17.
 */
public class RecibosDataTableDTO {

    private Integer total;

    private Integer pagesTotal;

    private List<ReciboDTO> recibos = new ArrayList<>();

    private List<EncargadoDTO> encargados;

    private Iterable<Cliente> clientes;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ReciboDTO> getRecibos() {
        return recibos;
    }

    public void setRecibos(List<ReciboDTO> recibos) {
        this.recibos = recibos;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    public List<EncargadoDTO> getEncargados() {
        return encargados;
    }

    public void setEncargados(List<EncargadoDTO> encargados) {
        this.encargados = encargados;
    }

    public Iterable<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Iterable<Cliente> clientes) {
        this.clientes = clientes;
    }
}
