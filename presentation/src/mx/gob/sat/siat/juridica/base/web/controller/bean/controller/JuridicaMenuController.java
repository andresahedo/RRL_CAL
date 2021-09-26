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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Bean que implementan la l&oacute;gica de negocio para firmar una
 * tarea y avanzar el BP
 * 
 * @author Softtek
 * 
 */
@RequestScoped
@ManagedBean(name = "juridicaMenuController")
public class JuridicaMenuController extends BaseControllerBean {

    /** N&uacute;mero de versi&oacute;n */
    private static final long serialVersionUID = 1L;
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    private String urlDestino = "";

    @PostConstruct
    public void init() {
        urlDestino = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("urlDestino");

    }

    public void validaAccesoProcesoMenu() {

        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + urlDestino);
        }
        catch (IOException ioe) {
            logger.error("error al redireccionar a la página solicitada", ioe);
        }
    }

    public String getUrlDestino() {
        return urlDestino;
    }

    public void setUrlDestino(String urlDestino) {
        this.urlDestino = urlDestino;
    }
}
