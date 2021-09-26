/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.api;

import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;

/**
 * Interfaz para el manejo de la solicitud.
 * 
 * @author Softtek
 * 
 */
public interface RegistroSolicitudRRLFacade {

    /**
     * M&eacute;todo para obtener los datos de la solicitud a partir
     * del idenificador.
     * 
     * @param idSolicitud
     *            identificador de la solicitud.
     * @return {@link SolicitudDTO} datos de la solicitud.
     */
    SolicitudDTO obtenerSolicitudPorId(Long idSolicitud);

}
