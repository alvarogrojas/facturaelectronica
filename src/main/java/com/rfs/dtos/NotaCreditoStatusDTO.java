package com.rfs.dtos;

public class NotaCreditoStatusDTO {

    private Integer notaCreditoId;

    private Integer facturaId;

    private String estado;


    public NotaCreditoStatusDTO(Integer notaCreditoId, Integer facturaId, String estado) {
        this.notaCreditoId = notaCreditoId;
        this.facturaId = facturaId;
        this.estado = estado;
    }

    public Integer getNotaCreditoId() {
        return notaCreditoId;
    }

    public void setNotaCreditoId(Integer notaCreditoId) {
        this.notaCreditoId = notaCreditoId;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
