package com.rfs.dtos;

import java.io.Serializable;

public class ResultDTO  implements Serializable {

    private String status;
    private String mensaje;
    private String description;

    public ResultDTO() {
    }

    public ResultDTO(String status, String description, String message) {
        this.status = status;
        this.description = description;
        this.mensaje = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
