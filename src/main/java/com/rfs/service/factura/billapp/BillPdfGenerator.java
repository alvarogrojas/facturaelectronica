package com.rfs.service.factura.billapp;

import com.rfs.domain.factura.BillSenderDetail;
import com.rfs.fe.v43.FacturaElectronica;


public interface BillPdfGenerator {

    void generatePdf(FacturaElectronica fe, BillSenderDetail b);

}
