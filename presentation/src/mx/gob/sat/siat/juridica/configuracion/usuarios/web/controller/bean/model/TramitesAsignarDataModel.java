package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.model;

import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class TramitesAsignarDataModel extends ListDataModel<TipoTramiteDTO> implements
        SelectableDataModel<TipoTramiteDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8349207107254263116L;

    @SuppressWarnings("unchecked")
    @Override
    public TipoTramiteDTO getRowData(String arg0) {
        List<TipoTramiteDTO> tramites = (List<TipoTramiteDTO>) getWrappedData();
        for (TipoTramiteDTO tramite : tramites) {
            if (tramite.getServicio().equals(arg0)) {
                return tramite;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(TipoTramiteDTO arg0) {
        if (arg0 != null && arg0.getServicio() != null) {
            return arg0.getServicio();
        }
        else {
            return "";
        }

    }

    public TramitesAsignarDataModel() {}

    public TramitesAsignarDataModel(List<TipoTramiteDTO> tramitesListDTO) {
        super(tramitesListDTO);
    }

}
