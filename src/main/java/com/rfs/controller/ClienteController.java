package com.rfs.controller;

import com.rfs.domain.Cliente;
import com.rfs.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
    @PostMapping(path="/create")
    public @ResponseBody
    String createRecibo(@RequestBody Cliente c) {
        Cliente r = clienteService.save(c);
        return r.getNombre();
    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
    @GetMapping(path="/data")
    public @ResponseBody
    Cliente getCliente(@RequestParam Integer id) {

        return clienteService.getCliente(id);
    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USUARIO')")
	@GetMapping(path="/data/list")
    public @ResponseBody
    Iterable<Cliente> getClienteList() {
        return clienteService.getClientes();
    }
}
