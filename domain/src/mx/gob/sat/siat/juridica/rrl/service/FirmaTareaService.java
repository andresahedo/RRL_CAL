/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoMensajeBPM;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DatosBandejaTarea;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Observacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.RequerimientoTarea;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author softtek
 */
public interface FirmaTareaService extends Serializable {

    /**
     * Metodo para firmar y turnar un asunto
     * 
     * @param numAsunto
     * @param idTarea
     * @param rfcAsignar
     */
    void firmarTurnar(String numAsunto, String idTarea, String rfcAsignar, String rfcUsuario);

    /**
     * Metodo para firmar y remitir un asunto
     * 
     * @param numAsunto
     * @param idTarea
     */
    void firmarRemitir(String numAsunto, Long idTarea, String rfcUsuario);

    /**
     * Metodo para terminar la atencion de una solicitud
     * 
     * @param numAsunto
     * @param idTareaUsuario
     * @param rfcAsignar
     */
    void terminarAtenderSolicitud(String numAsunto, String idTareaUsuario, String rfcAsignar, String rfcUsuario);

    /**
     * Metodo para firmar una autorizacion
     * 
     * @param numAsunto
     * @param idTarea
     * @return
     */
    Long firmarAutorizar(String numAsunto, String idTarea, String rfcUsuario);

    /**
     * Metodo para completrar una tarea de atencion de requerimiento
     * 
     * @param tipoMensajeBPM
     * @param idTarea
     * @param idTipoTransicion
     * @param rfcAsignar
     * @param esAutorizador
     */
    void completarTareaAtenderRequerimiento(String numeroAsunto, TipoMensajeBPM tipoMensajeBPM, String idTarea,
            String idTipoTransicion, boolean esAutorizador, String rfcUsuario, boolean esOficilia);

    /**
     * Metodo para enviar una tarea de requerimiento
     * 
     * @param tipoMensajeBPM
     * @param idTarea
     * @param idTipoTransicion
     * @param rfcAsignar
     * @param reqs
     */
    void enviarTareaRequerimiento(String numAsunto, String idTarea, String rfcAsignar, String rfcUsuario, List<RequerimientoTarea> reqs);

    /**
     * Metodo para completar una tarea de requerimiento
     * 
     * @param tipoMensajeBPM
     * @param idTarea
     * @param idTipoTransicion
     * @param rfcAsignar
     * @param esAutorizador
     * @param tipoRequerimiento
     * @param idRequerimiento
     */
    void completarTareaRequerimientoAutoridad(DatosBandejaTarea datosBandejaTarea, String idTipoTransicion,
            String rfcAsignar, boolean esAutorizador, String tipoRequerimiento, String rfcUsuario,
            String claveUnidadAdministrativa);

    /**
     * Metodo para completar una tarea de requerimiento
     * 
     * @param tipoMensajeBPM
     * @param idTarea
     * @param idTipoTransicion
     * @param rfcAsignar
     * @param esAutorizador
     * @param tipoRequerimiento
     * @param idRequerimiento
     */
    void completarTareaRequerimientoPromovente(TipoMensajeBPM tipoMensajeBPM, DatosBandejaTarea datosBandejaTarea, String idTipoTransicion,
            String rfcAsignar, boolean esAutorizador, String rfcUsuario, String tipoReq);

    /**
     * Metodo para firmar la confirmacion de una notificacion
     * 
     * @param numAsunto
     * @param idTarea
     * @param idTareaBPM
     * @param idRequerimiento
     */
    void firmarConfirmarNotificacion(String numAsunto, String idTarea, Long idRequerimiento, String rfcUsuario);

    void firmarCapturaFechaNotificacion(String numAsunto, String idTarea, Long idTareaBPM, Boolean blnResolucion,
            String rfcUsuario, Long idNotificacion, Long idRequerimiento);

    void completarTareaAtenderRequerimientoAutoridad(TipoMensajeBPM tipoMensajeBPM, String idTarea, String rfcUsuario,
            String numAsunto, String nombreTarea);

    Observacion observar(String idTarea, String rfc, Observacion observacion);

    void completarTareaAtenderObservacion(String numeroAsunto, Long idObservacion, String rfcUsuario, String idTarea);

    void actualizaDatosBP(int idInstancia, EstadoTramite estadoTramite);

    void firmarReasignar(String idTarea, String rfcUsuario, String rfcAsignar, String numAsunto, String rfcPropietario, Long idInstancia);

}
