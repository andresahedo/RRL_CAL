/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
/**
 * 
 */
package mx.gob.sat.siat.juridica.cal.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.cal.api.ConsultasAutorizacionesCALFacade;
import mx.gob.sat.siat.juridica.cal.dto.ManifiestoDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

/**
 * Bean que implementan la l&oacute;gica de negocio para la consulta
 * de autorizaciones.
 * 
 * @author Softtek
 * 
 */
@NoneScoped
@ManagedBean
public class ConsultasAutorizacionesCALBusiness extends BaseBussinessBean {

    
    /*
     * Lazy    
     */
        public DataPage obtenerDocumentosObligatoriosLazy(String idSol) {
            return consultasAutorizacionesCALFacade.obtenerDocumentosRegistroObligatoriosLazy(idSol);
        }
        
        
        /*
         * LAzy DocumentoOpciones
         */
        public DataPage obtenerDocumentosOpcionalesLazy(String idSol) {
            return consultasAutorizacionesCALFacade.obtenerDocumentosRegistroOpcionalesLazy(idSol);
        }
        
        
    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -1573975247827792823L;
    /**
     * Facade para manejar las consultas de autorizaciones.
     */
    @ManagedProperty("#{consultasAutorizacionesCALFacade}")
    private ConsultasAutorizacionesCALFacade consultasAutorizacionesCALFacade;

    /**
     * Metodo para obtener datos de una solicitud por el idSolicitud.
     * 
     * @param idSolicitud
     * @return
     */
    public SolicitudCALDTO obtenerDatos(Long idSolicitud) {
        return consultasAutorizacionesCALFacade.obtenerDatosSolicitud(idSolicitud);
    }

    /**
     * Metodo para obtener datos de una solicitud por el idSolicitud.
     * 
     * @param idSolicitud
     * @return
     */
    public TramiteDTO obtenerDatosTramite(Long idSolicitud) {
        return consultasAutorizacionesCALFacade.obtenerDatosTramite(idSolicitud);
    }

    /**
     * Metodo que realiza una busqueda de solicitante por el
     * idSolicitud.
     * 
     * @param idSolicitud
     * @return
     */
    public DatosSolicitudDTO buscarSolicitante(Long idSolicitud) {
        return consultasAutorizacionesCALFacade.obtenerDatosSolicitante(idSolicitud);
    }

    /**
     * M&eacute;todo para obtener lista comun de desici&oacute;n SI o
     * NO.
     */
    public List<CatalogoDTO> obtenerListaSiNo() {
        return getConsultasAutorizacionesCALFacade().obtenerListaSiNo();
    }

    public ConsultasAutorizacionesCALFacade getConsultasAutorizacionesCALFacade() {
        return consultasAutorizacionesCALFacade;
    }

    public void setConsultasAutorizacionesCALFacade(ConsultasAutorizacionesCALFacade consultasAutorizacionesCALFacade) {
        this.consultasAutorizacionesCALFacade = consultasAutorizacionesCALFacade;
    }

    /**
     * M&eacute;todo para obtener las autorizades emisoras.
     */
    public List<CatalogoDTO> obtenerAutoridadesEmisoras() {
        return consultasAutorizacionesCALFacade.obtenerAutoridadesEmisoras();
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerMediosDeDefensa() {
        return consultasAutorizacionesCALFacade.obtenerMediosDeDefensa();
    }

    /**
     * M&eacute;todo para obtener lista de sentidos de
     * resolcui&oacute;n.
     */
    public List<CatalogoDTO> obtenerSentidosResolucion() {
        return consultasAutorizacionesCALFacade.obtenerSentidosResolucion();
    }

    /**
     * M&eacute;todo para obtener lista de sentidos de
     * resolcui&oacute;n.
     */
    public List<CatalogoDTO> obtenerSentidosResolucion(String codGen1) {
        return consultasAutorizacionesCALFacade.obtenerCatalogoDPorCodGen1(codGen1);
    }

    /**
     * M&eacute;todo para obtener lista de tipos de
     * clasificaci&oacute;n arancelaria
     */
    public List<CatalogoDTO> obtenerListaTipoClasificacion() {
        return consultasAutorizacionesCALFacade.obtenerListaTipoClasificacion();
    }

    public String obtenerDescripcionModalidad(String idTipoTramite) {
        return consultasAutorizacionesCALFacade.obtenerDescripcionModalidad(idTipoTramite);
    }

    public String obtenerNombreAdministracion(Long idSolicitud) {
        return consultasAutorizacionesCALFacade.obtenerNombreAdministracion(idSolicitud);
    }

    /**
     * Metodo que obtiene los documentos obligatorios correspondiente
     * al idSol y los devuelve en una lista DocumentoDTO.
     * 
     * @param idSol
     * @return
     */
    public List<DocumentoDTO> obtenerDocumentosObligatorios(String idSol) {
        return consultasAutorizacionesCALFacade.obtenerDocumentosRegistroObligatorios(idSol);
    }

    /**
     * Metodo que obtiene los documentos obligatorios correspondiente
     * al idSol y los devuelve en una lista DocumentoDTO.
     * 
     * @param idSol
     * @return
     */
    public List<DocumentoDTO> obtenerDocumentosOpcionales(String idSol) {
        return consultasAutorizacionesCALFacade.obtenerDocumentosRegistroOpcionales(idSol);
    }
    
    public List<ManifiestoDTO> obtenerManifiestosPorModalidad(Integer modalidad) {
        return consultasAutorizacionesCALFacade.obtenerManifiestosPorModalidad(modalidad);
    }


	public SolicitudCALDTO obtenerSolicitudPorId(Long idSolicitud) {
		return consultasAutorizacionesCALFacade.obtenerSolicitudPorId(idSolicitud);
	}

}
