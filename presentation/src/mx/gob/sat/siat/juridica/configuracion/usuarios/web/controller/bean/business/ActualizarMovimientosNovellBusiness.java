package mx.gob.sat.siat.juridica.configuracion.usuarios.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.configuracion.usuarios.api.ActualizarMovimientosNovellFacade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

@ManagedBean(name = "actualizarMovimientosNovellBusiness")
@NoneScoped
public class ActualizarMovimientosNovellBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -335564780922103859L;

    @ManagedProperty("#{actualizarMovimientosNovellFacade}")
    private ActualizarMovimientosNovellFacade actualizarMovimientosNovellFacade;

    public ActualizarMovimientosNovellFacade getActualizarMovimientosNovellFacade() {
        return actualizarMovimientosNovellFacade;
    }

    public void
            setActualizarMovimientosNovellFacade(ActualizarMovimientosNovellFacade actualizarMovimientosNovellFacade) {
        this.actualizarMovimientosNovellFacade = actualizarMovimientosNovellFacade;
    }

}
