package mx.gob.sat.siat.juridica.cal.web.controller.bean.model;

import mx.gob.sat.siat.juridica.cal.dto.FraccionArancelariaDudaDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class FraccionArancelariaDudaDataModel extends ListDataModel<FraccionArancelariaDudaDTO> implements
        SelectableDataModel<FraccionArancelariaDudaDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6629181375025290653L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public FraccionArancelariaDudaDTO getRowData(String arg0) {
        List<FraccionArancelariaDudaDTO> fraccionAracelariaDataModels =
                (List<FraccionArancelariaDudaDTO>) getWrappedData();
        for (FraccionArancelariaDudaDTO fraccionArancelariaDudaDTO : fraccionAracelariaDataModels) {
            if (fraccionArancelariaDudaDTO.getIdFraccionArancelaria().toString().equals(arg0)) {
                return fraccionArancelariaDudaDTO;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(FraccionArancelariaDudaDTO arg0) {
        // TODO Auto-generated method stub
        return arg0.getIdFraccionArancelaria();
    }

    public FraccionArancelariaDudaDataModel(List<FraccionArancelariaDudaDTO> fraccionDTOs) {
        super(fraccionDTOs);
    }
}
