package mx.gob.sat.siat.juridica.base.api.impl;

import mx.gob.sat.siat.juridica.base.api.ReinicioFoliosFacade;
import mx.gob.sat.siat.juridica.base.facade.impl.BaseFacadeImpl;
import mx.gob.sat.siat.juridica.base.service.NumeroAsuntoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("reinicioFoliosFacade")
@Scope("request")
public class ReinicioFoliosFacadeImpl extends BaseFacadeImpl implements ReinicioFoliosFacade {

    /**
     * 
     */
    private static final long serialVersionUID = 5586907206068705190L;

    @Autowired
    private NumeroAsuntoServices numeroAsuntoServices;
    
    @Value("${configuracion.administracion.visible}")
    private boolean administracionVisible;
    
    public Integer calcularFolio()  {
        return numeroAsuntoServices.obtenerSecuencia("testSingleton2015", 1);
    }
    public void reiniciarFolios()  {
    
        numeroAsuntoServices.reiniciarSecuencias();
    }
    
    public boolean isAdministracionVisible(){
        return this.administracionVisible;
    }
    
    public void setAdministracionVisible(boolean administracionVisible){
        this.administracionVisible = administracionVisible;
    }
}
