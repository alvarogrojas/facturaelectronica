package com.rfs.repository;

import com.rfs.domain.UsuarioRole;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRoleRepository {
        //extends CrudRepository<UsuarioRole, Integer> {

    public UsuarioRole findByUsuarioId(Integer usuarioId);

}
