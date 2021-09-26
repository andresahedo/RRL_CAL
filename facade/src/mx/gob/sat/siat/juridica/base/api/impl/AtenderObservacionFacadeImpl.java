package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.AtenderObservacionFacade;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Firma;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Observacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.ObservacionDTO;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.FirmaTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.ObservacionDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.PersonaSolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.FirmarServices;
import mx.gob.sat.siat.juridica.base.service.ObservacionServices;
import mx.gob.sat.siat.juridica.base.service.PersonaServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("atenderObservacionFacade")
public class AtenderObservacionFacadeImpl extends BaseFacadeImpl implements AtenderObservacionFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -9049592343198922060L;

    @Autowired
    private transient ObservacionServices observacionServices;

    @Autowired
    private transient FirmaTareaService firmaTareaService;

    @Autowired
    private transient FirmaTransformer firmaTransformer;

    @Autowired
    private transient PersonaServices personaService;

    @Autowired
    private transient PersonaSolicitudDTOTransformer solicitanteDTOTransformer;

    @Autowired
    private transient ObservacionDTOTransformer observacionTransformer;

    @Autowired
    private transient FirmarServices firmarServices;

    @Autowired
    private transient TramiteServices tramiteServices;

    public ObservacionDTO obternerObservacion(String numeroAsunto) {
        Observacion observacion = observacionServices.obtenerUltimaObservacionPorTramite(numeroAsunto);
        return observacionTransformer.transformarDTO(observacion);
    }

    @Override
    public PersonaSolicitudDTO obtenerSolicitante(Long idSolicitud) {
        Solicitante solicitante = (Solicitante) personaService.buscarPersonaSolicitante(idSolicitud);
        return solicitanteDTOTransformer.transformarDTO(solicitante);
    }

    @Override
    public void firmaAtender(String numeroAsunto, Long idObservacion, String rfc, FirmaDTO firmaDto, String idTarea)
            throws SolicitudNoGuardadaException {
        Firma firma = firmaTransformer.transformarDTO(firmaDto);
        firma = firmarServices.guardarFrima(firma);
        firmarServices.guardarFrimaObservacion(firma, idObservacion);
        firmaTareaService.completarTareaAtenderObservacion(numeroAsunto, idObservacion, rfc, idTarea);
        tramiteServices.modificaEstadoProcesal(numeroAsunto, EstadoTramite.EN_ESTUDIO.getClave());
    }

}
