/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dao.domain.model.PersonaInterna;
import mx.gob.sat.siat.juridica.base.dto.CatalogoDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ReasignarTareaFacade;
import mx.gob.sat.siat.juridica.rrl.dto.DatosPersonaTareaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;
import java.util.Map;

/**
 * Business para la consulta de los usuarios
 * 
 * @author softtek - EQG 08/10/2014
 */
@ManagedBean(name = "reasignarTareaBusiness")
@NoneScoped
public class ReasignarTareaBusiness extends BaseBussinessBean {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 2838095268207095230L;

    @ManagedProperty(value = "#{reasignarTareaFacade}")
    private transient ReasignarTareaFacade reasignarTareaFacade;

    /**
     * Metodo que obtiene las tareas a reasignar.
     * 
     * @param filtroBandejaTareaDTO
     * @param cveRol
     * @return
     */
 

    public PersonaInterna obtenerEmpleado(Long numEmpleado) {
        return reasignarTareaFacade.obtenerEmpleado(numEmpleado);
    }

    public ReasignarTareaFacade getReasignarTareaFacade() {
        return reasignarTareaFacade;
    }

    public void setReasignarTareaFacade(ReasignarTareaFacade reasignarTareaFacade) {
        this.reasignarTareaFacade = reasignarTareaFacade;
    }

    /**
     * 
     * @return List de tipos de tramite.
     */
    public List<CatalogoDTO> getTiposDeTramite() {
        return reasignarTareaFacade.getTiposDeTramite();
    }

    /**
     * 
     * @return List de modalidad por tipos de tramite.
     */
    public List<CatalogoDTO> getModalidadByTipoTramite(String idServicio) {
        return reasignarTareaFacade.getModalidadByTipoTramite(idServicio);
    }

    public List<CatalogoDTO> getRoles(List<String> roles) {
        return reasignarTareaFacade.getRoles(roles);
    }

    public void reasignar(List<DatosPersonaTareaDTO> idAsuntos, String permiso, String rfcAsignar, String reasignador) {
        reasignarTareaFacade.reasignar(idAsuntos, permiso, rfcAsignar, reasignador);
    }

    public void buscarTareasBP(Map<String, String> filters) {

    }
}
