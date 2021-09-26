/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.EstadoReproceso;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoErrorEnvioBPM;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 
 * @author softtek
 * 
 */
public interface TramiteReprocesarServices extends Serializable {

    
    /**
     * Metodo para guardar los tramites que fallaron por el JMS 
     * 
     * @param numeroAsunto
     * @param idSolicitud
     */
    void guardarTramiteAReprocesar(String numeroAsunto, String parametros, TipoErrorEnvioBPM tipoErrorEnvioBPM);
    
    void guardarTramiteAReprocesar(JSONObject json, String bpdId, String processAppId, TipoErrorEnvioBPM tipoErrorEnvioBPM);

 
    /**
     * Metodo para actualizar datos de los tramites que fallaron por el JMS 
     * 
     * @param numeroAsunto
     * @param idSolicitud
     * @param estatusInstancia
     */
    void actualizarTramiteAReprocesar(String numeroAsunto, EstadoReproceso estatusInstancia );

}
