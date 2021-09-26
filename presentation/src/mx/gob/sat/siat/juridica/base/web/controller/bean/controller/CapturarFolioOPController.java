package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;

public abstract class CapturarFolioOPController<S extends SolicitudDTO> extends BaseControllerBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private boolean banderaRazonSocial;

    private String nombrePromovente;

    private PersonaSolicitudDTO personaSolicitudDTO;

    private String url = "";

    public void iniciar() {
        setPersonaSolicitudDTO((PersonaSolicitudDTO) getFlash().get("solicitante"));
        setNombrePromovente(getPersonaSolicitudDTO().getNombre() + " " + getPersonaSolicitudDTO().getApellidoPaterno()
                + " " + getPersonaSolicitudDTO().getApellidoMaterno());
        if (getPersonaSolicitudDTO().getRazonSocial() != null) {
            if (getPersonaSolicitudDTO().getRazonSocial().equalsIgnoreCase("")) {
                setBanderaRazonSocial(false);
            }
            else {
                setBanderaRazonSocial(true);
            }

        }
        else {
            setBanderaRazonSocial(false);
        }
    }

    /**
     * Metodo para siguiente.
     * 
     * @return
     */
    public void siguiente() {
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();
        getFlash().put("datosSolicitud", getSolicitudDTO());
        getFlash().put("solicitante", getPersonaSolicitudDTO());
        configurableNavigationHandler.performNavigation(getUrl() + "?faces-redirect=true");

    }

    /**
     * @return the solicitud
     */
    public abstract S getSolicitudDTO();

    /**
     * @param solicitud
     *            the solicitud to set
     */
    public abstract void setSolicitudDTO(S solicitudDTO);

    public boolean isBanderaRazonSocial() {
        return banderaRazonSocial;
    }

    public void setBanderaRazonSocial(boolean banderaRazonSocial) {
        this.banderaRazonSocial = banderaRazonSocial;
    }

    public String getNombrePromovente() {
        return nombrePromovente;
    }

    public void setNombrePromovente(String nombrePromovente) {
        this.nombrePromovente = nombrePromovente;
    }

    public String getUrl() {
        return url;
    }

    public PersonaSolicitudDTO getPersonaSolicitudDTO() {
        return personaSolicitudDTO;
    }

    public void setPersonaSolicitudDTO(PersonaSolicitudDTO personaSolicitudDTO) {
        this.personaSolicitudDTO = personaSolicitudDTO;
    }

}
