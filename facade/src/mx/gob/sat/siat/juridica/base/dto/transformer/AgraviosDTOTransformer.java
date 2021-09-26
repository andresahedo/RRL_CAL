package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;
import mx.gob.sat.siat.juridica.base.dto.AgraviosDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

@Component
public class AgraviosDTOTransformer extends DTOTransformer<CatalogoD, AgraviosDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public AgraviosDTO transformarDTO(CatalogoD source) {
        AgraviosDTO agraviosDTO = new AgraviosDTO();
        agraviosDTO.setClave(source.getClave());
        agraviosDTO.setDescripcion(source.getDescripcionGenerica1());
        return agraviosDTO;
    }

}
