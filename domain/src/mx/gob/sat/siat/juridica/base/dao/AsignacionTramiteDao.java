/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.AsignacionTramite;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface AsignacionTramiteDao extends Serializable {

    List<AsignacionTramite> buscarAsignacionesByRFC(String rfc);

    List<AsignacionTramite> buscarAsignacionesByIdAsunto(String idAsunto);

    AsignacionTramite buscarAsignacionesRolAsunto(String numAsunto, String rol);

    AsignacionTramite guardarAsignacion(AsignacionTramite asignacionTramite);

}
