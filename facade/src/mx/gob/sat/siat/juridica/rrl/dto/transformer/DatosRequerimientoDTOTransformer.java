package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.RequerimientoDocumentos;
import mx.gob.sat.siat.juridica.rrl.dto.DatosRequerimientoDTO;
import org.springframework.stereotype.Component;

@Component
public class DatosRequerimientoDTOTransformer extends DTOTransformer<Requerimiento, DatosRequerimientoDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -4437227398166384496L;

    @Override
    public DatosRequerimientoDTO transformarDTO(Requerimiento requerimiento) {
        DatosRequerimientoDTO datosRequerimientoDTO = new DatosRequerimientoDTO();
        datosRequerimientoDTO.setIdRequerimiento(requerimiento.getIdRequerimiento());
        datosRequerimientoDTO.setTipoRequerimiento(requerimiento.getTipoRequerimiento() != null ? requerimiento
                .getTipoRequerimiento().getDescripcion() : null);
        datosRequerimientoDTO.setAutoridad(requerimiento.getUnidadAdministrativa() != null ? requerimiento
                .getUnidadAdministrativa().getNombre() : null);
        datosRequerimientoDTO.setEstadoRequerimiento(requerimiento.getEstadoRequerimiento() != null ? requerimiento
                .getEstadoRequerimiento().getDescripcion() : null);
        datosRequerimientoDTO.setFechaGeneracion(requerimiento.getFechaGeneracion() != null ? requerimiento
                .getFechaGeneracion() : null);
        datosRequerimientoDTO.setIdUsuario(requerimiento.getIdUsuario());
        datosRequerimientoDTO.setFechaNotificacion(requerimiento.getFechaVerificacion() != null ? requerimiento
                .getFechaVerificacion() : null);

        datosRequerimientoDTO.setFechaAtencion(requerimiento.getFechaAtencion() != null ? requerimiento
                .getFechaAtencion() : null);

        datosRequerimientoDTO.setFechaAutorizacion(requerimiento.getFechaAutorizacion() != null ? requerimiento
                .getFechaAutorizacion() : null);
        if (requerimiento.getTipoRequerimiento().getClave().equals(TipoRequerimiento.AUTORIDAD.getClave())) {
            datosRequerimientoDTO.setDeshabilitarFechas(false);
        }
        else {
            datosRequerimientoDTO.setDeshabilitarFechas(true);
        }

        if (requerimiento.getEstadoRequerimiento().equals(EstadoRequerimiento.CONCLUIDO)) {
            datosRequerimientoDTO.setDeshabilitarFechas(false);
        }
        return datosRequerimientoDTO;
    }

    public Requerimiento transformarModel(DatosRequerimientoDTO datosRequerimientoDTO) {
        RequerimientoDocumentos requerimiento = new RequerimientoDocumentos();

        requerimiento.setIdRequerimiento(datosRequerimientoDTO.getIdRequerimiento() != null ? datosRequerimientoDTO
                .getIdRequerimiento() : null);
        requerimiento.setFechaGeneracion(datosRequerimientoDTO.getFechaGeneracion() != null ? datosRequerimientoDTO
                .getFechaGeneracion() : null);
        requerimiento.setFechaVerificacion(datosRequerimientoDTO.getFechaNotificacion() != null ? datosRequerimientoDTO
                .getFechaNotificacion() : null);
        requerimiento.setFechaAtencion(datosRequerimientoDTO.getFechaAtencion() != null ? datosRequerimientoDTO
                .getFechaAtencion() : null);
        return requerimiento;
    }

}
