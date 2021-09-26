/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface TurnarRecursoRevocacionServices extends Serializable {

    /**
     * Metodo para obtener una lista de abogados
     * 
     * @param numAsunto
     * @return Persona
     */
    List<Persona> obtenerPersonaAbogados(String numAsunto);

    /**
     * Metodo para obtener un tramite por id
     * 
     * @param numAsunto
     * @return Tramite
     */
    Tramite obtenerTramitePorId(String numAsunto);

    /**
     * Metodo para enviar un correo
     * 
     * @param numeroAsunto
     * @param idPersona
     */
    void enviarCorreo(String numeroAsunto, Long idPersona, String nombreTarea);

    String getIdeTareaOrigen();
}
