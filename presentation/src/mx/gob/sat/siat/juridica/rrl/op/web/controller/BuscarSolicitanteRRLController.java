package mx.gob.sat.siat.juridica.rrl.op.web.controller;

import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseBuscarSolicitanteController;
import mx.gob.sat.siat.juridica.ca.util.constants.RegistroSolicitudConstants;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "buscarSolicitanteRRLController")
@ViewScoped
public class BuscarSolicitanteRRLController extends BaseBuscarSolicitanteController {

    /**
     * 
     */
    private static final long serialVersionUID = 8315431893576943641L;

    @Override
    public String getUrl() {
        return "/resources/pages/rrl/op/capturaOficialia.xhtml";
    }

    private void redireccionarPagina(String url) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().setResponseCharacterEncoding("UTF-8");
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
        configurableNavigationHandler.performNavigation(url + RegistroSolicitudConstants.FACES_REDIRECT);
    }

    public void continuarRegistro(ActionEvent event) {
        getFlash().put("solicitante", getPersonaSolicitud());
        redireccionarPagina(getUrl());
    };

}
