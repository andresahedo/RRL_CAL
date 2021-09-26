/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import java.io.Serializable;



/**
 * 
 * @author Softtek
 * 
 */
public interface NumeroAsuntoServices extends Serializable {

    Integer obtenerSecuencia(String claveMapaFolios, Integer secuenciaActual);    
    
    void reiniciarSecuencias();
}
