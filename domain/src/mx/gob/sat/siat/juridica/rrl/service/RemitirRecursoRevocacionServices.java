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
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.rrl.util.exception.RemitirAsuntoException;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 */
public interface RemitirRecursoRevocacionServices extends Serializable {

    /**
     * Metodo para obtener una lista de unidades administrativas
     * 
     * @return Lista
     */
     List<UnidadAdministrativa> obtenerCatalogo();

    /**
     * Metoodo para remitir
     * 
     * @param NumAsunto
     * @param unidadAdministrativa
     * @param idTarea
     * @param rfcAsignar
     * @param ideTareaOrigen
     */
     void remitir(String numAsunto, String unidadAdministrativa, Long idTarea, String rfcAsignar,
            String rfcUsuario, String ideTareaOrigen) throws RemitirAsuntoException;

    /**
     * Metodo para obtener una lista de personas por rol
     * 
     * @param tramite
     * @return Lista de personas
     */
    List<Persona> obtenerPersonaByRol(Tramite tramite);

    /**
     * Metodo para enviar un correo
     * 
     * @param numeroAsunto
     * @param rfc
     */
    void enviarCorreo(String numeroAsunto, String rfc, String tarea);

    Tramite obtenerTramitePorId(String idTramite);

    String getDefaultIdeTareaOrigen();

}
