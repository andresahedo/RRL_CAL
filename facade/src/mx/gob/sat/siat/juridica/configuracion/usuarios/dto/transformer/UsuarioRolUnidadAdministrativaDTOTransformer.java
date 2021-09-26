package mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UserCredentials;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UserState;
import mx.gob.sat.siat.juridica.base.dao.domain.model.UsuarioRolUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.UnidadAdministrativaDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.PersonaServices;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioRolUnidadAdministrativaDTOTransformer extends
        DTOTransformer<UsuarioRolUnidadAdministrativa, UsuarioRolUnidadAdministrativaDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 8228436369425267700L;

    @Autowired
    private UnidadAdministrativaServices unidadService;

    @Autowired
    private UnidadAdministrativaDTOTransformer unidadDTOTransformer;

    @Autowired
    private PersonaServices personaServices;

    @Autowired
    private transient TipoTramiteServices tipoTramiteServices;

    @Override
    public UsuarioRolUnidadAdministrativaDTO transformarDTO(UsuarioRolUnidadAdministrativa usuarioRol) {

        UsuarioRolUnidadAdministrativaDTO usuarioRolDTO = new UsuarioRolUnidadAdministrativaDTO();
        usuarioRolDTO.setIdUsuarioRolUA(usuarioRol.getIdUsuarioRolUA());
        UnidadAdministrativaDTO unidad = new UnidadAdministrativaDTO();

        UnidadAdministrativa uniAdmin =
                unidadService.obtenerUnidadPorId(usuarioRol.getUnidadAdministrativa() != null ? usuarioRol
                        .getUnidadAdministrativa().getClave() : null);
        if (uniAdmin != null) {
            List<UnidadAdministrativa> lista = new ArrayList<UnidadAdministrativa>();
            lista.add(uniAdmin);
            List<UnidadAdministrativaDTO> listDTO = unidadDTOTransformer.transformarDTO(lista);
            unidad = listDTO.get(0);
        }
        usuarioRolDTO.setNumeroEmpleado("");
        usuarioRolDTO.setUnidadAdministrativa(unidad != null ? unidad : new UnidadAdministrativaDTO());
        usuarioRolDTO.setIdUsuario(usuarioRol.getUsuario() != null ? usuarioRol.getUsuario().getUserName() : null);
        usuarioRolDTO.setRol(usuarioRol.getRol() != null ? usuarioRol.getRol().getName() : "");
        usuarioRolDTO.setUniadminPrincipal(usuarioRol.getUniAdminPrincipal() != null ? usuarioRol
                .getUniAdminPrincipal() : false);
        usuarioRolDTO.setDescripcionRol(usuarioRol.getRol() != null ? usuarioRol.getRol().getDescription() : null);

        return usuarioRolDTO;
    }

    public UsuarioRolUnidadAdministrativa transformarModel(UsuarioRolUnidadAdministrativaDTO permiso) {
        Role rol = new Role();
        UsuarioRolUnidadAdministrativa nuevoPermiso = new UsuarioRolUnidadAdministrativa();
        nuevoPermiso.setIdUsuarioRolUA(permiso.getIdUsuarioRolUA() != null ? permiso.getIdUsuarioRolUA() : null);
        nuevoPermiso.setUniAdminPrincipal(permiso.isUniadminPrincipal());
        rol.setName(permiso.getRol());
        nuevoPermiso.setRol(rol);
        nuevoPermiso.setUnidadAdministrativa(unidadService.obtenerUnidadPorId(permiso.getUnidadAdministrativa()
                .getClave()));
        UserCredentials user = new UserCredentials();
        user.setUserName(permiso.getIdUsuario());
        nuevoPermiso.setUsuario(new UserState(user));
        nuevoPermiso.setBlnActivo(permiso.isBlnActivo());

        return nuevoPermiso;

    }

    public UsuarioRolUnidadAdministrativaDTO transformarDTO(UsuarioRolUnidadAdministrativa usuarioRol, int tipoT) {
        UsuarioRolUnidadAdministrativaDTO usuarioRolDTO = new UsuarioRolUnidadAdministrativaDTO();
        usuarioRolDTO.setIdUsuarioRolUA(usuarioRol.getIdUsuarioRolUA());
        UnidadAdministrativaDTO unidad = new UnidadAdministrativaDTO();
        UnidadAdministrativa uniAdmin =
                unidadService.obtenerUnidadPorId(usuarioRol.getUnidadAdministrativa().getClave());
        if (uniAdmin != null) {
            List<UnidadAdministrativa> lista = new ArrayList<UnidadAdministrativa>();
            lista.add(uniAdmin);
            List<UnidadAdministrativaDTO> listDTO = unidadDTOTransformer.transformarDTO(lista);
            unidad = listDTO.get(0);
        }
        Persona empleado = personaServices.obtenerPersonaPorRFC(usuarioRol.getUsuario().getUserName());
        if (empleado != null) {
            usuarioRolDTO.setNumeroEmpleado(empleado.getNumeroEmpleado().toString());
            usuarioRolDTO.setNombreEmpleado(empleado.getNombre() + " " + empleado.getApellidoPaterno() + " "
                    + empleado.getApellidoMaterno());
        }

        TipoTramite tipoTramite = tipoTramiteServices.obtenerTipoTramiteId(tipoT);
        if (tipoTramite != null) {
            usuarioRolDTO.setModalidad(tipoTramite.getDescripcionModalidad());
            usuarioRolDTO.setTipoTramite(tipoTramite.getDescripcionServicio());
        }
        usuarioRolDTO.setUnidadAdministrativa(unidad != null ? unidad : new UnidadAdministrativaDTO());
        usuarioRolDTO.setIdUsuario(usuarioRol.getUsuario().getUserName());
        usuarioRolDTO.setRol(usuarioRol.getRol() != null ? usuarioRol.getRol().getName() : "");
        usuarioRolDTO.setUniadminPrincipal(usuarioRol.getUniAdminPrincipal());
        return usuarioRolDTO;
    }

}
