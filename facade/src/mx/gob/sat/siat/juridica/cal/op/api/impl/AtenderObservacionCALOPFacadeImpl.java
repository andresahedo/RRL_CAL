package mx.gob.sat.siat.juridica.cal.op.api.impl;

import mx.gob.sat.siat.juridica.base.api.impl.AtenderObservacionFacadeImpl;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoAutoridad;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.SolicitanteServices;
import mx.gob.sat.siat.juridica.base.util.constante.EnumeracionConstantes;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;
import mx.gob.sat.siat.juridica.ca.service.MedioDefensaService;
import mx.gob.sat.siat.juridica.ca.service.PersonaSolicitudServices;
import mx.gob.sat.siat.juridica.cal.dto.FraccionArancelariaDudaDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaInvolucradaDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaOirNotificacionesDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.dto.transformer.SolicitudCALDTOTransformer;
import mx.gob.sat.siat.juridica.cal.dto.transformer.TipoTramiteDTOTransformer;
import mx.gob.sat.siat.juridica.cal.op.api.AtenderObservacionCALOPFacade;
import mx.gob.sat.siat.juridica.cal.service.FraccionDudaService;
import mx.gob.sat.siat.juridica.cal.service.RegistroSolicitudCALServices;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DatosSolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("atenderObservacionCALOPFacade")
public class AtenderObservacionCALOPFacadeImpl extends AtenderObservacionFacadeImpl implements
        AtenderObservacionCALOPFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private transient SolicitudService solicitudService;

    @Autowired
    private transient SolicitudCALDTOTransformer solicitudCALDTOTransformer;

    @Autowired
    private transient MedioDefensaService medioDefensaService;

    @Autowired
    private transient PersonaSolicitudServices personaSolicitudServices;

    @Autowired
    private transient RegistroSolicitudCALServices registroSolicitudCALServices;

    @Autowired
    private transient FraccionDudaService fraccionDudaService;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;

    @Autowired
    private transient TipoTramiteDTOTransformer tipoTramiteDTOTransformer;

    @Autowired
    private transient SolicitanteServices solicitanteServices;

    @Autowired
    private transient DatosSolicitudDTOTransformer datosSolicitudDTOTransformer;

    public SolicitudCALDTO obtenerSolicitud(Long idSolicitud) {
        Solicitud solicitud = solicitudService.obtenerSolicitudporId(idSolicitud);
        SolicitudCALDTO solicitudCALDTO = new SolicitudCALDTO();
        if (solicitud instanceof SolicitudConsultaAutorizacion) {
            MedioDefensa medioDefensa = medioDefensaService.obtenerMedioDefensaById(idSolicitud);
            // Sentidos de resolucion para seccion medios de defensa

            Autoridad autoridadDeHechos =
                    registroSolicitudCALServices.obtenerAutoridad(idSolicitud, TipoAutoridad.AUTORIDAD_HCH);
            Autoridad autoridadSujetoEjercicio =
                    registroSolicitudCALServices.obtenerAutoridad(idSolicitud, TipoAutoridad.AUTORIDAD_SUJETO);
            List<PersonaSolicitud> listaPersonaSolicitud =
                    personaSolicitudServices.obtenerPersonasSolicitudByIdSol(idSolicitud);
            List<FraccionDuda> fraccionesDuda = fraccionDudaService.obtenerFraccionesDudaBySolicitudId(idSolicitud);

            solicitudCALDTO =
                    solicitudCALDTOTransformer.transformaSolicitud((SolicitudConsultaAutorizacion) solicitud,
                            medioDefensa, listaPersonaSolicitud, fraccionesDuda, autoridadDeHechos,
                            autoridadSujetoEjercicio);
        }

        return solicitudCALDTO;
    }

    /**
     * M&eacute;todo para guardar la solicitud.
     */
    @Override
    public SolicitudCALDTO guardarSolicitud(SolicitudCALDTO solicitud, String rfcSolicitante)
            throws SolicitudNoGuardadaException {

        List<PersonaOirNotificacionesDTO> listaPersonaOirNotificacionesDTO =
                new ArrayList<PersonaOirNotificacionesDTO>();
        List<PersonaInvolucradaDTO> listaPersonaInvolucradaDTO = new ArrayList<PersonaInvolucradaDTO>();
        List<FraccionArancelariaDudaDTO> listaFraccionArancelariaDudaDTO = new ArrayList<FraccionArancelariaDudaDTO>();

        SolicitudConsultaAutorizacion solicitudCA = solicitudCALDTOTransformer.transformarDTO(solicitud);
        List<PersonaOirRecibirNotificaciones> personasOirRecibirNotificaciones = solicitudCA.getPersonaONHelperList();
        List<PersonaResidenteExtranjero> personasResidenteExtranjero = solicitudCA.getPersonaResExtHelperList();
        List<FraccionDuda> fraccionesDuda = solicitudCA.getFraccionDudaHelperList();
        solicitudCA = registroSolicitudCALServices.guardarSolicitud(solicitudCA, rfcSolicitante);

        if (solicitudCA.getAutoridadHelper() != null) {
            solicitudCA.getAutoridadHelper().setIdSolicitud(solicitudCA.getIdSolicitud());
            registroSolicitudCALServices.guardaAutoridad(solicitudCA.getAutoridadHelper());
        }
        if (solicitudCA.getAutoridadSujetoHelper() != null) {
            solicitudCA.getAutoridadSujetoHelper().setIdSolicitud(solicitudCA.getIdSolicitud());
            registroSolicitudCALServices.guardaAutoridad(solicitudCA.getAutoridadSujetoHelper());
        }
        for (PersonaOirRecibirNotificaciones personaOirRecibirNotificaciones : personasOirRecibirNotificaciones) {
            personaOirRecibirNotificaciones.setIdSolicitud(solicitudCA.getIdSolicitud());
            listaPersonaOirNotificacionesDTO.add(solicitudCALDTOTransformer
                    .transformaPersonaOirNotificaciones(registroSolicitudCALServices
                            .guardaPersonaOirNot(personaOirRecibirNotificaciones)));
        }
        for (PersonaResidenteExtranjero personaResidenteExtranjero : personasResidenteExtranjero) {
            personaResidenteExtranjero.setIdSolicitud(solicitudCA.getIdSolicitud());
            listaPersonaInvolucradaDTO.add(solicitudCALDTOTransformer
                    .transformaPersonaResidenteExtrajero(registroSolicitudCALServices
                            .guardaResidenteExtranjero(personaResidenteExtranjero)));
        }
        for (FraccionDuda fraccionDuda : fraccionesDuda) {
            fraccionDuda.setIdSolicitud(solicitudCA.getIdSolicitud());
            listaFraccionArancelariaDudaDTO.add(solicitudCALDTOTransformer
                    .transformaFraccionDuda(registroSolicitudCALServices.guardarFraccionDuda(fraccionDuda)));
        }
        solicitud.getListaPersonasNotificaciones().clear();
        solicitud.getListaPersonasNotificaciones().addAll(listaPersonaOirNotificacionesDTO);
        solicitud.getListaPersonasInvolucradas().clear();
        solicitud.getListaPersonasInvolucradas().addAll(listaPersonaInvolucradaDTO);
        if (solicitudCA.getFraccionDudaHelperList() != null) {
            solicitud.getListaFraccionDuda().clear();
            solicitud.getListaFraccionDuda().addAll(listaFraccionArancelariaDudaDTO);
        }
        if (solicitudCA.getMedioDefensaHelper() != null && solicitudCA.getMedioDefensaHelper().getBlnActivo()) {
            solicitud.setIdMedioDefensa(solicitudCA.getMedioDefensaHelper().getIdMedioDefensa());
        }
        solicitud.setIdSolicitud(solicitudCA.getIdSolicitud());
        solicitud.setFechaCreacion(solicitudCA.getFechaCreacion());
        return solicitud;
    }

    @Override
    public void eliminaPersona(Long idPersona) {
        registroSolicitudCALServices.eliminaPersona(idPersona);
    }

    @Override
    public void eliminaFaccion(Long idFraccion) {
        registroSolicitudCALServices.eliminaFraccion(idFraccion);
    }

    /*
     * M&eacute;todo para obtener las autorizades emisoras.
     */
    @Override
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        List<CatalogoDTO> listaCatalogo =
                catalogoDTOTransformer.transformarDTO(registroSolicitudCALServices.obtenerAutoridadesEmisoras());
        return listaCatalogo;
    }

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o
     * NO.
     */
    @Override
    public List<CatalogoDTO> obtenerListaSiNo() {
        List<CatalogoDTO> listaCatalogo =
                catalogoDTOTransformer.transformarCatalogo(registroSolicitudCALServices.obtenerListaSiNo());
        return listaCatalogo;
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    @Override
    public List<CatalogoDTO> obtenerMediosDeDefensa() {
        List<EnumeracionTr> listaEnumeraciones =
                registroSolicitudCALServices.obtenerEnumeracionPorId(EnumeracionConstantes.TIPOS_MEDIOS_DEFENSA);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    @Override
    public List<CatalogoDTO> obtenerListaTipoPersona() {
        List<CatalogoDTO> listaCatalogo =
                catalogoDTOTransformer.transformarCatalogo(registroSolicitudCALServices.obtenerTipoPersona());
        return listaCatalogo;
    }

    @Override
    public List<CatalogoDTO> obtenerListaTipoClasificacion() {
        List<CatalogoDTO> listaCatalogo =
                catalogoDTOTransformer.transformarEnumeracionTrDTO(registroSolicitudCALServices
                        .obtenerTipoClasificacion());
        return listaCatalogo;
    }

    public List<CatalogoDTO> obtenerCatalogoDPorCodGen1(String codGen1) {
        List<CatalogoD> listaEnumeraciones = registroSolicitudCALServices.obtenerCatalogoDCodGen1(codGen1);
        return catalogoDTOTransformer.transformaCatalogoDDTO(listaEnumeraciones);
    }

    public TipoTramiteDTO obtenerTipoTramitePorId(String idTIpoTramite) {
        return tipoTramiteDTOTransformer.transformarDTO(registroSolicitudCALServices
                .obtenerTipoTramitePorId(idTIpoTramite));
    }

    @Override
    public DatosSolicitudDTO obtenerDatosSolicitante(Long idSolicitud) {
        Solicitante solicitante = solicitanteServices.obtenerSolicitanteByIdSol(idSolicitud);
        return datosSolicitudDTOTransformer.transformarSolicitanteDTO(solicitante);
    }

}
