package com.rfs.service.factura.billapp;

import com.rfs.domain.factura.BillSenderDetail;

public class Result {

    private String resultStr;
    private String mensaje;
    private Integer result;
    private String estado;

    private String location;
    private BillSenderDetail billSenderDetail;

    public Integer getResult() {
        return result;
    }
    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {

        return estado;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BillSenderDetail getBillSenderDetail() {
        return billSenderDetail;
    }

    public void setBillSenderDetail(BillSenderDetail billSenderDetail) {
        this.billSenderDetail = billSenderDetail;
    }
}
