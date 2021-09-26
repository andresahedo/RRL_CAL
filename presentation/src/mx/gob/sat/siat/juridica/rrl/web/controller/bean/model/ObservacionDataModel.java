package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class ObservacionDataModel extends ListDataModel<ObservacionDTO> implements SelectableDataModel<ObservacionDTO>,
        Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3230811769845599753L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public ObservacionDTO getRowData(String idObservacion) {

        List<ObservacionDTO> observaciones = (List<ObservacionDTO>) getWrappedData();

        for (ObservacionDTO obs : observaciones) {
            if (obs.getIdObservacion() != null && obs.getIdObservacion().toString().equals(idObservacion)) {
                return obs;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(ObservacionDTO obsDTO) {

        if (obsDTO != null && obsDTO.getIdObservacion() != null) {
            return obsDTO.toString();
        }
        else {
            return "";
        }
    }

    public ObservacionDataModel(List<ObservacionDTO> observaciones) {
        super(observaciones);
    }

}
