/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResultadoAdminResponsable;

import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface BalanceadorDAO {

    /**
     * Metodo para obtener una lista de roles por id
     * 
     * @param tipoTramite
     * @return
     */
    List<Role> getRolesByTipoTramite(String tipoTramite);

    /**
     * Metodo para obtener un administrador responsable
     * 
     * @param unidadAdministrativa
     * @param tipoTramite
     * @return
     */
    ResultadoAdminResponsable getAdministradorResponsable(String unidadAdministrativa, String tipoTramite);

    /**
     * Metodo para obtener un administrador responsable sin considerar
     * UnidadAdministrativaRelacionada
     * 
     * @param unidadAdministrativa
     * @param tipoTramite
     * @return
     */
    ResultadoAdminResponsable getAdministradorResponsableSinUAR(String unidadAdministrativa, String tipoTramite);

    /**
     * Metodo para obtener un administrador responsable de una unidad
     * local de recaudacion
     * 
     * @param unidadAdministrativa
     * @param tipoTramite
     * @return
     */
    ResultadoAdminResponsable getAdministradorResponsableUnidadLocalRecaudadora(String unidadAdministrativa,
            String tipoTramite);

    String getAdministradorResponsableRegistro(String unidadAdministrativa, String tipoTramite);

    boolean existeConfiguracionUnidadAdmin(String clave);

    ResultadoAdminResponsable getAdministradorResponsableUnidadLocalRecaudadoraCAL(String unidadAdministrativa,
            String tipoTramite);

    String getUnidadPorTipoRol(String tipoTramite, String tipoRolContribuyente);

}
