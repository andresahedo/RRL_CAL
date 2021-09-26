package mx.gob.sat.siat.juridica.cal.op.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.web.controller.bean.controller.BaseBuscarSolicitanteController;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "buscarSolicitanteCALController")
@ViewScoped
public class BuscarSolicitanteCALController extends BaseBuscarSolicitanteController {

    /**
     * 
     */
    private static final long serialVersionUID = 8128471902582792029L;

    private String idModulo;
    private String modalidad;
    private String unidadAdmin;
    private String asignacion;

    @Override
    public void init() {
        super.init();

        setIdModulo((String) getFlash().get("idModulo") != null ? (String) getFlash().get("idModulo") : null);
        setModalidad((String) getFlash().get("modalidad") != null ? (String) getFlash().get("modalidad") : null);
        setUnidadAdmin((String) getFlash().get("unidadAdmin") != null ? (String) getFlash().get("unidadAdmin") : null);
        setAsignacion((String) getFlash().get("asignacion") != null ? (String) getFlash().get("asignacion") : null);

    }

    @Override
    public String getUrl() {
        getFlash().put("modalidad", modalidad);
        getFlash().put("unidadAdmin", unidadAdmin);
        getFlash().put("idModulo", idModulo);
        getFlash().put("asignacion", asignacion);
        return "/resources/pages/cal/op/registroSolicitud/capturaFolioOficialia.xhtml";
    }

    /**
     * @return the idModulo
     */
    public String getIdModulo() {
        return idModulo;
    }

    /**
     * @param idModulo
     *            the idModulo to set
     */
    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad
     *            the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the unidadAdmin
     */
    public String getUnidadAdmin() {
        return unidadAdmin;
    }

    /**
     * @param unidadAdmin
     *            the unidadAdmin to set
     */
    public void setUnidadAdmin(String unidadAdmin) {
        this.unidadAdmin = unidadAdmin;
    }

    /**
     * @return the asignacion
     */
    public String getAsignacion() {
        return asignacion;
    }

    /**
     * @param asignacion
     *            the asignacion to set
     */
    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

}
