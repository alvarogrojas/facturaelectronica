package com.rfs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by alvaro on 10/28/17.
 */
@Entity
public class EmpresaTarifa {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer empresaId;

    private String mesAno;

    private Integer cantidad;

    public EmpresaTarifa() {}


    public EmpresaTarifa(Integer id, Integer empresaId, String mesAno, Integer cantidad) {
        this.id= id;
        this.empresaId = empresaId;
        this.mesAno = mesAno;
        this.cantidad = cantidad;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmpresaTarifa that = (EmpresaTarifa) o;

        if (!empresaId.equals(that.empresaId)) return false;
        return mesAno.equals(that.mesAno);
    }

    @Override
    public int hashCode() {
        int result = empresaId.hashCode();
        result = 31 * result + mesAno.hashCode();
        return result;
    }
}
