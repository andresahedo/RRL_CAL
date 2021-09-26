package mx.gob.sat.siat.juridica.configuracion.usuarios.dao;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;

import java.util.List;

public interface RolesDao {

    List<Role> obtenerRoles();

    Role obtenerRolePorIdRol(String claveRol);

}
