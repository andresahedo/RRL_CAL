/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface LoginDummyService extends Serializable {

    /**
     * Metodo para obtener a las personas internas
     * 
     * @return Lista de personas internas
     */
    List<PersonaInterna> obtenerPersonasInternas();

    /**
     * Metodo para obtener una persona interna por id
     * 
     * @param idPersona
     * @return PersonaInterna
     */
    PersonaInterna obtenerPersonaInternaById(Long idPersona);

    /**
     * Metodo para obtener una persona interna por rfc
     * 
     * @param rfc
     * @return PersonaInterna
     */
    PersonaInterna obtenerPersonaInternaByRFC(String rfc);

}
