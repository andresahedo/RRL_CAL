package mx.gob.sat.siat.juridica.cal.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.RegistroSolicitudCALBussines;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.RegistroSolicitudCALCommonBussines;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "registroSolicitudCALController")
@ViewScoped
public class RegistroSolicitudCALController extends RegistroSolicitudCALCommonController {

   
    
    public RegistroSolicitudCALController() {
        super();
    }
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{registroSolicitudCALBussines}")
    private RegistroSolicitudCALBussines registroSolicitudCALBussines;

    /**
     * @return the registroSolicitudCALBussines
     */
    public RegistroSolicitudCALBussines getRegistroSolicitudCALBussines() {
        return registroSolicitudCALBussines;
    }

    /**
     * @param registroSolicitudCALBussines
     *            the registroSolicitudCALBussines to set
     */
    public void setRegistroSolicitudCALBussines(RegistroSolicitudCALBussines registroSolicitudCALBussines) {
        this.registroSolicitudCALBussines = registroSolicitudCALBussines;
    }

    public RegistroSolicitudCALCommonBussines getRegistroSolicitudCALCommonBussines() {
        return registroSolicitudCALBussines;
    }

    @Override
    protected String tipoCapturista() {
        return TipoRol.SOLICITANTE.getClave();
    }

}
