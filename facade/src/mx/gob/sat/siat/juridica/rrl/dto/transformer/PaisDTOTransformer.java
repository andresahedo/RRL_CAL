package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Pais;
import mx.gob.sat.siat.juridica.base.dto.PaisDTO;
import org.springframework.stereotype.Component;

@Component
public class PaisDTOTransformer extends DTOTransformer<Pais, PaisDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 8578917733514910993L;

    @Override
    public PaisDTO transformarDTO(Pais pais) {
        PaisDTO paisDTO = new PaisDTO();
        if (pais != null && !pais.getClave().isEmpty()) {
            paisDTO.setClave(pais.getClave() != null ? pais.getClave() : null);
            paisDTO.setNombre(pais.getNombre());
            paisDTO.setCvePaisWco(pais.getCvePaisWco());
            paisDTO.setNombreAlterno(pais.getNombreAlterno());
        }

        return paisDTO;
    }

}
