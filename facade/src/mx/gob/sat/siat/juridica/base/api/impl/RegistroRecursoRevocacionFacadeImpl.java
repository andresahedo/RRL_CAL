package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.RegistroRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.DocumentoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.CatalogoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.DocumentoDTOTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.FirmaTransformer;
import mx.gob.sat.siat.juridica.base.dto.transformer.SolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.*;
import mx.gob.sat.siat.juridica.base.util.constante.BuzonConstantes;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.bpm.excepcion.TareaInicialException;
import mx.gob.sat.siat.juridica.bpm.excepcion.TereaSinUsuarioAsignadoException;
import mx.gob.sat.siat.juridica.buzon.exception.BuzonNoDisponibleException;
import mx.gob.sat.siat.juridica.buzon.exception.CorreoNoRegistradoException;
import mx.gob.sat.siat.juridica.buzon.service.BuzonTributarioService;
import mx.gob.sat.siat.juridica.idc.service.IdcJuridicoService;
import mx.gob.sat.siat.juridica.rrl.dao.domain.model.SolicitudRecursoRevocacion;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.DatosSolicitudDTOTransformer;
import mx.gob.sat.siat.juridica.rrl.dto.transformer.FirmasSolicitudTransformer;
import mx.gob.sat.siat.juridica.rrl.service.RegistroRecursoRevocacionServices;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("registroRecursoRevocacionFacade")
public class RegistroRecursoRevocacionFacadeImpl extends BaseFacadeImpl implements RegistroRecursoRevocacionFacade {

    private static final long serialVersionUID = 5118516847185582663L;
    private static final String FORMATO_FECHA = "dd/MM/yyyy HH:mm:ss:SS";

    @Autowired
    private transient RegistroRecursoRevocacionServices registroRecursoRevocacionServices;
    @Autowired
    private transient IdcJuridicoService idcJuridicoService;
    @Autowired
    private transient CatalogoDTOTransformer catalogoDTOTransformer;
    @Autowired
    private transient DatosSolicitudDTOTransformer datosSolicitudDTOTransformer;
    @Autowired
    private transient SolicitudDTOTransformer solicitudDTOTransformer;

    @Autowired
    private transient FirmasSolicitudTransformer firmasSolicitudTransformer;
    @Autowired
    private transient SolicitudService solicitudService;
    @Autowired
    private transient FirmaTransformer firmaTransformer;
    @Autowired
    private transient TareaServices tareaServices;

    
    @Autowired
    private transient DocumentoDTOTransformer documentoDTOTransformer;

    @Autowired
    private transient DocumentosServices documentosServices;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient TipoTramiteServices tipoTramiteService;

    @Autowired
    private transient AsignacionTramiteServices asignacionTramiteServices;

    @Autowired
    private transient BuzonTributarioService buzonTributarioService;

    @Autowired
    private transient EnviarCorreoService enviarCorreoService;

    @Autowired
    private transient BitacoraTramiteServices bitacoraTramiteServices;

    @Override
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        List<UnidadAdministrativa> listaUA = registroRecursoRevocacionServices.obtenerCatalogo();

        return catalogoDTOTransformer.transformarDTO(listaUA);
    }

    @Override
    public DatosSolicitudDTO obtenerInformacionIDC(String rfc) {
        Solicitante sol = new Solicitante();
        String mensajeBuzon = null;
        boolean muestraMensaje = false;
        try {
            sol = idcJuridicoService.buscarContribuyenteIdc(rfc, new Solicitante());            
        }
        catch (Exception e) {
            getLogger().error("", e);
        }

        try {
            String correo = buzonTributarioService.obtenerCorreos(rfc);
            if (correo != null && !correo.equals("")) {
                sol.setCorreoElectronico(correo);
                sol.setBlnAmparado(true);
                muestraMensaje = false;
            }
            else {
                muestraMensaje = true;
                mensajeBuzon = BuzonConstantes.SIN_CORREOS;
            }
        }
        catch (BuzonNoDisponibleException e) {
            getLogger().error("", e);
            muestraMensaje = true;
            mensajeBuzon = BuzonConstantes.BUZON_NO_DISPONIBLE;
        }
        catch (CorreoNoRegistradoException e) {
            getLogger().error("", e);
            muestraMensaje = true;
            mensajeBuzon = BuzonConstantes.SIN_CORREOS;
        }
        DatosSolicitudDTO datosSol = datosSolicitudDTOTransformer.transformarDTO(sol);
        datosSol.setMuestraMensajeBuzon(muestraMensaje);
        datosSol.setMensajeBuzon(mensajeBuzon);

        return datosSol;

    }

    @Override
    public SolicitudDTO guardarSolicitud(DatosSolicitudDTO datosSolicitud) throws SolicitudNoGuardadaException {
        SolicitudRecursoRevocacion solicitud = datosSolicitudDTOTransformer.transformarSolicitudModel(datosSolicitud);
        Solicitante solicitante = datosSolicitudDTOTransformer.transformarSolicitanteModel(datosSolicitud);
        solicitud = registroRecursoRevocacionServices.guardarSolicitud(solicitud, solicitante);

        return solicitudDTOTransformer.transformarDTO(solicitud);
    }

    public String generarCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return solicitudService.generarCadenaOriginalRegistroPromocionRRL(idSolicitud, fechaFirma);
    }

    @Override
    public String firmarSolicitud(long idSolicitud, FirmaDTO firma, String usuario, String rfcContribuyente,
            Object ceritifcadoUtilizado) throws TareaInicialException, TereaSinUsuarioAsignadoException {
        getLogger().debug("Profiling inicio service obtenerTramitePorIdSolicitud {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        Tramite tramite = tramiteServices.obtenerTramitePorIdSolicitud(idSolicitud);
        getLogger().debug("Profiling fin service obtenerTramitePorIdSolicitud {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        if (tramite != null) {
            return tramite.getNumeroAsunto();
        }
        Firma firmaModel = firmasSolicitudTransformer.transformarDTO(firma);


        getLogger().debug("Profiling inicio service firmarSolicitud {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        List<Object> resultados = solicitudService.firmarSolicitud(idSolicitud, firmaModel, usuario, rfcContribuyente,
            ceritifcadoUtilizado);
        getLogger().debug("Profiling fin service firmarSolicitud {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        SolicitudDatosGenerales solDat = (SolicitudDatosGenerales)resultados.get(NumerosConstantes.UNO);
        String administradorResponsable = (String)resultados.get(NumerosConstantes.DOS);
        mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite inicioTramite = (mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite)resultados.get(NumerosConstantes.TRES);
        tramite = (Tramite)resultados.get(NumerosConstantes.CUATRO);
        getLogger().debug("Profiling inicio service iniciaTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
       try {
            Tarea tarea = new Tarea();
            tarea.setNumeroAsunto(tramite.getNumeroAsunto());
            tarea.setClaveAdm(administradorResponsable);
            tarea.setClaveAsignado(administradorResponsable);
            tarea.setTarea(EnumeracionBitacora.TURNAR_ASUNTO.getClave());
            tarea.setDescripcion(EnumeracionBitacora.TURNAR_ASUNTO.getDescripcion());
            tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tarea.setFechaCreacion(new Date());
            tarea.setFechaAct(new Date());
            tareaServices.guardarTarea(tarea);
        }
        catch (Exception ex){
            getLogger().error("Error en la tarea al generar la tarea", ex);
        }


        getLogger().debug("Profiling fin service iniciaTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        if (administradorResponsable != null) {
            getLogger().debug("Profiling inicio service enviarCorreoTareaPendiente {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
            enviarCorreoService.enviarCorreoTareaPendiente(tramite.getNumeroAsunto(), administradorResponsable,
                    "Turnar Asunto");
            getLogger().debug("Profiling fin service enviarCorreoTareaPendiente {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        }
        if (solDat.getUnidadAdminBalanceo() != null) {
            getLogger().debug("Profiling inicio service enviarCorreoAdministracion {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
            enviarCorreoService.enviarCorreoAdministracion(tramite.getNumeroAsunto(), solDat.getUnidadAdminBalanceo());
            getLogger().debug("Profiling fin service enviarCorreoAdministracion {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        }

        try{
            Bitacora bitacora = new Bitacora();
            bitacora.setFechaEvento(new Date());
            bitacora.setNumeroAsunto(inicioTramite.getFolioTramite());
            bitacora.setRfcUsuario(usuario);
            bitacora.setUnidadAdministrativa(solDat.getUnidadAdminBalanceo());
            bitacora.setTarea(EnumeracionBitacora.REGISTRO.getClave());
            bitacora.setRfcNuevo(administradorResponsable);
            bitacoraTramiteServices.insertarBitacora(bitacora);
            if (TipoRol.OFICIAL_PARTES.getClave().equals(solDat.getCveRolCapturista())) {
                getLogger().debug("Profiling inicio service guardarAsignacionTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
                asignacionTramiteServices.guardarAsignacionTramite(tramite.getNumeroAsunto(), usuario,
                        TipoRol.OFICIAL_PARTES.getClave());
                getLogger().debug("Profiling fin service guardarAsignacionTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
            }
            getLogger().debug("Profiling inicio service guardarAsignacionTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
            asignacionTramiteServices.guardarAsignacionTramite(tramite.getNumeroAsunto(), administradorResponsable,
                    TipoRol.ADMINISTRADOR.getClave());
            getLogger().debug("Profiling fin service guardarAsignacionTramite {}", new SimpleDateFormat(FORMATO_FECHA).format( new Date()) );
        }catch(Exception ex){
            getLogger().error("Error en la bitacora al firmar la solicitud", ex);
        }

        return inicioTramite.getFolioTramite();
    }

    @Override
    public void firmarDocumentos(Long idSolicitud, FirmaDTO firmaDTO) {
        Firma firma = firmaTransformer.transformarDTO(firmaDTO);
        solicitudService.firmarDocumentosRegistro(idSolicitud, firma, false);

    }

    @Override
    public void sellaDocumentos(Long idSolicitud) {
        solicitudService.sellaDocumentos(idSolicitud);
    }

    @Override
    public DatosSolicitudDTO obtenerSolicitudPorId(Long idSolicitud) {
        return datosSolicitudDTOTransformer.transformarDTO((SolicitudRecursoRevocacion) solicitudService
                .obtenerSolicitudporId(idSolicitud));
    }

    @Override
    /**
     * Obtiene una Firma con la cadena original, y la firma (sello) generada por la SIAT JURIDICA
     * @param numAsunto Numero de asunto
     * @param idSolicitud Id de la solicitud
     * @param fechaFirma Fecha de firma
     * @return FirmaDTO con la cadena y sello de la selladora (SIAT Juridica)
     */
    public FirmaDTO obtenSelloPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma) {
        Firma firma = solicitudService.generaFirmaRegistroPromocionSIAT(numAsunto, idSolicitud, fechaFirma);
        FirmaDTO firmaDTO = firmaTransformer.transformarFirma(firma);

        return firmaDTO;
    }

    @Override
    public DatosSolicitudDTO guardarSolicitud(DatosSolicitudDTO datosSolicitud, String rfcCapturista)
            throws SolicitudNoGuardadaException {
        SolicitudRecursoRevocacion solicitud = datosSolicitudDTOTransformer.transformarSolicitudModel(datosSolicitud);
        Solicitante solicitante = datosSolicitudDTOTransformer.transformarSolicitanteModel(datosSolicitud);
        solicitud = registroRecursoRevocacionServices.guardarSolicitud(solicitud, solicitante, rfcCapturista);

        return datosSolicitudDTOTransformer.transformarDTO(solicitud);
    }

    @Override
    public List<DocumentoDTO> getDocumentosObligatorios(Integer idTipoTramite) {
        List<DocumentoDTO> docsDTO = new ArrayList<DocumentoDTO>();
        List<DocumentoTramite> docsTramite =
                registroRecursoRevocacionServices.obtenerDocumentosPorTipoTramite(idTipoTramite, 1);
        for (DocumentoTramite docTramite : docsTramite) {
            DocumentoDTO docDTO = documentoDTOTransformer.transformarDTO(docTramite);
            docsDTO.add(docDTO);
        }
        return docsDTO;

    }

    @Override
    public List<DocumentoDTO> getDocumentosOpcionales(Integer idTipoTramite) {
        List<DocumentoDTO> docsDTO = new ArrayList<DocumentoDTO>();
        List<DocumentoTramite> docsTramite =
                registroRecursoRevocacionServices.obtenerDocumentosPorTipoTramite(idTipoTramite, 0);
        for (DocumentoTramite docTramite : docsTramite) {
            DocumentoDTO docDTO = documentoDTOTransformer.transformarDTO(docTramite);
            docsDTO.add(docDTO);
        }
        return docsDTO;
    }

    /**
     * Devuelve la lista de documentos que estan en
     * listaDocumentosOpcionales y que ya hayan sido anexados a la
     * solicitud
     * 
     * @param listaDocumentosOpcionales
     * @param idSolicitud
     *            Id de la solicitud
     * @return Lista de documentos que estan en
     *         listaDocumentosOpcionales y que ya hayan sido anexados
     *         a la solicitud
     */
    @Override
    public DocumentoDTO[]
            obtenerDocumentosSeleccionados(long idSolicitud, List<DocumentoDTO> listaDocumentosOpcionales) {
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

    @Override
    public String obtenerModalidadDeTramite(Integer idTipoTramite) {

        return tipoTramiteService.obtenerTipoTramite(idTipoTramite);
    }

    @Override
    public List<DocumentoDTO> obtenerDocumentosPorIdSolicitud(Long idSolicitud) {
        List<DocumentoSolicitud> listaDocumentos = documentosServices.obtenerDocumentosPorIdSolicitud(idSolicitud);
        List<DocumentoDTO> listaDocumentosDTO;
        if (listaDocumentos != null && !listaDocumentos.isEmpty()) {
            listaDocumentosDTO = documentoDTOTransformer.tranformarDocSolicitud(listaDocumentos);
            return listaDocumentosDTO;
        }
        else {
            return null;
        }
    }

}
