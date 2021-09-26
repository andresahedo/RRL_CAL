package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.LoginFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.ConsultaSolicitudServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("loginFacade")
@Scope("request")
public class LoginFacadeImpl extends BaseFacadeImpl implements LoginFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 5586907206068705190L;
    @Autowired
    private transient ConsultaSolicitudServices consultaSolicitudServices;

    public Map<String, String> getUsers() {
        Map<String, String> usuarios = new HashMap<String, String>();

        List<Persona> personas = consultaSolicitudServices.obtenerUsuarios();
        for (Persona personaInterna : personas) {
            if (personaInterna.getRazonSocial() == null) {
                usuarios.put(personaInterna.getNombre() + " " + personaInterna.getApellidoPaterno(),
                        personaInterna.getRfc());
            }
            else {
                usuarios.put(personaInterna.getRazonSocial(), personaInterna.getRfc());
            }
        }

        return usuarios;
    }

    public Map<String, String> getRoles() {
        Map<String, String> roles = new HashMap<String, String>();

        roles.put("Contribuyente", "Contribuyente");
        roles.put("Abogado", "Abogado");
        roles.put("Administrador", "Administrador");
        roles.put("Oficial de Partes", "OficialPartes");
        roles.put("Administrador Unidad Administrativa", "AdministradorUnidadAdministrativa");
        roles.put("Administrador Global", "AdministradorGlobal");
        return roles;
    }

    public String login() {
        return null;
    }

    @Override
    public UserProfileDTO buscarUsuario(UserProfileDTO user) {
        UserProfileDTO userR = new UserProfileDTO();

        return userR;
    }
}
