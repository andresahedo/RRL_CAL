package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.UsuarioFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Pais;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.InformacionUsuarioServices;
import mx.gob.sat.siat.juridica.base.service.PersonaServices;
import mx.gob.sat.siat.juridica.base.service.UserStateServices;
import mx.gob.sat.siat.juridica.idc.service.IdcJuridicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("usuarioFacade")
public class UsuarioFacadeImpl extends BaseFacadeImpl implements UsuarioFacade {

    private static final long serialVersionUID = 1L;
    private transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private transient PersonaServices personaServices;

    @Autowired
    private transient InformacionUsuarioServices informacionUsuarioServices;

    @Autowired
    private transient UserStateServices userStateServices;

    @Autowired
    private transient IdcJuridicoService idcJuridicoService;

    public void generarContribuyente(UserProfileDTO userProfileDTO) {
        try {
            logger.debug("generarContribuyente");
            // Buscamos al usuario y a la persona, as� como sus datos
            // de idc
            UserState usuario = userStateServices.buscarUsuario(userProfileDTO.getRfc());
            logger.debug("usuario {} ", usuario);

            Solicitante sol = idcJuridicoService.buscarContribuyenteIdc(userProfileDTO.getRfc(), new Solicitante());
            logger.debug("sol {} ", sol);
            // Obtenemos el tipo de persona de que se trata
            String tipoPersona = "";
            String rolPersona = "";
            Persona nuevaPersona = null;
            if (sol != null) {
                logger.debug("sol.getPersonaMoral() {} ", sol.getPersonaMoral());
                if (sol.getPersonaMoral()) {
                    rolPersona = TipoRol.PERSONA_MORAL.getClave();
                    tipoPersona = DiscriminadorConstants.PERSONA_MORAL;
                    nuevaPersona = new PersonaMoral();
                }
                else {
                    rolPersona = TipoRol.PERSONA_FISICA.getClave();
                    tipoPersona = DiscriminadorConstants.PERSONA_FISICA;
                    nuevaPersona = new PersonaFisica();
                }
            }
            logger.debug("userProfileDTO.getRfc() {} ", userProfileDTO.getRfc());
            logger.debug("tipoPersona() {} ", tipoPersona);
            Persona persona = personaServices.obtenerPersona(userProfileDTO.getRfc(), tipoPersona);
            logger.debug("persona {} ", persona);
            // Si no existe el usuario se crea
            if (usuario == null) {
                logger.debug("creando al usuario");
                // Creacion del usuario
                usuario = new UserState(userProfileDTO.getRfc(), "1234567");
                userStateServices.insertarUsuario(usuario);

                // Se le asigna el rol de solicitante
                this.crearRolUsuario(userProfileDTO.getRfc(), TipoRol.SOLICITANTE.getClave());

                // Se le crea el rol de F�sica o Moral
                this.crearRolUsuario(userProfileDTO.getRfc(), rolPersona);
            }

            // Si no existe la persona se crea
            if (persona == null) {
                logger.debug("creando la persona");
                nuevaPersona.setRfc(userProfileDTO.getRfc());
                nuevaPersona.setClaveUsuario(userProfileDTO.getRfc());
                nuevaPersona.setNombre(sol.getNombre());
                nuevaPersona.setApellidoPaterno(sol.getApellidoPaterno());
                nuevaPersona.setApellidoMaterno(sol.getApellidoMaterno());
                nuevaPersona.setCorreoElectronico(sol.getCorreoElectronico());
                nuevaPersona.setRazonSocial(sol.getRazonSocial());
                Pais pais = new Pais("MEX");
                nuevaPersona.setPaisOrigen(pais);

                personaServices.insertarPersona(nuevaPersona);
            }

        }
        catch (Exception e) {
            // TODO Auto-generated catch block

            logger.error("Error en generarContribuyente {}", e);
        }
    }

    private void crearRolUsuario(String rfc, String rol) {
        InformacionUsuarioPK informacionUsuarioPK = new InformacionUsuarioPK();
        informacionUsuarioPK.setIdUsuario(rfc);
        informacionUsuarioPK.setIdRol(rol);
        InformacionUsuario informacionUsuario = new InformacionUsuario();
        informacionUsuario.setInformacionUsuarioPK(informacionUsuarioPK);
        informacionUsuario.setFechaInicio(new Date());
        informacionUsuarioServices.insertarRolUsuario(informacionUsuario);
    }

}
