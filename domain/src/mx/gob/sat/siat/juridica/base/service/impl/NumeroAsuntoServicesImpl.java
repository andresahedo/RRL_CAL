/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import mx.gob.sat.siat.juridica.base.service.NumeroAsuntoServices;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Softtek
 * 
 */
public class NumeroAsuntoServicesImpl extends BaseSerializableBusinessServices implements
        NumeroAsuntoServices {

        private static final ConcurrentHashMap<String, Integer> CACHE_FOLIOS = new ConcurrentHashMap<String, Integer>();
    
    public Integer obtenerSecuencia(String claveMapaFolios, Integer secuenciaActual) {
        Integer secuenciaNueva = secuenciaActual;
         synchronized (this) {
            Integer cache = CACHE_FOLIOS.get(claveMapaFolios);
            getLogger().debug("cache: {}", cache);
            getLogger().debug("secuencia enviada a validar: {}", secuenciaActual);
            if (cache == null || secuenciaActual > cache) {
                //En caso de que no exista el valor en el mapa o bien
                //que la secuencia obtenida de base sea mayor a lo que tiene el mapa
                //entonces en el mapa se guarda la secuencia de base +1
                CACHE_FOLIOS.put(claveMapaFolios, secuenciaActual + 1);
            }else {
                if (secuenciaActual <= CACHE_FOLIOS.get(claveMapaFolios)) {
                    //Si la secuencia de base es menor o igual al valor del mapa
                    //entonces tomamos el valor del mapa y agrega 1 para guardarlo en el mapa
                    secuenciaNueva = CACHE_FOLIOS.get(claveMapaFolios);
                    CACHE_FOLIOS.put(claveMapaFolios, secuenciaNueva + 1);
                }
            }
         }
         getLogger().debug("secuencia local: {}", secuenciaNueva);
         return secuenciaNueva;
         
    }
    
    public void reiniciarSecuencias(){
        CACHE_FOLIOS.clear();
    }
    
}
