package mx.gob.sat.siat.juridica.rrl.op.web.controller;

import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.CapturarFolioOPController;
import mx.gob.sat.siat.juridica.rrl.dto.DatosSolicitudDTO;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Date;

@ViewScoped
@ManagedBean
public class CapturarFolioRRLOPController extends CapturarFolioOPController<DatosSolicitudDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 7014556884515966855L;

    private DatosSolicitudDTO solicitud;

    private Date fechaMax;

    @PostConstruct
    public void iniciar() {
        super.iniciar();
        fechaMax = new Date();
        setSolicitudDTO(new DatosSolicitudDTO());
        getSolicitudDTO().setSolicitante(getPersonaSolicitudDTO());
    }

    public void siguiente() {
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        getFlash().put("datosSolicitud", getSolicitudDTO());
        configurableNavigationHandler.performNavigation("/resources/pages/rrl/op/registroSolicitudRRL.xhtml"
                + "?faces-redirect=true");

    }

    public void atras() {

        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        configurableNavigationHandler.performNavigation("/resources/pages/rrl/op/buscarPersonaOficialia.xhtml"
                + "?faces-redirect=true");

    }

    @Override
    public DatosSolicitudDTO getSolicitudDTO() {
        // TODO Auto-generated method stub
        return solicitud;
    }

    @Override
    public void setSolicitudDTO(DatosSolicitudDTO solicitudDTO) {
        this.solicitud = solicitudDTO;

    }

    @Override
    public String getUrl() {

        return "/resources/pages/rrl/op/registroSolicitudRRL.xhtml";
    }

    public Date getFechaMax() {
        return fechaMax != null ? (Date) fechaMax.clone() : null;
    }

    public void setFechaMax(Date fechaMax) {
        if (fechaMax != null) {
            this.fechaMax = (Date) fechaMax.clone();
        }
        else {
            this.fechaMax = null;
        }
    }

}
