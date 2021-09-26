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
package mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.ca.api.BandejaConsultasAutorizacionesFacade;
import mx.gob.sat.siat.juridica.rrl.api.BPMFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

/**
 * Bean que implementa la logica de negocio para la bandeja de
 * consulta de autorizaciones.
 * 
 * @author Softtek
 * 
 */
@NoneScoped
@ManagedBean
public class BandejaConsultasAutorizacionesBussines extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -2563989429907054594L;
    /**
     * Propiedad BPMFacade utilizada para obtener los catalogos.
     */
    @ManagedProperty("#{bpmFacade}")
    private BPMFacade bpmFacade;
    /**
     * Facade para manejar las consultas de autorizaciones.
     */
    @ManagedProperty("#{bandejaConsultasAutorizacionesFacade}")
    private BandejaConsultasAutorizacionesFacade bandejaConsultasAutorizacionesFacade;

    /**
     * Metodo que obtiene una promocion de acuerdo al filtro de
     * busqueda.
     * 
     * @param filtroBandeja
     * @return
     */
    public List<DatosBandejaTareaDTO> obtenerPromocion(FiltroBandejaTareaDTO filtroBandeja) {
        return bandejaConsultasAutorizacionesFacade.obtenerTramites(filtroBandeja);
    }

    /**
     * Metodo para obtener el catalogo de estados.
     * 
     * @return
     */
    public List<CatalogoDTO> obtenerCatalogoEstados() {
        List<CatalogoDTO> catalogoDTOs = bpmFacade.obtenerCatalogoEstados();

        return catalogoDTOs;

    }

    /**
     * Metodo para obtener el catalogo de los tipos de tramites.
     * 
     * @return
     */
    public List<CatalogoDTO> obtenerCatalogoTipoTramites() {
        List<CatalogoDTO> catalogoDTOs = bandejaConsultasAutorizacionesFacade.obtenerCatalogoTipoTramites("02");

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
     *            la bpmFacade a fijar.
     */
    public void setBpmFacade(BPMFacade bpmFacade) {
        this.bpmFacade = bpmFacade;
    }

    /**
     * 
     * @return bandejaConsultasAutorizacionesFacade.
     */
    public BandejaConsultasAutorizacionesFacade getBandejaConsultasAutorizacionesFacade() {
        return bandejaConsultasAutorizacionesFacade;
    }

    /**
     * 
     * @param bandejaConsultasAutorizacionesFacade
     *            la bandejaConsultasAutorizacionesFacade a fijar.
     */
    public void setBandejaConsultasAutorizacionesFacade(
            BandejaConsultasAutorizacionesFacade bandejaConsultasAutorizacionesFacade) {
        this.bandejaConsultasAutorizacionesFacade = bandejaConsultasAutorizacionesFacade;
    }

}
