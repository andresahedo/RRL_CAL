package mx.gob.sat.siat.juridica.cal.op.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dao.domain.model.Tramite;
import mx.gob.sat.siat.juridica.cal.api.RegistroFolioOPFacade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

@NoneScoped
@ManagedBean(name = "registroFolioOPBusiness")
public class RegistroFolioOPBusiness extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{registroFolioOPFacade}")
    private RegistroFolioOPFacade registroFolioOPFacade;

    public Tramite obtenerTramitePorId(String numeroAsunto) {
        return registroFolioOPFacade.obtenerTramitePorId(numeroAsunto);
    }

    public RegistroFolioOPFacade getRegistroFolioOPFacade() {
        return registroFolioOPFacade;
    }

    public void setRegistroFolioOPFacade(RegistroFolioOPFacade registroFolioOPFacade) {
        this.registroFolioOPFacade = registroFolioOPFacade;
    }

}
