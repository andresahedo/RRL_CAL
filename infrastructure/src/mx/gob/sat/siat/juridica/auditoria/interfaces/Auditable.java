/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.auditoria.interfaces;

import mx.gob.sat.siat.juridica.auditoria.dto.BitacoraDTO;

/**
 * Interfaz para los servicios de bitacora
 * 
 * @author softtek
 * 
 */
public interface Auditable {

    /**
     * Metodo que agregara un movimiento
     */
    BitacoraDTO getBitacoraDTO();

    void guardarDatosBitacora(int idProceso, int idMovimiento, String observaciones);

    void guardarObservacionesBitacora(String observaciones);

}
