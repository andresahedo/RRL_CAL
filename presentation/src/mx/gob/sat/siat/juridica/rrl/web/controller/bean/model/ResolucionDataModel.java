package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class ResolucionDataModel extends ListDataModel<ResolucionImpugnadaDTO> implements
        SelectableDataModel<ResolucionImpugnadaDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3623744534013380972L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public ResolucionImpugnadaDTO getRowData(String arg0) {

        List<ResolucionImpugnadaDTO> resoluciones = (List<ResolucionImpugnadaDTO>) getWrappedData();

        for (ResolucionImpugnadaDTO resoluciones2 : resoluciones) {
            if (resoluciones2.getId().equals(new Long(arg0))) {
                return resoluciones2;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(ResolucionImpugnadaDTO arg0) {
        if (arg0 != null && arg0.getId() != null) {
            return arg0.getId().toString();
        }
        else {
            return "";
        }
    }

    public ResolucionDataModel() {}

    public ResolucionDataModel(List<ResolucionImpugnadaDTO> resolucionesList) {
        super(resolucionesList);
    }
}
