/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.service.JuridicaSingletonClientServices;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Softtek
 * 
 */

public class JuridicaSingletonClientServicesImpl extends
        BaseSerializableBusinessServices implements
        JuridicaSingletonClientServices {
    private static final long serialVersionUID = 1L;
    private static final ConcurrentHashMap<String, Boolean> CACHE_PROCESOS = new ConcurrentHashMap<String, Boolean>();

    public Boolean permiteEjecucion(String idProceso) {
        Boolean ejecucionPermitida = null;
        synchronized (this) {

            // Agrega el proceso si no se encuentra en el CACHE y regresa null
            // cuando aún no se encuentra
            ejecucionPermitida = CACHE_PROCESOS.putIfAbsent(idProceso,
                    Boolean.TRUE);
            if (ejecucionPermitida != null){
                return Boolean.FALSE;
            }else{
                return Boolean.TRUE;
            }
        }
    }

}
