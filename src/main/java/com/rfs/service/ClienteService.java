package com.rfs.service;

import com.rfs.domain.Cliente;
import com.rfs.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;


/**
 * Created by alvaro on 10/17/17.
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public Cliente save(Cliente cliente) {
        cliente.setUltimoCambioId(usuarioService.getCurrentLoggedUserId());
        cliente.setFechaUltimoCambio(new Date());
        cliente.setEmpresa(this.usuarioService.getCurrentLoggedUser().getEmpresa());
        Cliente result = clienteRepository.save(cliente);

        return result;

    }

    public Iterable<Cliente> getClientes() {
        return this.clienteRepository.findByEmpresaIdOrderByNombre(usuarioService.getCurrentLoggedUser().getEmpresa().getId());

    }

    public Cliente getCliente(Integer id) {
        return this.clienteRepository.findById(id);
    }
}
