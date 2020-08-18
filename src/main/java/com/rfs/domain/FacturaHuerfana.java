package com.rfs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by alvaro on 11/13/17.
 */
@Entity
public class FacturaHuerfana {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer facturaId;

    public FacturaHuerfana(Integer facturaId, Integer id) {
        this.facturaId = facturaId;
        this.id = id;
    }

    public FacturaHuerfana() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }
}
