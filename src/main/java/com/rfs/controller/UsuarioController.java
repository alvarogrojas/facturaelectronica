package com.rfs.controller;

import com.rfs.domain.Usuario;
import com.rfs.dtos.EstadisticaDTO;
import com.rfs.dtos.ResultDTO;
import com.rfs.dtos.UsuarioDTO;
import com.rfs.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

/**
 * Created by alvaro on 10/29/17.
 */
@Controller
@RequestMapping(path="/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
    @GetMapping(path="/data")
    public @ResponseBody UsuarioDTO getUsuarioData() {
        UsuarioDTO dto = this.usuarioService.getUsuarioData();

        return dto;
    }

    @PutMapping(path="/update/npw")
    public @ResponseBody ResultDTO resetPwdAndName(@RequestBody UsuarioDTO userDto) {
        Usuario user = this.usuarioService.getCurrentLoggedUser();
        try {
            String clave = user.getClave();
            String claveActual = this.usuarioService.getEncriptPwd(userDto.getClave());
            if(clave.equalsIgnoreCase(claveActual)){
                user.setNombre(userDto.getNombre());
                user.setClave(userDto.getClaveNueva());
                this.usuarioService.updateUsuario(user);
                return new ResultDTO("OK","El nombre del usuario y clave fueron actualizados, para reflejar los cambios, salga e ingrese nuevamente.", "");
            }else {
                return new ResultDTO("CLAVE_IVALID","La clave actual es diferente.", "");
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new ResultDTO("ERROR","Error al intentar modificar el usuario.", e.getMessage());
        }

    }

    @GetMapping(path="/estadistica")
    public @ResponseBody EstadisticaDTO getEstadisticas() {
        return this.usuarioService.getUsuarioEstadisticas();
    }
}
