package mx.gob.sat.siat.juridica.health.api;

import mx.gob.sat.siat.juridica.health.dto.HealthDTO;

public interface HealthCheckFacade {
    
    HealthDTO obtenerResultadosSincronizacion(Boolean permitirEjecucion);

}
