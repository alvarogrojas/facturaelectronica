package com.rfs.service;

import com.rfs.domain.Cliente;
import com.rfs.domain.Tarifa;
import com.rfs.dtos.ClienteTarifasDTO;
import com.rfs.repository.ClienteRepository;
import com.rfs.repository.ServicioCorreduriaAduaneraRepository;
import com.rfs.repository.TarifaRepository;
import com.rfs.repository.TipoCambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alvaro on 10/24/17.
 */
@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TipoCambioRepository tipoCambioRepository;

    @Autowired
    private ServicioCorreduriaAduaneraRepository servicioCorreduriaAduaneraRepository;

    public Tarifa save(Tarifa t) {
        return tarifaRepository.save(t);
    }

    public Tarifa update(Tarifa t) {
        return tarifaRepository.save(t);
    }


    public ClienteTarifasDTO getTarifas(Integer id) {
        ClienteTarifasDTO dto = new ClienteTarifasDTO();
        dto.setTarifas(tarifaRepository.findByClienteId(id));
        Cliente c = clienteRepository.findById(id);
        dto.setServicios(this.servicioCorreduriaAduaneraRepository.findAll());
        dto.setId(id);
        if (c!=null) {
            dto.setNombre(c.getNombre());
        }
        dto.setTipoCambios(tipoCambioRepository.findAll());
        return dto;
    }

    public void delete(Tarifa t) {
        this.tarifaRepository.delete(t);
    }

    public void delete(Long id) {
        this.tarifaRepository.delete(id);
    }
}
