/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service;

import mx.gob.sat.siat.juridica.base.dao.domain.model.DocumentoOficial;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Notificacion;
import mx.gob.sat.siat.juridica.base.dao.domain.model.NotificacionRequerimiento;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 */
public interface RegistrarFechaNotificacionService extends Serializable {

    Long guardarFechaNotificacion(String numAsunto, Boolean blnResolucion, Date fechaNotificacion, Long idNotificacion);

    NotificacionRequerimiento obtenerNotificacionRequerimientoByReqId(Long idRequerimiento);

    Boolean validarFechaNotificacion(Date fechaNotificacion, String numeroAsunto);

    Date buscarFechaNotificacionByIdTramite(String numeroAsunto);

    List<DocumentoOficial> buscarTiposDocumentosPorIdTramite(String numAsunto);

    Date buscarFechaNotificacionByIdRequerimiento(Long idRequerimiento);

    List<DocumentoOficial> buscarTiposDocumentosAnexadosPorIdTramite(String numAsunto);
    
    Notificacion buscarNotificacionParcial(String numAsunto, Boolean blnResolucion);
}
