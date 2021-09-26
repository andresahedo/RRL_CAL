package mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosAUDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

@Component
public class RoleTransformer extends DTOTransformer<Role, PermisosAUDTO> {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Override
    public PermisosAUDTO transformarDTO(Role rol) {
        PermisosAUDTO permiso = new PermisosAUDTO();
        permiso.setPermiso(rol.getDescription());
        permiso.setIdRol(rol.getName());
        permiso.setPermisoNovell(rol.getRolNovell());
        return permiso;
    }

}
