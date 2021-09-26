package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.rrl.dto.DatosRequerimientoDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class DatosRequerimientoDataModel extends ListDataModel<DatosRequerimientoDTO> implements
        SelectableDataModel<DatosRequerimientoDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7171870606097944328L;

    @SuppressWarnings("unchecked")
    @Override
    public DatosRequerimientoDTO getRowData(String arg0) {
        List<DatosRequerimientoDTO> requerimientoDataModels = (List<DatosRequerimientoDTO>) getWrappedData();
        for (DatosRequerimientoDTO datosRequerimientoDTO : requerimientoDataModels) {
            if (datosRequerimientoDTO.getIdRequerimiento().toString().equals(arg0)) {
                return datosRequerimientoDTO;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DatosRequerimientoDTO arg0) {
        return arg0.getIdRequerimiento();
    }

    public DatosRequerimientoDataModel(List<DatosRequerimientoDTO> datosRequerimientoDTOs) {
        super(datosRequerimientoDTOs);
    }

}
