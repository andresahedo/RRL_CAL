package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.rrl.dto.FuncionarioDTO;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioDTOTransformer extends DTOTransformer<PersonaInterna, FuncionarioDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 7386400536576957315L;

    @Override
    public FuncionarioDTO transformarDTO(PersonaInterna personaInterna) {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setNombre(personaInterna.getNombre());
        funcionarioDTO.setApellidoPaterno(personaInterna.getApellidoPaterno());
        funcionarioDTO.setApellidoMaterno(personaInterna.getApellidoMaterno());
        funcionarioDTO.setRfc(personaInterna.getRfc());
        return funcionarioDTO;
    }

}
