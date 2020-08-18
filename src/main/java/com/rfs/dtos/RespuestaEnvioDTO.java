package com.rfs.dtos;

import java.util.Date;
import java.util.List;

public class RespuestaEnvioDTO {

    private Integer facturaId;

    private List<RespuestaHaciendaDTO> envios;


    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public List<RespuestaHaciendaDTO> getEnvios() {
        return envios;
    }

    public void setEnvios(List<RespuestaHaciendaDTO> envios) {
        this.envios = envios;
    }
}

