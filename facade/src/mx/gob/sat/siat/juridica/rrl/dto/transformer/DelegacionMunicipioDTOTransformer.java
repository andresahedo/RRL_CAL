package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DelegacionMunicipio;
import mx.gob.sat.siat.juridica.base.dto.DelegacionMunicipioDTO;
import org.springframework.stereotype.Component;

@Component
public class DelegacionMunicipioDTOTransformer extends DTOTransformer<DelegacionMunicipio, DelegacionMunicipioDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 935515769188539424L;

    @Override
    public DelegacionMunicipioDTO transformarDTO(DelegacionMunicipio delegMunicipio) {

        DelegacionMunicipioDTO delegMunDTO = new DelegacionMunicipioDTO();
        if (delegMunicipio != null && !delegMunicipio.getClave().isEmpty()) {
            delegMunDTO.setClave(delegMunicipio.getClave());
            delegMunDTO.setIdMunicipioIDC(delegMunicipio.getIdMunicipioIDC());
            delegMunDTO.setNombre(delegMunicipio.getNombre());
            return delegMunDTO;
        }
        else {
            return null;

        }

    }

}
