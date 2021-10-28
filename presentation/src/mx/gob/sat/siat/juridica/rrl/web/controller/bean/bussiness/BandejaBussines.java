/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

import mx.gob.sat.siat.juridica.base.api.ConsultaRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.api.RegistroRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.api.TareaFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.DocumentoOficialDTO;
import mx.gob.sat.siat.juridica.base.dto.FirmaDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.api.BPMFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

/**
 * Bean para conectar con el backend, atiende todas las peticiones de
 * la bandeja.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "bandejaBussiness")
@NoneScoped
public class BandejaBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 2868691474413525562L;

    /**
     * Propiedad que implementa los servicios expuestos por BPMFacade.
     */
    @ManagedProperty("#{bpmFacade}")
    private BPMFacade bpmFacade;

    @ManagedProperty("#{tareaFacade}")
    private TareaFacade tareaFacade;
    
    /** Facade para capturar la solicitud */
    @ManagedProperty("#{registroRecursoRevocacionFacade}")
    private RegistroRecursoRevocacionFacade registroRecursoRevocacionFacade;
    
    /** Facade para consultar recursos de revocacion. */
    @ManagedProperty("#{consultaRecursoRevocacionFacade}")
    private ConsultaRecursoRevocacionFacade consultaRecursoRevocacionFacade;
    
    /**
     * Metodo que obtiene los datos de bandeja de BD
     * @param bandejaTareaDTO
     * @return
     */
    public DataPage obtenerDatosBandejaDB(FiltroBandejaTareaDTO bandejaTareaDTO){
        getLogger().debug("getting bandeja data with facade ");
        return tareaFacade.buscarTareasporUsuario(bandejaTareaDTO);
    }

    public DataPage obtenerDatosBandejaDBR(FiltroBandejaTareaDTO bandejaTareaDTO){
        getLogger().debug("getting bandeja data with facade ");
        return tareaFacade.buscarTareasporUsuarioReasignacion(bandejaTareaDTO);
    }

    /**
     * Metodo que obtiene el catalodo de estados.
     * 
     * @return
     */
    public List<CatalogoDTO> obtenerCatalogoEstados() {
        getLogger().debug("getting obtenerListaEstados data with facade {}", bpmFacade);
        List<CatalogoDTO> catalogoDTOs = bpmFacade.obtenerCatalogoEstados();

        return catalogoDTOs;

    }

    public RegistroRecursoRevocacionFacade getRegistroRecursoRevocacionFacade() {
		return registroRecursoRevocacionFacade;
	}

	public void setRegistroRecursoRevocacionFacade(RegistroRecursoRevocacionFacade registroRecursoRevocacionFacade) {
		this.registroRecursoRevocacionFacade = registroRecursoRevocacionFacade;
	}

	public ConsultaRecursoRevocacionFacade getConsultaRecursoRevocacionFacade() {
		return consultaRecursoRevocacionFacade;
	}

	public void setConsultaRecursoRevocacionFacade(ConsultaRecursoRevocacionFacade consultaRecursoRevocacionFacade) {
		this.consultaRecursoRevocacionFacade = consultaRecursoRevocacionFacade;
	}

	/**
     * 
     * @return bpmFacade
     */
    public BPMFacade getBpmFacade() {
        return bpmFacade;
    }

    /**
     * 
     * @param bpmFacade
     *            el bpmFacade a fijar.
     */
    public void setBpmFacade(BPMFacade bpmFacade) {
        this.bpmFacade = bpmFacade;
    }

    public  TareaFacade getTareaFacade() {return  tareaFacade; }

    public void setTareaFacade(TareaFacade tareaFacade) { this.tareaFacade = tareaFacade; }

    public SolicitudDTO obtenerSolicitud(Long idSolicitud) {
        return getBpmFacade().obtenerSolicitud(idSolicitud);
    }

	public boolean tieneDocumentosAnexados(String idSolicitud) {
		return tareaFacade.tieneDocumentosAnexados(idSolicitud);
	}

	public FirmaDTO obtenSelloPromocionSIAT(String numeroAsunto, Long idSolicitud, Date fechaFirma) {
		return registroRecursoRevocacionFacade.obtenSelloPromocionSIAT(numeroAsunto, idSolicitud, fechaFirma);
	}

	public FirmaDTO obtieneFirma(DatosBandejaTareaDTO datoSelected) {
		return tareaFacade.obtieneFirma(datoSelected);
	}

	public List<DocumentoOficialDTO> obtenerDocumentosOficialesTipo(String idTipoTramite, String idTipoDocumentoOficial){
		return consultaRecursoRevocacionFacade.obtenerDocumentoOficialesTipo(idTipoTramite, idTipoDocumentoOficial);
	}

	public void cambiarEstadofirmarDocumentos(Long idSolicitud, String estadoActual, String estadoNuevo) {
		registroRecursoRevocacionFacade.cambiarEstadofirmarDocumentos(idSolicitud, estadoActual, estadoNuevo);
	}
}
