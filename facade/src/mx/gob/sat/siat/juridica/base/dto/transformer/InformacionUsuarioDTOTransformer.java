package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Role;
import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuario;
import mx.gob.sat.siat.juridica.base.dao.domain.model.InformacionUsuarioPK;
import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.service.AdministrarUsuarioUAService;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InformacionUsuarioDTOTransformer extends DTOTransformer<InformacionUsuarioDTO, InformacionUsuario> {

    /**
     * 
     */
    private static final long serialVersionUID = -2901785912702738645L;

    @Autowired
    private AdministrarUsuarioUAService administrarUsuarioUAService;

    @Override
    public InformacionUsuario transformarDTO(InformacionUsuarioDTO infoUsuario) {
        InformacionUsuario info = new InformacionUsuario();
        InformacionUsuarioPK infPK = new InformacionUsuarioPK();
        infPK.setIdRol(infoUsuario.getIdRol());
        infPK.setIdUsuario(infoUsuario.getIdUsuario());
        info.setInformacionUsuarioPK(infPK);
        info.setRolReplica(infoUsuario.isRolReplica());
        info.setBlnActivo(infoUsuario.isBlnActivo());
        return info;
    }

    public InformacionUsuarioDTO transformarModel(InformacionUsuario infoUsuario) {
        Role rol =
                administrarUsuarioUAService.obtenerRolePorIdRol(infoUsuario.getInformacionUsuarioPK() != null
                        ? infoUsuario.getInformacionUsuarioPK().getIdRol() : null);
        InformacionUsuarioDTO infoDTO = new InformacionUsuarioDTO();

        infoDTO.setBlnActivo(infoUsuario.getBlnActivo() != null ? infoUsuario.getBlnActivo() : false);
        infoDTO.setIdRol(infoUsuario.getInformacionUsuarioPK() != null ? infoUsuario.getInformacionUsuarioPK()
                .getIdRol() : null);
        infoDTO.setIdUsuario(infoUsuario.getInformacionUsuarioPK() != null ? infoUsuario.getInformacionUsuarioPK()
                .getIdUsuario() : null);
        infoDTO.setRolReplica(infoUsuario.getRolReplica() != null ? infoUsuario.getRolReplica() : false);

        infoDTO.setDescripcionRol(rol != null ? rol.getDescription() : "");

        return infoDTO;
    }

    public InformacionUsuario transformarInfoDTO(InformacionUsuarioDTO infoUsuario) {
        InformacionUsuario info = new InformacionUsuario();
        InformacionUsuarioPK infPK = new InformacionUsuarioPK();
        infPK.setIdRol(infoUsuario.getIdRol());
        infPK.setIdUsuario(infoUsuario.getIdUsuario());
        info.setInformacionUsuarioPK(infPK);
        info.setRolReplica(infoUsuario.isRolReplica());
        info.setBlnActivo(infoUsuario.isAsignado());
        return info;
    }
}
