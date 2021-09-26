/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.api;

import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.ModalidadesDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.rrl.dto.SolicitudConsultaAutorizacionDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Softtek
 * 
 */
public interface RegistroSolicitudConsultaAutorizacionFacade extends BaseFacade {

    /**
     * M&eacute;todo para preparar la solicitud que se desea capturar.
     * 
     * @param rfc
     * @param modalidad
     * @return SolicitudConsultaAutorizacionDTO
     * @throws SolicitudNoGuardadaException
     */
    SolicitudConsultaAutorizacionDTO iniciarRegistroSolicitud(String rfc, String modalidad)
            throws SolicitudNoGuardadaException;

    /**
     * M&eacute;todo para obtener las autorizades emisoras.
     * 
     * @return Lista CatalogoDTO
     */
    List<CatalogoDTO> obtenerAutoridadesEmisoras();

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o
     * NO.
     * 
     * @return Lista CatalogoDTO
     */
    List<CatalogoDTO> obtenerListaSiNo();

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     * 
     * @return Lista CatalogoDTO
     */
    List<CatalogoDTO> obtenerMediosDeDefensa();

    /**
     * M&eacute;todo para obtener lista de sentidos de
     * resolcui&oacute;n.
     * 
     * @return Lista CatalogoDTO
     */
    List<CatalogoDTO> obtenerSentidosResolucion();

    /**
     * M&eacute;todo para obtener modalidades de consulta.
     * 
     * @return Lista ModalidadesDTO
     */
    List<ModalidadesDTO> obtenerModalidadesConsulta(String tipoPersona);

    /**
     * M&eacute;todo para obtener modalidades de autorizaci&oacute;n.
     * 
     * @return Lista ModalidadesDTO
     */
    List<ModalidadesDTO> obtenerModalidadesAutorizacion(String tipoPersona);

    /**
     * M&eacute;todo para obtener documentos obligatorios por tipo
     * tramite.
     * 
     * @param idTipoTramite
     * @return Lista DocumentoDTO
     */
    List<DocumentoDTO> obtenerDocumentosObligatorios(Integer idTipoTramite, String tipoClasificacion);

    /**
     * M&eacute;todo para obtener documentos opcionales por tipo
     * tramite.
     * 
     * @param idTipoTramite
     * @return Lista DocumentoDTO
     */
    List<DocumentoDTO> obtenerDocumentosOpcionales(Integer idTipoTramite, String tipoClasificacion);

    /**
     * M&eacute;todo para guardar la solicitud.
     * 
     * @param solicitud
     * @return SolicitudConsultaAutorizacionDTO
     */
    SolicitudConsultaAutorizacionDTO guardarSolicitud(SolicitudConsultaAutorizacionDTO solicitud)
            throws SolicitudNoGuardadaException;

    InputStream descargarArchivo(String ruta);

    void guardarDocumentosSolicitud(SolicitudConsultaAutorizacionDTO solicitud, List<DocumentoDTO> listaDocumentos)
            throws ArchivoNoGuardadoException;

    String generaCadenaOriginal(long idSolicitud, Date fechaFirma);

    String firmaSolicitud(SolicitudConsultaAutorizacionDTO solicitud, FirmaDTO firma);

}
