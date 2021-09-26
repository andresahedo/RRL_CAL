package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business.ActualizarMovimientosNovellBusiness;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name = "actualizarMovimientosNovellController")
public class ActualizarMovimientosNovellController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = -3170711456834352133L;

    @ManagedProperty(value = "#{actualizarMovimientosNovellBusiness}")
    private ActualizarMovimientosNovellBusiness actualizarMovimientosNovellBusiness;

 
    public ActualizarMovimientosNovellBusiness getActualizarMovimientosNovellBusiness() {
        return actualizarMovimientosNovellBusiness;
    }

    public void setActualizarMovimientosNovellBusiness(
            ActualizarMovimientosNovellBusiness actualizarMovimientosNovellBusiness) {
        this.actualizarMovimientosNovellBusiness = actualizarMovimientosNovellBusiness;
    }

}
