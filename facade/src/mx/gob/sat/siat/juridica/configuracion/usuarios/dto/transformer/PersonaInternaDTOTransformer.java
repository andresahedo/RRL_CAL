package mx.gob.sat.siat.juridica.configuracion.usuarios.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

@Component
public class PersonaInternaDTOTransformer extends DTOTransformer<PersonaInterna, PersonaInternaDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -5162281454260317097L;

    @Override
    public PersonaInternaDTO transformarDTO(PersonaInterna personaInterna) {
        PersonaInternaDTO personaDTO = new PersonaInternaDTO();
        personaDTO.setIdPersona(personaInterna.getIdPersona());
        personaDTO.setNombre(personaInterna.getNombre());
        personaDTO.setApellidoPaterno(personaInterna.getApellidoPaterno());
        personaDTO.setApellidoMaterno(personaInterna.getApellidoMaterno());
        personaDTO.setNumeroEmpleado(personaInterna.getNumeroEmpleado());
        personaDTO.setRfc(personaInterna.getRfc());
        personaDTO.setRfcCorto(personaInterna.getRfcCorto());
        personaDTO.setCorreoElectronico(personaInterna.getCorreoElectronico());

        return personaDTO;
    }

}
