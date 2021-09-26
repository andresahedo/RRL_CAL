/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.api.impl;

import mx.gob.sat.siat.juridica.base.api.impl.BaseCloudFacadeImpl;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.InfRoleUnidadAdmin;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.InfRoleUnidadAdminDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.InfRoleUnidadAdminService;
import mx.gob.sat.siat.juridica.cal.dto.transformer.TipoTramiteDTOTransformer;
import mx.gob.sat.siat.juridica.comun.service.ObtenerUnidadesAdministrativasService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ConsultarUsuariosFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.FiltroUsuariosDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PermisosAUDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer.RoleTransformer;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.ReasignarTareaService;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.RolesServices;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.InfRoleUnidadAdminDTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase DTO para el armado de los bienes resarcimeinto
 * 
 * @author Softtek - EQG
 * @since 08/10/2014
 */
@Component("consultarUsuariosFacade")
public class ConsultarUsuariosFacadeImpl extends BaseCloudFacadeImpl implements ConsultarUsuariosFacade {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    @Autowired
    private transient TipoTramiteDTOTransformer tipoTramiteDTOTransformer;

    @Autowired
    private transient ObtenerUnidadesAdministrativasService obtenerUnidadesAdministrativasService;

    @Autowired
    private RolesServices rolesServices;

    @Autowired
    private RoleTransformer roleTransformer;

    @Autowired
    private transient InfRoleUnidadAdminService infRoleUnidadAdminService;

    @Autowired
    private transient ReasignarTareaService reasignarTareaService;

    @Autowired
    private InfRoleUnidadAdminDTOTransformer permisoTransformer;

    @Override
    public List<CatalogoDTO> obtenerUnidadesAdministrativas() {
        return catalogoDTOTransformer.transformarDTO(obtenerUnidadesAdministrativasService
                .obtenerCatalogoUnidadesAdministrativas());
    }

    @Override
    public List<InfRoleUnidadAdminDTO> consultarPermisosUsuarios(FiltroUsuariosDTO filtroUsuarios) {
        List<InfRoleUnidadAdmin> listaUsuarios =
                infRoleUnidadAdminService.obtenerPermisosPorUnidadAdministratuva(filtroUsuarios.getIdUnidadAdmin(),
                        filtroUsuarios.getModalidad(), filtroUsuarios.getNumeroEmpleado(), filtroUsuarios.getPermiso(),
                        filtroUsuarios.getRfcEmpleado(), filtroUsuarios.getTipoTramite());
        List<InfRoleUnidadAdminDTO> listaUsuariosDTO = new ArrayList<InfRoleUnidadAdminDTO>();

        for (InfRoleUnidadAdmin lista : listaUsuarios) {
            listaUsuariosDTO.add(permisoTransformer.transformarDTO(lista));
        }
        return listaUsuariosDTO;
    }

    @Override
    public List<PermisosAUDTO> obtenerPermisos() {
        List<Role> listaRoles = rolesServices.obtenerRoles();
        List<PermisosAUDTO> listaPermisos = new ArrayList<PermisosAUDTO>();
        for (Role rol : listaRoles) {
            listaPermisos.add(roleTransformer.transformarDTO(rol));
        }
        return listaPermisos;
    }

    @Override
    public List<TipoTramiteDTO> obtenerTiposDeTramites() {
        return tipoTramiteDTOTransformer.transformarDTOList(reasignarTareaService.obtenerTiposDeTramite());
    }

    @Override
    public List<CatalogoDTO> obtenerUnidadAdministrativasUAByRfc(String rfc) {
        return catalogoDTOTransformer.transformarDTO(infRoleUnidadAdminService.obtenerUnidadesAUA(rfc));
    }
}
