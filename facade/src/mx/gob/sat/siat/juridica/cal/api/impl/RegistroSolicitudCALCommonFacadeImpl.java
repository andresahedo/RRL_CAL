/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.api.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.softtek.idc.client.util.exception.ContribuyenteInactivoException;
import com.softtek.idc.client.util.exception.ContribuyenteNoEncontradoException;
import com.softtek.idc.client.util.exception.IDCException;
import com.softtek.idc.client.util.exception.RFCNoVigenteException;
import com.softtek.idc.common.exceptions.IDCNoDisponibleException;

import mx.gob.sat.siat.juridica.base.api.impl.BaseCloudFacadeImpl;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Descripcion;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Autoridad;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Bitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Documento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Firma;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FirmaSolicitudPK;
import mx.gob.sat.siat.juridica.base.dao.domain.model.FraccionDuda;
import mx.gob.sat.siat.juridica.base.dao.domain.model.MensajeBPM;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaOirRecibirNotificaciones;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaResidenteExtranjero;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.DomicilioSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.ModalidadesDTO;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.FirmaTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.ModalidadesDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.PersonaSolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.base.service.BitacoraTramiteServices;
import mx.gob.sat.siat.juridica.base.service.BuscarPersonaSolicitanteService;
import mx.gob.sat.siat.juridica.base.service.CatalogosServices;
import mx.gob.sat.siat.juridica.base.service.DocumentosServices;
import mx.gob.sat.siat.juridica.base.service.EnviarCorreoService;
import mx.gob.sat.siat.juridica.base.service.FirmarServices;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.base.util.constante.BuzonConstantes;
import mx.gob.sat.siat.juridica.base.util.constante.CatalogoConstantes;
import mx.gob.sat.siat.juridica.base.util.constante.EnumeracionConstantes;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;
import mx.gob.sat.siat.juridica.buzon.service.BuzonTributarioService;
import mx.gob.sat.siat.juridica.ca.dao.domain.model.SolicitudConsultaAutorizacion;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;
import mx.gob.sat.siat.juridica.cal.api.RegistroSolicitudCALCommonFacade;
import mx.gob.sat.siat.juridica.cal.dto.FraccionArancelariaDudaDTO;
import mx.gob.sat.siat.juridica.cal.dto.ManifiestoDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaInvolucradaDTO;
import mx.gob.sat.siat.juridica.cal.dto.PersonaOirNotificacionesDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.dto.transformer.SolicitudCALDTOTransformer;
import mx.gob.sat.siat.juridica.cal.dto.transformer.TipoTramiteDTOTransformer;
import mx.gob.sat.siat.juridica.cal.service.ManifiestoService;
import mx.gob.sat.siat.juridica.cal.service.RegistroSolicitudCALCommonServices;
import mx.gob.sat.siat.juridica.cal.service.SolicitudCALServices;
import mx.gob.sat.siat.juridica.idc.service.IdcJuridicoService;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.FirmasSolicitudTransformer;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

/**
 * Facade para atender el registro de la solicitud.
 * 
 * @author Softtek
 * 
 */

public abstract class RegistroSolicitudCALCommonFacadeImpl extends BaseCloudFacadeImpl
        implements RegistroSolicitudCALCommonFacade {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1514140449969098245L;

    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;
    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;
    @Autowired
    private transient ModalidadesDTOTransformer modalidadesDTOTransformer;
    @Autowired
    private transient SolicitudCALDTOTransformer solicitudCALDTOTransformer;

    @Autowired
    private transient SolicitudCALServices solicitudCALServices;
    @Autowired
    private transient UnidadAdministrativaServices unidadAdministrativaServices;

    @Autowired
    private transient DocumentosServices documentosServices;
    @Autowired
    private transient FirmasSolicitudTransformer firmasSolicitudTransformer;
    @Autowired
    private transient IdcJuridicoService idcJuridicoService;

    @Autowired
    private transient CatalogosServices catalogosServices;
    @Autowired
    private transient FirmarServices firmarServices;
    @Autowired
    private transient FirmaTransformer firmaTransformer;
    @Autowired
    private transient TipoTramiteDTOTransformer tipoTramiteDTOTransformer;
    @Autowired
    private transient BuzonTributarioService buzonTributarioService;

    @Autowired
    private transient ManifiestoService manifiestoService;

    @Autowired
    private transient BitacoraTramiteServices bitacoraTramiteServices;

    @Autowired
    private transient EnviarCorreoService enviarCorreoService;
    @Autowired
    private BuscarPersonaSolicitanteService buscarPersonaService;
    @Autowired
    private transient PersonaSolicitudDTOTransformer personaSolicitudTransformer;

    /**
     * M&eacute;todo para preparar la solicitud que se desea capturar.
     */
    public SolicitudCALDTO iniciarRegistroSolicitud(String rfc, String modalidad) throws SolicitudNoGuardadaException {

        Solicitante sol = new Solicitante();
        boolean muestraMensaje = false;
        String mensajeBuzon = null;
        try {
            sol = idcJuridicoService.buscarContribuyenteIdc(rfc, new Solicitante());
        } catch (Exception e) {
            getLogger().error("", e);
        }

        SolicitudCALDTO solicitud = new SolicitudCALDTO();
        PersonaSolicitudDTO solicitante = new PersonaSolicitudDTO();
        DomicilioSolicitudDTO domicilio = new DomicilioSolicitudDTO();
        if (sol.getDomicilio() != null) {
            domicilio.setCalle(sol.getDomicilio().getCalle());
            domicilio.setCodigoPostal(sol.getDomicilio().getCodigoPostal());
            domicilio.setNumeroExterior(sol.getDomicilio().getNumeroExterior());
            domicilio.setNumeroInterior(sol.getDomicilio().getNumeroInterior());
            if (sol.getDomicilio().getColonia() != null) {
                domicilio.setCveColonia(sol.getDomicilio().getColonia().getClave());
                domicilio.setColonia(sol.getDomicilio().getColonia().getNombre());
            }
            if (sol.getDomicilio().getDelegacionMunicipio() != null) {
                domicilio.setCveDelegacion(sol.getDomicilio().getDelegacionMunicipio().getClave());
                domicilio.setDelegacionMunicipio(sol.getDomicilio().getDelegacionMunicipio().getNombre());
            }
            if (sol.getDomicilio().getEntidadFederativa() != null) {
                domicilio.setEstado(sol.getDomicilio().getEntidadFederativa().getNombre());
                domicilio.setCveEstado(sol.getDomicilio().getEntidadFederativa().getClave());
            }
            if (sol.getDomicilio().getPais() != null) {
                domicilio.setNombrePais(sol.getDomicilio().getPais().getNombre());
                domicilio.setCvePais(sol.getDomicilio().getPais().getClave());
            }
            domicilio.setTelefono(sol.getDomicilio().getTelefono());

            // //TODO Pendiente para obtener este dato del Idc
            if (sol.getDomicilio().getClaveAdministracionLocalRecaudadora() != null) {
                domicilio.setAdministracionLocal(sol.getDomicilio().getClaveAdministracionLocalRecaudadora());
                domicilio.setNombreAdministracionLocal(unidadAdministrativaServices
                        .obtenerUnidadPorClaveLocal(sol.getDomicilio().getClaveAdministracionLocalRecaudadora())
                        .getNombre());
            }
        }

        solicitante.setRfc(sol.getRfc());
        solicitante.setEstadoContribuyente(sol.getEstadoContribuyente());
        solicitante.setNombre(sol.getNombre());
        solicitante.setApellidoPaterno(sol.getApellidoPaterno());
        solicitante.setApellidoMaterno(sol.getApellidoMaterno());
        solicitante.setDomicilio(domicilio);
        solicitante.setRazonSocial(sol.getRazonSocial());
        solicitud.setSolicitante(solicitante);
        solicitud.setTipoTramite(modalidad);
        sol.setBlnAmparado(true);
        try {
            solicitud.getSolicitante().setAmparado(true);
            String correo = buzonTributarioService.obtenerCorreos(rfc);
            if (correo != null && !correo.equals("")) {
                solicitud.getSolicitante().getDomicilio()
                        .setCorreoElectronico(buzonTributarioService.obtenerCorreos(rfc));
                muestraMensaje = false;
            } else {
                muestraMensaje = true;
                mensajeBuzon = BuzonConstantes.SIN_CORREOS;
            }
        } catch (BuzonNoDisponibleException e) {
            getLogger().error("", e);
            muestraMensaje = true;
            mensajeBuzon = BuzonConstantes.BUZON_NO_DISPONIBLE;
        } catch (CorreoNoRegistradoException e) {
            // TODO Auto-generated catch block
            getLogger().error("", e);
            muestraMensaje = true;
            mensajeBuzon = BuzonConstantes.SIN_CORREOS;
        }

        solicitud.setMuestraMensaje(muestraMensaje);
        solicitud.setMensajeBuzon(mensajeBuzon);

        return solicitud;
    }

    /**
     * M&eacute;todo para obtener las autorizades emisoras.
     */
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        List<CatalogoDTO> listaCatalogo = catalogoDTOTransformer
                .transformarDTO(getRegistroSolicitudCALCommonServices().obtenerAutoridadesEmisoras());
        return listaCatalogo;
    }

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o NO.
     */
    public List<CatalogoDTO> obtenerListaSiNo() {
        List<CatalogoDTO> listaCatalogo = catalogoDTOTransformer
                .transformarCatalogo(getRegistroSolicitudCALCommonServices().obtenerListaSiNo());
        return listaCatalogo;
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerMediosDeDefensa() {
        List<EnumeracionTr> listaEnumeraciones = getRegistroSolicitudCALCommonServices()
                .obtenerEnumeracionPorId(EnumeracionConstantes.TIPOS_MEDIOS_DEFENSA);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerCatalogoDPorCodGen1(String codGen1) {
        List<CatalogoD> listaEnumeraciones = getRegistroSolicitudCALCommonServices().obtenerCatalogoDCodGen1(codGen1);
        return catalogoDTOTransformer.transformaCatalogoDDTO(listaEnumeraciones);
    }

    /**
     * M&eacute;todo para obtener lista de sentidos de resolcui&oacute;n.
     */
    public List<CatalogoDTO> obtenerSentidosResolucion() {
        List<EnumeracionTr> listaEnumeraciones = getRegistroSolicitudCALCommonServices()
                .obtenerEnumeracionPorId(EnumeracionConstantes.SENTIDOS_RESOLCUION);
        return catalogoDTOTransformer.transformarEnumeracionTrDTO(listaEnumeraciones);
    }

    /**
     * M&eacute;todo para obtener modalidades de consulta.
     */
    public List<ModalidadesDTO> obtenerModalidadesConsulta(String tipoPersona) {
        return obtenerModalidadesPorTipo(RegistroSolicitudConstants.MODALIDADES_CONSULTA, tipoPersona);
    }

    /**
     * M&eacute;todo para obtener modalidades de autorizaci&oacute;n.
     */
    public List<ModalidadesDTO> obtenerModalidadesAutorizacion(String tipoPersona) {
        return obtenerModalidadesPorTipo(RegistroSolicitudConstants.MODALIDADES_AUTORIZACION, tipoPersona);
    }

    /**
     * M&eacute;todo para obtener modalidades de aviso.
     */
    public List<ModalidadesDTO> obtenerModalidadesAviso(String tipoPersona) {
        return obtenerModalidadesPorTipo(RegistroSolicitudConstants.MODALIDADES_AVISO, tipoPersona);
    }

    /**
     * M&eacute;todo para obtener documentos obligatorios por tipo tramite.
     */
    public List<DocumentoDTO> obtenerDocumentosObligatorios(Integer idTipoTramite, String tipoClasificacion) {
        return obtenerdocumentosTramite(idTipoTramite, RegistroSolicitudConstants.DOCUMENTOS_OBLIGATORIOS,
                tipoClasificacion);
    }

    /**
     * M&eacute;todo para obtener documentos opcionales por tipo tramite.
     */
    public List<DocumentoDTO> obtenerDocumentosOpcionales(Integer idTipoTramite, String tipoClasificacion) {
        return obtenerdocumentosTramite(idTipoTramite, RegistroSolicitudConstants.DOCUMENTOS_OPCIONALES,
                tipoClasificacion);
    }

    public void guardarFirma(FirmaDTO firmaDTO, Long idSolicitud) {
        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        firmarServices.guardarFrima(firma);
        FirmaSolicitud firmaSol = new FirmaSolicitud();
        firmaSol.setFirmaSolicitudPK(new FirmaSolicitudPK());
        firmaSol.getFirmaSolicitudPK().setIdFirma(firma.getIdFirma());
        firmaSol.getFirmaSolicitudPK().setIdSolicitud(idSolicitud);
        firmarServices.guardarFirmaSolicitud(firmaSol);
    }

    /**
     * M&eacute;todo para guardar la solicitud.
     */
    public SolicitudCALDTO guardarSolicitud(SolicitudCALDTO solicitud, String rfcSolicitante)
            throws SolicitudNoGuardadaException {

        getLogger().debug("guardar solicitud fachada 1");
        List<PersonaOirNotificacionesDTO> listaPersonaOirNotificacionesDTO = new ArrayList<PersonaOirNotificacionesDTO>();
        List<PersonaInvolucradaDTO> listaPersonaInvolucradaDTO = new ArrayList<PersonaInvolucradaDTO>();
        List<FraccionArancelariaDudaDTO> listaFraccionArancelariaDudaDTO = new ArrayList<FraccionArancelariaDudaDTO>();
        getLogger().debug("guardar solicitud fachada 2");

        if (solicitud.getTipoRolContribuyente().trim().equals("")) {

            if (solicitud.getClaveUnidadEmisora() == null) {
                getLogger().debug("guardar solicitud fachada 3");
                UnidadAdministrativa unidadAdmin = unidadAdministrativaServices
                        .obtenerUnidadPorClaveLocal(solicitud.getSolicitante().getDomicilio().getAdministracionLocal());
                if (unidadAdmin != null) {
                    getLogger().debug("guardar solicitud fachada 4");
                    solicitud.setClaveUnidadEmisora(unidadAdmin.getClave());
                } else {
                    getLogger().debug("guardar solicitud fachada 5");
                    buscaUnidadAdminSolicitante(solicitud, rfcSolicitante);
                }
            }
        } else {

            String uniAdminEmisora = unidadAdministrativaServices.obtenerUnidadPorTipoRol(solicitud.getTipoTramite(),
                    solicitud.getTipoRolContribuyente());
            if (uniAdminEmisora != null) {
                solicitud.setClaveUnidadEmisora(uniAdminEmisora);
            } else {
                if (solicitud.getClaveUnidadEmisora() == null) {
                    getLogger().debug("guardar solicitud fachada 3");
                    UnidadAdministrativa unidadAdmin = unidadAdministrativaServices.obtenerUnidadPorClaveLocal(
                            solicitud.getSolicitante().getDomicilio().getAdministracionLocal());
                    if (unidadAdmin != null) {
                        getLogger().debug("guardar solicitud fachada 4");
                        solicitud.setClaveUnidadEmisora(unidadAdmin.getClave());
                    } else {
                        getLogger().debug("guardar solicitud fachada 5");
                        buscaUnidadAdminSolicitante(solicitud, rfcSolicitante);
                    }
                }
            }
        }
        SolicitudConsultaAutorizacion solicitudCA = solicitudCALDTOTransformer.transformarDTO(solicitud);
        getLogger().debug("guardar solicitud fachada 6");
        if (solicitud.getManifiestos() != null && solicitud.getManifiestos().size() > 0) {
            for (ManifiestoDTO manifiesto : solicitud.getManifiestos()) {
                solicitudCA.addEstadoManifiesto(manifiestoService.obtenerManifiestoPorId(manifiesto.getIdManifiesto()),
                        manifiesto.isAceptado());
            }
        }
        List<PersonaOirRecibirNotificaciones> personasONHelperList = solicitudCA.getPersonaONHelperList() != null
                ? solicitudCA.getPersonaONHelperList()
                : new ArrayList<PersonaOirRecibirNotificaciones>();
        List<PersonaResidenteExtranjero> personaONHelperList = solicitudCA.getPersonaResExtHelperList() != null
                ? solicitudCA.getPersonaResExtHelperList()
                : new ArrayList<PersonaResidenteExtranjero>();
        List<FraccionDuda> fraccionesDuda = solicitudCA.getFraccionDudaHelperList() != null
                ? solicitudCA.getFraccionDudaHelperList()
                : new ArrayList<FraccionDuda>();
        Autoridad autoridad1 = solicitudCA.getAutoridadHelper();
        Autoridad autoridad2 = solicitudCA.getAutoridadSujetoHelper();
        solicitudCA = getRegistroSolicitudCALCommonServices().guardarSolicitud(solicitudCA, rfcSolicitante);

        if (autoridad1 != null) {
            getLogger().debug("guardar solicitud fachada 7");
            autoridad1.setIdSolicitud(solicitudCA.getIdSolicitud());
            getRegistroSolicitudCALCommonServices().guardaAutoridad(autoridad1);
        }
        if (autoridad2 != null) {
            getLogger().debug("guardar solicitud fachada 8");
            autoridad2.setIdSolicitud(solicitudCA.getIdSolicitud());
            getRegistroSolicitudCALCommonServices().guardaAutoridad(autoridad2);
        }
        getLogger().debug("guardar solicitud fachada 9");
        for (PersonaOirRecibirNotificaciones personaOirRecibirNotificaciones : personasONHelperList) {
            personaOirRecibirNotificaciones.setIdSolicitud(solicitudCA.getIdSolicitud());
            listaPersonaOirNotificacionesDTO.add(solicitudCALDTOTransformer.transformaPersonaOirNotificaciones(
                    getRegistroSolicitudCALCommonServices().guardaPersonaOirNot(personaOirRecibirNotificaciones)));
        }
        getLogger().debug("guardar solicitud fachada 10");
        for (PersonaResidenteExtranjero personaResidenteExtranjero : personaONHelperList) {
            personaResidenteExtranjero.setIdSolicitud(solicitudCA.getIdSolicitud());
            listaPersonaInvolucradaDTO.add(solicitudCALDTOTransformer.transformaPersonaResidenteExtrajero(
                    getRegistroSolicitudCALCommonServices().guardaResidenteExtranjero(personaResidenteExtranjero)));
        }
        getLogger().debug("guardar solicitud fachada 11");
        this.eliminarFraccionesDuda(solicitud.getIdSolicitud());
        for (FraccionDuda fraccionDuda : fraccionesDuda) {
            fraccionDuda.setIdSolicitud(solicitudCA.getIdSolicitud());
            listaFraccionArancelariaDudaDTO.add(solicitudCALDTOTransformer
                    .transformaFraccionDuda(getRegistroSolicitudCALCommonServices().guardarFraccionDuda(fraccionDuda)));
        }
        getLogger().debug("guardar solicitud fachada 12");
        solicitud.getListaPersonasNotificaciones().clear();
        solicitud.getListaPersonasNotificaciones().addAll(listaPersonaOirNotificacionesDTO);
        solicitud.getListaPersonasInvolucradas().clear();
        solicitud.getListaPersonasInvolucradas().addAll(listaPersonaInvolucradaDTO);
        if (solicitudCA.getFraccionDudaHelperList() != null) {
            solicitud.getListaFraccionDuda().clear();
            solicitud.getListaFraccionDuda().addAll(listaFraccionArancelariaDudaDTO);
        }
        getLogger().debug("guardar solicitud fachada 13");
        if (solicitudCA.getMedioDefensaHelper() != null && solicitudCA.getMedioDefensaHelper().getBlnActivo()) {
            solicitud.setIdMedioDefensa(solicitudCA.getMedioDefensaHelper().getIdMedioDefensa());
        }
        getLogger().debug("guardar solicitud fachada 14");
        solicitud.setIdSolicitud(solicitudCA.getIdSolicitud());
        solicitud.setFechaCreacion(solicitudCA.getFechaCreacion());
        getLogger().debug("guardar solicitud fachada 15");
        return solicitud;
    }

    public void buscaUnidadAdminSolicitante(SolicitudCALDTO solicitud, String rfcSolicitante) {

    }

    /**
     * M&eacute;todo para obtener las modalidades por id de subservicio.
     * 
     * @param idSubservicio
     * @return Lista ModalidadesDTO
     */
    private List<ModalidadesDTO> obtenerModalidadesPorTipo(String idSubservicio, String tipoPersona) {
        List<TipoTramite> listaTipoTramite = getRegistroSolicitudCALCommonServices()
                .obtenerModalidadesPorSubservicio(idSubservicio, tipoPersona);
        if (listaTipoTramite != null && !listaTipoTramite.isEmpty()) {
            for (TipoTramite tram : listaTipoTramite) {
                Descripcion desc = catalogosServices.buscarDescripcionPorTipoIdentificador(
                        CatalogoConstantes.ENU_CATALOGO_DESCRIPCION_MODALIDAD, tram.getIdTipoTramite().toString());
                tram.setTooltip("");
                if (desc != null) {
                    tram.setTooltip(desc.getDescripcion());
                }
            }
        } else {
            listaTipoTramite = new ArrayList<TipoTramite>();
        }

        return modalidadesDTOTransformer.transformarDTO(listaTipoTramite);
    }

    /**
     * M&eacute;todo para obtener los documentos del tramite por tipo de documento.
     * 
     * @param idTipoTramite
     * @param opcional
     * @return Lista DocumentoDTO
     */
    private List<DocumentoDTO> obtenerdocumentosTramite(Integer idTipoTramite, Integer opcional,
            String tipoClasificacion) {
        List<DocumentoDTO> listaDocumentos = new ArrayList<DocumentoDTO>();
        List<DocumentoTramite> listaDocumentoTramite = getRegistroSolicitudCALCommonServices()
                .obtenerTiposDocumentosPorTramite(idTipoTramite, opcional);
        DocumentoTramite docTra = getRegistroSolicitudCALCommonServices().obtenerDocumentoRazonesLogicoJuridicas();
        if (idTipoTramite.toString().equals(DiscriminadorConstants.T2_CLASIFICACION_ARANCELARIA)) {
            if (tipoClasificacion != null && tipoClasificacion.equals("CLA.CON")) {
                if (opcional == 1) {
                    listaDocumentoTramite.add(docTra);
                    for (DocumentoTramite documento : listaDocumentoTramite) {
                        if (documento.getDocumentoTramitePK().getIdTipoDoc() != docTra.getDocumentoTramitePK()
                                .getIdTipoDoc()) {
                            listaDocumentos.add(documentoDTOTransformer.transformarDTO(documento));
                        }
                    }
                } else if (opcional == 0) {
                    listaDocumentoTramite.add(docTra);
                    for (DocumentoTramite documento : listaDocumentoTramite) {
                        listaDocumentos.add(documentoDTOTransformer.transformarDTO(documento));
                    }
                }
            } else {

                if (opcional == 1) {
                    DocumentoDTO docLogico = documentoDTOTransformer.transformarDTO(docTra);
                    for (DocumentoTramite documento : listaDocumentoTramite) {
                        if (!listaDocumentos.contains(docLogico)) {
                            listaDocumentos.add(documentoDTOTransformer.transformarDTO(documento));
                        }
                    }

                } else if (opcional == 0) {
                    listaDocumentoTramite.add(docTra);
                    for (DocumentoTramite documento : listaDocumentoTramite) {
                        if (!documento.getDocumentoTramitePK().getIdTipoDoc()
                                .equals(docTra.getDocumentoTramitePK().getIdTipoDoc())) {
                            listaDocumentos.add(documentoDTOTransformer.transformarDTO(documento));
                        }
                    }
                }
            }
        } else {
            for (DocumentoTramite documentoTramite : listaDocumentoTramite) {
                DocumentoDTO documento = documentoDTOTransformer.transformarDTO(documentoTramite);
                listaDocumentos.add(documento);
            }

        }
        return listaDocumentos;
    }

    public InputStream descargarArchivo(String ruta) {
        return getRegistroSolicitudCALCommonServices().descargarArchivo(ruta);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mx.gob.sat.siat.juridica.cal.api.RegistroSolicitudCALFacade
     * #guardarDocumentosSolicitud
     * (mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO, java.util.List,
     * java.util.List)
     */
    public void guardarDocumentosSolicitud(SolicitudCALDTO solicitud, List<DocumentoDTO> listaDocumentos,
            List<DocumentoDTO> listaDocumentosR, String rfcCapturista) throws ArchivoNoGuardadoException {
        Persona persona = getRegistroSolicitudCALCommonServices().obtenerPersonaPorRFC(rfcCapturista);
        List<Documento> listaDeDocumento = documentoDTOTransformer.transdormarModelDocumento(listaDocumentos, persona);
        List<Documento> listaDeDocumentoRequeridos = documentoDTOTransformer.transdormarModelDocumento(listaDocumentosR,
                persona);

        getRegistroSolicitudCALCommonServices().validarDocumentosRequeridos(listaDeDocumento,
                listaDeDocumentoRequeridos);
        List<DocumentoSolicitud> listaDocumentoSolicitud = documentoDTOTransformer
                .transformarModelDocumentoSolicitud(solicitud.getIdSolicitud(), persona, listaDeDocumento);
        if (solicitud.getNumeroFolio() != null) {
            for (DocumentoSolicitud docSol : listaDocumentoSolicitud) {
                docSol.setFolio(Long.parseLong(solicitud.getNumeroFolio()));
                docSol.setFechaApertura(solicitud.getFechaApertura());
            }
        }
        getRegistroSolicitudCALCommonServices().guardarDocumentos(listaDeDocumento, listaDocumentoSolicitud);
    }

    public List<DocumentoDTO> guardarDocumentosParcialSolicitud(SolicitudCALDTO solicitud,
            List<DocumentoDTO> listaDocumentos, String rfcCapturista) {
        List<DocumentoDTO> listaDocumentosDTO = new ArrayList<DocumentoDTO>();
        if (listaDocumentos != null && !listaDocumentos.isEmpty()) {
            Persona persona = getRegistroSolicitudCALCommonServices().obtenerPersonaPorRFC(rfcCapturista);
            List<Documento> listaDeDocumento = documentoDTOTransformer.transdormarModelDocumento(listaDocumentos,
                    persona);
            List<DocumentoSolicitud> listaDocumentoSolicitud = documentoDTOTransformer
                    .transformarModelDocumentoSolicitud(solicitud.getIdSolicitud(), persona, listaDeDocumento);

            for (DocumentoSolicitud docSol : listaDocumentoSolicitud) {

                if ((solicitud.getNumeroFolio() != null)) {
                    docSol.setFolio(Long.parseLong(solicitud.getNumeroFolio()));
                }
                docSol.setFechaApertura(solicitud.getFechaApertura());
            }

            List<DocumentoSolicitud> documentosSolicitud = getRegistroSolicitudCALCommonServices()
                    .guardarParcialDocumentos(solicitud.getIdSolicitud(), listaDeDocumento, listaDocumentoSolicitud);
            if (documentosSolicitud != null) {
                for (DocumentoSolicitud docSol : documentosSolicitud) {
                    DocumentoDTO docAnexadoDTO = documentoDTOTransformer.transformarDocumentoDTO(docSol.getDocumento(),
                            docSol.getIdDocumentoSolicitud());
                    TipoDocumento td = documentosServices.obtenerTipoDocumentoPorId(docSol.getIdTipoDocumento());
                    docAnexadoDTO.setTipoDocumento(td.getNombre());

                    listaDocumentosDTO.add(docAnexadoDTO);
                }
            }
        }

        return listaDocumentosDTO;
    }

    public String generaCadenaOriginal(Long idSolicitud, Date fechaFirma) {
        return getRegistroSolicitudCALCommonServices().generaCadenaOriginal(idSolicitud, fechaFirma);
    }

    @Override
    public Tarea firmaSolicitud(SolicitudCALDTO solicitud, String usuario, FirmaDTO firma) {

        Firma firmaModel = firmasSolicitudTransformer.transformarDTO(firma);

        List<Object> resultados = solicitudCALServices.firmarSolicitud(solicitud.getIdSolicitud(),
                solicitud.getSolicitante().getRfc(), usuario, firmaModel);

        MensajeBPM mensajeBPM = (MensajeBPM) resultados.get(0);
        SolicitudDatosGenerales sol = (SolicitudDatosGenerales) resultados.get(1);
        String administradorResponsable = (String) resultados.get(2);
        mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite inicioTramite = (mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite) resultados
                .get(NumerosConstantes.TRES);
        Tramite tramite = (Tramite) resultados.get(NumerosConstantes.CUATRO);

        Tarea tarea = solicitudCALServices.iniciaTramite(mensajeBPM, sol, administradorResponsable);

        if (administradorResponsable != null) {
            enviarCorreoService.enviarCorreoTareaPendiente(tramite.getNumeroAsunto(), administradorResponsable,
                    "Turnar Asunto");
        }
        if (sol.getUnidadAdminBalanceo() != null) {
            enviarCorreoService.enviarCorreoAdministracion(tramite.getNumeroAsunto(), sol.getUnidadAdminBalanceo());
        }

        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setFechaEvento(new Date());
            bitacora.setNumeroAsunto(inicioTramite.getFolioTramite());
            bitacora.setRfcUsuario(usuario);
            bitacora.setUnidadAdministrativa(sol.getUnidadAdminBalanceo());
            bitacora.setTarea(EnumeracionBitacora.REGISTRO.getClave());
            bitacora.setRfcNuevo(administradorResponsable);
            bitacoraTramiteServices.insertarBitacora(bitacora);

        } catch (Exception ex) {
            getLogger().error("Error en la bitacora al firmar la solicitud", ex);
        }
        return tarea;
    }

    @Override
    public List<CatalogoDTO> obtenerListaTipoPersona() {
        List<CatalogoDTO> listaCatalogo = catalogoDTOTransformer
                .transformarCatalogo(getRegistroSolicitudCALCommonServices().obtenerTipoPersona());
        return listaCatalogo;
    }

    @Override
    public List<CatalogoDTO> obtenerListaTipoClasificacion() {
        List<CatalogoDTO> listaCatalogo = catalogoDTOTransformer
                .transformarEnumeracionTrDTO(getRegistroSolicitudCALCommonServices().obtenerTipoClasificacion());
        return listaCatalogo;
    }

    public void eliminaPersona(Long idPersona) {
        getRegistroSolicitudCALCommonServices().eliminaPersona(idPersona);
    }

    @Override
    public void eliminaFaccion(Long idFraccion) {
        getRegistroSolicitudCALCommonServices().eliminaFraccion(idFraccion);
    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosAnexados(long idSolicitud,
            List<DocumentoDTO> listaDocumentosSeleccionadosAnexar) {
        List<DocumentoDTO> documentosAnexados = new ArrayList<DocumentoDTO>();

        if (listaDocumentosSeleccionadosAnexar != null && !listaDocumentosSeleccionadosAnexar.isEmpty()) {
            List<Documento> documentosSeleccion = documentoDTOTransformer
                    .transdormarModelDocumento(listaDocumentosSeleccionadosAnexar, null);

            List<DocumentoSolicitud> documentosSolicitud = documentosServices.filtraDocumentosSeleccionados(idSolicitud,
                    documentosSeleccion);
            if (documentosSolicitud != null) {
                for (DocumentoSolicitud docSol : documentosSolicitud) {
                    DocumentoDTO docDTO = documentoDTOTransformer.transformarDocumentoDTO(docSol.getDocumento(),
                            docSol.getIdDocumentoSolicitud());
                    TipoDocumento td = documentosServices.obtenerTipoDocumentoPorId(docSol.getIdTipoDocumento());

                    docDTO.setTipoDocumento(td.getNombre());
                    documentosAnexados.add(docDTO);
                }
            }
        }

        return documentosAnexados;
    }

    /**
     * Remueve de listaDocumentosSolicitud el documento identificado por
     * idDocumentoSol
     * 
     * @param listaDocumentosSolicitud Lista de documentos
     * @param idDocumentoSol           Id del documento a remover de la lista
     * 
     * @return List<DocumentoSolicitud> con los elementos en
     *         listaDocumentosSolicitud, menos aquel cuyo idDocumentoSolicitud sea
     *         igual al par&aacute;metro idDocumentoSol.
     */
    public List<DocumentoSolicitud> remueveDocumento(List<DocumentoSolicitud> listaDocumentosSolicitud,
            long idDocumentoSol) {
        List<DocumentoSolicitud> listaDocsFiltrados = null;
        if (listaDocumentosSolicitud != null) {
            listaDocsFiltrados = new ArrayList<DocumentoSolicitud>(listaDocumentosSolicitud.size());
            for (DocumentoSolicitud docSol : listaDocumentosSolicitud) {
                if (!docSol.getIdDocumentoSolicitud().equals(Long.valueOf(idDocumentoSol))) {
                    listaDocsFiltrados.add(docSol);
                }
            }
        }

        return listaDocsFiltrados;
    }

    /**
     * Elimina el documento especificado por idDocumentoSol
     * 
     * @param idDocumentoSol
     */
    public void eliminaDocumento(long idDocumentoSol) {
        documentosServices.eliminaDocumentoSol(idDocumentoSol);
    }

    /**
     * Devuelve la lista de documentos que est�n en listaDocumentosOpcionales y que
     * ya hayan sido anexados a la solicitud
     * 
     * @param listaDocumentosOpcionales
     * @param idSolicitud               Id de la solicitud
     * @return Lista de documentos que est�n en listaDocumentosOpcionales y que ya
     *         hayan sido anexados a la solicitud
     */
    @Override
    public DocumentoDTO[] obtenerDocumentosSeleccionados(long idSolicitud,
            List<DocumentoDTO> listaDocumentosOpcionales) {
        List<DocumentoDTO> documentosSeleccion = new ArrayList<DocumentoDTO>();
        List<Integer> idsTipoDocumentosSeleccion = new ArrayList<Integer>();

        if (listaDocumentosOpcionales != null && !listaDocumentosOpcionales.isEmpty()) {
            List<DocumentoSolicitud> documentosSolicitud = documentosServices.obtenerDocumentosAnexados(idSolicitud);
            if (documentosSolicitud != null) {
                for (DocumentoSolicitud docSol : documentosSolicitud) {
                    DocumentoDTO docSolDTO = documentoDTOTransformer.transformarDocumentoDTO(docSol.getDocumento());

                    for (DocumentoDTO docOpcional : listaDocumentosOpcionales) {

                        if (docOpcional.getIdTipoDocumento().equals(docSolDTO.getIdTipoDocumento())
                                && !idsTipoDocumentosSeleccion.contains(docOpcional.getIdTipoDocumento())) {
                            documentosSeleccion.add(docOpcional);
                            idsTipoDocumentosSeleccion.add(docSolDTO.getIdTipoDocumento());
                            break;
                        }
                    }
                }
            }
        }

        return documentosSeleccion.toArray(new DocumentoDTO[0]);
    }

    public TipoTramiteDTO obtenerTipoTramitePorId(String idTIpoTramite) {
        return tipoTramiteDTOTransformer
                .transformarDTO(getRegistroSolicitudCALCommonServices().obtenerTipoTramitePorId(idTIpoTramite));
    }

    @Override
    /**
     * Obtiene una Firma con la cadena original, y la firma (sello) generada por la
     * SIAT JURIDICA
     * 
     * @param numAsunto   Numero de asunto
     * @param idSolicitud Id de la solicitud
     * @param fechaFirma  Fecha de firma
     * @return FirmaDTO con la cadena y sello de la selladora (SIAT Juridica)
     */
    public FirmaDTO obtenSelloPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma) {
        Firma firma = getRegistroSolicitudCALCommonServices().generaFirmaPromocionSIAT(numAsunto, idSolicitud,
                fechaFirma);
        FirmaDTO firmaDTO = firmaTransformer.transformarFirma(firma);

        return firmaDTO;
    }

    public boolean tieneDocumentosAnexados(String idSolicitud) {
        return getRegistroSolicitudCALCommonServices().tieneDocumentosAnexados(idSolicitud);
    }

    public PersonaSolicitudDTO buscarPersonaSolicitante(String rfc) throws IDCNoDisponibleException, IDCException,
            ContribuyenteNoEncontradoException, ContribuyenteInactivoException, RFCNoVigenteException,
            BuzonNoDisponibleException, CorreoNoRegistradoException {
        Solicitante solicitante = buscarPersonaService.buscarPersonaSolicitanteIdc(rfc);
        if (solicitante != null) {
            return personaSolicitudTransformer.transformarDTO(solicitante);
        } else {
            return null;
        }
    }

    public abstract RegistroSolicitudCALCommonServices getRegistroSolicitudCALCommonServices();

    public void eliminarFraccionesDuda(Long idSolicitud) {
        getRegistroSolicitudCALCommonServices().eliminarFraccionesDuda(idSolicitud);
    }

}
