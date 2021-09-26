package mx.gob.sat.siat.juridica.base.service;

import java.io.Serializable;

public interface JuridicaSingletonClientServices extends Serializable {

    /**
     * Metodo para controlar la ejecución del scheduler 
     * 
     * @param fecha
     */
    Boolean permiteEjecucion(String idProceso);

}
