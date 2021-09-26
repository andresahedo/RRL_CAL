package mx.gob.sat.siat.juridica.health.service;

import mx.gob.sat.siat.juridica.health.dto.HealthDTO;


public interface HealthCheckService {

    HealthDTO obtenerResultadosSincronizacion(Boolean permitirEjecucion);

}
