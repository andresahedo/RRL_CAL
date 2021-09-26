/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.controller;

import mx.gob.sat.siat.juridica.base.controller.BaseControllerBean;
import mx.gob.sat.siat.juridica.base.web.controller.bean.bussiness.ReinicioFoliosBussinessBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Controller que implementa las reglas de negocio para el login.
 * 
 * @author softtek
 * 
 */
@ManagedBean(name = "reinicioFoliosController")
@RequestScoped
public class ReinicioFoliosController extends BaseControllerBean {
    /**
     * Numero de serie.
     */
    private static final long serialVersionUID = -2088216086284425905L;
    
    private final transient Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * Controller que implementa las reglas de negocio para la
     * autenticacion.
     */
    @ManagedProperty(value = "#{reinicioFoliosBussinessBean}")
    private ReinicioFoliosBussinessBean reinicioFoliosBussinessBean;
    
    private Integer secuenciaActual;
    
    public void calcularFolio()  {
        this.secuenciaActual = reinicioFoliosBussinessBean.calcularFolio();
        logger.debug("secuenciaActual:" + secuenciaActual);
    }
    
    public void reiniciarFolios()  {
        reinicioFoliosBussinessBean.reiniciarFolios();
    }
    
    public void validaAcceso() {
    
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                
        if(!reinicioFoliosBussinessBean.isAdministracionVisible()){
             try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(request.getContextPath() + "/resources/pages/error/accesoDenegado.jsf");
            }
            catch (IOException e) {
                logger.error("Error al valida acceso del rol: ", e);
            }
        }
    }
    

    public ReinicioFoliosBussinessBean getReinicioFoliosBussinessBean() {
        return reinicioFoliosBussinessBean;
    }

    public void setReinicioFoliosBussinessBean(ReinicioFoliosBussinessBean reinicioFoliosBussinessBean) {
        this.reinicioFoliosBussinessBean = reinicioFoliosBussinessBean;
    }
    
    public Integer getSecuenciaActual(){
        return this.secuenciaActual;
    }
    public void setSecuenciaActual(Integer secuenciaActual){
        this.secuenciaActual = secuenciaActual;
    }
    
}
