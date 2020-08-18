package com.rfs.service;

import com.rfs.domain.Parametro;
import org.springframework.stereotype.Component;

/**
 * Created by alvaro on 11/2/17.
 */
@Component
public class ParametroService {

    public Boolean getBooleanValue(Parametro parametro) {
        Boolean result = false;
        if (parametro!=null && parametro.getTipo().equals("BOOLEAN")) {
            result = Boolean.valueOf(parametro.getValor());
        } else {
            String msg = parametro==null ? " Is a NULL value": " Tipo esperado: BOOLEAN, tipo encontrado: " + parametro.getTipo();
            throw new RuntimeException("Not a valid parameter value. " + msg);
        }
        return result;
    }

    public Integer getIntegerValue(Parametro parametro) {
        Integer result = 0;
        if (parametro!=null && parametro.getTipo().equals("INTEGER")) {
            result = Integer.valueOf(parametro.getValor());
        } else {
            String msg = parametro==null ? " Is a NULL value": " Tipo esperado: BOOLEAN, tipo encontrado: " + parametro.getTipo();
            throw new RuntimeException("Not a valid parameter value. " + msg);
        }
        return result;
    }
}
