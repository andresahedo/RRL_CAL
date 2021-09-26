package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dto.ConceptosDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class ConceptoDataModel extends ListDataModel<ConceptosDTO> implements SelectableDataModel<ConceptosDTO>,
        Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7288105854277217687L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public ConceptosDTO getRowData(String arg0) {

        List<ConceptosDTO> conceptos = (List<ConceptosDTO>) getWrappedData();

        for (ConceptosDTO resoluciones2 : conceptos) {
            if (resoluciones2.getId().equals(new Long(arg0))) {
                return resoluciones2;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(ConceptosDTO arg0) {
        if (arg0 != null && arg0.getId() != null) {
            return arg0.getId();
        }
        else {
            return "";
        }
    }

    public ConceptoDataModel(List<ConceptosDTO> conceptoList) {
        super(conceptoList);
    }

}
