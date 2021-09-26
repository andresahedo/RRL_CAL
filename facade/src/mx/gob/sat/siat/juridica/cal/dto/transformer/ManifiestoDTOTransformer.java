package mx.gob.sat.siat.juridica.cal.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Manifiesto;
import mx.gob.sat.siat.juridica.cal.dto.ManifiestoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

@Component
public class ManifiestoDTOTransformer extends DTOTransformer<Manifiesto, ManifiestoDTO> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public ManifiestoDTO transformarDTO(Manifiesto source) {
        ManifiestoDTO manifiestoDTO = new ManifiestoDTO();

        manifiestoDTO.setIdManifiesto(source.getIdManifiesto());
        manifiestoDTO.setActivo(source.isActivo());
        manifiestoDTO.setDescModalidad(source.getTipoTramite().getDescripcionModalidad());
        manifiestoDTO.setManifiesto(source.getManifiesto());
        manifiestoDTO.setModalidad(source.getTipoTramite().getIdTipoTramite());
        manifiestoDTO.setObligatorio(source.isObligatorio());
        manifiestoDTO.setSecuencia(source.getSecuencia());
        return manifiestoDTO;
    }

}
