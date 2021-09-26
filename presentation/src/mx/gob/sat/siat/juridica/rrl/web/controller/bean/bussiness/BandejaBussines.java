/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.TareaFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;
import mx.gob.sat.siat.juridica.rrl.api.BPMFacade;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

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
}
