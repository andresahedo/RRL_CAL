package mx.gob.sat.siat.juridica.cal.web.controller.bean.model;

import mx.gob.sat.siat.juridica.cal.dto.PersonaInvolucradaDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class PersonaInvolucradaDataModel extends ListDataModel<PersonaInvolucradaDTO> implements
        SelectableDataModel<PersonaInvolucradaDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1965346410391724323L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public PersonaInvolucradaDTO getRowData(String arg0) {
        List<PersonaInvolucradaDTO> personaInvolucradaDataModels = (List<PersonaInvolucradaDTO>) getWrappedData();
        for (PersonaInvolucradaDTO personaInvolucradaDTO : personaInvolucradaDataModels) {
            if (personaInvolucradaDTO.getIdPersona().toString().equals(arg0)) {
                return personaInvolucradaDTO;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(PersonaInvolucradaDTO arg0) {
        // TODO Auto-generated method stub
        return arg0.getIdPersona();
    }

    public PersonaInvolucradaDataModel(List<PersonaInvolucradaDTO> personaDTOs) {
        super(personaDTOs);
    }

}
