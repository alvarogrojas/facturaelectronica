package com.rfs.service.impl;

import com.rfs.domain.Role;
import com.rfs.domain.Usuario;
import com.rfs.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nydiarra on 06/05/17.
 */
@Component
public class AppUserDetailsService
        implements UserDetailsService {
        @Autowired
        private UsuarioRepository usuarioRepository;

          @Override
          public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
              Usuario user = usuarioRepository.findByUsuario(s);
              if (user == null) {
                  throw new UsernameNotFoundException(String.format("El usuario %s no existe", s));
              }
              if (user.getEmpresa().getEstado().toUpperCase().equals("INACTIVO")) {
                  throw new RuntimeException("El usuario se encuentra inactivo. Consultar con los administradores del sistema");
              }
              List<GrantedAuthority> authorities = new ArrayList<>();
              for (Role r : user.getRoles()) {
                  authorities.add(new SimpleGrantedAuthority(r.getNombre()));
              }
              UserDetails userDetails = new org.springframework.security.core.userdetails.
                      User(user.getUsuario(), user.getClave(), authorities);
              return userDetails;
          }
}
