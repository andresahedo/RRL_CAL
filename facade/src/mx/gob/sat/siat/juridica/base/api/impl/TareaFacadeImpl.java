package mx.gob.sat.siat.juridica.base.api.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.sat.siat.juridica.base.api.TareaFacade;
import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EnumeracionBitacora;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoRequerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.OrdenBandeja;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tarea;
import mx.gob.sat.siat.juridica.base.dto.TareaDTO;
import mx.gob.sat.siat.juridica.base.dto.transformer.TareaDTOTransformer;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.BandejaServices;
import mx.gob.sat.siat.juridica.base.service.CatalogoDServices;
import mx.gob.sat.siat.juridica.base.service.RequerimientoServices;
import mx.gob.sat.siat.juridica.base.service.TareaServices;
import mx.gob.sat.siat.juridica.base.service.TipoTramiteServices;
import mx.gob.sat.siat.juridica.base.service.TramiteServices;
import mx.gob.sat.siat.juridica.cal.util.constants.CadenasConstants;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.util.constante.RequerimientoConstante;

@Component("tareaFacade")
public class TareaFacadeImpl extends BaseFacadeImpl implements TareaFacade {

    private static final long serialVersionUID = -9092814970103826858L;
    private transient Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    @Autowired
    private transient TareaServices tareaServices;

    @Autowired
    private transient TareaDTOTransformer tareaDTOTransformer;

    @Autowired
    private transient BandejaServices bandejaServices;

    @Autowired
    private CatalogoDServices catalogoDServices;

    @Autowired
    private transient TramiteServices tramiteServices;

    @Autowired
    private transient RequerimientoServices requerimientoServices;

    @Autowired
    private transient TipoTramiteServices tipoTramiteServices;

    @Override
    public void crearTarea(TareaDTO tarea) {
        logger.debug("guardarTarea");
        Tarea tareamodel = tareaDTOTransformer.transformarDTO(tarea);
        tareaServices.guardarTarea(tareamodel);
    }

    @Override
    public DataPage buscarTareasporUsuario(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        DataPage dataPage = new DataPage();
        String usuario = "";
        String numasunto = "";
        String promovente = "";
        Date fecIni = null;
        Calendar fechaFin = null;
        Date fecFin = null;
        String edoprocesal = "";
        usuario = filtroBandejaTareaDTO.getUsuario();
        if (filtroBandejaTareaDTO.getNumeroAsunto() != null && !filtroBandejaTareaDTO.getNumeroAsunto().isEmpty()) {
            numasunto = filtroBandejaTareaDTO.getNumeroAsunto();
        }
        if (filtroBandejaTareaDTO.getEstadoProcesal() != null) {
            edoprocesal = filtroBandejaTareaDTO.getEstadoProcesal().getClave();
        }
        if (filtroBandejaTareaDTO.getRfcPromovente() != null && !filtroBandejaTareaDTO.getRfcPromovente().isEmpty()) {
            promovente = filtroBandejaTareaDTO.getRfcPromovente();
        }
        if (filtroBandejaTareaDTO.getFechaInicio() != null && filtroBandejaTareaDTO.getFechaFin() != null) {
            fecIni = filtroBandejaTareaDTO.getFechaInicio();
            fechaFin = Calendar.getInstance();
            fechaFin.setTime(filtroBandejaTareaDTO.getFechaFin());
            fechaFin.set(Calendar.HOUR_OF_DAY, NumerosConstantes.VEINTITRES);
            fechaFin.set(Calendar.MINUTE, NumerosConstantes.CINCUENTA_NUEVE);
            fechaFin.set(Calendar.SECOND, NumerosConstantes.CINCUENTA_NUEVE);
            fechaFin.set(Calendar.MILLISECOND, NumerosConstantes.CINCUENTA_NUEVE);
            fecFin = fechaFin.getTime();
        }
        List<DatosBandejaTareaDTO> datosBandejaTareaDTOs = new ArrayList<DatosBandejaTareaDTO>();
        List<Tarea> tareas;
        try {
            DatosBandejaTareaDTO bandejaTareaDTO = null;
            tareas = tareaServices.buscarTareasporUsuario(numasunto, usuario, promovente, fecIni, fecFin, edoprocesal);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String estadoProcesal;
            if (tareas != null) {
                for (Tarea tarea : tareas) {
                    bandejaTareaDTO = new DatosBandejaTareaDTO();
                    bandejaTareaDTO.setIdTareaUsuario(tarea.getIdTarea());
                    bandejaTareaDTO.setIdTarea(tarea.getIdTarea());
                    bandejaTareaDTO.setIdSolicitud(tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto())
                            .getSolicitud().getIdSolicitud());
                    bandejaTareaDTO.setIdInstancia(tarea.getIdTarea());
                    int idtipotramite = tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getSolicitud()
                            .getTipoTramite().getIdTipoTramite();
                    bandejaTareaDTO.setTipoTramite(Integer.toString(idtipotramite));
                    bandejaTareaDTO.setDescripcionTipoTramite(
                            tipoTramiteServices.obtenerTipoTramiteId(idtipotramite).getDescripcionModalidad());
                    bandejaTareaDTO.setNombreTarea(tarea.getDescripcion());
                    bandejaTareaDTO.setNumeroAsunto(tarea.getNumeroAsunto());
                    bandejaTareaDTO.setRfcSolicitante(tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto())
                            .getSolicitud().getCveUsuarioCapturista());
                    String rfcOwner = filtroBandejaTareaDTO.getUsuario();
                    bandejaTareaDTO.setRfcUsuarioAsignado(rfcOwner);
                    Requerimiento requerimiento;
                    if (tarea.getIdRequerimeinto() != null) {
                        bandejaTareaDTO.setIdRequerimiento(tarea.getIdRequerimeinto());
                        requerimiento = requerimientoServices.obtenerRequerimientoById(tarea.getIdRequerimeinto());
                        bandejaTareaDTO.setFechaAsignacion(requerimiento.getFechaCreacion());
                    } else {
                        requerimiento = requerimientoServices.obtenerUltimoRequerimientoPorTramiteTarea(
                                tarea.getNumeroAsunto(), String.valueOf(idtipotramite));
                        if (requerimiento != null) {
                            bandejaTareaDTO.setIdRequerimiento(requerimiento.getIdRequerimiento());
                        }
                        bandejaTareaDTO.setFechaAsignacion(tarea.getFechaCreacion());
                    }

                    if (tarea.getTarea().equals(EnumeracionBitacora.NOTIFICACION_FECHA.getClave())
                            || tarea.getTarea().equals(EnumeracionBitacora.NOTIFICACION_REQUERIMIENTO.getClave())) {
                        Requerimiento reqContribuyente = requerimientoServices.obtenerRequerimientoContributente(
                                tarea.getNumeroAsunto(), bandejaTareaDTO.getTipoTramite());
                        if (reqContribuyente != null) {
                            if (!reqContribuyente.getEstadoRequerimiento().getClave()
                                    .equals(EstadoRequerimiento.ATENDIDO.getClave())) {
                                bandejaTareaDTO.setIdRequerimiento(reqContribuyente.getIdRequerimiento());
                            }
                        }
                    }
                    if (tarea.getTarea().equals(EnumeracionBitacora.CONFIRMAR_NOTIFICACION_REQ.getClave())
                            || tarea.getTarea().equals(EnumeracionBitacora.ATENDER_REQ_AUTORIDAD.getClave())) {
                        bandejaTareaDTO.setIdRequerimiento(tarea.getIdRequerimeinto());
                    }
                    estadoProcesal = tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getEstadoTramite()
                            .getClave();
                    bandejaTareaDTO.setCveEstadoProcesal(estadoProcesal);
                    bandejaTareaDTO.setEstadoProcesal(EstadoTramite.parse(estadoProcesal).getDescripcion());
                    bandejaTareaDTO.setEdoToUser(
                            catalogoDServices.obtenerCatalogoPorClave(estadoProcesal).getDescripcionGenerica1());
                    bandejaTareaDTO.setFechaAsignacionRequerimiento(
                            catalogoDServices.obtenerFechaRequerimiento(bandejaTareaDTO.getNumeroAsunto()));
                    Persona persona = null;
                    persona = bandejaServices.obtenerPersonaPorRfc(rfcOwner);
                    if (persona.getRazonSocial() != null) {
                        bandejaTareaDTO.setNombrePersonaAsignada(persona.getRazonSocial());

                    } else {
                        bandejaTareaDTO.setNombrePersonaAsignada(persona.getNombre() + " "
                                + persona.getApellidoPaterno() + " " + persona.getApellidoMaterno());

                    }
                    if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.TURNAR_ASUNTO.getDescripcion())) {
                        bandejaTareaDTO.setUrl("/resources/pages/rrl/turnarRecursoRevocacion.jsf");
                        if (bandejaTareaDTO.getTipoTramite() != null && bandejaTareaDTO.getTipoTramite()
                                .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/cal/seguimiento/turnarAsunto.jsf");
                        }
                    } else if (bandejaTareaDTO.getNombreTarea()
                            .equals(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion())) {
                        bandejaTareaDTO.setUrl("/resources/pages/abogado/abogadoCaptura.jsf");
                        if (bandejaTareaDTO.getTipoTramite() != null && bandejaTareaDTO.getTipoTramite()
                                .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/cal/atencion/atenderPromocion.jsf");
                        }
                    } else if (bandejaTareaDTO.getNombreTarea()
                            .equals(EnumeracionBitacora.FIRMAR_REQUERIMIENTO.getDescripcion())) {
                        bandejaTareaDTO.setUrl(RequerimientoConstante.AUTORIZAR_REQUERIMIENTO_RRL);
                    } else if (bandejaTareaDTO.getNombreTarea()
                            .equals(EnumeracionBitacora.FIRMAR_REMISION.getDescripcion())) {
                        if (bandejaTareaDTO.getTipoTramite() != null && bandejaTareaDTO.getTipoTramite()
                                .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/cal/generacion/autorizarRemitir.jsf");
                        } else if (bandejaTareaDTO.getTipoTramite() != null && bandejaTareaDTO.getTipoTramite()
                                .startsWith(DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/abogado/autorizarRemitir.jsf");
                        }
                    } else if (bandejaTareaDTO.getNombreTarea()
                            .equals(EnumeracionBitacora.FIRMAR_RESOLUCION.getDescripcion())) {
                        if (bandejaTareaDTO.getTipoTramite() != null && bandejaTareaDTO.getTipoTramite()
                                .startsWith(DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/autorizador/autorizarResolucion.jsf");
                        } else if (bandejaTareaDTO.getTipoTramite() != null && bandejaTareaDTO.getTipoTramite()
                                .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl(CadenasConstants.GENERAR_RESOLUCION_ACTION_NAVEGATION_AUTORIZACION);
                        }
                    } else if (bandejaTareaDTO.getNombreTarea()
                            .equals(EnumeracionBitacora.ATENDER_OBSERVACION.getDescripcion())) {
                        if (bandejaTareaDTO.getTipoTramite() != null && bandejaTareaDTO.getTipoTramite()
                                .startsWith(DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/rrl/op/observacion/atenderObservacionRRL");
                        } else if (bandejaTareaDTO.getTipoTramite() != null && bandejaTareaDTO.getTipoTramite()
                                .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl(CadenasConstants.ATENDER_OBSERVACION_OFICIALIA_ACTION_NAVEGATION);
                        }
                    } else if (bandejaTareaDTO.getNombreTarea()
                            .equals(EnumeracionBitacora.ATENDER_REQ.getDescripcion())) {
                        bandejaTareaDTO.setUrl(
                                "/resources/pages/rrl/requerimiento/atenderrequerimiento/atenderRequerimiento.jsf");
                    } else if ((bandejaTareaDTO.getNombreTarea()
                            .equals(EnumeracionBitacora.NOTIFICACION_FECHA.getDescripcion()))
                            || (bandejaTareaDTO.getNombreTarea()
                                    .equals(EnumeracionBitacora.NOTIFICACION_REQUERIMIENTO.getDescripcion())
                                    || (bandejaTareaDTO.getNombreTarea()
                                            .equals(EnumeracionBitacora.NOTIFICACION_RESOLUCION.getDescripcion())))) {
                        bandejaTareaDTO.setUrl("/resources/pages/abogado/registrarFechaNotificacion.jsf");
                    } else if (bandejaTareaDTO.getNombreTarea()
                            .equals(EnumeracionBitacora.CONFIRMAR_NOTIFICACION_REQ.getDescripcion())
                            && bandejaTareaDTO.getTipoTramite()
                                    .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                        bandejaTareaDTO.setUrl("/resources/pages/autorizador/firmaConfirmarNotificacion.jsf");
                    } else if (bandejaTareaDTO.getNombreTarea()
                            .equals(EnumeracionBitacora.ATENDER_REQ_AUTORIDAD.getDescripcion())
                            && bandejaTareaDTO.getTipoTramite()
                                    .startsWith(DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                        bandejaTareaDTO.setUrl("/resources/pages/autorizador/atenderRequerimientoAutoridad.jsf");
                    }
                    logger.debug(bandejaTareaDTO.toString());
                    datosBandejaTareaDTOs.add(bandejaTareaDTO);
                }
            }

        } catch (Exception e) {
            getLogger().error("Error al obtener tareas del usuario", e);
        }
        datosBandejaTareaDTOs = ordenaTareas(datosBandejaTareaDTOs, filtroBandejaTareaDTO.getOrden());
        dataPage.setData(datosBandejaTareaDTOs);
        dataPage.setTotalOfRecords(datosBandejaTareaDTOs.size());
        return dataPage;
    }

    private List<DatosBandejaTareaDTO> ordenaTareas(List<DatosBandejaTareaDTO> datosBandejaTareaDTOs, String orden){
        if(orden.equals(OrdenBandeja.BDESTADO_PROCESAL+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getCveEstadoProcesal().compareTo(d2.getCveEstadoProcesal());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDESTADO_PROCESAL+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d2.getCveEstadoProcesal().compareTo(d1.getCveEstadoProcesal());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDFOLIO_TRAMITE+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getNumeroAsunto().compareTo(d2.getNumeroAsunto());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDFOLIO_TRAMITE+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d2.getNumeroAsunto().compareTo(d1.getNumeroAsunto());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDRFC_PROMOVENTE+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getRfcSolicitante().compareTo(d2.getRfcSolicitante());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDRFC_PROMOVENTE+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d2.getRfcSolicitante().compareTo(d1.getRfcSolicitante());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDDESCRIPCION_TIPO_TRAMITE+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getTipoTramite().compareTo(d2.getTipoTramite());
                }
            });
        }else if(orden.equals(OrdenBandeja.BDDESCRIPCION_TIPO_TRAMITE+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d2.getTipoTramite().compareTo(d1.getTipoTramite());
                }
            });
        }else if(orden.equals(OrdenBandeja.NAME+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getNombreTarea().compareTo(d2.getNombreTarea());
                }
            });
        }else if(orden.equals(OrdenBandeja.NAME+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                    return d2.getNombreTarea().compareTo(d1.getNombreTarea());
                }
            });
        }else if(orden.equals(OrdenBandeja.OWNER+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getNombrePersonaAsignada().compareTo(d2.getNombrePersonaAsignada());
                }
            });
        }else if(orden.equals(OrdenBandeja.OWNER+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                    return d2.getNombrePersonaAsignada().compareTo(d1.getNombrePersonaAsignada());
                }
            });
        }else if(orden.equals(OrdenBandeja.DUE+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getFechaAsignacion().compareTo(d2.getFechaAsignacion());
                }
            });
        }else if(orden.equals(OrdenBandeja.DUE+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                    return d2.getFechaAsignacion().compareTo(d1.getFechaAsignacion());
                }
            });
        }else if(orden.equals(OrdenBandeja.DUEREQ+" "+ASC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d1.getFechaAsignacionRequerimiento().compareTo(d2.getFechaAsignacionRequerimiento());
                }
            });
        }else if(orden.equals(OrdenBandeja.DUEREQ+" "+DESC) ) {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                    return d2.getFechaAsignacionRequerimiento().compareTo(d1.getFechaAsignacionRequerimiento());
                }
            });
        }else {
            Collections.sort(datosBandejaTareaDTOs, new Comparator<DatosBandejaTareaDTO>() {
                @Override
                public int compare(DatosBandejaTareaDTO d1, DatosBandejaTareaDTO d2) {
                  return d2.getFechaAsignacion().compareTo(d1.getFechaAsignacion()); 
                }
            });
        }
        return datosBandejaTareaDTOs;
    } 
    
    public DataPage buscarTareasporUsuarioReasignacion(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        DataPage dataPage = new DataPage();
        String usuario = "";
        String numasunto = "";
        String abogado = "";
        usuario = filtroBandejaTareaDTO.getUsuario();
        if (filtroBandejaTareaDTO.getNumeroAsunto() != null && !filtroBandejaTareaDTO.getNumeroAsunto().isEmpty()) {
            numasunto = filtroBandejaTareaDTO.getNumeroAsunto();
        }
        if (filtroBandejaTareaDTO.getRfcPromovente() != null) {
            abogado = filtroBandejaTareaDTO.getRfcPromovente();
        }
        List<DatosBandejaTareaDTO> datosBandejaTareaDTOs = new ArrayList<DatosBandejaTareaDTO>();
        List<Tarea> tareas;
        try {
            DatosBandejaTareaDTO bandejaTareaDTO = null;
            tareas = tareaServices.buscarTareasporUsuarioReasignacion(numasunto, usuario, abogado);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String estadoProcesal;
            if (!tareas.isEmpty()) {
                for (Tarea tarea : tareas) {
                    bandejaTareaDTO = new DatosBandejaTareaDTO();
                    bandejaTareaDTO.setIdTareaUsuario(tarea.getIdTarea());
                    bandejaTareaDTO.setIdTarea(tarea.getIdTarea());
                    bandejaTareaDTO.setIdSolicitud(tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getSolicitud().getIdSolicitud());
                    bandejaTareaDTO.setIdInstancia(tarea.getIdTarea());
                    int idtipotramite = tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getSolicitud().getTipoTramite().getIdTipoTramite();
                    bandejaTareaDTO.setTipoTramite(Integer.toString(idtipotramite));
                    bandejaTareaDTO.setDescripcionTipoTramite(tipoTramiteServices.obtenerTipoTramiteId(idtipotramite).getDescripcionModalidad());
                    bandejaTareaDTO.setNombreTarea(tarea.getDescripcion());
                    bandejaTareaDTO.setNumeroAsunto(tarea.getNumeroAsunto());
                    bandejaTareaDTO.setRfcSolicitante(tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getSolicitud().getCveUsuarioCapturista());
                    String rfcOwner = filtroBandejaTareaDTO.getUsuario();
                    bandejaTareaDTO.setRfcUsuarioAsignado(rfcOwner);
                    Requerimiento requerimiento;
                    if (tarea.getIdRequerimeinto() != null) {
                        bandejaTareaDTO.setIdRequerimiento(tarea.getIdRequerimeinto());
                        requerimiento = requerimientoServices.obtenerRequerimientoById(tarea.getIdRequerimeinto());
                        bandejaTareaDTO.setFechaAsignacion(requerimiento.getFechaCreacion());
                    }
                    else
                    {
                        requerimiento = requerimientoServices.obtenerUltimoRequerimientoPorTramiteTarea(tarea.getNumeroAsunto(), String.valueOf(idtipotramite));
                        if(requerimiento != null)
                        {
                            bandejaTareaDTO.setIdRequerimiento(requerimiento.getIdRequerimiento());
                        }
                        bandejaTareaDTO.setFechaAsignacion(tarea.getFechaCreacion());
                    }

                    if(tarea.getTarea().equals(EnumeracionBitacora.NOTIFICACION_FECHA.getClave()) ||
                            tarea.getTarea().equals(EnumeracionBitacora.NOTIFICACION_REQUERIMIENTO.getClave()))
                    {
                        Requerimiento reqContribuyente = requerimientoServices.obtenerRequerimientoContributente(tarea.getNumeroAsunto(),bandejaTareaDTO.getTipoTramite());
                        if(reqContribuyente != null)
                        {
                            if (!reqContribuyente.getEstadoRequerimiento().getClave().equals(EstadoRequerimiento.ATENDIDO.getClave()))
                            {
                                bandejaTareaDTO.setIdRequerimiento(reqContribuyente.getIdRequerimiento());
                            }
                        }
                    }
                    if(tarea.getTarea().equals(EnumeracionBitacora.CONFIRMAR_NOTIFICACION_REQ.getClave())
                            || tarea.getTarea().equals(EnumeracionBitacora.ATENDER_REQ_AUTORIDAD.getClave())){
                        Requerimiento reqAutoridad = requerimientoServices.obtenerRequerimientoAutoridadInterna(tarea.getNumeroAsunto());
                        bandejaTareaDTO.setIdRequerimiento(reqAutoridad.getIdRequerimiento());
                    }
                    estadoProcesal = tramiteServices.buscarTramiteTarea(tarea.getNumeroAsunto()).getEstadoTramite().getClave();
                    bandejaTareaDTO.setCveEstadoProcesal(estadoProcesal);
                    bandejaTareaDTO.setEstadoProcesal(EstadoTramite.parse(estadoProcesal).getDescripcion());
                    bandejaTareaDTO.setEdoToUser(catalogoDServices.obtenerCatalogoPorClave(estadoProcesal).getDescripcionGenerica1());
                    bandejaTareaDTO.setFechaAsignacionRequerimiento(catalogoDServices
                            .obtenerFechaRequerimiento(bandejaTareaDTO.getNumeroAsunto()));
                    Persona persona = null;
                    persona = bandejaServices.obtenerPersonaPorRfc(rfcOwner);
                    if (persona.getRazonSocial() != null) {
                        bandejaTareaDTO.setNombrePersonaAsignada(persona.getRazonSocial());
                    }
                    else {
                        bandejaTareaDTO.setNombrePersonaAsignada(persona.getNombre() + " "
                                + persona.getApellidoPaterno() + " " + persona.getApellidoMaterno());
                    }
                    if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.TURNAR_ASUNTO.getDescripcion())) {
                        bandejaTareaDTO.setUrl("/resources/pages/rrl/turnarRecursoRevocacion.jsf");

                        if (bandejaTareaDTO.getTipoTramite() != null
                                && bandejaTareaDTO.getTipoTramite().startsWith(
                                DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/cal/seguimiento/turnarAsunto.jsf");
                        }
                    } else if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.ATENDER_ASUNTO.getDescripcion())) {
                        bandejaTareaDTO.setUrl("/resources/pages/abogado/abogadoCaptura.jsf");
                      if (bandejaTareaDTO.getTipoTramite() != null
                                && bandejaTareaDTO.getTipoTramite().startsWith(
                                DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/cal/atencion/atenderPromocion.jsf");
                        }
                    } else if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.FIRMAR_REQUERIMIENTO.getDescripcion())) {
                        bandejaTareaDTO.setUrl(RequerimientoConstante.AUTORIZAR_REQUERIMIENTO_RRL);
                    }
                    else if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.FIRMAR_REMISION.getDescripcion())) {
                        if (bandejaTareaDTO.getTipoTramite() != null
                                && bandejaTareaDTO.getTipoTramite().startsWith(
                                DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/cal/generacion/autorizarRemitir.jsf");
                        } else if (bandejaTareaDTO.getTipoTramite() != null
                                && bandejaTareaDTO.getTipoTramite().startsWith(
                                DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/abogado/autorizarRemitir.jsf");
                        }
                    } else if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.FIRMAR_RESOLUCION.getDescripcion())) {
                        if (bandejaTareaDTO.getTipoTramite() != null
                                && bandejaTareaDTO.getTipoTramite().startsWith(
                                DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/autorizador/autorizarResolucion.jsf");
                        } else if (bandejaTareaDTO.getTipoTramite() != null
                                && bandejaTareaDTO.getTipoTramite().startsWith(
                                DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl(CadenasConstants.GENERAR_RESOLUCION_ACTION_NAVEGATION_AUTORIZACION);
                        }
                    } else if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.ATENDER_OBSERVACION.getDescripcion())) {
                        if (bandejaTareaDTO.getTipoTramite() != null
                                && bandejaTareaDTO.getTipoTramite().startsWith(
                                DiscriminadorConstants.T1_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl("/resources/pages/rrl/op/observacion/atenderObservacionRRL");
                        } else if (bandejaTareaDTO.getTipoTramite() != null
                                && bandejaTareaDTO.getTipoTramite().startsWith(
                                DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                            bandejaTareaDTO.setUrl(CadenasConstants.ATENDER_OBSERVACION_OFICIALIA_ACTION_NAVEGATION);
                        }
                    } else if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.ATENDER_REQ.getDescripcion())) {
                        bandejaTareaDTO
                                .setUrl("/resources/pages/rrl/requerimiento/atenderrequerimiento/atenderRequerimiento.jsf");
                    } else if ((bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.NOTIFICACION_FECHA.getDescripcion()))
                            || (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.NOTIFICACION_REQUERIMIENTO.getDescripcion()) || (bandejaTareaDTO
                            .getNombreTarea().equals(EnumeracionBitacora.NOTIFICACION_RESOLUCION.getDescripcion())))) {
                        bandejaTareaDTO.setUrl("/resources/pages/abogado/registrarFechaNotificacion.jsf");
                    } else if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.CONFIRMAR_NOTIFICACION_REQ.getDescripcion())
                            && bandejaTareaDTO.getTipoTramite().startsWith(
                            DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                        bandejaTareaDTO.setUrl("/resources/pages/autorizador/firmaConfirmarNotificacion.jsf");
                    } else if (bandejaTareaDTO.getNombreTarea().equals(EnumeracionBitacora.ATENDER_REQ_AUTORIDAD.getDescripcion())
                            && bandejaTareaDTO.getTipoTramite().startsWith(
                            DiscriminadorConstants.T2_TIPO_TRAMITE_PREFIJO)) {
                        bandejaTareaDTO.setUrl("/resources/pages/autorizador/atenderRequerimientoAutoridad.jsf");
                    }
                    datosBandejaTareaDTOs.add(bandejaTareaDTO);
                }
            }

        } catch (Exception e) {
            getLogger().error("Error al obtener tareas del usuario", e);
        }
        datosBandejaTareaDTOs = ordenaTareas(datosBandejaTareaDTOs, filtroBandejaTareaDTO.getOrden());
        dataPage.setData(datosBandejaTareaDTOs);
        dataPage.setTotalOfRecords(datosBandejaTareaDTOs.size());
        return dataPage;
    }


}
