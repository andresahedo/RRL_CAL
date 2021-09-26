package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.dao.InfRoleUnidadAdminDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.InfRoleUnidadAdmin;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.service.InfRoleUnidadAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfRoleUnidadAdminServiceImpl implements InfRoleUnidadAdminService {

    @Autowired
    private InfRoleUnidadAdminDao infRoleUnidadAdminDao;

    public List<InfRoleUnidadAdmin> obtenerPermisosPorUnidadAdministratuva(String idUnidadAdmin, String modalidad,
            String numeroEmpleado, String permiso, String rfcEmpleado, String tipoTramite) {
        return infRoleUnidadAdminDao.obtenerPermisosPorUnidadAdministratuva(
                (idUnidadAdmin != null && !idUnidadAdmin.equals("") ? idUnidadAdmin : null), (modalidad != null
                        ? modalidad : null), (numeroEmpleado != null && !numeroEmpleado.equals("") ? numeroEmpleado
                        : null), (permiso != null && !permiso.equals("") ? permiso : null), (rfcEmpleado != null
                        && !rfcEmpleado.equals("") ? rfcEmpleado : null),
                (tipoTramite != null && !tipoTramite.equals("") ? tipoTramite : null));
    }

    @Override
    public List<UnidadAdministrativa> obtenerUnidadesAUA(String rfc) {
        return infRoleUnidadAdminDao.obtenerUnidadesAUA(rfc);
    }
}
