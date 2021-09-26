package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class DatosBandejaConsultasAutorizacionesDataModel extends ListDataModel<DatosBandejaTareaDTO> implements
        SelectableDataModel<DatosBandejaTareaDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2921314859297312050L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public DatosBandejaTareaDTO getRowData(String arg0) {
        List<DatosBandejaTareaDTO> bandejaDataModels = (List<DatosBandejaTareaDTO>) getWrappedData();
        if (arg0 != null) {
            for (DatosBandejaTareaDTO datosBandejaDataModel : bandejaDataModels) {
                if (datosBandejaDataModel.getIdSolicitud().equals(new Long(arg0))) {
                    return datosBandejaDataModel;
                }
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DatosBandejaTareaDTO arg0) {

        return arg0.getIdSolicitud();
    }

    public DatosBandejaConsultasAutorizacionesDataModel(List<DatosBandejaTareaDTO> datosBandejaTareaDTOs) {
        super(datosBandejaTareaDTOs);
    }

}
