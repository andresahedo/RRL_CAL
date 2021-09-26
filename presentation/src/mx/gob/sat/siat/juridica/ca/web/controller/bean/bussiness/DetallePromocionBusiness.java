/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.ca.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.ca.api.DetallePromocionFacade;
import mx.gob.sat.siat.juridica.rrl.api.BPMFacade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

/**
 * Bean que implementa la logica de negocio para los detalles de
 * promociones.
 * 
 * @author softtek
 * 
 */
@NoneScoped
@ManagedBean
public class DetallePromocionBusiness extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 3031402712728782460L;
    /**
     * Propiedad BPMFacade utilizada para obtener los catalogos.
     */
    @ManagedProperty("#{bpmFacade}")
    private BPMFacade bpmFacade;
    /**
     * Facade para manejar el detalle de promocion.
     */
    @ManagedProperty("#{detallePromocionFacade}")
    private DetallePromocionFacade detallePromocionFacade;

    /**
     * Metodo que obtiene los tramites por el numero de asunto que
     * recibe como parametro.
     * 
     * @param numAsunto
     * @return
     */
    public TramiteDTO obtenerTramite(String numAsunto) {
        return detallePromocionFacade.obtenerTramite(numAsunto);
    }

    /**
     * Metodo para obtener el catalogo de estados.
     * 
     * @return catalogoDTOs
     */
    public List<CatalogoDTO> obtenerCatalogoEstados() {
        List<CatalogoDTO> catalogoDTOs = bpmFacade.obtenerCatalogoEstados();

        return catalogoDTOs;

    }

    /**
     * Metodo para modificar un tramite.
     * 
     * @param tramiteDTO
     * @throws SolicitudNoGuardadaException
     */
    public void modificarTramite(TramiteDTO tramiteDTO) throws SolicitudNoGuardadaException {
        detallePromocionFacade.modificarTramite(tramiteDTO);
    }

    /**
     * 
     * @return detallePromocionFacade
     */
    public DetallePromocionFacade getDetallePromocionFacade() {
        return detallePromocionFacade;
    }

    /**
     * 
     * @param detallePromocionFacade
     *            el detallePromocionFacade a fijar.
     */
    public void setDetallePromocionFacade(DetallePromocionFacade detallePromocionFacade) {
        this.detallePromocionFacade = detallePromocionFacade;
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

}
