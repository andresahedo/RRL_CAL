/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface InformacionUsuarioDao extends Serializable {

    /**
     * Metodo para insertar un rol de usuario
     * 
     * @param usuario
     * @return void
     */

    void insertarRolUsuario(InformacionUsuario informacionUsuario);

    /**
     * Metodo para verificar si un usuario esta activo.
     * 
     * @param idUsuario
     * @return
     */
    InformacionUsuario verificarUsuarioActivo(String idUsuario);

    List<InformacionUsuario> consultarRolesFuncionario(String idUsuario);

    /**
     * Metodo para buscar Informacion de Usuario por Rol
     * 
     * @param idRol
     * @return objeto InformacionUsuario
     */
    List<InformacionUsuario> buscarUsuarioPorRol(String idRol);

    /**
     * M&eacute;todo para buscar todos los roles por id de usuario
     * 
     * @param idUsuario
     * @return lista de roles
     */
    List<InformacionUsuario> buscarRolesPorId(String idUsuario);

    /**
     * M&eacute;todo para buscar los roles activos por id de usuario
     * 
     * @param idUsuario
     * @return lista de roles
     */
    List<InformacionUsuario> buscarRolesActivosPorId(String idUsuario);

    /**
     * M&eacute;todo para dar de baja el rol de un usuario
     * 
     * @param infoUsu
     */
    void modificarRolUsuario(InformacionUsuario infoUsu);

    /**
     * Buscar el rol de un usuario
     */
    InformacionUsuario buscarUsuarioPorId(String idUsuario, String idRol);
}
