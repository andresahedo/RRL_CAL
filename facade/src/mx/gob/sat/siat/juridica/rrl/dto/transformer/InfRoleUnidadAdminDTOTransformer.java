package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;
import mx.gob.sat.siat.juridica.base.dto.InfRoleUnidadAdminDTO;
import mx.gob.sat.siat.juridica.base.service.PersonaServices;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.RolesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfRoleUnidadAdminDTOTransformer extends DTOTransformer<InfRoleUnidadAdmin, InfRoleUnidadAdminDTO> {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private PersonaServices personaServices;

    @Autowired
    private TipoTramiteServices tipoTramiteServices;

    @Autowired
    private UnidadAdministrativaServices unidadAdministrativaServices;

    @Autowired
    private RolesServices rolesServices;

    @Override
    public InfRoleUnidadAdminDTO transformarDTO(InfRoleUnidadAdmin unidadAdmin) {

        UnidadAdministrativa unidadAdministrativa =
                unidadAdministrativaServices.obtenerUnidadPorId(unidadAdmin.getUnidadAdminPK()
                        .getClaveUnidadAdministrativa().toString());
        if (unidadAdministrativa != null) {
            Persona empleado =
                    personaServices.obtenerPersonaPorRFCUsuarios(unidadAdmin.getUnidadAdminPK().getClaveUsuario());
            TipoTramite tipoTramite =
                    tipoTramiteServices.obtenerTipoTramiteId(unidadAdmin.getUnidadAdminPK().getIdeTipoTramite()
                            .intValue());
            Role rol = rolesServices.obtenerRolePorIdRol(unidadAdmin.getUnidadAdminPK().getClaveRol());
            InfRoleUnidadAdminDTO unidad = new InfRoleUnidadAdminDTO();
            unidad.setNombreUnidad(unidadAdministrativa.getNombre());
            unidad.setNumeroEmpleado(empleado.getNumeroEmpleado() != null ? empleado.getNumeroEmpleado().toString()
                    : "");
            unidad.setRfcUsuario(unidadAdmin.getUnidadAdminPK().getClaveUsuario());
            unidad.setNombreEmpleado(empleado.getNombre() + " " + empleado.getApellidoPaterno() + " "
                    + empleado.getApellidoMaterno());
            unidad.setTipoTramite(tipoTramite.getDescripcionServicio());
            unidad.setModalidad(tipoTramite.getDescripcionModalidad());
            unidad.setPermiso(rol.getDescription());
            unidad.setResponsable(unidadAdmin.isResponsable());
            unidad.setBlnActivo(unidadAdmin.getVigencia().getBlnActivo());
            String idTipo = unidadAdmin.getUnidadAdminPK().getIdeTipoTramite().toString();
            unidad.setIdTipoTramite(Integer.valueOf(idTipo));
            return unidad;
        }
        else {
            return null;
        }
    }

}
