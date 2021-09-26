package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Localidad;
import mx.gob.sat.siat.juridica.base.dto.LocalidadDTO;
import org.springframework.stereotype.Component;

@Component
public class LocalidadDTOTransformer extends DTOTransformer<Localidad, LocalidadDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -6809057242150051672L;

    @Override
    public LocalidadDTO transformarDTO(Localidad localidad) {
        if (localidad != null && !localidad.getClave().isEmpty()) {
            LocalidadDTO locDTO = new LocalidadDTO();
            locDTO.setClave(localidad.getClave());
            locDTO.setCodigoPostal(localidad.getCodigoPostal());
            locDTO.setCodigoSAT(localidad.getCodigoSAT());
            locDTO.setNombre(localidad.getNombre());
            return locDTO;
        }
        else {
            return null;
        }
    }

}
