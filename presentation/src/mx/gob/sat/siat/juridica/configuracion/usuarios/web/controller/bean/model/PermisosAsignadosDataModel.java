package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.model;

import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.UsuarioRolUnidadAdministrativaDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class PermisosAsignadosDataModel extends ListDataModel<UsuarioRolUnidadAdministrativaDTO> implements
        SelectableDataModel<UsuarioRolUnidadAdministrativaDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5942838466469337670L;

    @SuppressWarnings("unchecked")
    @Override
    public UsuarioRolUnidadAdministrativaDTO getRowData(String arg0) {
        List<UsuarioRolUnidadAdministrativaDTO> roles = (List<UsuarioRolUnidadAdministrativaDTO>) getWrappedData();
        for (UsuarioRolUnidadAdministrativaDTO rol : roles) {
            if (rol.getIdUsuarioRolUA().equals(new Long(arg0))) {
                return rol;
            }
        }
        return null;

    }

    @Override
    public Object getRowKey(UsuarioRolUnidadAdministrativaDTO arg0) {
        if (arg0 != null && arg0.getIdUsuarioRolUA() != null) {
            return arg0.getIdUsuarioRolUA().toString();
        }
        else {
            return "";
        }
    }

    public PermisosAsignadosDataModel() {}

    public PermisosAsignadosDataModel(List<UsuarioRolUnidadAdministrativaDTO> permisos) {
        super(permisos);
    }

}
