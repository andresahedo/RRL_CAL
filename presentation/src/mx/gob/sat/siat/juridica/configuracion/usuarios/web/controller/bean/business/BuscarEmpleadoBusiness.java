/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.api.BaseCloudFacade;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.BaseCloudBusiness;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.BuscarEmpleadoFacade;
import mx.gob.sat.siat.juridica.configuracion.usuarios.dto.PersonaInternaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Clase Business para la busqueda de empleados
 * 
 * @author Softtek - EQG
 * @since 1/11/2014
 */
@ManagedBean(name = "buscarEmpleadoBusiness")
@SessionScoped
public class BuscarEmpleadoBusiness extends BaseCloudBusiness {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{buscarEmpleadoFacade}")
    private BuscarEmpleadoFacade buscarEmpleadoFacade;

    /**
     * Metodo para obtener informacion de un empleado por numero de
     * empleado
     * 
     * @param numEmpleado
     * @return
     */
    public PersonaInternaDTO obtenerInformacionEmpleado(Long numEmpleado) {
        return buscarEmpleadoFacade.obtenerInformacionEmpleado(numEmpleado);
    }

    @Override
    public BaseCloudFacade getCloudFacade() {
        // TODO Auto-generated method stub
        return null;
    }

    public BuscarEmpleadoFacade getBuscarEmpleadoFacade() {
        return buscarEmpleadoFacade;
    }

    public void setBuscarEmpleadoFacade(BuscarEmpleadoFacade buscarEmpleadoFacade) {
        this.buscarEmpleadoFacade = buscarEmpleadoFacade;
    }

}
