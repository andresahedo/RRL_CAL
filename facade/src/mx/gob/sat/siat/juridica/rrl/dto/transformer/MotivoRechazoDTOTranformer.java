package mx.gob.sat.siat.juridica.rrl.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.model.MotivoRechazo;
import mx.gob.sat.siat.juridica.base.dto.MotivoRechazoDTO;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MotivoRechazoDTOTranformer extends DTOTransformer<MotivoRechazo, MotivoRechazoDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = -250471587467913675L;

    @Override
    public MotivoRechazoDTO transformarDTO(MotivoRechazo motivo) {
        MotivoRechazoDTO motivoDTO = new MotivoRechazoDTO();

        motivoDTO.setIdMotivo(motivo.getCveRechazo());
        motivoDTO.setIdSolDocto(motivo.getIdSolDocumento());
        motivoDTO.setIdTipoRechazo(motivo.getCveRechazo());
        motivoDTO.setIdTramite(motivo.getIdTramite());
        motivoDTO.setFechaRechazo(motivo.getFechaRechazo());
        motivoDTO.setIdUsuario(motivo.getIdUsuario());
        motivoDTO.setIdEstRechazo(motivo.getIdEstRechazo());

        return motivoDTO;
    }

    public MotivoRechazo transformar(MotivoRechazoDTO motivoDTO) {
        MotivoRechazo motivo = new MotivoRechazo();

        motivo.setCveRechazo(motivoDTO.getCveRechazo());
        motivo.setIdSolDocumento(motivoDTO.getIdSolDocto());
        motivo.setIdTipoRechazo(motivoDTO.getIdTipoRechazo());
        motivo.setIdTramite(motivoDTO.getIdTramite());
        motivo.setFechaRechazo(new Date());
        motivo.setIdUsuario(motivoDTO.getRfc());
        motivo.setIdEstRechazo(motivoDTO.getIdEstRechazo());

        return motivo;
    }

}
