package mx.gob.sat.siat.juridica.oficialia.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.auditoria.annotation.Audit;
import mx.gob.sat.siat.juridica.auditoria.controller.BaseAuditoriaControllerBean;
import mx.gob.sat.siat.juridica.base.constantes.MenusConstantes;
import mx.gob.sat.siat.juridica.base.constantes.MovimientosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.constantes.SistemasConstantes;
import mx.gob.sat.siat.juridica.oficialia.web.controller.bean.constantes.OficialiaConstantesController;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ViewScoped
@ManagedBean(name = "seleccionTramiteController")
public class SeleccionTramiteController extends BaseAuditoriaControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 3539788292595452386L;

    @PostConstruct
    public void iniciar() {

    }

    public void registroPromocion() {
        redireccionarPagina(OficialiaConstantesController.PROMOCION_NUEVA);
    }

    public String registroPromocionRRLOficialia() {
        return OficialiaConstantesController.PROMOCION_NUEVA_RRL_OFICIALIA;
    }

    public void registroPromocionCALOficialia() {
        redireccionarPagina(OficialiaConstantesController.PROMOCION_NUEVA_CAL_OFICIALIA);
    }

    public void actualizarAsunto() {
        redireccionarPagina(OficialiaConstantesController.CONSULTAR_ASUNTO);
    }

    private void redireccionarPagina(String url) {

        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        configurableNavigationHandler.performNavigation(url + OficialiaConstantesController.FACES_REDIRECT);

    }

    @Audit(identificadorProceso = ProcesosConstantes.PROCESO_EMPLEADO_REGISTRO_OFICIALIA,
            movimiento = MovimientosConstantes.MOVIMIENTO_OFICIAL_REGISTRO_ASUNTO,
            claveSistema = SistemasConstantes.SISTEMA_RRL, observaciones = "Seleccion de tramites Oficial de Partes")
    public void validaAccesoProcesoMenu() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoProcesoMenu(ProcesosConstantes.PROCESO_EMPLEADO_REGISTRO_OFICIALIA,
                MenusConstantes.MENU_EMPLEADOS);
    }
}
