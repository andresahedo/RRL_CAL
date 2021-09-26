/**
 * 
 */
package mx.gob.sat.siat.juridica.ca.api.impl;

import mx.gob.sat.siat.juridica.base.dao.domain.model.MedioDefensa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.SolicitanteServices;
import mx.gob.sat.siat.juridica.ca.api.ConsultasAutorizacionesFacade;
import mx.gob.sat.siat.juridica.ca.dto.transformer.SolicitudConsultaAutorizacionDTOTransformer;
import mx.gob.sat.siat.juridica.ca.service.MedioDefensaService;
import mx.gob.sat.siat.juridica.ca.service.PersonaSolicitudServices;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.dto.SolicitudConsultaAutorizacionDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DatosSolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ivan.guzman
 * 
 */
@Component("consultasAutorizacionesFacade")
public class ConsultasAutorizacionesFacadeImpl extends BaseFacadeImpl implements ConsultasAutorizacionesFacade {

    /**
     * 
     */
    private static final long serialVersionUID = -7145088847701561182L;

    @Autowired
    private transient SolicitudService solicitudService;

    @Autowired
    private transient MedioDefensaService medioDefensaService;

    @Autowired
    private transient PersonaSolicitudServices personaSolicitudServices;

    @Autowired
    private transient SolicitanteServices solicitanteServices;

    @Autowired
    private transient SolicitudConsultaAutorizacionDTOTransformer solicitudConsultaAutorizacionDTOTransformer;

    @Autowired
    private transient DatosSolicitudDTOTransformer datosSolicitudDTOTransformer;

    public SolicitudConsultaAutorizacionDTO obtenerDatosSolicitud(Long idSolicitud) {
        SolicitudDatosGenerales solicitud = solicitudService.obtenerSolicitudConsultaAutorizacionporId(idSolicitud);
        MedioDefensa medioDefensa = medioDefensaService.obtenerMedioDefensaById(idSolicitud);
        List<PersonaSolicitud> listaPersonaSolicitud =
                personaSolicitudServices.obtenerPersonasSolicitudByIdSol(idSolicitud);
        SolicitudConsultaAutorizacionDTO solicitudConsultaAutorizacionDTO =
                solicitudConsultaAutorizacionDTOTransformer.transformaSolicitud(solicitud, medioDefensa,
                        listaPersonaSolicitud);
        return solicitudConsultaAutorizacionDTO;
    }

    public DatosSolicitudDTO obtenerDatosSolicitante(Long idSolicitud) {
        Solicitante solicitante = solicitanteServices.obtenerSolicitanteByIdSol(idSolicitud);

        return datosSolicitudDTOTransformer.transformarSolicitanteDTO(solicitante);
    }

}
