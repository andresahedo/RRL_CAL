/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.base.dto.TipoTramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;
import mx.gob.sat.siat.juridica.base.dto.UnidadAdministrativaDTO;
import mx.gob.sat.siat.juridica.cal.api.GeneracionResolucionFacade;
import mx.gob.sat.siat.juridica.cal.dto.BienResarcimientoDTO;
import mx.gob.sat.siat.juridica.cal.dto.ResolucionDatosGeneradosDTO;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

@ManagedBean(name = "generacionResolucionBussines")
@NoneScoped
public class GeneracionResolucionCALBussines extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = 264314855084549637L;
    @ManagedProperty("#{generacionResolucionFacade}")
    private GeneracionResolucionFacade generacionResolucionFacade;

    /**
     * Method to update data from atenci&oacute;n promoci&oacute;n.
     * 
     * @param filtroBandejaTareaDTO
     * @param userProfileDTO
     */
    public List<CatalogoDTO> obtenerResolucionCatalog(String type) {
        return generacionResolucionFacade.obtenerResolucionCatalog(type);
    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerMediosDeDefensa() {
        return generacionResolucionFacade.obtenerMediosDeDefensa();
    }

    public void rechazarResolucion(String numeroAsunto, Long idTarea, String rfc, Long idSolicitud) {
        generacionResolucionFacade.rechazarResolucion(numeroAsunto, idTarea.toString(), rfc, idSolicitud);

    }

    /**
     * M&eacute;todo para obtener lista de medios de defensa.
     */
    public List<CatalogoDTO> obtenerMediosDeTransporte() {
        return generacionResolucionFacade.obtenerMediosDeTransporte();
    }

    /**
     * 
     * @param bienResarcimientoDTO
     * @return
     */
    public void guardarBienResolucion(BienResarcimientoDTO bienResarcimientoDTO) {
        this.getGeneracionResolucionFacade().guardaBienResolucion(bienResarcimientoDTO);
    }

    /**
     * 
     * @return
     */
    public List<UnidadAdministrativaDTO> obtenerAutoridades() {
        return this.getGeneracionResolucionFacade().obtenerAutoridades();
    }

    /**
     * M&eacute;todo para obtener los bienes de una resoluci&oacute;n
     * para su aprobaci&oacute;n
     * 
     * @param filtroBandejaTareaDTO
     * @return
     */
    public List<BienResarcimientoDTO> obtenerBienes(ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO) {
        return this.getGeneracionResolucionFacade().obtenerBienesResolucion(resolucionDatosGeneradosDTO);
    }

    /**
     * 
     * @param numeroAsunto
     */
    public TramiteDTO obtenerTramite(String numeroAsunto) {
        return this.getGeneracionResolucionFacade().obtenerSolicitud(numeroAsunto);
    }

    public List<TipoTramiteDTO> getTipoTramiteModulo(String idTipoTramite) {
        return this.getGeneracionResolucionFacade().getTipoTramiteModulo(idTipoTramite);
    }

    /**
     * 
     * @param dataDTO
     */
    public void guardaResarcimiento(ResolucionDatosGeneradosDTO dataDTO, String rfcAbogado) {
        this.getGeneracionResolucionFacade().guardarResolucionResarcimiento(dataDTO, rfcAbogado);
    }

    /**
     * 
     * @param dataDTO
     */
    public void guardaClasificacion(ResolucionDatosGeneradosDTO dataDTO, String rfcAbogado) {
        this.getGeneracionResolucionFacade().guardarResolucionclasificacion(dataDTO, rfcAbogado);

    }

    /**
     * 
     * @param numeroAsunto
     */
    public ResolucionDatosGeneradosDTO getDatosResolucion(String numeroAsunto) {
        return getGeneracionResolucionFacade().obtenerResolucionDatosGenerados(numeroAsunto);
    }

    /**
     * 
     * @param idTarea
     * @param numAsunto
     */
    public void actualizaDatosBP(DatosBandejaTareaDTO datosBandejaTareaDTO,
            ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO) {
        getGeneracionResolucionFacade().actualizaDatosBP(datosBandejaTareaDTO, resolucionDatosGeneradosDTO);
    }

    /**
     * 
     * @param dto
     * @param resolucionDatosGeneradosDTO
     */
    public void actualizarResolucion(ResolucionDatosGeneradosDTO dto,
            ResolucionDatosGeneradosDTO resolucionDatosGeneradosDTO) {
        getGeneracionResolucionFacade().actualizarResolucion(dto, resolucionDatosGeneradosDTO);

    }

    /**
     * 
     * @param type
     * @return
     */
    public List<CatalogoDTO> getAutorizadores(String type) {
        return generacionResolucionFacade.obtenerAutorizadores(type);
    }
    
    /**
     * bln avanzarResolucion
     * Este bln solo es activo cuando: 
     * 1) No se tienen requerimientos asociados al tramite
     * 2) Se tienen requerimientos a Autoridad Externa y 
     *                         dicho requerimiento tiene capturada la fecha de notificacion
     * 
     */
    public boolean obtenerBlnAvanzarResolucion(String numeroAsunto) {
                return generacionResolucionFacade.obtenerBlnAvanzarResolucion(numeroAsunto);
            }
    
    /**
     * @return the generacionResolucionFacade
     */
    public GeneracionResolucionFacade getGeneracionResolucionFacade() {
        return generacionResolucionFacade;
    }

    /**
     * @param generacionResolucionFacade
     *            the generacionResolucionFacade to set
     */
    public void setGeneracionResolucionFacade(GeneracionResolucionFacade generacionResolucionFacade) {
        this.generacionResolucionFacade = generacionResolucionFacade;
    }

}
