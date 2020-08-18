package com.rfs.service;

import com.rfs.domain.TipoCambio;
import com.rfs.repository.TipoCambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TipoCambioService {
    @Autowired
    private TipoCambioRepository tipoCambioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public TipoCambio save(TipoCambio c) {
        Date currentDate = new Date();
        c.setFechaUltimoCambio(currentDate);
        c.setEmpresa(this.usuarioService.getCurrentLoggedUser().getEmpresa());

        c.setUltimoCambioId(usuarioService.getCurrentLoggedUserId());
        return this.tipoCambioRepository.save(c);
    }

    public TipoCambio getTipoCambio(Integer id) {
        return this.tipoCambioRepository.findById(id);
    }

    public Iterable<TipoCambio> getTiposCambio() {
        return this.tipoCambioRepository.findByEmpresaId(usuarioService.getCurrentLoggedUser().getEmpresa().getId());
    }
}
