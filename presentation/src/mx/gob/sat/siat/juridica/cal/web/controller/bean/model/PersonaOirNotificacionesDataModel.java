package mx.gob.sat.siat.juridica.cal.web.controller.bean.model;

import mx.gob.sat.siat.juridica.cal.dto.PersonaOirNotificacionesDTO;
import mx.gob.sat.siat.juridica.constantes.SuppressWarningsConstants;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

public class PersonaOirNotificacionesDataModel extends ListDataModel<PersonaOirNotificacionesDTO> implements
        SelectableDataModel<PersonaOirNotificacionesDTO>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2959531701247312238L;

    @SuppressWarnings(SuppressWarningsConstants.UNCHECKED)
    @Override
    public PersonaOirNotificacionesDTO getRowData(String arg0) {
        List<PersonaOirNotificacionesDTO> personaOirNotDataModels =
                (List<PersonaOirNotificacionesDTO>) getWrappedData();
        for (PersonaOirNotificacionesDTO personaOirNotDTO : personaOirNotDataModels) {
            if (personaOirNotDTO.getIdPersona().toString().equals(arg0)) {
                return personaOirNotDTO;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(PersonaOirNotificacionesDTO arg0) {
        // TODO Auto-generated method stub
        return arg0.getIdPersona();
    }

    public PersonaOirNotificacionesDataModel(List<PersonaOirNotificacionesDTO> personaDTOs) {
        super(personaDTOs);
    }

}
