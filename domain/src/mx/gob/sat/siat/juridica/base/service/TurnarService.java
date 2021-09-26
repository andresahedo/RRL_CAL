/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Turnar;

import java.io.Serializable;

/**
 * Clase Service para la Reasignacion y turnado de asuntos
 * 
 * @author Softtek - EQG
 * @since 14/11/2014
 */
public interface TurnarService extends Serializable {

    /**
     * Metodo para guardar las observaciones del turnado
     * 
     * @param datosTurnar
     */
    void guardarDatosTurnado(Turnar datosTurnar);

    /**
     * Metodo para obtener la descripcion de la observacion del
     * turnado
     * 
     * @param numeroAsunto
     * @return String descObservacion
     */
    String obtenerObservacionTurnado(String numeroAsunto);

}
