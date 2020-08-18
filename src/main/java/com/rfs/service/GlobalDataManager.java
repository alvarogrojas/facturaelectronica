package com.rfs.service;

import com.rfs.domain.FacturaConsecutivo;
import com.rfs.repository.FacturaConsecutivoRepository;
import com.rfs.repository.FacturaHuerfanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by alvaro on 11/2/17.
 */
@Service
public class GlobalDataManager {

    private static Integer currentReciboId = 0;

    private static Integer currentFacturaId = 0;

    private static Boolean isFromHuerfanas = false;

    @Autowired
    private FacturaHuerfanaRepository facturaHuerfanaRepository;

    @Autowired
    private FacturaConsecutivoRepository facturaConsecutivoRepository;

    public synchronized Integer getReciboNext() {
        currentReciboId = currentReciboId + 1;
        return currentReciboId;
    }

    public synchronized Integer getFacturaNext(Integer empresaId) {
        FacturaConsecutivo fc  = facturaConsecutivoRepository.findByEmpresaIdAndEmpresa(empresaId,"FACTURA");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        currentFacturaId = fc.getActualId();
        return currentFacturaId;
    }

    public synchronized Integer getNotaCreditoNext(Integer empresaId) {

        FacturaConsecutivo fc  = facturaConsecutivoRepository.findByEmpresaIdAndEmpresa(empresaId,"NOTACREDITO");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        currentFacturaId = fc.getActualId();
        return currentFacturaId;
    }


    @Transactional
    public synchronized Integer getFacturaElectronicaNext(Integer empresaId) {
        FacturaConsecutivo fc  = facturaConsecutivoRepository.findByEmpresaIdAndEmpresa(empresaId,"FE");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        currentFacturaId = fc.getActualId();
        return currentFacturaId;
    }

    public synchronized Integer getNotaCreditoElectronicaNext(Integer empresaId) {
        FacturaConsecutivo fc  = facturaConsecutivoRepository.findByEmpresaIdAndEmpresa(empresaId,"NCE");
        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        currentFacturaId = fc.getActualId();
        return currentFacturaId;
    }
    public synchronized void goBackFacturaId() {
        if (!isFromHuerfanas) {
            currentFacturaId = currentFacturaId - 1;
        }
    }

    public synchronized Integer getConfirmacionNext(Integer empresaId) {
//        FacturaConsecutivo fc  = facturaConsecutivoRepository.findByEmpresa("MCE");
        FacturaConsecutivo fc  = facturaConsecutivoRepository.findByEmpresaIdAndEmpresa(empresaId,"MCE");

        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        currentFacturaId = fc.getActualId();
        return currentFacturaId;
    }

    public synchronized Integer getConfirmacionRechazoNext(Integer empresaId) {
//        FacturaConsecutivo fc  = facturaConsecutivoRepository.findByEmpresa("MRE");
        FacturaConsecutivo fc  = facturaConsecutivoRepository.findByEmpresaIdAndEmpresa(empresaId,"MRE");

        fc.setActualId(fc.getActualId() + 1);
        facturaConsecutivoRepository.save(fc);
        currentFacturaId = fc.getActualId();
        return currentFacturaId;
    }

//    public synchronized Integer getConfirmacionLocalNext() {
//        FacturaConsecutivo fc  = facturaConsecutivoRepository.findByEmpresa("MCELocal");
//        fc.setActualId(fc.getActualId() + 1);
//        facturaConsecutivoRepository.save(fc);
//        currentFacturaId = fc.getActualId();
//        return currentFacturaId;
//    }
//
//    public synchronized Integer getConfirmacionRechazoLocalNext() {
//        FacturaConsecutivo fc  = facturaConsecutivoRepository.findByEmpresa("MRELocal");
//        fc.setActualId(fc.getActualId() + 1);
//        facturaConsecutivoRepository.save(fc);
//        currentFacturaId = fc.getActualId();
//        return currentFacturaId;
//    }


    protected static void setGlobalFactura(Integer currentFacturaId) {
        GlobalDataManager.currentFacturaId = currentFacturaId;
    }

    protected static void setGlobalReciboConsecutivo(Integer currentReciboId) {
        GlobalDataManager.currentReciboId = currentReciboId;
    }
}
