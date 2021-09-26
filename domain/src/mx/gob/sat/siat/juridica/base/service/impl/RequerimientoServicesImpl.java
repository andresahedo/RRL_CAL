/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.*;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.EnumeracionTr;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.*;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.base.excepcion.BusinessException;
import mx.gob.sat.siat.juridica.base.service.EnviarCorreoService;
import mx.gob.sat.siat.juridica.base.service.RequerimientoServices;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.util.helper.DocumentosHelper;
import mx.gob.sat.siat.juridica.base.util.helper.SelladoraHelper;
import mx.gob.sat.siat.juridica.ca.util.helper.GenerarCadenasHelper;
import mx.gob.sat.siat.juridica.rrl.dao.RegistroRecursoRevocacionDAO;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import mx.gob.sat.siat.juridica.rrl.util.exception.FechaInvalidaException;
import mx.gob.sat.siat.juridica.rrl.util.helper.TramiteHelper;
import mx.gob.sat.siat.selladora.SelladoraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Servicio para atender las peticiones del requerimiento.
 * 
 * @author Softtek
 * 
 */
@Service("requerimientoServices")
public class RequerimientoServicesImpl extends BaseSerializableBusinessServices
        implements RequerimientoServices {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = -4465439269873257374L;
    /**
     * DAO para atender las solicitudes a BD de las unidades administrativas
     */

    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;
    /** DAO para atender las solicitudes a BD del requerimiento */
    @Autowired
    private RequerimientoDao requerimientoDao;
    /** DAO para atender las solicitudes a BD de las enumeraciones */
    @Autowired
    private EnumeracionDao enumeracionDao;

    @Autowired
    private DocumentoDao documentoDao;

    @Autowired
    private DocumentosHelper documentosHelper;

    @Autowired
    private SelladoraHelper selladoraHelper;

    /** DAO para atender las solicitudes a BD del tramite */
    @Autowired
    private TramiteDao tramiteDao;
    /** DAO para atender las solicitudes a BD de las personas */
    /**
     * Atributo xmlHelper tipo XMLHelper
     */
    @Autowired
    private GenerarCadenasHelper generarCadenasHelper;

    @Autowired
    private SolicitudDAO solicitudDao;
    /**
     * Atributo bpmjmsDao tipo BPMJMSDao
     */
    @Autowired
    private PersonaDao personaDao;
    @Autowired
    private NotificacionDao notificacionDao;

    /** Validator para atender reglas de negocio del requerimiento */
    @Autowired
    private RegistroRecursoRevocacionDAO registroRecursoRevocacionDAO;

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private transient TareaServices tareaServices;

    @Autowired
    private DiaInhabilDAO diaInhabilDAO;

    @Autowired
    private TramiteHelper tramiteHelper;

    @Autowired
    private EnviarCorreoService enviarCorreoService;

    /**
     * M&eacute;todo para obtener los tipos de requerimiento.
     */
    public List<EnumeracionTr> obtenerTiposDeRequerimiento(
            String ideEnumeracionH) {
        return enumeracionDao.obtenerEnumeracionPorId(ideEnumeracionH);
    }

    /**
     * M&eacute;todo para obtener unidades administrativas por tipo.
     */
    public List<UnidadAdministrativa> obtenerUnidadesPorTipo(
            TipoUnidadAdministrativa tipoUnidad) {
        return unidadAdministrativaDao.obtenerUnidadesPorTipo(tipoUnidad);
    }

    /**
     * M&eacute;todo para obtener unidades administrativas por tipo.
     */
    @Override
    public List<UnidadAdministrativa> obtenerUnidadesTodas() {
        return unidadAdministrativaDao.obtenerUnidAdmvasVigentes();
    }

    public List<Persona> obtenerAutorizadores(String numeroAsunto) {
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);

        return personaDao.buscarPersonaSinUARByRol(tramite,
                TipoRol.ADMINISTRADOR.getClave());

    }

    /**
     * M&eacute;todo para obtener tramite por n&uacute;mero de asunto.
     */
    public Tramite buscarTramitePorAsunto(String numeroAsunto) {
        return tramiteDao.obtenerTramitePorId(numeroAsunto);
    }

    /**
     * M&eacute;todo para guardar requerimiento.
     */
    @Override
    public List<RequerimientoTarea> guardarRequerimientos(List<Requerimiento> requerimientos)
            throws BusinessException {
        List<RequerimientoTarea> requerimientoTareas = new ArrayList<RequerimientoTarea>();
        if (requerimientos != null && !requerimientos.isEmpty()) {
            Tramite tramite = tramiteDao.obtenerTramitePorId(requerimientos
                    .get(0).getTramite().getNumeroAsunto());
            documentosHelper.complementarDocumentos(tramite.getSolicitud()
                    .getIdSolicitud());
    
    
            if(!requerimientoDao.obtenerTramitesActivos(tramite.getNumeroAsunto())){
                for (Requerimiento  requerimiento: requerimientos) {
                    RequerimientoTarea requerimientoBPM = new RequerimientoTarea();
                    int idSubP;
                    requerimientoDao.crearRequerimiento(requerimiento);
                    idSubP =requerimiento.getIdRequerimiento().intValue();
                    requerimientoBPM.setIdSubProceso(idSubP);
                    requerimientoBPM.setTipoRequerimiento(requerimiento.getTipoRequerimientoBpm().getClave());
                    requerimientoTareas.add(requerimientoBPM);
                }
            }
            else {
                throw new BusinessException();
            }
           
            
        }
        return requerimientoTareas;
    }

    /**
     * M&eacute;todo para obtener el &uacute;ltimo requerimiento del tramite
     * solicitado.
     */
    @Override
    public Requerimiento obtenerUltimoRequerimientoPorTramite(String idTramite,
            String modalidad) {
        if (modalidad
                .equals(DiscriminadorConstants.T1_CLASIFICACION_ARANCELARIA)) {
            return requerimientoDao
                    .obtenerUltimoRequerimientoPorTramiteRRL(idTramite);
        } else {
            return requerimientoDao
                    .obtenerUltimoRequerimientoPorTramiteCAL(idTramite);
        }
    }

    @Override
    public Requerimiento obtenerUltimoRequerimientoPorTramiteTarea(String idTramite,
                                                              String modalidad) {
        if (modalidad
                .equals(DiscriminadorConstants.T1_CLASIFICACION_ARANCELARIA)) {
            return requerimientoDao
                    .obtenerUltimoRequerimientoPorTramiteRRLTarea(idTramite);
        } else {
            return requerimientoDao
                    .obtenerUltimoRequerimientoPorTramiteCAL(idTramite);
        }
    }

    @Override
    public Requerimiento obtenerRequerimientoAutoridadInterna(String idTramite){
        return  requerimientoDao.obtenerRequerimientoAutoridadInterna(idTramite);
    }

    @Override
    public Requerimiento obtenerRequerimientoContributente(String idTramite, String modalidad){
        if (modalidad
                .equals(DiscriminadorConstants.T1_CLASIFICACION_ARANCELARIA)) {
            return requerimientoDao
                    .obtenerRequerimientoContributente(idTramite);
        } else {
            return requerimientoDao
                    .obtenerUltimoRequerimientoPorTramiteCAL(idTramite);
        }
    }

    /**
     * M&eacute;todo para obtener el &uacute;ltimo requerimiento del tramite
     * solicitado.
     */
    @Override
    public String obtenerAbogadoRequerimiento(Long idRequerimiento) {
        return personaDao.obtenerAbogadoRequerimiento(idRequerimiento);

    }

    @Override
    public Requerimiento obtenerRequerimientoById(Long idRequerimiento) {
        Requerimiento req = requerimientoDao
                .obtenerRequerimientoPorId(idRequerimiento);
        req.getTramite().getEstadoTramite();
        return req;
    }

    /**
     * Metodo para rechazar un requerimiento
     * 
     * @param numAsunto
     * @param idTarea
     * @return
     */
    @Override
    public void rechazarRequerimiento(String numAsunto, String idTarea,
            String rfc, Long idRequerimiento) {

        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);

        tramiteDao.modificarTramite(tramite);

        Requerimiento requerimiento = requerimientoDao
                .obtenerRequerimientoPorId(idRequerimiento);

        getLogger().debug("TareaServicesImpl : firmarTurnarAct");
        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);

            List<Requerimiento> reqActivos = requerimientoDao.obtenerRequerimientosActivos(tramite.getNumeroAsunto());
           if(reqActivos.size() <= 1 ) {
                getLogger().debug("TareaServicesImpl : firmarTurnarNuevaTarea");
                Date dt = new Date();
                Tarea tarea = new Tarea();
                tarea.setNumeroAsunto(numAsunto);
                tarea.setClaveAdm(tareaAct.getClaveAdm());
                String abogado = requerimiento.getIdUsuario();
                tarea.setClaveAsignado(abogado);
                tarea.setClaveAbogado(tareaAct.getClaveAbogado());
                tarea.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
                tarea.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
                tarea.setFechaCreacion(dt);
                tarea.setFechaAct(dt);
                tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
                tareaServices.guardarTarea(tarea);
            }
        }
        catch (Exception ex){
            getLogger().error("Error al turnar el asunto en tarea y bitacora", ex);
        }


        requerimiento.setEstadoRequerimiento(EstadoRequerimiento.RECHAZADO);
        requerimiento.setFechaAutorizacion(new Date());

    }

    @Override
    public List<DocumentoOficial> buscarTiposDocumentosOfiAtencionRetroPorIdTramite(
            String numAsunto) {

        return documentoDao.obtenerDocumentosPorTipo(numAsunto,
                TipoDocumentoOficial.OFICIO_ATENCION_RETRO.getClave());
    }

    @Override
    public List<DocumentoOficial> buscarTiposDocumentosOfiAtencionRetroPorIdTramiteIdReq(
            String numAsunto, Long idRequerimiento) {

        return documentoDao.obtenerDocumentosPorTipo(numAsunto,
                TipoDocumentoOficial.OFICIO_ATENCION_RETRO.getClave(),
                idRequerimiento);
    }

    /**
     * M&eacute;todo para recuperar el archivo del documento oficial.
     */
    public InputStream descargarDocumento(String ruta) {
        InputStream input = null;
        try {
            File file = new File(ruta);
            input = new FileInputStream(file);
            return input;
        } catch (FileNotFoundException e) {
            getLogger().error("", e);
        }
        return input;

    }

    /**
     * M&eacute;todo para guardar los documentos oficiales asociadas al
     * requerimiento (autorizaci&oacute;n).
     */
    public void guardaDocumentosOficiales(
            List<DocumentoOficial> listaDocumentosOficiales,
            Long idRequerimiento, String numeroAsunto) {

        for (DocumentoOficial documentoOficial : listaDocumentosOficiales) {
            documentoOficial.setEstadoDocumento(EstadoDocumento.FIRMADO
                    .getClave());
            if (documentoOficial.getIdDocumentoOficial() == null) {
                DocumentoRequerimiento documentoRequerimiento = new DocumentoRequerimiento();
                requerimientoDao.guardaDocumentoOficial(documentoOficial);
                documentoRequerimiento.setIdDocumentoOficial(documentoOficial
                        .getIdDocumentoOficial());
                documentoRequerimiento.setIdRequerimiento(idRequerimiento);
                requerimientoDao
                        .guardaDocumentoRequerimiento(documentoRequerimiento);
            }
        }

    }

    /**
     * M&eacute;todo para guardar los documentos oficiales asociadas al
     * requerimiento (autorizaci&oacute;n).
     */
    @Override
    public void guardaDocumentosOficialesAtencion(
            List<DocumentoOficial> listaDocumentosOficiales,
            Long idRequerimiento, String numeroAsunto) {

        for (DocumentoOficial documentoOficial : listaDocumentosOficiales) {
            if (documentoOficial.getIdDocumentoOficial() == null) {
                DocumentoRequerimiento documentoRequerimiento = new DocumentoRequerimiento();
                requerimientoDao.guardaDocumentoOficial(documentoOficial);
                documentoRequerimiento.setIdDocumentoOficial(documentoOficial
                        .getIdDocumentoOficial());
                documentoRequerimiento.setIdRequerimiento(idRequerimiento);
                requerimientoDao
                        .guardaDocumentoRequerimiento(documentoRequerimiento);
            }
        }

    }

    /**
     * M&eacute;todo para actualizar requerimiento.
     */
    public void actualizaRequerimiento(Requerimiento requerimiento,
            boolean esAutorizacion) throws FechaInvalidaException {
        List<DocumentoSolicitud> docs = registroRecursoRevocacionDAO
                .obtenerDocumentoSolicitudByIdReq(requerimiento
                        .getIdRequerimiento());
        for (DocumentoSolicitud doc : docs) {
            doc.setEstadoDocumentoSolicitud(EstadoDocumento.FIRMADO.getClave());
            doc.getDocumento().setEstadoDocumento(
                    EstadoDocumento.FIRMADO.getClave());
        }
        if (esAutorizacion) {
            RequerimientoDocumentos requerimientoBD = (RequerimientoDocumentos) requerimientoDao
                    .obtenerRequerimientoPorId(requerimiento
                            .getIdRequerimiento());
            requerimiento.setFechaAutorizacion(new Date());
            documentosHelper.complementarDocumentos(requerimientoBD
                    .getTramite().getSolicitud().getIdSolicitud());

        } else if (requerimiento.getEstadoRequerimiento() == EstadoRequerimiento.ATENDIDO) {
            requerimiento.setFechaAtencion(new Date());

        }
        this.actualizarRequerimiento(requerimiento);
    }

    /**
     * M&eacute;todo para actualizar requerimiento.
     */
    public void actualizaRetroalimentacion(Requerimiento requerimiento,
            boolean esAutorizacion) throws FechaInvalidaException {
        List<DocumentoOficial> docs = requerimientoDao
                .obtenerDocumentosOficialesPorIdRequerimiento(requerimiento
                        .getIdRequerimiento());
        for (DocumentoOficial doc : docs) {
            doc.setEstadoDocumento(EstadoDocumento.FIRMADO.getClave());
        }
        if (esAutorizacion) {
            RequerimientoDocumentos requerimientoBD = (RequerimientoDocumentos) requerimientoDao
                    .obtenerRequerimientoPorId(requerimiento
                            .getIdRequerimiento());
            requerimiento.setFechaAutorizacion(new Date());
            documentosHelper.complementarDocumentos(requerimientoBD
                    .getTramite().getSolicitud().getIdSolicitud());

        } else if (requerimiento.getEstadoRequerimiento() == EstadoRequerimiento.ATENDIDO) {
            requerimiento.setFechaAtencion(new Date());

        }
        this.actualizarRequerimiento(requerimiento);
    }

    /**
     * M&eacute;todo para actualizar tramite.
     */
    public void actualizaTramitePorTarea(String numeroAsunto,
            EstadoTramite estadoTramite, String claveTarea) {
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        tramite.setEstadoTramite(estadoTramite);
        tramite.setFechaEstatus(new Date());
        tramiteDao.modificarTramite(tramite);

    }

    @Override
    public void actualizarRequerimiento(Requerimiento requerimiento)
            throws FechaInvalidaException {

        RequerimientoDocumentos requerimientoBD = (RequerimientoDocumentos) requerimientoDao
                .obtenerRequerimientoPorId(requerimiento.getIdRequerimiento());

        Calendar fecRes = Calendar.getInstance();
        Calendar fecNot = Calendar.getInstance();
        Date fechaAutorizacion = null;

        fechaAutorizacion = requerimientoBD.getFechaAutorizacion();
        if (requerimiento.getFechaVerificacion() != null) {
            fecNot.setTime(requerimiento.getFechaVerificacion());
            Calendar calNot = Calendar.getInstance();
            calNot.set(fecNot.get(Calendar.YEAR), fecNot.get(Calendar.MONTH),
                    fecNot.get(Calendar.DATE));

            fecRes.setTime(fechaAutorizacion);
            Calendar calRes = Calendar.getInstance();
            calRes.set(fecRes.get(Calendar.YEAR), fecRes.get(Calendar.MONTH),
                    fecRes.get(Calendar.DATE));

            // fecnot es menor a fecres, si puede ser igual, entonces
            // se cambia el -1 por 0
            if (calNot.compareTo(calRes) < 0) {
                throw new FechaInvalidaException("fechas invalidas");
            }
        }
        requerimientoBD.setDescripcion(requerimiento.getDescripcion());

        if (requerimiento.getFechaVerificacion() != null) {
            requerimientoBD.setFechaVerificacion(requerimiento
                    .getFechaVerificacion());
        }
        if (requerimiento.getFechaAtencion() != null) {
            requerimientoBD.setFechaAtencion(requerimiento.getFechaAtencion());
        }
        if (requerimiento.getFechaAutorizacion() != null) {
            requerimientoBD.setFechaAutorizacion(requerimiento
                    .getFechaAutorizacion());
        }
        if (requerimiento.getEstadoRequerimiento() != null) {
            requerimientoBD.setEstadoRequerimiento(requerimiento
                    .getEstadoRequerimiento());
        }
        if (requerimiento.getUnidadAdministrativa() != null) {
            requerimientoBD.setUnidadAdministrativa(requerimiento
                    .getUnidadAdministrativa());
        }
        requerimientoDao.actualizarRequerimiento(requerimientoBD);
    }

    @Override
    public void eliminaDocumentoOficial(long idDocumento) {
        DocumentoOficial docOficial = registroRecursoRevocacionDAO
                .obtenerDocumentosOficialesById(idDocumento);
        docOficial.setBlnActivo(0);
    }

    @Override
    public String generaCadenaOriginalAutorizarRequerimiento(
            Long idRequerimiento, Date fechaFirma, String rfcFuncionario) {
        StringBuffer cadenaOriginal = new StringBuffer();

        Requerimiento requerimiento = requerimientoDao
                .obtenerRequerimientoPorId(idRequerimiento);
        SolicitudDatosGenerales solicitud = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(requerimiento.getTramite()
                        .getSolicitud().getIdSolicitud());
        Tramite tramite = tramiteDao.obtenerTramitePorIdSolicitud(solicitud
                .getIdSolicitud());

        String idSolicitud = solicitud.getIdSolicitud().toString();
        List<Documento> listaDocumentos = new ArrayList<Documento>();
        List<DocumentoSolicitud> listaDocumentoSolicitud = registroRecursoRevocacionDAO
                .obtenerDocumentoSolicitud(idSolicitud);
        for (DocumentoSolicitud documentoSolicitud : listaDocumentoSolicitud) {
            Documento doc = documentoSolicitud.getDocumento();
            listaDocumentos.add(doc);
        }

        // TODO: Cuando en la tabla DocumentoOficial se alamacene el
        // estatus del documento, se debe contemplar para filtrar solo
        // los documentos "Anexados" en la generacion de la cadena
        // original (edgar.mendoza)
        List<DocumentoOficial> listaDocumentoOficial = documentoDao
                .obtenerDocumentosOficialesAnexadosPorTipo(
                        tramite.getNumeroAsunto(),
                        TipoDocumentoOficial.OFICIO_REQUERIMIENTO.getClave());
        cadenaOriginal.append(generarCadenasHelper
                .generarCadenaGenerarAutorizarRequerimientoCAL(requerimiento,
                        tramite, rfcFuncionario)); 
        // |cadena|datos|requerimiento|
        cadenaOriginal.append(generarCadenasHelper
                .generaCadenaDocumentoOficial(listaDocumentoOficial));
        // doc1|hash1|doc2|hash2|
        return cadenaOriginal.toString();
    }

    @Override
    public String generaCadenaOriginalNotificacionRequerimiento(
            Long idSolicitud, Long idRequerimiento, Date fechaFirma,
            String rfcUsuario) {
        StringBuffer cadenaOriginal = new StringBuffer();
        SolicitudDatosGenerales solicitud = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(idSolicitud);
        cadenaOriginal.append(generarCadenasHelper
                .generarCadenaConfirmacionNotificacionRetroalimentacion(
                        solicitud, idRequerimiento, fechaFirma, rfcUsuario));
        return cadenaOriginal.toString();
    }

    @Override
    public String generaCadenaOriginalRetroalimentacion(String numeroAsunto,
            Date fechaFirma, String rfcFuncionario) {
        StringBuffer cadenaOriginal = new StringBuffer();

        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);

        // TODO: Cuando en la tabla DocumentoOficial se alamacene el
        // estatus del documento, se debe contemplar para filtrar solo
        // los documentos "Anexados" en la generacion de la cadena
        // original (edgar.mendoza)
        List<DocumentoOficial> listaDocumentoOficial = documentoDao
                .obtenerDocumentosOficialesAnexadosPorTipo(
                        tramite.getNumeroAsunto(),
                        TipoDocumentoOficial.OFICIO_ATENCION_RETRO.getClave());
        cadenaOriginal.append(generarCadenasHelper
                .generarCadenaDatosAtencionRetroalimentacion(numeroAsunto,
                        fechaFirma, rfcFuncionario)); 
        // |cadena|datos|requerimiento|
        cadenaOriginal.append(generarCadenasHelper
                .generaCadenaDocumentoOficial(listaDocumentoOficial)); 
        // doc1|hash1|doc2|hash2|...|docN|hashN|

        return cadenaOriginal.toString();
    }

    @Override
    public Firma generaFirmaRetroalimentacionSIAT(String numeroAsunto,
            Date fechaFirma, String rfcFuncionario) {
        String strCadena = generaCadenaOriginalRetroalimentacion(numeroAsunto,
                fechaFirma, rfcFuncionario);
        Firma firmaSIAT = null;
        try {
            firmaSIAT = selladoraHelper.getSello(strCadena);
        } catch (SelladoraException se) {
            firmaSIAT = selladoraHelper.getSelloDefault(strCadena);
            getLogger().error("Sella promocion RRL:  " + se.getMessage());
        }

        return firmaSIAT;
    }

    @Override
    public List<DocumentoSolicitud> obtenerDocumentosComplementarios(
            Long idSolicitud) {

        return documentoDao.obtenerDocumentosComplementarios(idSolicitud);
    }

    /**
     * Obtiene la notificaci&oacute;n del requerimiento especificado
     * 
     * @param idRequerimiento
     * @return
     */
    @Override
    public NotificacionRequerimiento obtenerNotificacionRequerimiento(
            Long idRequerimiento) {
        return notificacionDao
                .obtenerNotificacionByIdRequerimiento(idRequerimiento);
    }

    @Override
    public void actualizarFecha(String numeroAsunto,
             String claveTarea,
            Requerimiento requerimiento) {

        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        tramite.setFechaEstatus(new Date());
        Solicitud solicitud = solicitudService.obtenerSolicitudporId(tramite
                .getSolicitud().getIdSolicitud());

        if (solicitud.getTipoTramite().getIdTipoTramite().toString()
                .equals(DiscriminadorConstants.T2_CLASIFICACION_ARANCELARIA)) {
            tramite.setFechaCalculoTranscurridos(tramiteHelper
                    .fechaVencimientoTramiteClasifArancelaria(
                            tramite.getFechaInicioTramite(),
                            diaInhabilDAO.obtenerDiasInhabiles()));
        } else {
            tramite.setFechaCalculoTranscurridos(tramiteHelper
                    .fechaVencimientoTramite(tramite.getFechaInicioTramite(),
                            diaInhabilDAO.obtenerDiasInhabiles()));
        }
    }

    public EnviarCorreoService getEnviarCorreoService() {
        return enviarCorreoService;
    }

    public void setEnviarCorreoService(EnviarCorreoService enviarCorreoService) {
        this.enviarCorreoService = enviarCorreoService;
    }

    public void enviarCorreoRechazoAsuntoRequerimiento(String rfcPara,
            String numAsunto, Long idSolicitud, String elemento,
            Date fechaRecepcion, String nombreRechazo) {
        getEnviarCorreoService().enviarCorreoRechazoAsunto(rfcPara, numAsunto,
                idSolicitud, elemento, fechaRecepcion, nombreRechazo);
    }

}
