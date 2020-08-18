package com.rfs.service;

import com.rfs.domain.Parametro;
import com.rfs.repository.ParametroRepository;
import com.rfs.utils.ParametroHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by alvaro on 11/2/17.
 */
@Component
public class IntegraInitializer {

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private FacturaService facturaService;



    @PostConstruct
    public void init() {

        waitForDatabaseService();

        Parametro reciboP = parametroRepository.findByNombre(ParametroHelper.RECIBO_INIT);
        Boolean wasRecibosInitialized = parametroService.getBooleanValue(reciboP);

        Parametro facturaP = parametroRepository.findByNombre(ParametroHelper.FACTURA_INIT);
        Boolean wasFacturasInitialized =  parametroService.getBooleanValue(facturaP);

        if (!wasRecibosInitialized) {
            initializeReciboConsecutivo(reciboP);
        } else {
            //GlobalDataManager.setGlobalReciboConsecutivo(this.reciboService.getNextRecibo().getId());
        }

        if (!wasFacturasInitialized) {
            initializeFacturaConsecutivo(facturaP);

        } else {
            //GlobalDataManager.setGlobalFactura(this.facturaService.getNexFactura().getId());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
        }
    }

    private void waitForDatabaseService() { // 10 Seconds
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initializeReciboConsecutivo(Parametro estaInit) {
        Parametro reciboP = parametroRepository.findByNombre(ParametroHelper.RECIBO_CONSECUTIVO);
        Integer consecutivo = parametroService.getIntegerValue(reciboP);
        GlobalDataManager.setGlobalReciboConsecutivo(consecutivo);

        estaInit.setValor("true");
        parametroRepository.save(estaInit);
    }

    private void initializeFacturaConsecutivo(Parametro estaInit) {
        Parametro facturaP = parametroRepository.findByNombre(ParametroHelper.FACTURA_CONSECUTIVO);
        Integer consecutivo = parametroService.getIntegerValue(facturaP);
        GlobalDataManager.setGlobalFactura(consecutivo);

        estaInit.setValor("true");
        parametroRepository.save(estaInit);
    }


}
