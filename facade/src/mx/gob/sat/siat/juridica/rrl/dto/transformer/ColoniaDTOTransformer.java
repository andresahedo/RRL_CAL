package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Colonia;
import mx.gob.sat.siat.juridica.base.dto.ColoniaDTO;
import org.springframework.stereotype.Component;

@Component
public class ColoniaDTOTransformer extends DTOTransformer<Colonia, ColoniaDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 7089768073373064773L;

    @Override
    public ColoniaDTO transformarDTO(Colonia colonia) {
        ColoniaDTO coloniaDTO = new ColoniaDTO();
        if (colonia != null && !colonia.getClave().isEmpty()) {
            coloniaDTO.setClave(colonia.getClave());
            coloniaDTO.setCp(colonia.getCp());
            coloniaDTO.setIdColoniaIDC(colonia.getIdColoniaIDC());
            coloniaDTO.setNombre(colonia.getNombre());
            return coloniaDTO;
        }
        else {
            return null;
        }
    }

}
