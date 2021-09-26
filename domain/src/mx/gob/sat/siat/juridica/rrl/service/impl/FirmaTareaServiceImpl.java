/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.dao.DocumentoDao;
import mx.gob.sat.siat.juridica.base.dao.EnumeracionDao;
import mx.gob.sat.siat.juridica.base.dao.NotificacionDao;
import mx.gob.sat.siat.juridica.base.dao.ObservacionDao;
import mx.gob.sat.siat.juridica.base.dao.PersonaDao;
import mx.gob.sat.siat.juridica.base.dao.RemisionDao;
import mx.gob.sat.siat.juridica.base.dao.RequerimientoDao;
import mx.gob.sat.siat.juridica.base.dao.ResolucionDao;
import mx.gob.sat.siat.juridica.base.dao.TipoTramiteDAO;
import mx.gob.sat.siat.juridica.base.dao.TramiteDao;
import mx.gob.sat.siat.juridica.base.dao.UnidadAdministrativaDao;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionParametro;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoDocumento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoNotificacion;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoObservacion;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRemision;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoDocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoMensajeBPM;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoUnidadAdministrativa;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Bitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DatosBandejaTarea;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoResolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.MensajeBPM;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Notificacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.NotificacionRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Observacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Remision;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.RequerimientoTarea;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Resolucion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.ResultadoAdminResponsable;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitante;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Solicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.SolicitudDatosGenerales;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.base.service.AsignacionTramiteServices;
import mx.gob.sat.siat.juridica.base.service.BalanceadorServices;
import mx.gob.sat.siat.juridica.base.service.BitacoraTramiteServices;
import mx.gob.sat.siat.juridica.base.service.EnviarCorreoService;
import mx.gob.sat.siat.juridica.base.service.SolicitanteServices;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.UnidadAdministrativaServices;
import mx.gob.sat.siat.juridica.base.service.impl.BaseSerializableBusinessServices;
import mx.gob.sat.siat.juridica.base.util.helper.DocumentosHelper;
import mx.gob.sat.siat.juridica.bpm.dao.domain.MensajeTarea;
import mx.gob.sat.siat.juridica.rrl.dao.SolicitudDAO;
import mx.gob.sat.siat.juridica.rrl.service.AsignarTareaCorreoService;
import mx.gob.sat.siat.juridica.rrl.service.FirmaTareaService;
import mx.gob.sat.siat.juridica.rrl.service.SolicitudService;
import mx.gob.sat.siat.juridica.rrl.util.constante.SentidoResolucion;
import mx.gob.sat.siat.juridica.rrl.util.helper.DiasHabilesHelper;

/**
 * 
 * @author softtek
 * 
 */
@Service("firmaTareaService")
public class FirmaTareaServiceImpl extends BaseSerializableBusinessServices implements FirmaTareaService {

    @Autowired
    private UnidadAdministrativaDao unidadAdministrativaDao;
    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 728557768371111733L;

    /**
     * Atributo balanceadorServices tipo BalanceadorServices
     */
    @Autowired
    private BalanceadorServices balanceadorServices;

    @Autowired
    private DiasHabilesHelper diasHabilesHelper;
    /**
     * Atributo tramiteDao tipo TramiteDao
     */
    @Autowired
    private TramiteDao tramiteDao;

    @Autowired
    private TipoTramiteDAO tipoTramiteDAO;

    /**
     * Atributo remisionDao tipo RemisionDao
     */
    @Autowired
    private RemisionDao remisionDao;

    /**
     * Atributo remisionDao tipo RemisionDao
     */
    @Autowired
    private DocumentoDao documentoDao;

    /**
     * Atributo resolucionDao tipo ResolucionDao
     */
    @Autowired
    private ResolucionDao resolucionDao;

    /**
     * Atributo solicitudDAO tipo SolicitudDAO
     */
    @Autowired
    private SolicitudDAO solicitudDAO;

    /**
     * Atributo requerimientoDao tipo RequerimientoDao
     */
    @Autowired
    private RequerimientoDao requerimientoDao;

    /**
     * Atributo notificacionDao tipo NotificacionDao
     */
    @Autowired
    private NotificacionDao notificacionDao;

    @Autowired
    private DocumentosHelper documentosHelper;
    /**
     * Atributo personaDao tipo PersonaDao
     */
    @Autowired
    private PersonaDao personaDao;

    @Autowired
    private BitacoraTramiteServices bitacoraTramiteServices;

    /**
     * Atributo solicitanteServices tipo SolicitanteServices
     */
    @Autowired
    private SolicitanteServices solicitanteServices;

    /**
     * Atributo unidadAdministrativaServices tipo UnidadAdministrativaServices
     */
    @Autowired
    private UnidadAdministrativaServices unidadAdministrativaServices;

    /**
     * Atributo solicitudService tipo SolicitudService
     */
    @Autowired
    private SolicitudService solicitudService;

    /**
     * Atributo asignarTareaCorreoService tipo AsignarTareaCorreoService
     */
    @Autowired
    private AsignarTareaCorreoService asignarTareaCorreoService;

    @Autowired
    private AsignacionTramiteServices asignacionTramiteServices;

    @Autowired
    private transient TareaServices tareaServices;
    /**
     * Inyeccion de Dao de enumeracion
     */
    @Autowired
    private EnumeracionDao enumeracionDao;

    @Autowired
    private SolicitudDAO solicitudDao;

    /**
     * Inyeccion de Dao de observacion
     */
    @Autowired
    private ObservacionDao observacionDao;

    @Autowired
    private EnviarCorreoService enviarCorreoService;

    /**
     * Metodo para observar una autorizacion
     * 
     * @param numAsunto
     * @param idTarea
     * @return
     */
    @Override
    public Observacion observar(String idTarea, String rfc, Observacion observacion) {
        observacion.setFechaObservacion(new Date());
        observacion.setEstadoObservacion(EstadoObservacion.GENERADA.getClave());
        observacion.setRfcAbogado(rfc);
        String numFolio = observacion.getTramite().getNumeroAsunto();
        observacionDao.guardarObservacion(observacion);
        Tramite tramite = tramiteDao.obtenerTramitePorId(numFolio);
        tramite.setEstadoTramite(EstadoTramite.OBSERVADO);
        tramiteDao.modificarTramite(tramite);
        SolicitudDatosGenerales solicitud = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
        asignarTareaCorreoService.enviarCorreo(tramite.getNumeroAsunto(), solicitud.getCveUsuarioCapturista(),
                "Atender observaci&oacute;n");
        return observacion;
    }

    /**
     * Metodo para completrar una tarea de atencion de requerimiento
     * 
     * @param tipoMensajeBPM
     * @param idTarea
     * @param idTipoTransicion
     * @param rfcAsignar
     * @param esAutorizador
     */
    @Override
    public void completarTareaAtenderObservacion(String numeroAsunto, Long idObservacion, String rfcUsuario,
            String idTarea) {
        Observacion observacion = observacionDao.obtenerObservacionPorId(idObservacion);
        observacion.setFechaAtencion(new Date());
        observacion.setEstadoObservacion(EstadoObservacion.ATENDIDA.getClave());
        asignarTareaCorreoService.enviarCorreo(numeroAsunto, observacion.getRfcAbogado(), "Atender promoci&oacute;n");
    }

    /**
     * Metodo para firmar y turnar un asunto
     * 
     * @param numAsunto
     * @param idTarea
     * @param rfcAsignar
     */
    public void firmarTurnar(String numAsunto, String idTarea, String rfcAsignar, String rfcUsuario) {
        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        tramite.setFechaEstatus(new Date());
        tramiteDao.modificarTramite(tramite);
        asignarTareaCorreoService.enviarCorreo(numAsunto, rfcAsignar, "Atender promoci&oacute;n");
        SolicitudDatosGenerales solicitud = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
        asignacionTramiteServices.guardarAsignacionTramite(tramite.getNumeroAsunto(), rfcAsignar,
                TipoRol.ABOGADO.getClave());
        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(numAsunto);
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitud.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.TURNAR_ASUNTO.getClave());
        bitacora.setRfcNuevo(rfcAsignar);
        bitacoraTramiteServices.insertarBitacora(bitacora);
    }

    /**
     * Metodo para firmar y reasignar un asunto
     * 
     * @param idTarea
     * @param rfcUsuario
     * @param rfcAsignar
     * @param numAsunto
     * @param rfcPropietario
     * @param idInstancia
     */
    @Override
    public void firmarReasignar(String idTarea, String rfcUsuario, String rfcAsignar, String numAsunto,
            String rfcPropietario, Long idInstancia) {
        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        SolicitudDatosGenerales solicitud = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(numAsunto);
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitud.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.REASIGNAR.getClave());
        bitacora.setRfcActual(rfcPropietario);
        bitacora.setRfcNuevo(rfcAsignar);
        bitacoraTramiteServices.insertarBitacora(bitacora);

        asignacionTramiteServices.guardarAsignacionTramite(numAsunto, rfcAsignar, TipoRol.ABOGADO.getClave());
        
        /* Resolver aqui */
        Tarea tarea = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, idInstancia);
        tarea.setClaveAbogado(rfcAsignar);
        tarea.setClaveAsignado(rfcAsignar);
        tareaServices.guardarTarea(tarea);
    }

    /**
     * Metodo para firmar y remitir un asunto
     * 
     * @param numAsunto
     * @param idTarea
     */
    @Override
    public void firmarRemitir(String numAsunto, Long idTarea, String rfcUsuario) {

        Remision remision = remisionDao.obtenerUltimaRemisionPorIdTramite(numAsunto);
        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        SolicitudDatosGenerales solicitud = null;
        UnidadAdministrativa unidadOriginal = null;
        if (remision != null) {
            unidadOriginal = unidadAdministrativaDao.obtenerUnidadPorId(remision.getUnidadAdminNueva().getClave());
        }
        if (unidadOriginal != null && (!unidadOriginal.getIdeTipoUnidadAdministrativa().getClave()
                .equals(TipoUnidadAdministrativa.ADMINISTRACION_EXTERNA.getClave())
                && !unidadOriginal.getIdeTipoUnidadAdministrativa().getClave()
                        .equals(TipoUnidadAdministrativa.ADMINISTRACION_INTERNA.getClave()))) {
            String rfcAsignar = null;
            ResultadoAdminResponsable resultado = null;
            if (tramite != null) {
                if (tramite.getSolicitud() != null) {

                    documentosHelper.complementarDocumentos(tramite.getSolicitud().getIdSolicitud());
                    solicitud = solicitudDao
                            .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());

                    if (tramite.getSolicitud().getDiscriminatorValue() != null && tramite.getSolicitud()
                            .getDiscriminatorValue().startsWith(DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
                        resultado = balanceadorServices.obtenerAutorizadorResponsableSinUAR(
                                (SolicitudDatosGenerales) tramite.getSolicitud(), remision);
                        if (resultado != null) {
                            rfcAsignar = resultado.getRfcAdministrador();
                            solicitud.setUnidadAdminBalanceo(resultado.getUnidadAdmin());
                            solicitudService.actualizarUnidadAdminBalanceo(solicitud, resultado.getUnidadAdmin());
                        }

                    } else {
                        resultado = balanceadorServices.obtenerAutorizadorResponsableSinUAR(
                                (SolicitudDatosGenerales) tramite.getSolicitud(), remision);

                        if (resultado != null) {
                            rfcAsignar = resultado.getRfcAdministrador();

                            solicitud.setUnidadAdminBalanceo(resultado.getUnidadAdmin());
                            solicitudService.actualizarUnidadAdminBalanceo(solicitud, resultado.getUnidadAdmin());

                        }

                        tramite.setEstadoTramite(EstadoTramite.REMITIDO);

                    }
                }
                if (resultado != null) {
                    remision.setUnidadAdminNueva(new UnidadAdministrativa(resultado.getUnidadAdmin()));
                    ((SolicitudDatosGenerales) tramite.getSolicitud())
                            .setUnidadAdminBalanceo(remision.getUnidadAdminNueva().getClave());
                    tramiteDao.modificarTramite(tramite);
                    List<DocumentoOficial> docsOfi = documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(numAsunto,
                            TipoDocumentoOficial.OFICIO_REMISION.getClave());
                    for (DocumentoOficial doc : docsOfi) {
                        doc.setEstadoDocumento(EstadoDocumento.FIRMADO.getClave());
                        doc.setSello("0");
                    }
                }
            }

            if (null == rfcAsignar && tramite != null) {
                if (tramite.getSolicitud() != null) {
                    Solicitante solicitante = solicitanteServices
                            .obtenerSolicitanteByIdSol(tramite.getSolicitud().getIdSolicitud());
                    String claveAdministracionLocalRecaudadora = solicitante.getDomicilio()
                            .getClaveAdministracionLocalRecaudadora();
                    if (null != claveAdministracionLocalRecaudadora) {
                        UnidadAdministrativa unidadAdmin = unidadAdministrativaServices
                                .obtenerUnidadPorClaveLocal(claveAdministracionLocalRecaudadora);
                        tramite.setSolicitud(solicitudService.actualizarUnidadAdminBalanceo(tramite.getSolicitud(),
                                unidadAdmin.getClave()));
                    }

                    resultado = balanceadorServices.obtenerAutorizadorResponsableUnidadLocalRecaudadora(
                            (SolicitudDatosGenerales) tramite.getSolicitud());
                    if (resultado != null) {
                        rfcAsignar = resultado.getRfcAdministrador();
                        solicitud.setUnidadAdminBalanceo(resultado.getUnidadAdmin());
                        solicitudService.actualizarUnidadAdminBalanceo(solicitud, resultado.getUnidadAdmin());
                    }
                }
            } else {
                if (tramite != null && remision != null) {
                    tramite.setSolicitud(solicitudService.actualizarUnidadAdminBalanceo(tramite.getSolicitud(),
                            remision.getUnidadAdminNueva().getClave()));
                }
            }
            UnidadAdministrativa unidadSeleccionada = null;
            if (tramite != null) {
                tramite.setFechaEstatus(new Date());
            }
            if (remision != null) {
                unidadSeleccionada = unidadAdministrativaDao
                        .obtenerUnidadPorId(remision.getUnidadAdminNueva().getClave());

                if (unidadSeleccionada != null && tramite.getSolicitud().getDiscriminatorValue() != null
                        && tramite.getSolicitud().getDiscriminatorValue()
                                .startsWith(DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {

                    tramite.setEstadoTramite(EstadoTramite.REMITIDO);
                }
            }

            if (resultado != null) {
                remision.setUnidadAdminNueva(new UnidadAdministrativa(resultado.getUnidadAdmin()));
                tramite.setEstadoTramite(EstadoTramite.REMITIDO);
                ((SolicitudDatosGenerales) tramite.getSolicitud())
                        .setUnidadAdminBalanceo(remision.getUnidadAdminNueva().getClave());
                tramiteDao.modificarTramite(tramite);
                remision.setEstadoRemision(EstadoRemision.AUTORIZADA.getClave());
                remision.setFechaAutorizacion(new Date());
                remisionDao.guardarRemision(remision);
            }

            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, idTarea);
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);

            Date dt = new Date();
            Tarea tarea = new Tarea();
            tarea.setNumeroAsunto(numAsunto);

            if (tramite != null) {
                if (tramite.getEstadoTramite() == EstadoTramite.REMITIDO) {
                    tarea.setClaveAdm(rfcAsignar);
                } else {
                    tarea.setClaveAdm(tareaAct.getClaveAdm());
                }
            }
            tarea.setClaveAbogado(tareaAct.getClaveAbogado());
            tarea.setClaveAsignado(rfcAsignar);
            tarea.setTarea(EnumeracionBitacora.TURNAR_ASUNTO.getClave());
            tarea.setDescripcion(EnumeracionBitacora.TURNAR_ASUNTO.getDescripcion());
            tarea.setFechaCreacion(dt);
            tarea.setFechaAct(dt);
            tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tareaServices.guardarTarea(tarea);

            if (resultado != null) {
                solicitudService.actualizarUnidadAdminBalanceo(solicitud, resultado.getUnidadAdmin());
            }

            if (rfcAsignar != null) {
                try {
                    asignarTareaCorreoService.enviarCorreo(numAsunto, rfcAsignar, "Turnar Asunto");
                } catch (Exception e) {
                    getLogger().error(e.getMessage());
                }
            }
            Bitacora bitacora = new Bitacora();
            bitacora.setFechaEvento(new Date());
            bitacora.setNumeroAsunto(numAsunto);
            bitacora.setRfcUsuario(rfcUsuario);
            if (solicitud != null) {
                bitacora.setUnidadAdministrativa(
                        solicitud.getUnidadAdminBalanceo() != null ? solicitud.getUnidadAdminBalanceo() : null);
            }
            bitacora.setTarea(EnumeracionBitacora.FIRMAR_REMISION.getClave());
            bitacora.setRfcNuevo(rfcAsignar);
            bitacoraTramiteServices.insertarBitacora(bitacora);

            asignacionTramiteServices.guardarAsignacionTramite(numAsunto, rfcAsignar, TipoRol.ADMINISTRADOR.getClave());
        } else {
            if (tramite != null) {
                tramite.setEstadoTramite(EstadoTramite.REMITIDO);
                tramite.setSolicitud(solicitudService.actualizarUnidadAdminBalanceo(tramite.getSolicitud(),
                        remision.getUnidadAdminNueva().getClave()));
                tramiteDao.modificarTramite(tramite);
            }

            if (remision != null) {
                remision.setEstadoRemision(EstadoRemision.AUTORIZADA.getClave());
                remision.setFechaAutorizacion(new Date());
                remisionDao.guardarRemision(remision);
            }

            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, idTarea);
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);
            if (tramite != null) {
                solicitud = solicitudDao.obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
            }
            List<DocumentoOficial> docsOfi = documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(numAsunto,
                    TipoDocumentoOficial.OFICIO_REMISION.getClave());
            for (DocumentoOficial doc : docsOfi) {
                doc.setEstadoDocumento(EstadoDocumento.FIRMADO.getClave());

            }
            Bitacora bitacora = new Bitacora();
            bitacora.setFechaEvento(new Date());
            bitacora.setNumeroAsunto(numAsunto);
            bitacora.setRfcUsuario(rfcUsuario);
            if (solicitud != null) {
                bitacora.setUnidadAdministrativa(
                        solicitud.getUnidadAdminBalanceo() != null ? solicitud.getUnidadAdminBalanceo() : null);
            }
            bitacora.setTarea(EnumeracionBitacora.FIRMAR_REMISION.getClave());
            bitacoraTramiteServices.insertarBitacora(bitacora);

        }

    }

    /**
     * Metodo para terminar la atencion de una solicitud
     * 
     * @param numAsunto
     * @param idTareaUsuario
     * @param rfcAsignar
     */
    public void terminarAtenderSolicitud(String numAsunto, String idTareaUsuario, String rfcAsignar,
            String rfcUsuario) {

        getLogger().debug("FirmaTareaServiceImpl : resolucionAct");
        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTareaUsuario));
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);
            getLogger().debug("FirmaTareaServiceImpl : firmarResolucion");
            Date dt = new Date();
            Tarea tarea = new Tarea();
            tarea.setNumeroAsunto(numAsunto);
            tarea.setClaveAdm(tareaAct.getClaveAdm());
            tarea.setClaveAbogado(tareaAct.getClaveAbogado());
            tarea.setClaveAsignado(rfcAsignar);
            tarea.setTarea(EnumeracionBitacora.FIRMAR_RESOLUCION.getClave());
            tarea.setDescripcion(EnumeracionBitacora.FIRMAR_RESOLUCION.getDescripcion());
            tarea.setFechaCreacion(dt);
            tarea.setFechaAct(dt);
            tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tareaServices.guardarTarea(tarea);
        } catch (Exception ex) {
            getLogger().error("Error al firmar resolucion el asunto en tarea y bitacora", ex);
        }

        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        tramite.setEstadoTramite(EstadoTramite.EN_FIRMA);
        tramiteDao.modificarTramite(tramite);
        documentosHelper.complementarDocumentos(tramite.getSolicitud().getIdSolicitud());

        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(numAsunto);
        resolucion.setIdeEstadoResolucion(EstadoResolucion.EN_FIRMA.getClave());
        resolucion.setRfcAbogado(rfcUsuario);
        asignarTareaCorreoService.enviarCorreo(numAsunto, rfcAsignar, "Autorizar resoluci&oacute;n");

    }

    /**
     * Metodo para completar una tarea de requerimiento
     * 
     * @param tipoMensajeBPM
     * @param idTarea
     * @param idTipoTransicion
     * @param rfcAsignar
     * @param esAutorizador
     * @param idRequerimiento
     */
    @Override
    public void completarTareaRequerimientoPromovente(TipoMensajeBPM tipoMensajeBPM,
            DatosBandejaTarea datosBandejaTarea, String idTipoTransicion, String rfcAsignar, boolean esAutorizador,
            String rfcUsuario, String tipoReq) {

        Long idTarea = datosBandejaTarea.getIdTareaUsuario();
        Long idRequerimiento = datosBandejaTarea.getIdRequerimiento();
        Requerimiento requerimiento = requerimientoDao.obtenerRequerimientoPorId(idRequerimiento);
        String abogado = requerimiento.getIdUsuario();
        Tramite tramite = tramiteDao.obtenerTramitePorId(requerimiento.getTramite().getNumeroAsunto());
        Solicitud solicitud = solicitudDAO.obtenerSolicitudporId(tramite.getSolicitud().getIdSolicitud());
        String contribuyente = solicitud.getCveUsuarioCapturista();

        MensajeTarea mensajeTarea = new MensajeTarea();
        mensajeTarea.setEstadoProcesal(EstadoTramite.REQUERIDO.getClave());
        mensajeTarea.setTipoRequerimiento(tipoReq);

        if (!esAutorizador) {
            requerimiento.setIdUsuario(rfcUsuario);
        }
        try {
            String numAsunto = tramite.getNumeroAsunto();
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, idTarea);
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);
            Date dt = new Date();
            Tarea tareaContribuyente = new Tarea();
            tareaContribuyente.setNumeroAsunto(numAsunto);
            tareaContribuyente.setClaveAdm(tareaAct.getClaveAdm());
            tareaContribuyente.setClaveAbogado(tareaAct.getClaveAbogado());
            tareaContribuyente.setClaveAsignado(contribuyente);
            tareaContribuyente.setTarea(EnumeracionBitacora.ATENDER_REQ.getClave());
            tareaContribuyente.setDescripcion(EnumeracionBitacora.ATENDER_REQ.getDescripcion());
            tareaContribuyente.setFechaCreacion(dt);
            tareaContribuyente.setFechaAct(dt);
            tareaContribuyente.setIdRequerimeinto(requerimiento.getIdRequerimiento());
            tareaContribuyente.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tareaServices.guardarTarea(tareaContribuyente);

            Tarea tareaAbogado = new Tarea();
            tareaAbogado.setNumeroAsunto(numAsunto);
            tareaAbogado.setClaveAdm(tareaAct.getClaveAdm());
            tareaAbogado.setClaveAbogado(tareaAct.getClaveAbogado());
            tareaAbogado.setClaveAsignado(abogado);
            tareaAbogado.setTarea(EnumeracionBitacora.NOTIFICACION_REQUERIMIENTO.getClave());
            tareaAbogado.setDescripcion(EnumeracionBitacora.NOTIFICACION_REQUERIMIENTO.getDescripcion());
            tareaAbogado.setFechaCreacion(new Date());
            tareaAbogado.setFechaAct(new Date());
            tareaAbogado.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tareaServices.guardarTarea(tareaAbogado);

        } catch (Exception ex) {
            getLogger().error("Error al firmar requerimiento al contribuyente en tarea", ex);
        }

        String rfcCortoAsignar = personaDao.obtenerRfcCorto(rfcAsignar);
        mensajeTarea.setAdministrador(rfcCortoAsignar);
        SolicitudDatosGenerales solicitudDatosGenerales = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(tramite.getNumeroAsunto());
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitudDatosGenerales.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.FIRMAR_REQUERIMIENTO.getClave());
        bitacora.setRfcNuevo(abogado);
        bitacoraTramiteServices.insertarBitacora(bitacora);
    }

    public void actualizaDatosBP(int idInstancia, EstadoTramite estadoTramite) {
        MensajeBPM mensajeBPM = new MensajeBPM();
        mensajeBPM.setTipoMensajeBPM(TipoMensajeBPM.EJECUTAR_JS);
        mensajeBPM.setIdInstancia(idInstancia);

        StringBuffer bDoc = new StringBuffer(NumerosConstantes.CINCUENTA);
        bDoc.append("tw.local.tramite.estadoProcesal='");
        bDoc.append(estadoTramite.getClave());
        bDoc.append("'");
        mensajeBPM.setbDoc(bDoc.toString());
    }

    /**
     * Metodo para completar una tarea de requerimiento
     * 
     * @param datosBandejaTarea
     * @param idTipoTransicion
     * @param rfcAsignar
     * @param esAutorizador
     * @param tipoRequerimiento
     * @param rfcUsuario
     * @param claveUnidadAdministrativa
     */
    @Override
    public void completarTareaRequerimientoAutoridad(DatosBandejaTarea datosBandejaTarea, String idTipoTransicion,
            String rfcAsignar, boolean esAutorizador, String tipoRequerimiento, String rfcUsuario,
            String claveUnidadAdministrativa) {
        getLogger().debug("Inicia completarTareaRequerimientoAutoridad");
        Long idTarea = datosBandejaTarea.getIdTareaUsuario();
        Long idRequerimiento = datosBandejaTarea.getIdRequerimiento();
        Long idSolicitud = datosBandejaTarea.getIdSolicitud();

        Solicitud sol = solicitudService.obtenerSolicitudporId(idSolicitud);
        UnidadAdministrativa unidad = new UnidadAdministrativa(claveUnidadAdministrativa);
        String usuarioAsignado = balanceadorServices
                .obtenerAutorizadorResponsableUnidadLocalRecaudadoraCAL((SolicitudDatosGenerales) sol, unidad);
        SolicitudDatosGenerales solicitud = solicitudDao.obtenerSolicitudDatosGeneralesPorId(idSolicitud);
        Requerimiento requerimiento = requerimientoDao.obtenerRequerimientoPorId(idRequerimiento);
        Tramite tramite = tramiteDao.obtenerTramitePorId(requerimiento.getTramite().getNumeroAsunto());
        if (!esAutorizador) {
            requerimiento.setIdUsuario(rfcUsuario);
        }
        String abogado = requerimiento.getIdUsuario();
        String numAsunto = tramite.getNumeroAsunto();
        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, idTarea);
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);

            TipoTramite tipTram = tipoTramiteDAO
                    .obtenerTiposDeTramitesPorId(solicitud.getTipoTramite().getIdTipoTramite());
            if (tipTram.getServicio().equals(DiscriminadorConstants.SERVICIO_DOS)) {
                if (tipoRequerimiento.equals(TipoRequerimiento.AUTORIDAD_INTERNA.getClave())) {
                    try {
                        Date dt = new Date();
                        Calendar c = Calendar.getInstance();
                        Tarea tareaAI = new Tarea();
                        tareaAI.setNumeroAsunto(numAsunto);
                        tareaAI.setClaveAdm(tareaAct.getClaveAdm());
                        tareaAI.setClaveAbogado(tareaAct.getClaveAbogado());
                        tareaAI.setClaveAsignado(usuarioAsignado);
                        tareaAI.setTarea(EnumeracionBitacora.CONFIRMAR_NOTIFICACION_REQ.getClave());
                        tareaAI.setDescripcion(EnumeracionBitacora.CONFIRMAR_NOTIFICACION_REQ.getDescripcion());
                        c.setTime(dt);
                        c.add(Calendar.DATE, 1);
                        dt = c.getTime();
                        tareaAI.setFechaFin(dt);
                        tareaAI.setFechaCreacion(new Date());
                        tareaAI.setFechaAct(new Date());
                        tareaAI.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
                        tareaAI.setIdRequerimeinto(requerimiento.getIdRequerimiento());
                        tareaServices.guardarTarea(tareaAI);
                    } catch (Exception ex) {
                        getLogger().error("Error generar tarea responsable autoridad interna", ex);
                    }
                } else {
                    List<Requerimiento> reqActivos = requerimientoDao
                            .obtenerRequerimientosActivos(tramite.getNumeroAsunto());
                    if (reqActivos.size() <= 1) {
                        try {
                            Tarea tareaAI = new Tarea();
                            tareaAI.setNumeroAsunto(numAsunto);
                            tareaAI.setClaveAdm(tareaAct.getClaveAdm());
                            tareaAI.setClaveAbogado(tareaAct.getClaveAbogado());
                            tareaAI.setClaveAsignado(requerimiento.getIdUsuario());
                            tareaAI.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
                            tareaAI.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
                            tareaAI.setFechaCreacion(new Date());
                            tareaAI.setFechaAct(new Date());
                            tareaAI.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
                            tareaServices.guardarTarea(tareaAI);
                            getLogger().debug("genera tarea atender asunto");
                        } catch (Exception ex) {
                            getLogger().error("Error al generar tarea turnar asunto", ex);
                        }
                    }
                }
            } else {
                List<Requerimiento> reqActivos = requerimientoDao
                        .obtenerRequerimientosActivos(tramite.getNumeroAsunto());
                if (reqActivos.size() <= 1) {
                    try {
                        Tarea tareaAI = new Tarea();
                        tareaAI.setNumeroAsunto(numAsunto);
                        tareaAI.setClaveAdm(tareaAct.getClaveAdm());
                        tareaAI.setClaveAsignado(requerimiento.getIdUsuario());
                        tareaAI.setClaveAbogado(tareaAct.getClaveAbogado());
                        tareaAI.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
                        tareaAI.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
                        tareaAI.setFechaCreacion(new Date());
                        tareaAI.setFechaAct(new Date());
                        tareaAI.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
                        tareaServices.guardarTarea(tareaAI);
                    } catch (Exception ex) {
                        getLogger().error("Error al generar tarea turnar asunto", ex);
                    }
                }
            }
        } catch (Exception ex) {
            getLogger().error("Error al cerrar la tarea de firma de requerimiento", ex);
        }
        if (usuarioAsignado != null) {
            enviarCorreoService.enviarCorreo(tramite.getNumeroAsunto(), usuarioAsignado, "Atender Requerimiento");
        }
        if (claveUnidadAdministrativa != null) {
            enviarCorreoService.enviarCorreoAdministracion(tramite.getNumeroAsunto(), claveUnidadAdministrativa);
        }

        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(tramite.getNumeroAsunto());
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitud.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.FIRMAR_REQUERIMIENTO.getClave());
        bitacora.setRfcNuevo(abogado);
        bitacoraTramiteServices.insertarBitacora(bitacora);
    }

    /**
     * Metodo para completrar una tarea de atencion de requerimiento
     * 
     * @param tipoMensajeBPM
     * @param idTarea
     * @param idTipoTransicion
     * @param rfcAsignar
     * @param esAutorizador
     */
    @Override
    public void completarTareaAtenderRequerimientoAutoridad(TipoMensajeBPM tipoMensajeBPM, String idTarea,
            String rfcUsuario, String numAsunto, String nombreTarea) {
        getLogger().debug("incia completarTareaAtenderRequerimientoAutoridad");
        List<DocumentoOficial> docs = documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(numAsunto,
                TipoDocumentoOficial.OFICIO_ATENCION_RETRO.getClave());
        for (DocumentoOficial doc : docs) {
            doc.setEstadoDocumento(EstadoDocumento.FIRMADO.getClave());
            doc.setSello(null);
        }
        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        Requerimiento requerimiento = requerimientoDao.obtenerUltimoRequerimientoPorTramiteCAL(numAsunto);

        SolicitudDatosGenerales solicitud = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());

        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);
            Tarea tareaAbogado = tareaServices.obtenerTareaPorTramiteRequerimientoAtendido(tareaAct.getNumeroAsunto(),
                    EnumeracionBitacora.NOTIFICACION_REQUERIMIENTO.getClave());
            List<Requerimiento> reqActivos = requerimientoDao.obtenerRequerimientosActivos(numAsunto);
            if ((reqActivos == null) && ((tareaAbogado == null)
                    || (tareaAbogado.getEstadoTarea().equals(ProcesosConstantes.PROCESO_TAREA_ATENDIDO)))) {
                Tarea tarea = new Tarea();
                tarea.setNumeroAsunto(numAsunto);
                tarea.setClaveAdm(tareaAct.getClaveAdm());
                tarea.setClaveAbogado(tareaAct.getClaveAbogado());
                tarea.setClaveAsignado(requerimiento.getIdUsuario());
                tarea.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
                tarea.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
                tarea.setFechaCreacion(new Date());
                tarea.setFechaAct(new Date());
                tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
                tareaServices.guardarTarea(tarea);
            }
        } catch (Exception ex) {
            getLogger().error("Error al firmar requerimeinto a autoridad en tarea", ex);
        }

        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(tramite.getNumeroAsunto());
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitud.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.ATENDER_REQ.getClave());
        bitacoraTramiteServices.insertarBitacora(bitacora);
    }

    /**
     * Metodo para enviar una tarea de requerimiento
     * 
     * @param numAsunto
     * @param idTarea
     * @param rfcAsignar
     * @param rfcUsuario
     * @param reqs
     */
    @Override
    public void enviarTareaRequerimiento(String numAsunto, String idTarea, String rfcAsignar, String rfcUsuario,
            List<RequerimientoTarea> reqs) {

        getLogger().debug("TareaServicesImpl : enviarTareaRequerimientoAct");
        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);

            getLogger().debug("TareaServicesImpl : enviarTareaRequerimientoNuevaTarea");
            for (RequerimientoTarea req : reqs) {

                Tarea tarea = new Tarea();
                tarea.setNumeroAsunto(numAsunto);
                tarea.setClaveAdm(tareaAct.getClaveAdm());
                tarea.setClaveAbogado(tareaAct.getClaveAbogado());
                tarea.setClaveAsignado(rfcAsignar);
                tarea.setTarea(EnumeracionBitacora.FIRMAR_REQUERIMIENTO.getClave());
                tarea.setDescripcion(EnumeracionBitacora.FIRMAR_REQUERIMIENTO.getDescripcion());
                tarea.setFechaCreacion(new Date());
                tarea.setFechaAct(new Date());
                tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
                tarea.setIdRequerimeinto(Long.valueOf(req.getIdSubProceso()).longValue());
                tareaServices.guardarTarea(tarea);
            }
        } catch (Exception ex) {
            getLogger().error("Error al generar requerimiento en tarea", ex);
        }

        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        SolicitudDatosGenerales solicitud = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(tramite.getNumeroAsunto());
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitud.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.REQUERIMIENTO.getClave());
        bitacora.setRfcNuevo(rfcAsignar);
        bitacoraTramiteServices.insertarBitacora(bitacora);
    }

    /**
     * Metodo para completrar una tarea de atencion de requerimiento
     * 
     * @param tipoMensajeBPM
     * @param idTarea
     * @param idTipoTransicion
     * @param rfcAsignar
     * @param esAutorizador
     */
    @Override
    public void completarTareaAtenderRequerimiento(String numeroAsunto, TipoMensajeBPM tipoMensajeBPM, String idTarea,
            String idTipoTransicion, boolean esAutorizador, String rfcUsuario, boolean esOficilia) {

        getLogger().debug("FirmaTareaServicesImpl : completarTareaAtenderRequerimientoAct");
        Tramite tramite = tramiteDao.obtenerTramitePorId(numeroAsunto);
        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numeroAsunto, Long.parseLong(idTarea));
            tareaAct.setFechaAct(new Date());
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaServices.guardarTarea(tareaAct);

            List<Requerimiento> reqActivos = requerimientoDao.obtenerRequerimientosActivos(numeroAsunto);
            if (reqActivos == null) {
                Tarea tareaAbogado = tareaServices.obtenerTareaPorTramiteRequerimientoAtendido(
                        tareaAct.getNumeroAsunto(), EnumeracionBitacora.NOTIFICACION_REQUERIMIENTO.getClave());
                if (tareaAbogado != null) {
                    if (tareaAbogado.getEstadoTarea().equals(ProcesosConstantes.PROCESO_TAREA_ATENDIDO)) {
                        tramite.setEstadoTramite(EstadoTramite.EN_ESTUDIO);
                        tramiteDao.modificarTramite(tramite);
                        Tarea tareaReasignar = new Tarea();
                        tareaReasignar.setNumeroAsunto(tareaAbogado.getNumeroAsunto());
                        tareaReasignar.setClaveAdm(tareaAbogado.getClaveAdm());
                        tareaReasignar.setClaveAbogado(tareaAbogado.getClaveAbogado());
                        tareaReasignar.setClaveAsignado(tareaAbogado.getClaveAsignado());
                        tareaReasignar.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
                        tareaReasignar.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
                        tareaReasignar.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
                        tareaReasignar.setFechaCreacion(new Date());
                        tareaReasignar.setFechaAct(new Date());
                        tareaServices.guardarTarea(tareaReasignar);
                    }
                }
            }

        } catch (Exception ex) {
            getLogger().error("Error al generar tarea atender requerimiento promovente", ex);
        }

        SolicitudDatosGenerales solicitud = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(tramite.getNumeroAsunto());
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitud.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.ATENDER_REQ.getClave());
        bitacoraTramiteServices.insertarBitacora(bitacora);
    }

    /**
     * Metodo para firmar una autorizacion
     * 
     * @param numAsunto
     * @param idTarea
     * @return
     */
    public Long firmarAutorizar(String numAsunto, String idTarea, String rfcUsuario) {

        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        tramite.setEstadoTramite(EstadoTramite.RESUELTO);
        documentosHelper.complementarDocumentos(tramite.getSolicitud().getIdSolicitud());
        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(numAsunto);
        resolucion.setIdeEstadoResolucion(EstadoResolucion.AUTORIZADA.getClave());
        resolucion.setFechaResolucion(new Date());
        String rfcAsignar = resolucion.getRfcAbogado();

        Solicitud solicitud = solicitudDAO.obtenerSolicitudporId(tramite.getSolicitud().getIdSolicitud());
        if (resolucion.getIdeSentidoResolucion() != null) {
            if (resolucion.getIdeSentidoResolucion().equals(SentidoResolucion.ACEPTADA.getClave())) {
                solicitud.setEstadoSolicitud(EstadoSolicitud.FAVORABLE);
            } else {
                solicitud.setEstadoSolicitud(EstadoSolicitud.DESFAVORABLE);
            }
        }
        List<DocumentoOficial> docsOfi = documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(numAsunto,
                TipoDocumentoOficial.OFICIO_RESOLUCION.getClave());

        for (DocumentoOficial doc : docsOfi) {
            DocumentoResolucion docRes = new DocumentoResolucion();
            doc.setEstadoDocumento(EstadoDocumento.FIRMADO.getClave());
            docRes.setIdDocumentoOficial(doc.getIdDocumentoOficial());
            docRes.setDocumentoOficial(doc);
            docRes.setIdResolucion(resolucion.getIdResolucion());
            docRes.setResolucion(resolucion);
            docRes.setEstado(EstadoDocumento.POR_APROBAR.getClave());
            documentoDao.guardarDocumentResolucion(docRes);
        }
        getLogger().debug("FirmaTareaServiceImpl : firmarResolucionAct");
        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);

            getLogger().debug("FirmaTareaServiceImpl : firmarResolucionNuevaTarea");
            Date dt = new Date();
            Tarea tarea = new Tarea();
            tarea.setNumeroAsunto(numAsunto);
            tarea.setClaveAdm(rfcUsuario);
            tarea.setClaveAbogado(tareaAct.getClaveAbogado());
            tarea.setClaveAsignado(rfcAsignar);
            tarea.setTarea(EnumeracionBitacora.NOTIFICACION_RESOLUCION.getClave());
            tarea.setDescripcion(EnumeracionBitacora.NOTIFICACION_RESOLUCION.getDescripcion());
            tarea.setFechaCreacion(dt);
            tarea.setFechaAct(dt);
            tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tareaServices.guardarTarea(tarea);
        } catch (Exception ex) {
            getLogger().error("Error al firmar resolucion en tarea", ex);
        }
        SolicitudDatosGenerales solicitudDt = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(tramite.getNumeroAsunto());
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitudDt.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.FIRMAR_RESOLUCION.getClave());
        bitacora.setRfcNuevo(rfcAsignar);
        bitacoraTramiteServices.insertarBitacora(bitacora);
        return resolucion.getIdResolucion();
    }

    /**
     * Metodo para firmar la confirmacion de una notificacion
     * 
     * @param numAsunto
     * @param idTarea
     * @param idTareaBPM
     * @param idRequerimiento
     */
    @Override
    public void firmarConfirmarNotificacion(String numAsunto, String idTarea, Long idRequerimiento, String rfcUsuario) {
        generarNotificacion(idRequerimiento);
        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        SolicitudDatosGenerales solicitudDt = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
        getLogger().debug("ConfirmarNotificacionRequerimientoBusiness : firmarConfirmarNotificacion");
        try {
            Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
            tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
            tareaAct.setFechaAct(new Date());
            tareaServices.guardarTarea(tareaAct);
            Date dt = new Date();
            Tarea tarea = new Tarea();
            tarea.setNumeroAsunto(numAsunto);
            tarea.setClaveAdm(tareaAct.getClaveAdm());
            tarea.setClaveAbogado(tareaAct.getClaveAbogado());
            tarea.setClaveAsignado(rfcUsuario);
            tarea.setTarea(EnumeracionBitacora.ATENDER_REQ_AUTORIDAD.getClave());
            tarea.setDescripcion(EnumeracionBitacora.ATENDER_REQ_AUTORIDAD.getDescripcion());
            tarea.setFechaCreacion(dt);
            tarea.setFechaAct(dt);
            tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
            tarea.setIdRequerimeinto(tareaAct.getIdRequerimeinto());
            tareaServices.guardarTarea(tarea);
        } catch (Exception ex) {
            getLogger().error(
                    "Error al cerrar tarea Confirmar requerimiento autoridad y generar tarea Atender requerimeinto por autoridad.",
                    ex);
        }
        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(tramite.getNumeroAsunto());
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitudDt.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.CONFIRMAR.getClave());
        bitacoraTramiteServices.insertarBitacora(bitacora);
    }

    /**
     * Metodo para generar una notificacion
     * 
     * @param idRequerimiento
     * @param idTareaBPM
     */
    private void generarNotificacion(Long idRequerimiento) {
        Notificacion notificacion = null;
        NotificacionRequerimiento notificacionRequerimiento = new NotificacionRequerimiento();
        Requerimiento req = requerimientoDao.obtenerRequerimientoPorId(idRequerimiento);
        notificacionRequerimiento.setRequerimiento(req);
        notificacion = notificacionRequerimiento;
        if (notificacion != null) {
            notificacion.setFechaEnvioNotificacion(new Date());
            notificacion.setFechaAcuseNotificacion(new Date());
            notificacion.setEstadoNotificacion(EstadoNotificacion.EMITIDA);
            req.setFechaVerificacion(new Date());
            notificacionDao.guardarNotificacion(notificacion);
        }
    }

    @Override
    public void firmarCapturaFechaNotificacion(String numAsunto, String idTarea, Long idTareaBPM, Boolean blnResolucion,
            String rfcUsuario, Long idNotificacion, Long idRequerimiento) {
        Tramite tramite = tramiteDao.obtenerTramitePorId(numAsunto);
        Notificacion notificacion = guardarNotificacion(idNotificacion, numAsunto);
        Tarea tareaAct = tareaServices.obtenerTareaPorTramiteActivo(numAsunto, Long.parseLong(idTarea));
        if (blnResolucion) {
            this.resolverNotificarAsunto(tramite, numAsunto);
            this.atenderTarea(tareaAct);
        } else {
            this.notificarRequerimientoAsunto(numAsunto, tramite, idRequerimiento, notificacion, tareaAct, rfcUsuario);
        }
        this.guardarBitacora(rfcUsuario, tramite);
    }

    private Notificacion guardarNotificacion(Long idNotificacion, String numAsunto) {
        Notificacion notificacion = notificacionDao.obtenerNotificacionById(idNotificacion);
        notificacion.setEstadoNotificacion(EstadoNotificacion.NOTIFICADO_CONTRIBUYENTE);
        notificacionDao.guardarNotificacion(notificacion);
        List<DocumentoOficial> docs = documentoDao.obtenerDocumentosOficialesAnexadosPorTipo(numAsunto,
                TipoDocumentoOficial.ACTA_NOTIFICACION.getClave());
        for (DocumentoOficial doc : docs) {
            doc.setEstadoDocumento(EstadoDocumento.FIRMADO.getClave());
        }
        return notificacion;
    }

    private void resolverNotificarAsunto(Tramite tramite, String numAsunto) {
        tramite.setEstadoTramite(EstadoTramite.RESUELTO_NOTIFICADO);
        tramiteDao.modificarTramite(tramite);
        Resolucion resolucion = resolucionDao.obtenerResolucionIdTramite(numAsunto);
        List<DocumentoResolucion> docsRes = documentoDao
                .obtenerDocumentoResolucionPorIdRes(resolucion.getIdResolucion());
        for (DocumentoResolucion docRes : docsRes) {
            docRes.setEstado(EstadoDocumento.APROVADO.getClave());
        }
    }

    private void atenderTarea(Tarea tareaAct) {
        tareaAct.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_ATENDIDO);
        tareaAct.setFechaAct(new Date());
        tareaServices.guardarTarea(tareaAct);
    }

    private void crearTarea(String numAsunto, String rfcUsuario, Tarea tareaAct) {
        Tarea tarea = new Tarea();
        tarea.setNumeroAsunto(numAsunto);
        tarea.setClaveAdm(tareaAct.getClaveAdm());
        tarea.setClaveAbogado(tareaAct.getClaveAbogado());
        tarea.setClaveAsignado(rfcUsuario);
        tarea.setTarea(EnumeracionBitacora.ATENDER_ASUNTO.getClave());
        tarea.setDescripcion(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion());
        tarea.setFechaCreacion(new Date());
        tarea.setFechaAct(new Date());
        tarea.setEstadoTarea(ProcesosConstantes.PROCESO_TAREA_PROCESO);
        tareaServices.guardarTarea(tarea);
    }

    private void notificarRequerimientoAsunto(String numAsunto, Tramite tramite, Long idRequerimiento,
            Notificacion notificacion, Tarea tareaAct, String rfcUsuario) {

        boolean reqAtendido = false;
        Date fechaFinAtenderRequerimiento = null;
        String fechaReq = null;
        Requerimiento reqContribuyente = requerimientoDao.obtenerRequerimientoContributente(numAsunto);
        Requerimiento req = requerimientoDao.obtenerRequerimientoPorId(idRequerimiento);
        Tarea tareaContribuyente = tareaServices.obtenerTareaPorTramiteRequerimientoAtendido(numAsunto,
                EnumeracionBitacora.ATENDER_REQ.getClave());
        List<Requerimiento> reqActivos = requerimientoDao.obtenerRequerimientosActivos(tramite.getNumeroAsunto());

        if (reqContribuyente != null) {
            reqAtendido = reqContribuyente.getEstadoRequerimiento().getClave()
                    .equals(EstadoRequerimiento.ATENDIDO.getClave());
        }

        if (req != null) {
            req.setFechaVerificacion(notificacion.getFechaEnvioNotificacion());
            requerimientoDao.actualizarRequerimiento(req);
        }

        if (tramite.getSolicitud().getDiscriminatorValue().startsWith(DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
            fechaReq = enumeracionDao
                    .obtenerParametroByEnum(EnumeracionParametro.DIAS_ATENDER_REQ_INFORMACION.getClave());
        }
        if (tramite.getSolicitud().getDiscriminatorValue().startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
            fechaReq = enumeracionDao
                    .obtenerParametroByEnum(EnumeracionParametro.DIAS_ATENDER_REQ_INFORMACION_CAL.getClave());
        }
        fechaFinAtenderRequerimiento = diasHabilesHelper
                .obtenerFechaDiasHabiles(notificacion.getFechaEnvioNotificacion(), (Integer.valueOf(fechaReq) + 1));

        this.atenderTarea(tareaAct);

        if (tareaContribuyente != null && !reqAtendido) {
            if (fechaFinAtenderRequerimiento != null) {
                tareaContribuyente.setFechaFin(fechaFinAtenderRequerimiento);
                tareaServices.guardarTarea(tareaContribuyente);
            }
            if (tareaContribuyente.getEstadoTarea().equals(ProcesosConstantes.PROCESO_TAREA_ATENDIDO)
                    && reqActivos == null) {
                tramite.setEstadoTramite(EstadoTramite.EN_ESTUDIO);
                tramiteDao.modificarTramite(tramite);
                this.crearTarea(numAsunto, rfcUsuario, tareaAct);
            }
        } else if (reqActivos == null) {
            this.crearTarea(numAsunto, rfcUsuario, tareaAct);
        }
    }

    private void guardarBitacora(String rfcUsuario, Tramite tramite) {
        SolicitudDatosGenerales solicitudDt = solicitudDao
                .obtenerSolicitudDatosGeneralesPorId(tramite.getSolicitud().getIdSolicitud());
        Bitacora bitacora = new Bitacora();
        bitacora.setFechaEvento(new Date());
        bitacora.setNumeroAsunto(tramite.getNumeroAsunto());
        bitacora.setRfcUsuario(rfcUsuario);
        bitacora.setUnidadAdministrativa(solicitudDt.getUnidadAdminBalanceo());
        bitacora.setTarea(EnumeracionBitacora.NOTIFICACION.getClave());
        bitacora.setRfcNuevo(rfcUsuario);
        bitacoraTramiteServices.insertarBitacora(bitacora);
    }

    public AsignarTareaCorreoService getAsignarTareaCorreoService() {
        return asignarTareaCorreoService;
    }

    public void setAsignarTareaCorreoService(AsignarTareaCorreoService asignarTareaCorreoService) {
        this.asignarTareaCorreoService = asignarTareaCorreoService;
    }

}
