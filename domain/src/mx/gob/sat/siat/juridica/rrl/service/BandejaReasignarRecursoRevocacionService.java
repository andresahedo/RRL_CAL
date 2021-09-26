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
import java.util.List;

/**
 * 
 * @author softtek
 */
public interface BandejaReasignarRecursoRevocacionService extends Serializable {

    /**
     * Metodo para obtener una unidad administrativa por rfc y calve
     * del rol
     * 
     * @param rfc
     * @param cveRol
     * @return Lista
     */
    List<String> obtenerUnidadAdministrativa(String rfc, String cveRol);

}
