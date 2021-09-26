package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoObservacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Observacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

@Component
public class ObservacionDTOTransformer extends DTOTransformer<Observacion, ObservacionDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -3907469803886253314L;

    @Override
    public ObservacionDTO transformarDTO(Observacion observacion) {
        ObservacionDTO observacionDto = new ObservacionDTO();
        observacionDto.setIdObservacion(observacion.getIdObservacion());
        observacionDto.setEstadoObservacion(observacion.getEstadoObservacion());
        observacionDto.setFechaAtencion(observacion.getFechaAtencion());
        observacionDto.setFechaObservacion(observacion.getFechaObservacion());
        observacionDto.setObservacionDesc(observacion.getObservacionDesc());
        observacionDto.setRfcAbogado(observacion.getRfcAbogado());
        observacionDto.setDescEstado(EstadoObservacion.parse(observacion.getEstadoObservacion()).getDescripcion());
        return observacionDto;
    }

    public Observacion transdormarObservacionDTO(ObservacionDTO observacionDto) {
        Observacion observacion = new Observacion();
        observacion.setIdObservacion(observacionDto.getIdObservacion());
        observacion.setEstadoObservacion(observacionDto.getEstadoObservacion());
        observacion.setFechaAtencion(observacionDto.getFechaAtencion());
        observacion.setFechaObservacion(observacionDto.getFechaObservacion());
        observacion.setObservacionDesc(observacionDto.getObservacionDesc());
        observacion.setRfcAbogado(observacionDto.getRfcAbogado());
        observacion.setTramite(new Tramite(observacionDto.getNumeroAsunto()));
        return observacion;
    }

}
