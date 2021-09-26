/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.api;

import java.io.Serializable;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.MotivoRechazoDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.TurnarDTO;

/**
 * Fachada Base que proporciona soporte a todas las fachadas de
 * juridica.
 * 
 * @author Softtek
 * 
 */
public interface FirmarTareaFacade extends Serializable {

    void
            firmarTurnar(String numAsunto, String idTarea, String rfcAsignar, String rfcUsuario, TurnarDTO turnarDTO);

    void firmarRemitir(String numAsunto, Long idTarea, String rfcUsuario);

    void atender(String numAsunto, String idTarea, String rfcAsignar, String rfcUsuario, String numeroAsunto);

    Long firmarAutorizar(String numAsunto, String idTarea, String rfcUsuario);

    Long guardarDocOficial(String numFolio, DocumentoOficial documentoOficial);

    void firmarConfirmarNotificacion(String numAsunto, String idTarea, Long idTareaBPM, Long idRequerimiento,
            String rfcUsuario);

    void firmarCapturaFechaNotificacion(String numAsunto, String idTarea, Long idTareaBPM, Boolean blnResolucion,
            String rfcUsuario, Long idNotificacion, Long idRequerimiento);

    void firmarDocumentos(Long idSolicitud, FirmaDTO firma);

    FirmaDTO obtenSelloSIAT(String cadenaOriginal);

    void rechazoAsunto(MotivoRechazoDTO motivosDto);

    void rechazoDocumento(MotivoRechazoDTO motivosDto);

    void firmarReasignar(String idTarea, String rfcUsuario, String rfcAsignar, String numeroAsunto,
            String rfcPropietario, Long idInstancia);

}
