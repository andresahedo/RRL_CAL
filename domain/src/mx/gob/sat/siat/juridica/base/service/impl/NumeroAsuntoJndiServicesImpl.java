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
import mx.gob.sat.siat.juridica.singleton.JuridicaSingletonService;
import mx.gob.sat.siat.juridica.singleton.JuridicaSingletonServiceImpl;
import weblogic.jndi.Environment;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * 
 * @author Softtek
 * 
 */
public class NumeroAsuntoJndiServicesImpl extends BaseSerializableBusinessServices implements
        NumeroAsuntoServices {

    
    public Integer obtenerSecuencia(String claveMapaFolios, Integer secuenciaActual) {
        JuridicaSingletonService juridicaSingletonService = getSingleton();
        Integer secuenciaNueva = juridicaSingletonService.obtenerSecuencia(claveMapaFolios, secuenciaActual);
        getLogger().debug("secuencia jdni: {}", secuenciaNueva);
        return secuenciaNueva;
    }
    
    public void reiniciarSecuencias(){
        JuridicaSingletonService juridicaSingletonService = getSingleton();
        juridicaSingletonService.reiniciarSecuencias();
        getLogger().debug("se reiniciaron las secuencias en el singleton del cluster");
    }
    
    private JuridicaSingletonService getSingleton()  {
    
        JuridicaSingletonService singleton = null;
        try {
        Environment env = new Environment();
  
  
        Context ctx = env.getInitialContext();
        
        Object obj = ctx.lookup(JuridicaSingletonServiceImpl.JNDI_NAME);
    
        if (obj instanceof JuridicaSingletonService){
            singleton = (JuridicaSingletonService)obj;
        }else{
            singleton = (JuridicaSingletonService) PortableRemoteObject.narrow(obj, JuridicaSingletonService.class);
        }
        
        }catch(ClassCastException e){
            getLogger().error("Error en la conversion ", e);
        }catch(NamingException e){
            getLogger().error("Error en la busqueda de jndi ",e);
        }
        return singleton;
    }
}
