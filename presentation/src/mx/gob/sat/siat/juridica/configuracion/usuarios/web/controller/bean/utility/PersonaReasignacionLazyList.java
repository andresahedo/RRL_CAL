package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.utility;

import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.ReasignarTareaBusiness;
import mx.gob.sat.siat.juridica.rrl.dto.DatosPersonaTareaDTO;
import org.primefaces.model.LazyDataModel;

import javax.faces.bean.ManagedProperty;
import java.util.List;

public class PersonaReasignacionLazyList extends LazyDataModel<DatosPersonaTareaDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<DatosPersonaTareaDTO> datasource;

    @ManagedProperty("#{reasignarTareaBusiness}")
    private ReasignarTareaBusiness reasignarTareaBusiness;

    public PersonaReasignacionLazyList(List<DatosPersonaTareaDTO> datasource, ReasignarTareaBusiness personaBussines2) {
        this.datasource = datasource;
        this.reasignarTareaBusiness = personaBussines2;
    }

    @Override
    public DatosPersonaTareaDTO getRowData(String rowKey) {
        if (rowKey != null) {
            if (datasource != null) {
                for (DatosPersonaTareaDTO datosPersonaDataModel : datasource) {
                    if (datosPersonaDataModel.getIdTareaUsuario().equals(new Long(rowKey))) {
                        return datosPersonaDataModel;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DatosPersonaTareaDTO datosPersonaTareaDTO) {
        return datosPersonaTareaDTO.getIdTareaUsuario();
    }

  
    public ReasignarTareaBusiness getReasignarTareaBusiness() {
        return reasignarTareaBusiness;
    }

    public void setReasignarTareaBusiness(ReasignarTareaBusiness reasignarTareaBusiness) {
        this.reasignarTareaBusiness = reasignarTareaBusiness;
    }

}
