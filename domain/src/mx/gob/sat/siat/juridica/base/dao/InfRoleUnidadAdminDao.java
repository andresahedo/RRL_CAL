/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.InfRoleUnidadAdmin;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.InfRoleUnidadAdminPK;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface InfRoleUnidadAdminDao {

    List<InfRoleUnidadAdmin> obtenerPermisosPorUnidadAdministratuva(String idUnidadAdmin, String modalidad,
            String numeroEmpleado, String permiso, String rfcEmpleado, String tipoTramite);

    List<UnidadAdministrativa> obtenerUnidadesAUA(String rfc);

    void guardarModalidadesAsignadosUsuarios(List<InfRoleUnidadAdmin> modalidades);

    InfRoleUnidadAdmin consultarExistente(InfRoleUnidadAdminPK idInfRole);

    Boolean validarResponsable(InfRoleUnidadAdmin aux);

    List<InfRoleUnidadAdmin> obtenerPermisosPorFuncionario(String rfc, String unidadAdmin, String rol);

    List<InfRoleUnidadAdmin> obtenerPermisosPorFuncionario(String rfc);

    Boolean validarResponsableDuplicado(InfRoleUnidadAdminPK infRoleUnidadAdminPK);
}
