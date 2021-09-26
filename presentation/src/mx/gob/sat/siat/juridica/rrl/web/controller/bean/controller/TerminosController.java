package mx.gob.sat.siat.juridica.rrl.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.constantes.MenusConstantes;
import mx.gob.sat.siat.juridica.base.constantes.ProcesosConstantes;
import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.TerminosCondicionesDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness.TerminosCondicionesBusiness;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "terminosController")
@ViewScoped
public class TerminosController extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = -436470024208249055L;

    private TerminosCondicionesDTO terminosDTO;

    @ManagedProperty(value = "#{terminosCondicionesBusiness}")
    private TerminosCondicionesBusiness terminosCondicionesBusiness;

    private boolean noAceptaTerminos = false;

    private int numPantalla;

    public TerminosController() {}

    @PostConstruct
    public void init() {
        String numPant =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("numPantalla");
        setTerminosDTO(terminosCondicionesBusiness.obtenerTerminos(Integer.parseInt(numPant)));

    }

    public void validaAccesoProcesoMenu() {
        // Validacion de acceso a la funcionalidad
        this.validaAccesoContribuytente();
    }

    public void validaAccesoOficialia() {
        // Validacion de acceso a la funcionalidad para el rol de
        // oficialia
        this.validaAccesoProcesoMenu(ProcesosConstantes.PROCESO_EMPLEADO_REGISTRO_OFICIALIA,
                MenusConstantes.MENU_EMPLEADOS);

    }

    public boolean noAceptarTerminos() {
        setNoAceptaTerminos(true);
        return noAceptaTerminos;
    }

    /**
     * @return the terminosDTO
     */
    public TerminosCondicionesDTO getTerminosDTO() {
        return terminosDTO;
    }

    /**
     * @param terminosDTO
     *            the terminosDTO to set
     */
    public void setTerminosDTO(TerminosCondicionesDTO terminosDTO) {
        this.terminosDTO = terminosDTO;
    }

    /**
     * @return the terminosCondicionesBusiness
     */
    public TerminosCondicionesBusiness getTerminosCondicionesBusiness() {
        return terminosCondicionesBusiness;
    }

    /**
     * @param terminosCondicionesBusiness
     *            the terminosCondicionesBusiness to set
     */
    public void setTerminosCondicionesBusiness(TerminosCondicionesBusiness terminosCondicionesBusiness) {
        this.terminosCondicionesBusiness = terminosCondicionesBusiness;
    }

    /**
     * @return the noAceptaTerminos
     */
    public boolean isNoAceptaTerminos() {
        return noAceptaTerminos;
    }

    /**
     * @param noAceptaTerminos
     *            the noAceptaTerminos to set
     */
    public void setNoAceptaTerminos(boolean noAceptaTerminos) {
        this.noAceptaTerminos = noAceptaTerminos;
    }

    public int getNumPantalla() {
        return numPantalla;
    }

    public void setNumPantalla(int numPantalla) {
        this.numPantalla = numPantalla;
    }

}
