package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.GenerarObservacionFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Observacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.ObservacionDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.TramiteDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("generarObservacionFacade")
public class GenerarObservacionFacadeImpl extends BaseFacadeImpl implements GenerarObservacionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -2183981642428801454L;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient ObservacionDTOTransformer observacionDTOTransformer;

    @Autowired
    private transient TramiteDTOTransformer tramiteDTOTransformer;

    @Autowired
    private transient FirmaTareaService firmaTareaService;

    @Override
    public ObservacionDTO observar(ObservacionDTO observacionDTO, Long idTarea, String rfcUsuario)
            throws SolicitudNoGuardadaException {
        Observacion observacion = observacionDTOTransformer.transdormarObservacionDTO(observacionDTO);
        observacion = firmaTareaService.observar(idTarea.toString(), rfcUsuario, observacion);
        return observacionDTOTransformer.transformarDTO(observacion);
    }

    @Override
    public TramiteDTO obtenerTramite(String numAsunto) {
        TramiteDTO tramiteDto = null;
        if (numAsunto != null) {
            Tramite tramite = tramiteServices.buscarTramite(numAsunto, null); 
                                                                              // no
                                                                              // se
                                                                              // envia
                                                                              // id
                                                                              // de
                                                                              // solicitud
                                                                              // al
                                                                              // servicio
                                                                              // para
                                                                              // que
                                                                              // solo
                                                                              // busque
                                                                              // por
                                                                              // num.
                                                                              // de
                                                                              // asunto
            if (tramite != null) {
                tramiteDto = tramiteDTOTransformer.transformarDTO(tramite);
            }
        }
        return tramiteDto;
    }

}
