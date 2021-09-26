/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;

import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface PersonasInternasDAO {
    /**
     * Metodo para obtener una lista de personas internas
     * 
     * @return lista de Personas internas
     */
    List<PersonaInterna> getPersonasInternas();

    /**
     * Metodo para obtener una lista de personas internas por id
     * 
     * @param idPersona
     * @return Persona interna
     */
    PersonaInterna getPersonaInternaById(Long idPersona);

    /**
     * Metodo para obtener una persona interna por rfc
     * 
     * @param rfc
     * @return Persona interna
     */
    PersonaInterna getPersonaInternaByRFC(String rfc);

    /**
     * Metodo para obtener una persona interna por numero de empleado
     * 
     * @param numEmpleado
     * @throws Exception
     * 
     */

    PersonaInterna buscarEmpleado(Long numEmpleado);

}
