package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EntidadFederativa;
import mx.gob.sat.siat.juridica.base.dto.EntidadFederativaDTO;
import org.springframework.stereotype.Component;

@Component
public class EntidadFederativaDTOTransformer extends DTOTransformer<EntidadFederativa, EntidadFederativaDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -4837852527265145527L;

    @Override
    public EntidadFederativaDTO transformarDTO(EntidadFederativa entidad) {
        EntidadFederativaDTO estadoDTO = new EntidadFederativaDTO();
        estadoDTO.setClave(entidad.getClave());
        estadoDTO.setClaveEnIDC(entidad.getClaveEnIDC());
        estadoDTO.setNombre(entidad.getNombre());
        return estadoDTO;
    }

}
