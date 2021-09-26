package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.TerminosCondiciones;
import mx.gob.sat.siat.juridica.base.dto.TerminosCondicionesDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

@Component
public class TerminosCondicionesDTOTransformer extends DTOTransformer<TerminosCondicionesDTO, TerminosCondiciones> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public TerminosCondiciones transformarDTO(TerminosCondicionesDTO source) {
        // TODO Auto-generated method stub
        return null;
    }

    public TerminosCondicionesDTO transformarModel(TerminosCondiciones terminosCondiciones) {

        TerminosCondicionesDTO terminosDTO = new TerminosCondicionesDTO();
        terminosDTO.setDescripcion(terminosCondiciones.getDescripcion() != null ? terminosCondiciones.getDescripcion()
                : " ");
        terminosDTO.setBlnactivo(0 != terminosCondiciones.getVigencia().getBlnActivo());
        terminosDTO.setDescripcionHtml(terminosCondiciones.getDescripcionHtml() != null ? terminosCondiciones
                .getDescripcionHtml() : " ");
        terminosDTO.setFechaBaja(terminosCondiciones.getFechaBaja());
        terminosDTO.setFechaInicioVigencia(terminosCondiciones.getVigencia().getFechaInicioVigencia());
        terminosDTO.setFechaFinVigencia(terminosCondiciones.getVigencia().getFechaFinVigencia());
        terminosDTO.setIdCondicionesUso(terminosCondiciones.getIdCondicionUso());

        return terminosDTO;
    }

}
