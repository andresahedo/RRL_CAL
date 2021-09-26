/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */

package mx.gob.sat.siat.juridica.base.web.controller.bean.model;

import java.io.Serializable;

/**
 * Clase Base para los ModelBean
 * 
 * Bean que contienen objetos del modelo los cuales comparte con
 * ControllerBeans y/o BackingBeans
 * 
 * @author Softtek
 */
public class BaseModelBean implements Serializable {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -1;

    /** Constructor vacio */
    protected BaseModelBean() {
        super();
    }
}
