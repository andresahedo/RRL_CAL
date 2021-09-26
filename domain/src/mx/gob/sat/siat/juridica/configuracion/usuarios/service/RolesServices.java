package mx.gob.sat.siat.juridica.configuracion.usuarios.service;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;

import java.io.Serializable;
import java.util.List;

public interface RolesServices extends Serializable {

    List<Role> obtenerRoles();

    Role obtenerRolePorIdRol(String claveRol);

}
