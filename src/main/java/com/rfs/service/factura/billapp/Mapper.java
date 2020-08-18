package com.rfs.service.factura.billapp;

import javax.xml.datatype.DatatypeConfigurationException;

public interface Mapper {

    public final static String TIPO_DOCUMENTO_FACTURA_ELECTRONICA = "01";
    public final static String TIPO_DOCUMENTO_NOTA_CREDITO_ELECTRONICA = "03";

    public Object mapFacturaElectronica(Object o, String tipoDocumento) throws DatatypeConfigurationException;
}
