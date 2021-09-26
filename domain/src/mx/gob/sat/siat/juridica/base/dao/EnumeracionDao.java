/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface EnumeracionDao {

    /**
     * M&eacute;todo para obtener los tipos de requerimiento por id de
     * enumeraci&oacute;n.
     * 
     * @param ideEnumeracionH
     * @return Lista de EnumeracionTr
     */
    List<EnumeracionTr> obtenerEnumeracionPorId(String ideEnumeracionH);

    String obtenerParametroByEnum(String enu);
    
    void guardarParametro(String llave, String descripcion, String valor, Date fecinivigencia, 
            Date fecfinvigencia, boolean activo);

    /**
     * Obtiene en registro de EnumeracionTr por su clave de
     * enumeracion
     * 
     * @param idEnumeracionD
     * @return
     */
    EnumeracionTr obtenerEnumeracionPorCveEnum(String idEnumeracionD);

}
