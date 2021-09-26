/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.BuscarEmpleadoBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.constantes.AdministracionConstantes;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Clase Controller para la busqueda de empleados
 * 
 * @author Softtek - EQG
 * @since 1/11/2014
 */
@ViewScoped
@ManagedBean(name = "buscarEmpleadoController")
public class BuscarEmpleadoController extends AdministracionUsuariosBaseController {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -3529135334726558939L;

    /**
     * Propiedad datosEmpleado tipo PersonaInternaDTO
     */
    private PersonaInternaDTO datosEmpleado = new PersonaInternaDTO();

    /**
     * Propiedad numeroEmpleado tipo Long
     */
    private Long numeroEmpleado;

    /**
     * Propiedad para manejar los mensajes en pantalla.
     */
    @ManagedProperty("#{msg}")
    private transient ResourceBundle messages;

    /**
     * Propiedad Business para la busqueda de empleados
     */
    @ManagedProperty("#{buscarEmpleadoBusiness}")
    private BuscarEmpleadoBusiness buscarEmpleadoBusiness;

    /**
     * Metodo que incializa datos
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void inicio() {
        setUrlsRegreso((LinkedList<String>) getFlash().get(VistaConstantes.BACK));
    }

    /**
     * Metodo de busqueda de datos del empleado
     * 
     * @param e
     */
    public void buscarEmpleado() {
        datosEmpleado = buscarEmpleadoBusiness.obtenerInformacionEmpleado(getNumeroEmpleado());
        if (datosEmpleado != null) {
            getFlash().put("datosEmpleado", getDatosEmpleado());
            ConfigurableNavigationHandler configurableNavigationHandler =
                    (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                            .getNavigationHandler();
            configurableNavigationHandler.performNavigation(AdministracionConstantes.ADMINISTRAR_EMPLEADO);
        }
        else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", messages
                            .getString("usuarios.mensaje.empleado.noExiste")));
        }
    }

    /**
     * Metodo que regresa al menu del administrado, ya sea global o de
     * UA
     * 
     * @param e
     */
    public void back() {
        getFlash().put(VistaConstantes.DATOS_ADMINUSUARIO_DTO, getAdminUsuariosDTO());
        getFlash().put(VistaConstantes.BACK, getUrlsRegreso());
        super.back();
    }

    public PersonaInternaDTO getDatosEmpleado() {
        return datosEmpleado;
    }

    public void setDatosEmpleado(PersonaInternaDTO datosEmpleado) {
        this.datosEmpleado = datosEmpleado;
    }

    public Long getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(Long numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    public void setMessages(ResourceBundle messages) {
        this.messages = messages;
    }

    public BuscarEmpleadoBusiness getBuscarEmpleadoBusiness() {
        return buscarEmpleadoBusiness;
    }

    public void setBuscarEmpleadoBusiness(BuscarEmpleadoBusiness buscarEmpleadoBusiness) {
        this.buscarEmpleadoBusiness = buscarEmpleadoBusiness;
    }

}
