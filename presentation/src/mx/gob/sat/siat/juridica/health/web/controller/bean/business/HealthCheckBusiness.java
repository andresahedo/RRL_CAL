package mx.gob.sat.siat.juridica.health.web.controller.bean.business;

import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;
import mx.gob.sat.siat.juridica.health.api.HealthCheckFacade;
import mx.gob.sat.siat.juridica.health.dto.HealthDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;

@ManagedBean(name = "healthCheckBusiness")
@NoneScoped
public class HealthCheckBusiness extends BaseBussinessBean {

    private static final long serialVersionUID = 2051334397284732013L;

    @ManagedProperty("#{healthCheckFacade}")
     private transient HealthCheckFacade healthCheckFacade;

    public HealthDTO obtenerResultadosSincronizacion(Boolean permitirEjecucion) {
        return healthCheckFacade.obtenerResultadosSincronizacion(permitirEjecucion);
    }

    public HealthCheckFacade getHealthCheckFacade() {
        return healthCheckFacade;
    }

    public void setHealthCheckFacade(HealthCheckFacade healthCheckFacade) {
        this.healthCheckFacade = healthCheckFacade;
    }

}
