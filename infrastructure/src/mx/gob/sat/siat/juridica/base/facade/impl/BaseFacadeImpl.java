/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.facade.impl;

import mx.gob.sat.siat.juridica.base.dto.UserProfileDTO;
import mx.gob.sat.siat.juridica.base.facade.BaseFacade;
import mx.gob.sat.siat.juridica.base.util.constante.LoggerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Implementacion de la fachada Base, proporciona soporte para el
 * manejo del log.
 * 
 * @author Softtek
 * 
 */
public class BaseFacadeImpl implements BaseFacade, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4421382544810508762L;

    private transient Logger logger = LoggerFactory.getLogger(LoggerConstants.FACADE_LOGGER);

    public UserProfileDTO getUserProfile() {

        return (UserProfileDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .get("userProfile");
    }

    public Logger getLogger() {
        return this.logger;
    }
}
