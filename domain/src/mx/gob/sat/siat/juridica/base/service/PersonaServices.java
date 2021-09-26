/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud;

import java.io.Serializable;

/**
 * 
 * @author softtek
 * 
 */
public interface PersonaServices extends Serializable {

    Persona obtenerPersona(String rfc, String tipoPersona);

    void insertarPersona(Persona persona);

    Persona obtenerPersonaPorRFC(String rfc);

    Persona obtenerPersonaPorRFCUsuarios(String rfc);

    Persona obtenerPersonaPorRFCCorto(String rfc);

    /**
     * Metodo para obtener el rfc corto de una persona el rfc corto lo
     * tienen los funcionarios
     * 
     * @param rfc
     * @return rfcCorto del funcionario
     */
    String obtenerRfcCorto(String rfc);

    PersonaSolicitud buscarPersonaSolicitante(Long idSolicitud);

    String obtenerRfcCortoLike(String rfcFuncionario);

    PersonaInterna buscarPersonaPorRFC(String rfc);

}
