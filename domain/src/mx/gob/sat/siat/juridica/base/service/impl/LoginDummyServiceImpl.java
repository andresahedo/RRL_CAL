/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.PersonasInternasDAO;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.service.LoginDummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Service("loginDummyService")
public class LoginDummyServiceImpl extends BaseSerializableBusinessServices implements LoginDummyService {

    /**
     * Id de la version
     */
    private static final long serialVersionUID = -5689906931342197380L;

    /**
     * Atributo privado "personasInternasDAO" tipo PersonasInternasDAO
     */
    @Autowired
    private PersonasInternasDAO personasInternasDAO;

    /**
     * Metodo para obtener a las personas internas
     * 
     * @return Lista de personas internas
     */
    @Override
    public List<PersonaInterna> obtenerPersonasInternas() {
        return personasInternasDAO.getPersonasInternas();
    }

    /**
     * Metodo para obtener una persona interna por id
     * 
     * @param idPersona
     * @return PersonaInterna
     */
    @Override
    public PersonaInterna obtenerPersonaInternaById(Long idPersona) {
        return personasInternasDAO.getPersonaInternaById(idPersona);
    }

    /**
     * Metodo para obtener una persona interna por rfc
     * 
     * @param rfc
     * @return PersonaInterna
     */
    @Override
    public PersonaInterna obtenerPersonaInternaByRFC(String rfc) {
        return personasInternasDAO.getPersonaInternaByRFC(rfc);
    }

}
