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
package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.BandejaReasignarRecursoRevocacionFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dao.domain.model.DataPage;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

/**
 * Bean que implementan la l&oacute;gica de negocio para la bandeja de
 * reasignar recurso de revocacion.
 * 
 * @author Softtek
 * 
 */
@ManagedBean
@NoneScoped
public class BandejaReasignarRecursoRevocacionBussines extends BaseBussinessBean {
    /**
     * 
     */
    private static final long serialVersionUID = 4490089545973737886L;

    /**
     * Facade que publica los servicios para la bandeja de reasignar
     * recurso de revocacion.
     */
    @ManagedProperty("#{bandejaReasignarRecursoRevocacionFacade}")
    private transient BandejaReasignarRecursoRevocacionFacade bandejaReasignarRecursoRevocacionFacade;

    /**
     * Metodo que obtiene las tareas a reasignar.
     * 
     * @param filtroBandejaTareaDTO
     * @param cveRol
     * @return
     */
    public DataPage obtenerTareasReasignar(FiltroBandejaTareaDTO filtroBandejaTareaDTO, String cveRol) {
        return bandejaReasignarRecursoRevocacionFacade.obtenerTareasReasignar(filtroBandejaTareaDTO, cveRol);
    }

    /**
     * 
     * @return bandejaReasignarRecursoRevocacionFacade
     */
    public BandejaReasignarRecursoRevocacionFacade getBandejaReasignarRecursoRevocacionFacade() {
        return bandejaReasignarRecursoRevocacionFacade;
    }

    /**
     * 
     * @param bandejaReasignarRecursoRevocacionFacade
     *            la bandejaReasignarRecursoRevocacionFacade a fijar.
     */
    public void setBandejaReasignarRecursoRevocacionFacade(
            BandejaReasignarRecursoRevocacionFacade bandejaReasignarRecursoRevocacionFacade) {
        this.bandejaReasignarRecursoRevocacionFacade = bandejaReasignarRecursoRevocacionFacade;
    }

}
