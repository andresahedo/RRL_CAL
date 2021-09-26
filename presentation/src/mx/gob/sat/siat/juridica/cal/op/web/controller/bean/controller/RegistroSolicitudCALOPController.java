package mx.gob.sat.siat.juridica.cal.op.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoRol;
import mx.gob.sat.siat.juridica.base.dto.PersonaSolicitudDTO;
import mx.gob.sat.siat.juridica.cal.dto.SolicitudCALDTO;
import mx.gob.sat.siat.juridica.cal.op.web.controller.bean.business.RegistroSolicitudCALOPBusiness;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.business.RegistroSolicitudCALCommonBussines;
import mx.gob.sat.siat.juridica.cal.web.controller.bean.controller.RegistroSolicitudCALCommonController;
import mx.gob.sat.siat.juridica.rrl.util.constante.UrlFirma;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "registroSolicitudCALOPController")
@ViewScoped
public class RegistroSolicitudCALOPController extends RegistroSolicitudCALCommonController {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{registroSolicitudCALOPBusiness}")
    private RegistroSolicitudCALOPBusiness registroSolicitudCALOPBusiness;

    public RegistroSolicitudCALOPController() {

    }

    @Override
    public void iniciar() {
        super.iniciar();
        getLogger().debug("iniciar de REgistroSolicitudCALOPController 1");
        PersonaSolicitudDTO solicitante = (PersonaSolicitudDTO) getFlash().get("solicitante");
        SolicitudCALDTO sol = (SolicitudCALDTO) getFlash().get("datosSolicitud");
        getLogger().debug("iniciar de REgistroSolicitudCALOPController 2");
        getSolicitud().setSolicitante(solicitante);
        getSolicitud().setNumeroFolio(sol.getNumeroFolio());
        getSolicitud().setFechaApertura(sol.getFechaApertura());
        getLogger().debug("iniciar de REgistroSolicitudCALOPController 3");
        if (getSolicitud().getSolicitante() != null && getSolicitud().getSolicitante().getRazonSocial() != null) {
            setBanderaRazonSocial(true);
        }
        else {
            setBanderaRazonSocial(false);
        }
        getLogger().debug("iniciar de REgistroSolicitudCALOPController 4");
    }

    public RegistroSolicitudCALCommonBussines getRegistroSolicitudCALCommonBussines() {
        return registroSolicitudCALOPBusiness;
    }

    /**
     * @return the registroSolicitudCALOPBussines
     */
    public RegistroSolicitudCALOPBusiness getRegistroSolicitudCALOPBusiness() {
        return registroSolicitudCALOPBusiness;
    }

    /**
     * @param registroSolicitudCALOPBussines
     *            the registroSolicitudCALOPBussines to set
     */
    public void setRegistroSolicitudCALOPBusiness(RegistroSolicitudCALOPBusiness registroSolicitudCALOPBusiness) {
        this.registroSolicitudCALOPBusiness = registroSolicitudCALOPBusiness;
    }

    public String getDireccionFirma() {
        return UrlFirma.PAGINA_FIRMA_SOLICITUD_CAL_OP.toString();
    }

    @Override
    protected String tipoCapturista() {
        // TODO Auto-generated method stub
        return TipoRol.OFICIAL_PARTES.getClave();
    }

}
