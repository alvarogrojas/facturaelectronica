package com.rfs.domain;

import javax.persistence.*;

@Entity
public class FacturaConsecutivo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String empresa;

    private Integer actualId;

    private Integer empresaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Integer getActualId() {
        return actualId;
    }

    public void setActualId(Integer actualId) {
        this.actualId = actualId;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
}
