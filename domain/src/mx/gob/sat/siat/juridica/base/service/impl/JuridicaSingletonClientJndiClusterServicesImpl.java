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
import mx.gob.sat.siat.juridica.singleton.JuridicaSingletonService;
import weblogic.jndi.Environment;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * 
 * @author Softtek
 * 
 */



public  class JuridicaSingletonClientJndiClusterServicesImpl extends BaseSerializableBusinessServices implements
JuridicaSingletonClientServices {
    private static final long serialVersionUID = 1L;


    @Override
    public Boolean permiteEjecucion(String idProceso) {
        JuridicaSingletonService juridicaSingletonService = getSingleton();
        Boolean permiteEjecucion = juridicaSingletonService.permiteEjecucionProceso(idProceso);
        getLogger().info("ejecución permitida jdni: {}", permiteEjecucion);
        return permiteEjecucion;
    }
    
    
    private JuridicaSingletonService getSingleton()  {
    
        JuridicaSingletonService singleton = null;
        try {
        Environment env = new Environment();
  
  
        Context ctx = env.getInitialContext();
        
        Object obj = ctx.lookup(JuridicaSingletonService.JNDI_NAME);
    
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
