package com.rfs.dtos;

import com.rfs.domain.ConfirmaRechazaDocumento;

import java.util.Date;
import java.util.List;

public class ResultadoConfirmacionRechazoDTO {

    private Date fechaInicio;

    private Date fechaFinal;

    private List<ConfirmaRechazaDocumento> confirmRechazos;

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

    public List<ConfirmaRechazaDocumento> getConfirmRechazos() {
        return confirmRechazos;
    }

    public void setConfirmRechazos(List<ConfirmaRechazaDocumento> confirmRechazos) {
        this.confirmRechazos = confirmRechazos;
    }
}
