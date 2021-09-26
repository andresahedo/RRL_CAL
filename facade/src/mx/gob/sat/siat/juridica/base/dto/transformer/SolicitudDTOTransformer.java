package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

@Component
public class SolicitudDTOTransformer extends DTOTransformer<Solicitud, SolicitudDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -7596089864779331899L;

    @Override
    public SolicitudDTO transformarDTO(Solicitud source) {
        SolicitudDTO dto = new SolicitudDTO();
        dto.setIdSolicitud(source.getIdSolicitud());
        dto.setRfc(source.getCveUsuarioCapturista());
        dto.setTipoTramite(source.getDiscriminatorValue());
        dto.setCveRolCapturista(source.getCveRolCapturista());
        return dto;
    }

}
