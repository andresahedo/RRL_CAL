package mx.gob.sat.siat.juridica.oficialia.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.base.dto.BandejaConsultaRechazoDTO;
import mx.gob.sat.siat.juridica.oficialia.api.BandejaConsultasOficialiaFacade;
import mx.gob.sat.siat.juridica.rrl.dto.FiltroBandejaTareaDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import java.util.List;

@NoneScoped
@ManagedBean(name = "bandejaConsultasOPBussines")
public class BandejaConsultasOPBussines extends BaseBussinessBean {

    /**
     * 
     */
    private static final long serialVersionUID = -1619090539376123586L;

    @ManagedProperty("#{bandejaConsultasOficialiaFacade}")
    private BandejaConsultasOficialiaFacade bandejaOficialiaFacade;

    public List<BandejaConsultaRechazoDTO> obtenerTareasRechazo(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        return bandejaOficialiaFacade.obtenerTareasRechazo(filtroBandejaTareaDTO);
    }

    public List<BandejaConsultaRechazoDTO> obtenerTareasDoctoRechazo(FiltroBandejaTareaDTO filtroBandejaTareaDTO) {
        return bandejaOficialiaFacade.obtenerTareasDoctoRechazo(filtroBandejaTareaDTO);
    }

    public BandejaConsultasOficialiaFacade getBandejaOficialiaFacade() {
        return bandejaOficialiaFacade;
    }

    public void setBandejaOficialiaFacade(BandejaConsultasOficialiaFacade bandejaOficialiaFacade) {
        this.bandejaOficialiaFacade = bandejaOficialiaFacade;
    }

}
