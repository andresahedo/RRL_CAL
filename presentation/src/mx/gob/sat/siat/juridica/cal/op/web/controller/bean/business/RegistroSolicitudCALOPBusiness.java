package mx.gob.sat.siat.juridica.cal.op.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.util.exception.SolicitudNoGuardadaException;
import mx.gob.sat.siat.juridica.cal.api.RegistroSolicitudCALCommonFacade;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.op.api.RegistroSolicitudCALOPFacade;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.RegistroSolicitudCALCommonBussines;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

@ManagedBean(name = "registroSolicitudCALOPBusiness")
@NoneScoped
public class RegistroSolicitudCALOPBusiness extends RegistroSolicitudCALCommonBussines {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{registroSolicitudCALOPFacade}")
    private RegistroSolicitudCALOPFacade registroSolicitudCALOPFacade;

    @Override
    public SolicitudCALDTO iniciarRegistroSolicitud(String rfc, String modalidad) throws SolicitudNoGuardadaException {
        SolicitudCALDTO sol = new SolicitudCALDTO();
        sol.setTipoTramite(modalidad);

        return sol;
    }

    /**
     * @return the registroSolicitudCALOPFacade
     */
    public RegistroSolicitudCALOPFacade getRegistroSolicitudCALOPFacade() {
        return registroSolicitudCALOPFacade;
    }

    /**
     * @param registroSolicitudCALOPFacade
     *            the registroSolicitudCALOPFacade to set
     */
    public void setRegistroSolicitudCALOPFacade(RegistroSolicitudCALOPFacade registroSolicitudCALOPFacade) {
        this.registroSolicitudCALOPFacade = registroSolicitudCALOPFacade;
    }

    public RegistroSolicitudCALCommonFacade getRegistroSolicitudCALCommonFacade() {
        return registroSolicitudCALOPFacade;
    }
}
