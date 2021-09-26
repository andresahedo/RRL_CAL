/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.Requerimiento;
import mx.gob.sat.siat.juridica.base.dao.domain.model.TramiteReprocesar;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dao.domain.model.DatosCorreoNotificacion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
public interface EnviarCorreoService extends Serializable {

    /**
     * Metodo para enviar un correo
     * 
     * @param numeroAsunto
     * @param rfc
     */
    void enviarCorreo(String numeroAsunto, String rfc, String tarea);

    void enviarCorreoTareaPendiente(String numeroAsunto, String rfc, String tarea);

    void enviarCorreoAdministracion(String numeroAsunto, String admin);

    void enviarCorreoDocAdicionalesAbogado(String numeroAsunto, String rfc);

    void enviarCorreoDocAdicionalesAdministracion(String numeroAsunto, String admin);

    void enviarCorreoNotificacionActualizarMovimiento(DatosCorreoNotificacion datosCorreo);

    void enviarCorreoCumplimentacionRequerimiento(Requerimiento requerimiento, String unidadAdmin, String numAsunto);

    void enviarCorreoRechazoAsunto(String rfc, String numAsunto, Long idSolicitud, String elemento,
            Date fechaRecepcion, String nombreRechazo);
    
    void enviarCorreoEstatusReproceso(List<TramiteReprocesar> tramites);

}
