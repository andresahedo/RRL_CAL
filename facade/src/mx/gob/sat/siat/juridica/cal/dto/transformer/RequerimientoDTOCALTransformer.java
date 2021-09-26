package mx.gob.sat.siat.juridica.cal.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.RequerimientoDocumentos;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.RequerimientoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RequerimientoDTOCALTransformer extends DTOTransformer<Requerimiento, RequerimientoDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 4762936149076813029L;

    /**
     * M&eacute;todo para transformar el objeto RequerimientoDTO a
     * Requerimiento.
     */
    public Requerimiento transformarGenerarRequerimiento(RequerimientoDTO requerimientoDTO) {
        RequerimientoDocumentos requerimiento = new RequerimientoDocumentos();
        requerimiento.setTramite(new Tramite(requerimientoDTO.getTramite().getNumeroAsunto()));
        requerimiento.setFechaGeneracion(new Date());
        requerimiento.setFechaCreacion(new Date());
        requerimiento.setEstadoRequerimiento(EstadoRequerimiento.GENERADO);
        requerimiento.setTipoRequerimiento(TipoRequerimiento.parse(requerimientoDTO.getClaveTipoRequerimiento()));
        requerimiento.setIdUsuario(requerimientoDTO.getIdUsuario());
        if (!requerimientoDTO.getClaveTipoRequerimiento().equals(TipoRequerimiento.CONTRIBUYENTE.getClave())
                && !requerimientoDTO.getClaveTipoRequerimiento().equals(TipoRequerimiento.PROMOVENTE_CAL.getClave())) {
            requerimiento.setUnidadAdministrativa(new UnidadAdministrativa(requerimientoDTO
                    .getClaveUnidadAdministrativa()));
        }
        else {
            requerimiento.setUnidadAdministrativa(null);
        }
        return requerimiento;
    }

    @Override
    public RequerimientoDTO transformarDTO(Requerimiento source) {
        return null;
    }
}
