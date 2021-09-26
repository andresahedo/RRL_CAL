/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Turnar;

/**
 * Clase Dao para la Reasignacion y turnado de asuntos
 * 
 * @author Softtek - EQG
 * @since 14/11/2014
 */
public interface TurnarDao {

    /**
     * Metodo para guardar las observaciones del turnado
     * 
     * @param datosTurnar
     */
    void guardaDatosTurnar(Turnar datosTurnar);

    /**
     * Metodo para obtener la descripcion de la observacion del
     * turnado
     * 
     * @param numeroAsunto
     * @return String descObservacion
     */
    String obtenerObservacionTurnado(String numeroAsunto);

}
