/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.ModalidadesDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.ca.api.RegistroSolicitudConsultaAutorizacionFacade;
import mx.gob.sat.siat.juridica.rrl.dto.SolicitudConsultaAutorizacionDTO;
import mx.gob.sat.siat.juridica.rrl.util.exception.ArchivoNoGuardadoException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Bean para conectar con el backend, atiende todas las peticiones del
 * registro de la solicitud.
 * 
 * @author Softtek
 * 
 */
@ManagedBean(name = "registroSolicitudConsultaAutorizacionBussines")
@NoneScoped
public class RegistroSolicitudConsultaAutorizacionBussines extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -1231013432247486261L;
    /** Facade para atender el registro de la solicitud. */
    @ManagedProperty("#{registroSolicitudConsultaAutorizacionFacade}")
    private RegistroSolicitudConsultaAutorizacionFacade registroSolicitudConsultaAutorizacionFacade;

    /**
     * M&eacute;todo para obtener las autorizades emisoras.
     */
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        return getRegistroSolicitudConsultaAutorizacionFacade().obtenerAutoridadesEmisoras();
    }

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o
     * NO.
     */
    public List<CatalogoDTO> obtenerListaSiNo() {
        return getRegistroSolicitudConsultaAutorizacionFacade().obtenerListaSiNo();
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerMediosDeDefensa() {
        return getRegistroSolicitudConsultaAutorizacionFacade().obtenerMediosDeDefensa();
    }

    /**
     * M&eacute;todo para obtener lista de sentidos de
     * resolcui&oacute;n.
     */
    public List<CatalogoDTO> obtenerSentidosResolucion() {
        return getRegistroSolicitudConsultaAutorizacionFacade().obtenerSentidosResolucion();
    }

    /**
     * M&eacute;todo para obtener modalidades de consulta.
     * 
     * @return Lista ModalidadesDTO
     */
    public List<ModalidadesDTO> obtenerModalidadesConsulta(String tipoPersona) {
        return getRegistroSolicitudConsultaAutorizacionFacade().obtenerModalidadesConsulta(tipoPersona);
    }

    /**
     * M&eacute;todo para obtener modalidades de autorizaci&oacute;n.
     * 
     * @return Lista ModalidadesDTO
     */
    public List<ModalidadesDTO> obtenerModalidadesAutorizacion(String tipoPersona) {
        return getRegistroSolicitudConsultaAutorizacionFacade().obtenerModalidadesAutorizacion(tipoPersona);
    }

    /**
     * M&eacute;todo para obtener documentos obligatorios por tipo
     * tramite.
     * 
     * @param idTipoTramite
     * @return Lista DocumentoDTO
     */
    public List<DocumentoDTO> obtenerDocumentosObligatorios(Integer idTipoTramite, String tipoClasificacion) {
        return getRegistroSolicitudConsultaAutorizacionFacade().obtenerDocumentosObligatorios(idTipoTramite,
                tipoClasificacion);
    }

    /**
     * M&eacute;todo para obtener documentos opcionales por tipo
     * tramite.
     * 
     * @param idTipoTramite
     * @return Lista DocumentoDTO
     */
    public List<DocumentoDTO> obtenerDocumentosOpcionales(Integer idTipoTramite, String tipoClasificacion) {
        return getRegistroSolicitudConsultaAutorizacionFacade().obtenerDocumentosOpcionales(idTipoTramite,
                tipoClasificacion);
    }

    /**
     * M&eacute;todo para preparar la solicitud que se desea capturar.
     * 
     * @param rfc
     * @param modalidad
     * @return SolicitudConsultaAutorizacionDTO
     * @throws SolicitudNoGuardadaException
     */
    public SolicitudConsultaAutorizacionDTO iniciarRegistroSolicitud(String rfc, String modalidad)
            throws SolicitudNoGuardadaException {
        return getRegistroSolicitudConsultaAutorizacionFacade().iniciarRegistroSolicitud(rfc, modalidad);
    }

    /**
     * M&eacute;todo para guardar la solicitud.
     * 
     * @param solicitud
     * @return SolicitudConsultaAutorizacionDTO
     */
    public SolicitudConsultaAutorizacionDTO guardarSolicitud(SolicitudConsultaAutorizacionDTO solicitud)
            throws SolicitudNoGuardadaException {
        return getRegistroSolicitudConsultaAutorizacionFacade().guardarSolicitud(solicitud);
    }

    /**
     * M&eacute;todo para descargar archivos
     * 
     * @param documento
     * @return
     */
    public InputStream descargarArchivo(DocumentoDTO documento) {
        return getRegistroSolicitudConsultaAutorizacionFacade().descargarArchivo(documento.getRuta());
    }

    /**
     * Metodo para guardar los documentos de una solicitud
     * 
     * @param solicitud
     * @param listaDocumentos
     * @throws ArchivoNoGuardadoException
     */
    public void guardarDocumentosSolicitud(SolicitudConsultaAutorizacionDTO solicitud,
            List<DocumentoDTO> listaDocumentos) throws ArchivoNoGuardadoException {
        getRegistroSolicitudConsultaAutorizacionFacade().guardarDocumentosSolicitud(solicitud, listaDocumentos);
    }

    /**
     * Metodo que genera cadena original.
     * 
     * @param idSolicitud
     * @param fechaFirma
     * @return
     */
    public String generaCadenaOriginal(long idSolicitud, Date fechaFirma) {
        return getRegistroSolicitudConsultaAutorizacionFacade().generaCadenaOriginal(idSolicitud, fechaFirma);
    }

    /**
     * Metodo para firmar la solicitud.
     * 
     * @param solicitud
     * @param firma
     * @return
     */
    public String firmaSolicitud(SolicitudConsultaAutorizacionDTO solicitud, FirmaDTO firma) {
        return getRegistroSolicitudConsultaAutorizacionFacade().firmaSolicitud(solicitud, firma);
    }

    /**
     * 
     * @return registroSolicitudConsultaAutorizacionFacade
     */
    public RegistroSolicitudConsultaAutorizacionFacade getRegistroSolicitudConsultaAutorizacionFacade() {
        return registroSolicitudConsultaAutorizacionFacade;
    }

    /**
     * 
     * @param registroSolicitudConsultaAutorizacionFacade
     *            a fijar
     */
    public void setRegistroSolicitudConsultaAutorizacionFacade(
            RegistroSolicitudConsultaAutorizacionFacade registroSolicitudConsultaAutorizacionFacade) {
        this.registroSolicitudConsultaAutorizacionFacade = registroSolicitudConsultaAutorizacionFacade;
    }

}
