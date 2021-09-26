package mx.gob.sat.siat.juridica.configuracion.usuarios.service.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.RolesDao;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.RolesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServicesImpl implements RolesServices {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private RolesDao rolesDao;

    @Override
    public List<Role> obtenerRoles() {
        return rolesDao.obtenerRoles();
    }

    @Override
    public Role obtenerRolePorIdRol(String claveRol) {
        return rolesDao.obtenerRolePorIdRol(claveRol);
    }

}
