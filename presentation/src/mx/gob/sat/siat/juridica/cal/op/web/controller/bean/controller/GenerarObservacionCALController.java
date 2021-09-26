package mx.gob.sat.siat.juridica.cal.op.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.GenerarObservacionController;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ViewScoped
@ManagedBean(name = "generarObservacionCALController")
public class GenerarObservacionCALController extends GenerarObservacionController {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void anterior() {
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, getDatosBandejaTareaDTO());
        configurableNavigationHandler
                .performNavigation("/resources/pages/cal/atencion/atenderPromocion.jsf?faces-redirect=true");

    }

}
