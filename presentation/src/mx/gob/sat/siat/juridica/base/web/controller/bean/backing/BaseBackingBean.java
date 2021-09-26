/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */

package mx.gob.sat.siat.juridica.base.web.controller.bean.backing;

import java.io.Serializable;

/**
 * Clase Base para los BackingBeans
 * 
 * Bean que mapean los componentes de la vista mediante atributos, con
 * la intencion de controlar su comportamiento.
 * 
 * @author Hiram Cordova
 */
public class BaseBackingBean implements Serializable {

    private static final long serialVersionUID = 1121027861361406906L;

    /** Constructor vacio */
    protected BaseBackingBean() {
        super();
    }

}
