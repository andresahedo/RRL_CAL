/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

import mx.gob.sat.siat.juridica.auditoria.annotation.Audit;
import mx.gob.sat.siat.juridica.auditoria.bussiness.BaseAuditoriaBussinessBean;
import mx.gob.sat.siat.juridica.base.api.FirmarTareaFacade;
import mx.gob.sat.siat.juridica.base.constantes.MovimientosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.SistemasConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.MotivoRechazoDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.TurnarDTO;
import mx.gob.sat.siat.juridica.rrl.api.FirmarAutorizacionResolucionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;

/**
 * Bussines que implementan la l&oacute;gica de negocio para la firma
 * de una tarea
 * 
 * @author Softtek
 * 
 */
@NoneScoped
@ManagedBean(name = "firmarTareaBussines")
public class FirmarTareaBussines extends BaseAuditoriaBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -2835362955009523071L;

    /** Facade para firmar tarea */
    @ManagedProperty(value = "#{firmarTareaFacade}")
    private FirmarTareaFacade firmarTareaFacade;

    @ManagedProperty("#{firmarAutorizacionResolucionFacade}")
    private FirmarAutorizacionResolucionFacade firmarAutorizacionResolucionFacade;

    /**
     * M&eacute;todo para firmar la tarea de turnar recurso revocacion
     * 
     * @param turnarDTO
     */
    public void
            firmarTurnar(String numAsunto, String idTarea, String rfcAsignar, String rfcUsuario, TurnarDTO turnarDTO) {
        getFirmarTareaFacade().firmarTurnar(numAsunto, idTarea, rfcAsignar, rfcUsuario, turnarDTO);
    }

    /**
     * M&eacute;todo para firmar la tarea de reasignar recurso
     * revocacion
     * @param long1 
     */
    @Audit(identificadorProceso = ProcesosConstantes.PROCESO_EMPLEADO_REASIGNAR_RRL,
            movimiento = MovimientosConstantes.MOVIMIENTO_ADMIN_REASIGNACION,
            claveSistema = SistemasConstantes.SISTEMA_RRL)
    public void firmarReasignar(String idTarea, String rfcUsuario, String rfcAsignar, String numeroAsunto,
            String rfcPropietario, Long idInstancia) {
        getFirmarTareaFacade().firmarReasignar(idTarea, rfcUsuario, rfcAsignar, numeroAsunto, rfcPropietario, idInstancia);
        guardarObservacionesBitacora("Asunto Reasignado:" + numeroAsunto);
    }

    /**
     * M&eacute;todo para firmar la tarea de turnar recurso revocacion
     */
    public void firmaAutorizar(FirmaDTO firmaDTO, String numAsunto, String idTarea, String rfcUsuario) {
        getFirmarAutorizacionResolucionFacade().guardarFirma(firmaDTO,
                getFirmarTareaFacade().firmarAutorizar(numAsunto, idTarea, rfcUsuario));
    }

    /**
     * Metodo para remitir la firma.
     * 
     * @param numAsunto
     * @param idTarea
     */
    public void firmarRemitir(FirmaDTO firma, String numAsunto, Long idTarea, String rfcUsuario) {
        getFirmarAutorizacionResolucionFacade().guardarFirmaRemitir(firma, numAsunto, idTarea, rfcUsuario);

    }

    /**
     * M&eacute;todo para firmar la tarea de confirmar notificacion
     * 
     * @param idRequerimiento
     */
    public void firmarConfirmarNotificacion(String numAsunto, Long idTarea, Long idTareaBPM, Long idRequerimiento,
            String rfcUsuario) {
        getFirmarTareaFacade().firmarConfirmarNotificacion(numAsunto, idTarea.toString(), idTareaBPM, idRequerimiento,
                rfcUsuario);
    }

    /**
     * 
     * @return firmarTareaFacade
     */
    public FirmarTareaFacade getFirmarTareaFacade() {
        return firmarTareaFacade;
    }

    /**
     * 
     * @param firmarTareaFacade
     *            a fijar
     */
    public void setFirmarTareaFacade(FirmarTareaFacade firmarTareaFacade) {
        this.firmarTareaFacade = firmarTareaFacade;
    }

    /**
     * Metodo para guardar DocOficial.
     * 
     * @param numAsunto
     * @param documentoOficial
     * @return
     */
    public Long guardarDocOficial(String numAsunto, DocumentoOficial documentoOficial) {

        return getFirmarTareaFacade().guardarDocOficial(numAsunto, documentoOficial);

    }

    /**
     * M&eacute;todo para firmar la tarea de confirmar notificacion
     * 
     * @param idRequerimiento
     */
    public void firmarCapturaFechaNotificacion(FirmaDTO firma, DatosBandejaTareaDTO datosBandejaTareaDTO, String rfc,
            boolean blnResolucion) {
        String numeroAsunto = datosBandejaTareaDTO.getNumeroAsunto();
        Long idNotificacion = datosBandejaTareaDTO.getIdNotificacion();
        String idTarea = String.valueOf(datosBandejaTareaDTO.getIdTareaUsuario());
        Long idTareaBPM = datosBandejaTareaDTO.getIdTarea();
        Long idRequerimiento = datosBandejaTareaDTO.getIdRequerimiento();

        getFirmarTareaFacade().firmarCapturaFechaNotificacion(numeroAsunto, idTarea, idTareaBPM, blnResolucion, rfc,
                idNotificacion, idRequerimiento);
        getFirmarAutorizacionResolucionFacade().guardarFirmaNotificacion(firma, idNotificacion);
    }

    public void firmarCapturaFechaNotificacion(String numAsunto, Long idTarea, Long idTareaBPM, Boolean blnResolucion,
            String rfcUsuario) {

    }

    /**
     * Metodo que firma documento correspondiente al paramtro
     * idSolicitud que reciba.
     * 
     * @param idSolicitud
     * @param firma
     */
    public void firmarDocumentos(Long idSolicitud, FirmaDTO firma) {
        getFirmarTareaFacade().firmarDocumentos(idSolicitud, firma);
    }

    public FirmarAutorizacionResolucionFacade getFirmarAutorizacionResolucionFacade() {
        return firmarAutorizacionResolucionFacade;
    }

    public void setFirmarAutorizacionResolucionFacade(
            FirmarAutorizacionResolucionFacade firmarAutorizacionResolucionFacade) {
        this.firmarAutorizacionResolucionFacade = firmarAutorizacionResolucionFacade;
    }

    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return firmarAutorizacionResolucionFacade.generaCadenaOriginal(idSolicitud, fechaFirma);
    }

    public String generaCadenaOriginalRemitir(Long idSolicitud, String rfcAutorizador) {
        return firmarAutorizacionResolucionFacade.generaCadenaOriginalAutorizarRemitir(idSolicitud, rfcAutorizador);
    }

    public FirmaDTO obtenSelloSIAT(String cadenaOriginal) {
        return getFirmarTareaFacade().obtenSelloSIAT(cadenaOriginal);
    }

    public void rechazarAsunto(MotivoRechazoDTO motivosDto) {
        getFirmarTareaFacade().rechazoAsunto(motivosDto);
    }

    public void rechazarDocumento(MotivoRechazoDTO motivosDto) {
        getFirmarTareaFacade().rechazoDocumento(motivosDto);
    }

}
