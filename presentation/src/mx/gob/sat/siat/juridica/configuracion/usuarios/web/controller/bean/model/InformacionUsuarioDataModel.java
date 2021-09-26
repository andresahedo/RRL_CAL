package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.model;

import mx.gob.sat.siat.juridica.base.dto.InformacionUsuarioDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class InformacionUsuarioDataModel extends ListDataModel<InformacionUsuarioDTO> implements
        SelectableDataModel<InformacionUsuarioDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5940842076363926853L;

    @SuppressWarnings("unchecked")
    @Override
    public InformacionUsuarioDTO getRowData(String arg0) {
        List<InformacionUsuarioDTO> listaRoles = (List<InformacionUsuarioDTO>) getWrappedData();
        for (InformacionUsuarioDTO rol : listaRoles) {
            if (rol.getIdRol().equals(arg0)) {
                return rol;
            }

        }
        return null;
    }

    @Override
    public Object getRowKey(InformacionUsuarioDTO arg0) {
        if (arg0 != null && arg0.getIdRol() != null) {
            return arg0.getIdRol().toString();
        }
        else {
            return "";
        }
    }

    public InformacionUsuarioDataModel() {}

    public InformacionUsuarioDataModel(List<InformacionUsuarioDTO> roles) {
        super(roles);
    }

}
