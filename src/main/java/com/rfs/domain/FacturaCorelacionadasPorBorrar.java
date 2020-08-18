package com.rfs.domain;

/**
 * Created by alvaro on 11/13/17.
 */
public class FacturaCorelacionadasPorBorrar {

    private Integer id;

    private Integer reciboId;

    private Integer facturaId;

    FacturaCorelacionadasPorBorrar(Integer id, Integer reciboId, Integer facturaId) {
        this.id = id;
        this.reciboId = reciboId;
        this.facturaId = facturaId;
    }

    FacturaCorelacionadasPorBorrar(Integer id) {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReciboId() {
        return reciboId;
    }

    public void setReciboId(Integer reciboId) {
        this.reciboId = reciboId;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }
}
