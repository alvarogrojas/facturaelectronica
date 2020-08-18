package com.rfs.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class FacturaIdentity implements Serializable {

    @NotNull
    private Integer facturaId;

    @NotNull
    private Integer empresaId;

    public FacturaIdentity() {

    }

    public FacturaIdentity(Integer facturaId, Integer companyId) {
        this.facturaId = facturaId;
        this.empresaId = companyId;
    }


    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacturaIdentity that = (FacturaIdentity) o;

        if (!facturaId.equals(that.facturaId)) return false;
        return empresaId.equals(that.empresaId);
    }

    @Override
    public int hashCode() {
        int result = facturaId.hashCode();
        result = 31 * result + empresaId.hashCode();
        return result;
    }
}
