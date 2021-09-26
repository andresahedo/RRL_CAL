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
package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.BandejaConsultasFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultasDTO;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

/**
 * Bean que implementan la l&oacute;gica de negocio para la bandeja de
 * consultas.
 * 
 * @author Softtek
 * 
 */
@ManagedBean
@NoneScoped
public class BandejaConsultasBussines extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = 6351112878311056962L;

    /** Propiedad que representa la fachada BandejaConsultasFacade. */
    @ManagedProperty("#{bandejaConsultasFacade}")
    private BandejaConsultasFacade bandejaConsultasFacade;

    /**
     * M�todo que devuelve una lista con los asuntos de las consultas
     * de tipo BandejaConsultasDTO.
     */
    public List<BandejaConsultasDTO> obtenerAsuntosConsulta(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        return getBandejaConsultasFacade().obtenerRecursos(filtroBandejaTareaDTO);
    }
    /**
    * M�todo que devuelve una lista con los asuntos de las consultas
    * de tipo BandejaConsultasDescarga.
    */

     public DataPage obtenerAsuntosConcluidosLazy(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
         return getBandejaConsultasFacade().obtenerRecursosConcluidosLazy(filtroBandejaTareaDTO);
     }

    /** M�todo que devuelde una lista de estados de tipo CatalogoDTO. */
    public List<CatalogoDTO> obtenerCatalogoEstados() {

        return getBandejaConsultasFacade().obtenerCatalogoEstados();
    }

    /**
     * M�todo que devuelve una lista con los tipos de tramites de tipo
     * CatalogoDTO.
     */
    public List<CatalogoDTO> obtenerTiposTramites() {

        return getBandejaConsultasFacade().obtenerTiposTramites();
    }

    /**
     * 
     * @return bandejaConsultasFacade
     */
    public BandejaConsultasFacade getBandejaConsultasFacade() {
        return bandejaConsultasFacade;
    }

    /**
     * 
     * @param bandejaConsultasFacade
     *            la bandejaConsultarFacade a fijar.
     */
    public void setBandejaConsultasFacade(BandejaConsultasFacade bandejaConsultasFacade) {
        this.bandejaConsultasFacade = bandejaConsultasFacade;
    }

    public List<BandejaConsultasDTO> obtenerAsuntosOficialiaConsulta(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        return getBandejaConsultasFacade().obtenerRecursosOficialia(filtroBandejaTareaDTO);
    }
    
    
    public boolean verificarAdminResponsable(String rfcFuncionario) {
        return getBandejaConsultasFacade().verificarAdminResponsable(rfcFuncionario);
    }

}
