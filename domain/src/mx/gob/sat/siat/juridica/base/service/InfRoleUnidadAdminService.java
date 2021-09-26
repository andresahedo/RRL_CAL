/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.InfRoleUnidadAdmin;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;

import java.util.List;

/**
 * Service de InfRoleUnidadAdmin
 * 
 * @author softtek
 * 
 */
public interface InfRoleUnidadAdminService {

    List<InfRoleUnidadAdmin> obtenerPermisosPorUnidadAdministratuva(String idUnidadAdmin, String modalidad,
            String numeroEmpleado, String permiso, String rfcEmpleado, String tipoTramite);

    List<UnidadAdministrativa> obtenerUnidadesAUA(String rfc);

}
