 package mx.gob.sat.siat.juridica.rrl.api.impl;


 import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
 import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
 import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
 import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
 import mx.gob.sat.siat.juridica.base.dto.transformer.PersonaSolicitudDTOTransformer;
 import mx.gob.sat.siat.juridica.base.dto.transformer.SolicitudDTOTransformer;
 import mx.gob.sat.siat.juridica.base.service.EnumeracionServices;
 import mx.gob.sat.siat.juridica.base.service.PersonaServices;
 import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
 import mx.gob.sat.siat.juridica.rrl.api.BPMFacade;
 import mx.gob.sat.siat.juridica.rrl.dto.transformer.EnumeracionDTOTransformer;
 import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;

 import java.util.List;


@Component("bpmFacade")
public class BPMFacadeImpl implements BPMFacade {

    private static final long serialVersionUID = 1L;


    @Autowired
    private transient EnumeracionServices enumeracionServices;

    @Autowired
    private transient SolicitudService solicitudService;

    @Autowired
    private transient PersonaServices personaServices;

    @Autowired
    private transient EnumeracionDTOTransformer enumeracionDTOTransformer;


    @Autowired
    private transient SolicitudDTOTransformer solicitudDTOTransformer;

    @Autowired
    private transient PersonaSolicitudDTOTransformer personaSolicitudDTOTransformer;


    public List<CatalogoDTO> obtenerCatalogoEstados() {
        return enumeracionDTOTransformer.transformarDTO(enumeracionServices
                .obtenerEnumeracionPorId(CatalogoConstantes.ENU_ESTADO_TRAMITE));
    }

    @Override
    public SolicitudDTO obtenerSolicitud(Long idSolicitud) {

        SolicitudDTO solDto =
                solicitudDTOTransformer.transformarDTO(solicitudService
                        .obtenerSolicitudConsultaAutorizacionporId(idSolicitud));
        PersonaSolicitudDTO personaDto =
                personaSolicitudDTOTransformer.transformarDTO((Solicitante) personaServices
                        .buscarPersonaSolicitante(idSolicitud));
        solDto.setSolicitante(personaDto);
        return solDto;
    }
}
