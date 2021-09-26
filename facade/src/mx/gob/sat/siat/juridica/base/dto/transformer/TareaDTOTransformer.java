package mx.gob.sat.siat.juridica.base.dto.transformer;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;
import mx.gob.sat.siat.juridica.base.dto.TareaDTO;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.TurnarDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DTOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TareaDTOTransformer extends DTOTransformer<TareaDTO, Tarea>{

    private static final long serialVersionUID = -342893718604540155L;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient TipoTramiteServices tipoTramiteServices;


    public Tarea transformarDTO(TurnarDTO turnarDTO){
        Tarea tarea = new Tarea();
        tarea.setNumeroAsunto(turnarDTO.getIdTramite());
        tarea.setClaveAdm(turnarDTO.getIdUsuarioAnterior());
        tarea.setClaveAsignado(turnarDTO.getIdUsuarioAsigna());
        tarea.setTarea(turnarDTO.getNombreTarea());
        tarea.setDescripcion(EnumeracionBitacora.TURNAR_ASUNTO.getDescripcion());
        return tarea;
    }



    public List<TareaDTO> transformarTarea(List<Tarea> source) {
        List<TareaDTO> dtos = new ArrayList<TareaDTO>();
        for(Tarea tarea : source)
        {
            TareaDTO dto = new TareaDTO();
            dto.setIdTareaUsuario(tarea.getIdTarea());
            dto.setIdTarea(tarea.getIdTarea());
            dto.setIdSolicitud(tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getSolicitud().getIdSolicitud());
            dto.setIdInstancia(tarea.getIdTarea());
            int idtipotramite = tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getSolicitud().getTipoTramite().getIdTipoTramite();
            dto.setTipoTramite(Integer.toString(idtipotramite));
            dto.setDescripcionTipoTramite(tipoTramiteServices.obtenerTipoTramiteId(idtipotramite).getDescripcionModalidad());
            dto.setNombreTarea(tarea.getDescripcion());
            dto.setNumeroAsunto(tarea.getNumeroAsunto());
            dto.setRfcSolicitante(tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getSolicitud().getCveUsuarioCapturista());
            dto.setRfcUsuarioAsignado(tarea.getClaveAsignado());
            dto.setCveEstadoProcesal(tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getEstadoTramite().getClave());

            dtos.add(dto);
        }
        return dtos;
    }


    @Override
    public Tarea transformarDTO(TareaDTO source) {
        return null;
    }
}
