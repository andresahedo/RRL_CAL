package mx.gob.sat.siat.juridica.health.api.impl;

import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.health.api.HealthCheckFacade;
import mx.gob.sat.siat.juridica.health.dto.HealthDTO;
import mx.gob.sat.siat.juridica.health.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("healthCheckFacade")
public class HealthCheckFacadeImpl extends BaseFacadeImpl implements HealthCheckFacade {

    private static final long serialVersionUID = -8018288085435066822L;
    
    @Autowired
    private transient HealthCheckService healthCheckService;

    @Override
    public HealthDTO obtenerResultadosSincronizacion(Boolean permitirEjecucion) {
        return healthCheckService.obtenerResultadosSincronizacion(permitirEjecucion);
    }

}
