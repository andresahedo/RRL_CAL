package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TramiteDTOTransformer extends DTOTransformer<Tramite, TramiteDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -5537201411917171012L;

    @Override
    public TramiteDTO transformarDTO(Tramite tramite) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        TramiteDTO tramiteDTO = new TramiteDTO();
        tramiteDTO.setNumeroAsunto(tramite.getNumeroAsunto());
        tramiteDTO.setIdSolicitud(tramite.getSolicitud().getIdSolicitud());
        tramiteDTO.setFechaVencimiento(tramite.getFechaCalculoTranscurridos());
        tramiteDTO.setFechaRecepcion(tramite.getFechaInicioTramite());
        tramiteDTO.setFechaRecepcionStr(dateFormat.format(tramite.getFechaInicioTramite()));
        tramiteDTO.setFechaNotificacion(new Date());
        tramiteDTO.setEstadoProcesal(tramite.getEstadoTramite() != null ? tramite.getEstadoTramite().getDescripcion()
                : null);
        tramiteDTO.setTipoTramite(tramite.getSolicitud().getTipoTramite() != null ? tramite.getSolicitud()
                .getTipoTramite().getDescripcionModalidad() : "");
        tramiteDTO.setCatEstadoProcesal(new CatalogoDTO());
        tramiteDTO.getCatEstadoProcesal().setClave(tramite.getEstadoTramite().getClave());
        return tramiteDTO;
    }

}
