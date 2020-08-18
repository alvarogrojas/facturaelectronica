package com.rfs.utils;

import com.rfs.domain.Recibo;
import com.rfs.domain.Role;

import java.util.List;

/**
 * Created by alvaro on 10/24/17.
 */
public class Helper {
    public static final Integer CUSTOM_PAGE_SIZE = 500;

    public static final String CLIENTE_ACTIVO = "ACTIVO";
    public static final String CLIENTE_INACTIVO = "INACTIVO";

    //TODO: USER ID taken from security context


    public static Boolean isAdmin(List<Role> roles) {
        Boolean result = false;
        for (Role r: roles) {
            if (r.getNombre().equals(RolesEnum.ADMIN.toString())) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean isAdminOrCoordinador(List<Role> roles) {
        Boolean result = false;
        for (Role r: roles) {
            if (r.getNombre().equals(RolesEnum.ADMIN.toString()) || r.getNombre().equals(RolesEnum.COORDINADOR.toString())) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean estaFinalizado(Recibo recibo) {
        Boolean result = false;
//        if (recibo!=null && recibo.getEstado().equals(EstadosEnum.FINALIZADO.toString())) {
//            result = true;
//        }
        return result;
    }
}
