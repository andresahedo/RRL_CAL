/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.web.util.VistaConstantes;
import mx.gob.sat.siat.juridica.rrl.dto.DatosBandejaTareaDTO;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller para autenticacion.
 * 
 * @author softtek
 * 
 */
@ViewScoped
@ManagedBean(name = "autenticacionSSOController")
public class AutenticacionSSOController extends BaseControllerBean {

    /**
     * Numero de serie.
     */
    private static final long serialVersionUID = -5560034263210776094L;

    @PostConstruct
    public void init() {
        HttpServletRequest req =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        bandejaTareaDTO = new DatosBandejaTareaDTO();
        bandejaTareaDTO.setIdTareaUsuario(new Long(req.getParameter("idTareaUsuario")));
        bandejaTareaDTO.setIdSolicitud(new Long(req.getParameter("idSolicitud")));
        bandejaTareaDTO.setNombreTarea(req.getParameter("nombreTarea"));
        bandejaTareaDTO.setTipoTramite(req.getParameter("tipoTramite"));
        bandejaTareaDTO.setDescripcionTipoTramite(req.getParameter("descripcionTipoTramite"));
        bandejaTareaDTO.setNumeroAsunto(req.getParameter("numeroAsunto"));
        bandejaTareaDTO.setRfcSolicitante(req.getParameter("rfcSolicitante"));
        bandejaTareaDTO.setIdRequerimiento(new Long(req.getParameter("idRequerimiento")));
        bandejaTareaDTO.setUrl(req.getParameter("url"));
    }

    public void redirect(ActionEvent actionEvent) {
        ConfigurableNavigationHandler configurableNavigationHandler =
                (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication()
                        .getNavigationHandler();

        getLogger().debug("AutenticacionSSOController - init - bandejaTareaDTO", bandejaTareaDTO.getUrl());

        getFlash().put("idSolicitud", bandejaTareaDTO.getIdSolicitud());
        getFlash().put("tipoTramite", bandejaTareaDTO.getTipoTramite());
        getFlash().put("numAsunto", bandejaTareaDTO.getNumeroAsunto());
        getFlash().put("idRequerimiento", bandejaTareaDTO.getIdRequerimiento());

        getFlash().put(VistaConstantes.DATOS_BANDEJA_DTO, bandejaTareaDTO);

        configurableNavigationHandler.performNavigation(bandejaTareaDTO.getUrl() + "?faces-redirect=true");
    }

    private DatosBandejaTareaDTO bandejaTareaDTO;

    /**
     * @return the bandejaTareaDTO
     */
    public DatosBandejaTareaDTO getBandejaTareaDTO() {
        return bandejaTareaDTO;
    }

    /**
     * @param bandejaTareaDTO
     *            the bandejaTareaDTO to set
     */
    public void setBandejaTareaDTO(DatosBandejaTareaDTO bandejaTareaDTO) {
        this.bandejaTareaDTO = bandejaTareaDTO;
    }

}
