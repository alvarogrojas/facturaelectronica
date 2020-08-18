package com.rfs.dtos;

import com.rfs.domain.Recibo;

import java.util.List;

/**
 * Created by alvaro on 10/23/17.
 */
public class RecibosDTO {

    private List<Recibo> list;

    public List<Recibo> getRecibos() {
        return list;
    }

    public void setRecibos(List<Recibo> l) {
        this.list = l;;
    }


}
