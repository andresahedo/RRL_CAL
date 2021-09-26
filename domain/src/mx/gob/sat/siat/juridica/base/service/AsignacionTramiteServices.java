/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.AsignacionTramite;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface AsignacionTramiteServices extends Serializable {

    AsignacionTramite guardarAsignacionTramite(String idTramite, String rfc, String rol);

    AsignacionTramite buscarAsignacionesRolAsunto(String idTramite, String rol);

    List<AsignacionTramite> buscarAsignacionesByRFC(String rfc);

    List<AsignacionTramite> buscarAsignacionesByIdAsunto(String idAsunto);

}
