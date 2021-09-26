package mx.gob.sat.siat.juridica.rrl.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.TerminosCondicionesDTO;
import mx.gob.sat.siat.juridica.rrl.api.TerminosCondicionesFacade;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

@ManagedBean(name = "terminosCondicionesBusiness")
@NoneScoped
public class TerminosCondicionesBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -7868810020388945245L;

    @ManagedProperty(value = "#{terminosCondicionesFacade}")
    private TerminosCondicionesFacade terminosCondicionesFacade;

    @PostConstruct
    public void init() {

    }

    public TerminosCondicionesDTO obtenerTerminos(int numPantalla) {
        return terminosCondicionesFacade.obtenerTerminos(numPantalla);
    }

    /**
     * @return the terminosCondicionesFacade
     */
    public TerminosCondicionesFacade getTerminosCondicionesFacade() {
        return terminosCondicionesFacade;
    }

    /**
     * @param terminosCondicionesFacade
     *            the terminosCondicionesFacade to set
     */
    public void setTerminosCondicionesFacade(TerminosCondicionesFacade terminosCondicionesFacade) {
        this.terminosCondicionesFacade = terminosCondicionesFacade;
    }

}
