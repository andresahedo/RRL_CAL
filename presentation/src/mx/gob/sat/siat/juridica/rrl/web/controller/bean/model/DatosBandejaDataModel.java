package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class DatosBandejaDataModel extends ListDataModel<DatosBandejaTareaDTO> implements
        SelectableDataModel<DatosBandejaTareaDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7934198685993633595L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public DatosBandejaTareaDTO getRowData(String arg0) {
        List<DatosBandejaTareaDTO> bandejaDataModels = (List<DatosBandejaTareaDTO>) getWrappedData();
        if (arg0 != null) {
            if (bandejaDataModels != null) {
                for (DatosBandejaTareaDTO datosBandejaDataModel : bandejaDataModels) {
                    if (datosBandejaDataModel.getIdTareaUsuario().equals(new Long(arg0))) {
                        return datosBandejaDataModel;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DatosBandejaTareaDTO arg0) {

        return arg0.getIdTareaUsuario();
    }

    public DatosBandejaDataModel(List<DatosBandejaTareaDTO> datosBandejaTareaDTOs) {
        super(datosBandejaTareaDTOs);
    }

}
