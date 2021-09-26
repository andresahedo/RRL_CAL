package mx.gob.sat.siat.juridica.health.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.health.dto.HealthDTO;
import mx.gob.sat.siat.juridica.health.web.controller.bean.business.HealthCheckBusiness;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name = "healthCheckController")
public class HealthCheckController extends BaseControllerBean {

    private static final long serialVersionUID = -3790405310555686166L;

    @ManagedProperty(value = "#{healthCheckBusiness}")
    private HealthCheckBusiness healthCheckBusiness;

    private String rfcBPM;
    private String folioTramite;    
    private HealthDTO healthDTO = new HealthDTO();

    @PostConstruct
    public void init() {
        healthDTO = healthCheckBusiness.obtenerResultadosSincronizacion(null);
    }

    public void obtenerDatosUsuariosSincronizacion() {
        healthDTO = healthCheckBusiness.obtenerResultadosSincronizacion(healthDTO.isPermitirEjecucionSincronizacion());
    }

    public String getFolioTramite() {
        return folioTramite;
    }

    public void setFolioTramite(String folioTramite) {
        this.folioTramite = folioTramite;
    }

    public HealthCheckBusiness getHealthCheckBusiness() {
        return healthCheckBusiness;
    }

    public void setHealthCheckBusiness(HealthCheckBusiness healthCheckBusiness) {
        this.healthCheckBusiness = healthCheckBusiness;
    }

    public HealthDTO getHealthDTO() {
        return healthDTO;
    }

    public void setHealthDTO(HealthDTO healthDTO) {
        this.healthDTO = healthDTO;
    }    

    public String getRfcBPM() {
        return rfcBPM;
    }

    public void setRfcBPM(String rfcBPM) {
        this.rfcBPM = rfcBPM;
    }        

}
