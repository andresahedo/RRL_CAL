/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.RequerimientoDocumentos;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Transformer del requerimiento.
 * 
 * @author Softtek
 * 
 */
@Component
public class RequerimientoDTOTransformer extends DTOTransformer<Requerimiento, RequerimientoDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 4762936149076813029L;

    /**
     * M&eacute;todo para transformar el objeto Requerimiento a
     * RequerimientoDTO.
     */
    @Override
    public RequerimientoDTO transformarDTO(Requerimiento requerimiento) {
        RequerimientoDTO requerimientoDTO = new RequerimientoDTO();
        requerimientoDTO.setIdRequerimiento(requerimiento.getIdRequerimiento());
        requerimientoDTO.setIdUsuario(requerimiento.getIdUsuario());

        requerimientoDTO.setClaveTipoRequerimiento(requerimiento.getTipoRequerimiento().getClave());
        requerimientoDTO.setClaveUnidadAdministrativa(requerimiento.getUnidadAdministrativa() != null ? requerimiento
                .getUnidadAdministrativa().getClave() : null);
        requerimientoDTO.setEstadoRequerimiento(requerimiento.getEstadoRequerimiento() != null ? requerimiento
                .getEstadoRequerimiento().getDescripcion() : null);
        return requerimientoDTO;
    }

    /**
     * M&eacute;todo para transformar el objeto RequerimientoDTO a
     * Requerimiento.
     */
    public Requerimiento transformarGenerarRequerimiento(RequerimientoDTO requerimientoDTO) {
        RequerimientoDocumentos requerimiento = new RequerimientoDocumentos();

        requerimiento.setFechaGeneracion(new Date());
        requerimiento.setFechaCreacion(new Date());
        requerimiento.setFechaAutorizacion(requerimientoDTO.getFechaAutorizacion());
        requerimiento.setIdRequerimiento(requerimientoDTO.getIdRequerimiento());
        requerimiento.setTipoRequerimiento(TipoRequerimiento.parse(requerimientoDTO.getClaveTipoRequerimiento()));
        requerimiento.setIdUsuario(requerimientoDTO.getIdUsuario());
        if (requerimientoDTO.getClaveTipoRequerimiento().equals(TipoRequerimiento.AUTORIDAD.getClave())
                || requerimientoDTO.getClaveTipoRequerimiento().equals(
                        TipoRequerimiento.RETROALIMENTACION_CAL.getClave())) {
            requerimiento.setUnidadAdministrativa(new UnidadAdministrativa(requerimientoDTO
                    .getClaveUnidadAdministrativa()));
        }
        else {
            requerimiento.setUnidadAdministrativa(null);
        }
        return requerimiento;
    }
}
