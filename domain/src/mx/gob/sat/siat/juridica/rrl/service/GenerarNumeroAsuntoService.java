/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service;

import java.io.Serializable;

/**
 * 
 * @author softtek
 */
public interface GenerarNumeroAsuntoService extends Serializable {

    /**
     * Metodo para obtener una secuencia
     * 
     * @param tipoSecuencia
     * @param claveUno
     * @param claveDos
     * @return
     */
    String obtenerSecuencia(String tipoSecuencia, String claveUno, String claveDos);

}
