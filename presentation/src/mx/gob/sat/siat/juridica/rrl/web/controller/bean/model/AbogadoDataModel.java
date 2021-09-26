package mx.gob.sat.siat.juridica.rrl.web.controller.bean.model;

import mx.gob.sat.siat.juridica.base.dto.AbogadoDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class AbogadoDataModel extends ListDataModel<AbogadoDTO> implements SelectableDataModel<AbogadoDTO>,
        Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7680679018174867508L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public AbogadoDTO getRowData(String arg0) {
        List<AbogadoDTO> abogadoDataModels = (List<AbogadoDTO>) getWrappedData();
        for (AbogadoDTO abogadoDTO : abogadoDataModels) {
            if (abogadoDTO.getIdPersona().toString().equals(arg0)) {
                return abogadoDTO;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(AbogadoDTO arg0) {
        return arg0.getIdPersona();
    }

    public AbogadoDataModel(List<AbogadoDTO> datosabogadoDTOs) {
        super(datosabogadoDTOs);
    }

}
