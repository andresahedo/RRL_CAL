/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.service;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoSolicitud;
import mx.gob.sat.siat.juridica.base.dao.domain.model.*;
import mx.gob.sat.siat.juridica.bpm.excepcion.TareaInicialException;
import mx.gob.sat.siat.juridica.bpm.excepcion.TereaSinUsuarioAsignadoException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 */
public interface SolicitudService extends Serializable {

    /**
     * Metodo para obtener una solicitud por id
     * 
     * @param idSolicitud
     * @return Solicitud
     */
    Solicitud obtenerSolicitudporId(Long idSolicitud);

    /**
     * Metodo para iniciar un tramite
     * 
     * @param mensajeTarea
     */
    void iniciaTramite(MensajeBPM mensajeBPM, SolicitudDatosGenerales sol, String rfcResponsable);

    /**
     * Metodo para generar la cadena original
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return cadena
     */
    String generarCadenaOriginal(long idSolicitud, Date fechaFirma);

    /**
     * Metodo para generar la cadena original del registro de la
     * promocion
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return cadena
     */
    String generarCadenaOriginalRegistroPromocionRRL(long idSolicitud, Date fechaFirma);

    SolicitudDatosGenerales obtenerSolicitudDatosGeneralesPorId(long idSolicitud);

    /**
     * Metodo para generar un mensaje de inicio
     * 
     * @param tramite
     * @param rfcSolicitante
     * @param Autorizador
     * @return mensaje
     */

    /**
     * Metodo para generar un tramite
     * 
     * @param idSolicitud
     * @param firma
     * @param usuario
     * @param ceritifcadoUtilizado
     * @param prefijoTramite
     * @param idTipoTramite
     * @return tramite
     */
    Tramite generarTramite(long idSolicitud, Firma firmas, Object ceritifcadoUtilizado, String prefijoTramite,
            String idTipoTramite);

    /**
     * Metodo para guardar un tramite
     * 
     * @param tramite
     */
    void guardarTramite(Tramite tramite);

    /**
     * Metodo para guardar una solicitud
     * 
     * @param solicitud
     * @return Solicitud
     */
    Solicitud guardarSolicitud(Solicitud solicitud);

    /**
     * Metodo para consultar la autorizacion de una solicitud
     * 
     * @param idSolicitud
     * @return Solicitud
     */
    SolicitudDatosGenerales obtenerSolicitudConsultaAutorizacionporId(Long idSolicitud);

    /**
     * Metodo para actualizar una unidad administrativa
     * 
     * @param sol
     * @param claveUnidad
     * @return Solicitud
     */
    Solicitud actualizarUnidadAdminBalanceo(Solicitud sol, String claveUnidad);

    /**
     * Metodo para firmar documentos
     * 
     * @param idSolicitud
     */

    void firmarDocumentos(Long idSolicitud, Firma firma, boolean complementario);

    void sellaDocumentos(Long idSolicitud);

    /**
     * Metodo para obtener una lista de solicitudes por filtros
     * 
     * @param idSolicitud
     * @param fechaIni
     * @param fechaFin
     * @param rfc
     * @return Lista de Solicitudes
     */
    List<Solicitud> obtenerSolicitudesPorFiltros(Long idSolicitud, Date fechaIni, Date fechaFin, String rfc);

    void actualizaEstadoSolicitud(Long idSolicitud, EstadoSolicitud estadoSolicitud);

    String generarCadenaOriginalAtenderReq(long idSolicitud, Date fechaFirma);

    String generarCadenaOriginalDocumentosAdicionales(long idSolicitud, Date fechaFirma);

    Firma generaFirmaPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma);

    Firma generaFirmaRegistroPromocionSIAT(String numAsunto, long idSolicitud, Date fechaFirma);

    void modificarSolicitudRRL(SolicitudDatosGenerales solicitud);

    void firmarDocumentosRegistro(Long idSolicitud, Firma firma, boolean complementario);

    mx.gob.sat.siat.juridica.bpm.dao.domain.Tramite generarMensajeInicio(SolicitudDatosGenerales sol, Tramite tramite,
            String usuario, String promovente, String administrador);
            
    List<Object>  firmarSolicitud(long idSolicitud, Firma firma, String usuario, String rfcContribuyente,
            Object ceritifcadoUtilizado) throws TareaInicialException, TereaSinUsuarioAsignadoException;
}
