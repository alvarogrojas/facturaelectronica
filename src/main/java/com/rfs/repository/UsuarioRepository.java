package com.rfs.repository;

import com.rfs.domain.Usuario;
import com.rfs.dtos.EncargadoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Usuario findById(Integer id);

    Usuario findByUsuario(String usuario);

    public List<Usuario> findByEstado(String estado);

    @Query(value = "select new com.rfs.dtos.EncargadoDTO(u.id, u.nombre,u.estado, 1l) from Usuario u where estado='ACTIVO' order by u.id desc")
    public List<EncargadoDTO> findEncargadosByEstadoActivo();

}
