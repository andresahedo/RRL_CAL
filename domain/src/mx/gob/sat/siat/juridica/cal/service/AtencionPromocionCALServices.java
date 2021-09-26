/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.service;

import java.io.Serializable;

/**
 * 
 * @author Softtek
 */
public interface AtencionPromocionCALServices extends Serializable {

    /**
     * Actualiza el estado de la promocion a "En Estudio" dependiendo
     * del estado actual del tramite
     * 
     * @param idTarea
     * @param numAsunto
     */
    String getIdeTareaOrigen();
}
