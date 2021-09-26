/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness;

import mx.gob.sat.siat.juridica.base.api.ReinicioFoliosFacade;
import mx.gob.sat.siat.juridica.base.bussiness.BaseBussinessBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
/**
 * Bussiness que implementa la logica de negocio para el login.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "reinicioFoliosBussinessBean")
@NoneScoped
public class ReinicioFoliosBussinessBean extends BaseBussinessBean {

    /**
     * Numero de serie.
     */
    private static final long serialVersionUID = -7774939480059154619L;

    /**
     * Facade utilizada para login.
     */
    @ManagedProperty("#{reinicioFoliosFacade}")
    private ReinicioFoliosFacade reinicioFoliosFacade;

    
    public boolean isAdministracionVisible(){
        return reinicioFoliosFacade.isAdministracionVisible();
    }
    
    public Integer calcularFolio()  {
        return reinicioFoliosFacade.calcularFolio();
    }
    
    public void reiniciarFolios()  {
        reinicioFoliosFacade.reiniciarFolios();        
    }
    
    public ReinicioFoliosFacade getReinicioFoliosFacade() {
        return reinicioFoliosFacade;
    }

    public void setReinicioFoliosFacade(ReinicioFoliosFacade reinicioFoliosFacade) {
        this.reinicioFoliosFacade = reinicioFoliosFacade;
    }
}
