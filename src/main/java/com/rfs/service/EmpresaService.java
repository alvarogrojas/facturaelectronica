package com.rfs.service;

import com.rfs.domain.*;
import com.rfs.repository.*;
import com.rfs.service.factura.billapp.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.openxmlformats.schemas.wordprocessingml.x2006.main.STAlgClass.HASH;

/**
 * Created by kevin on 04/10/18.
 */
@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaTarifaRepository empresaTarifaRepository;

    @Autowired
    private TipoCambioRepository tipoCambioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FacturaConsecutivoRepository facturaConsecutivoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TipoCambioService tipoCambioService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Transactional
    public Empresa save(Empresa empresa) throws NoSuchAlgorithmException {
        Empresa result = null;
        Usuario u = new Usuario();
        EmpresaTarifa et = new EmpresaTarifa();
        FacturaConsecutivo f = new FacturaConsecutivo();
        TipoCambio t = new TipoCambio();

        if (empresa.getId() != null){
            Empresa empresaTempora = this.getEmpresa(empresa.getId());
            empresaTempora.setNombre(empresa.getNombre());
            empresaTempora.setIniciales(empresa.getIniciales());
            empresaTempora.setCorreo1(empresa.getCorreo1());
            empresaTempora.setCorreo2(empresa.getCorreo2());
            empresaTempora.setTelefono(empresa.getTelefono());
            empresaTempora.setDireccion(empresa.getDireccion());
            empresaTempora.setUsuario(empresa.getUsuario());
            empresaTempora.setClave(empresa.getClave());
            empresaTempora.setPin(empresa.getPin());
            empresaTempora.setKeyPath(empresa.getKeyPath());
            empresaTempora.setLogoPath(empresa.getLogoPath());
            empresaTempora.setEstado(empresa.getEstado());
            empresaTempora.setCedula(empresa.getCedula());
            empresaTempora.setTipo(empresa.getTipo());
            empresaTempora.setProvincia(empresa.getProvincia());
            empresaTempora.setCanton(empresa.getCanton());
            empresaTempora.setDistrito(empresa.getDistrito());
            empresaTempora.setCantidad(empresa.getCantidad());
            empresaTempora.setRecepcionCorreos(empresa.getRecepcionCorreos());
            empresaTempora.setTipoMedida(empresa.getTipoMedida());
            empresaTempora.setUltimoCambioId(usuarioService.getCurrentLoggedUserId());
            empresaTempora.setFechaUltimoCambio(new Date());
            result = empresaRepository.save(empresaTempora);
            emailSenderService.sendActualizaCuenta(empresaTempora);

        }
        else{
            empresa.setAgregadoPorId(usuarioService.getCurrentLoggedUserId());
            empresa.setFechaAgregado(new Date());
            empresa.setApiUri("https://api.comprobanteselectronicos.go.cr/recepcion/v1/");
            empresa.setIdpUri("https://idp.comprobanteselectronicos.go.cr/auth/realms/rut/protocol/openid-connect");
            empresa.setIdpClientId("api-prod");
            result = empresaRepository.save(empresa);
            saveUsuario(u,empresa);
            saveTarifa(et,empresa);
            saveConsecutivo(f,empresa);
            saveTipoCambio(t,empresa);

        }
        return result;

    }

    private void saveTarifa(EmpresaTarifa et, Empresa e){
        SimpleDateFormat sm = new SimpleDateFormat("MM-yyyy");

            et.setEmpresaId(e.getId());
            String strDate = sm.format(new Date());
            et.setMesAno(strDate);
            et.setCantidad(0);
            empresaTarifaRepository.save(et);

    }

    private void saveConsecutivo(FacturaConsecutivo f, Empresa e){

            f.setEmpresaId(e.getId());
            f.setActualId(0);
            f.setEmpresa("FACTURA");
            facturaConsecutivoRepository.save(f);

            f = new FacturaConsecutivo();
            f.setEmpresaId(e.getId());
            f.setActualId(0);
            f.setEmpresa("FE");
            facturaConsecutivoRepository.save(f);

            f = new FacturaConsecutivo();
            f.setEmpresaId(e.getId());
            f.setActualId(0);
            f.setEmpresa("NCE");
            facturaConsecutivoRepository.save(f);

            f = new FacturaConsecutivo();
            f.setEmpresaId(e.getId());
            f.setActualId(0);
            f.setEmpresa("NOTACREDITO");
            facturaConsecutivoRepository.save(f);

        f = new FacturaConsecutivo();
        f.setEmpresaId(e.getId());
        f.setActualId(0);
        f.setEmpresa("MRELocal");
        facturaConsecutivoRepository.save(f);

        f = new FacturaConsecutivo();
        f.setEmpresaId(e.getId());
        f.setActualId(0);
        f.setEmpresa("MRE");
        facturaConsecutivoRepository.save(f);

        f = new FacturaConsecutivo();
        f.setEmpresaId(e.getId());
        f.setActualId(0);
        f.setEmpresa("MCELocal");
        facturaConsecutivoRepository.save(f);

        f = new FacturaConsecutivo();
        f.setEmpresaId(e.getId());
        f.setActualId(0);
        f.setEmpresa("MCE");
        facturaConsecutivoRepository.save(f);


    }

    private void  saveUsuario(Usuario u, Empresa e) throws NoSuchAlgorithmException {

            u.setNombre(e.getNombre());
            u.setIniciales(e.getIniciales());
            u.setCorreo(e.getCorreo1());
            u.setTelefono(e.getTelefono());
            u.setDireccion(e.getDireccion());
            u.setUsuario(e.getCedula());

            String cedula12345 = e.getCedula();
            String lastFourDigits = "";
            cedula12345 = cedula12345.replace(" " , "");
            cedula12345 = cedula12345.replace("-","");
            cedula12345 = cedula12345.replaceAll("^\\dA-Za-z\\W","");

            if (cedula12345.length() > 4) {
                lastFourDigits = "18" + cedula12345.substring(cedula12345.length() - 4) + "123";
            } else {
                lastFourDigits = "18" + cedula12345 + "123";

            }

            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(
                    lastFourDigits.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            u.setClave(hexString.toString());
            u.setCedula(e.getCedula());
            u.setEstado("ACTIVO");
            u.setEmpresa(e);
            Role r = roleRepository.findOne(2l);
            if (r!=null) {
                List<Role> rl = new ArrayList<>();
                rl.add(r);
                u.setRoles(rl);
            }
            usuarioRepository.save(u);
            this.emailSenderService.sendNewCuenta(u,e,lastFourDigits);

    }

    private void saveTipoCambio(TipoCambio c, Empresa e){

            c.setNombre("Colon");
            c.setVenta(574.36);
            c.setCompra(588.00);
            c.setDescripcion("Moneda de Costa Rica");
            c.setEsDefault(1);
            c.setSimbol("¢");
            c.setUltimoCambioId(21);
            c.setFechaUltimoCambio(new Date());
            c.setEmpresa(e);
            tipoCambioRepository.save(c);

            c = new TipoCambio();
            c.setNombre("Dollar");
            c.setVenta(1.00);
            c.setCompra(1.00);
            c.setDescripcion("Moneda de USA");
            c.setEsDefault(0);
            c.setSimbol("$");
            c.setUltimoCambioId(21);
            c.setFechaUltimoCambio(new Date());
            c.setEmpresa(e);
            tipoCambioRepository.save(c);


    }

    public Iterable<Empresa> getEmpresas() {
        Iterable<Empresa> list = this.empresaRepository.findAll();//
        return list;
    }

    public Empresa getEmpresa(Integer id) {
        return this.empresaRepository.findById(id);
    }

}
