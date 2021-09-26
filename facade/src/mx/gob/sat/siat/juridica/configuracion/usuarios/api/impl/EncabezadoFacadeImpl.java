package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.PersonaServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.EncabezadoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("encabezadoFacade")
public class EncabezadoFacadeImpl extends BaseFacadeImpl implements EncabezadoFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -3690944306599642756L;

    @Autowired
    private PersonaServices personaServices;

    public String obtenerNombre(String rfc) {
        Persona administrador = getPersonaServices().obtenerPersonaPorRFC(rfc);
        StringBuffer sb = new StringBuffer();
        sb.append(administrador.getNombre());
        sb.append(" ");
        sb.append(administrador.getApellidoPaterno());
        sb.append(" ");
        sb.append(administrador.getApellidoMaterno());
        return sb.toString();
    }

    public PersonaServices getPersonaServices() {
        return personaServices;
    }

    public void setPersonaServices(PersonaServices personaServices) {
        this.personaServices = personaServices;
    }

}
