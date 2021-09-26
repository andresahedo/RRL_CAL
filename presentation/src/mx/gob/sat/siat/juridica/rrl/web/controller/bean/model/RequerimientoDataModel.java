package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class RequerimientoDataModel extends ListDataModel<RequerimientoDTO> implements
        SelectableDataModel<RequerimientoDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1801918947243645258L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public RequerimientoDTO getRowData(String arg0) {

        List<RequerimientoDTO> resoluciones = (List<RequerimientoDTO>) getWrappedData();

        for (RequerimientoDTO resoluciones2 : resoluciones) {
            if (resoluciones2.getId().equals(new Long(arg0))) {
                return resoluciones2;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(RequerimientoDTO arg0) {
        if (arg0 != null && arg0.getId() != null) {
            return arg0.getId().toString();
        }
        else {
            return "";
        }
    }

    public RequerimientoDataModel() {}

    public RequerimientoDataModel(List<RequerimientoDTO> requerimientosList) {
        super(requerimientosList);
    }
}
