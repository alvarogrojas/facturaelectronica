package com.rfs.dtos;

import java.util.List;

/**
 * Created by alvaro on 10/31/17.
 */
public class EncargadoDTO {

    private Integer id;

    private String nombre;

    private String estado;

    private Long cantidad;

    private boolean expandido = false;

    private List<PendienteDTO> pendientes;

    public EncargadoDTO(Integer id, String nombre, String estado, Long cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isExpandido() {
        return expandido;
    }

    public boolean getExpandido() {
        return expandido;
    }

    public void setExpandido(boolean expandido) {
        this.expandido = expandido;
    }

    public List<PendienteDTO> getPendientes() {
        return pendientes;
    }

    public void setPendientes(List<PendienteDTO> pendientes) {
        this.pendientes = pendientes;
    }
}
