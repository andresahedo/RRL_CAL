/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */

package mx.gob.sat.siat.juridica.base.web.controller.bean.support;

import java.io.Serializable;

/**
 * Clase Base para los ControllerBean
 * 
 * Bean que contienen objetos, regularmente listas que no cambian en
 * la aplicación, por ejemplo una lista de estados o paises, que se
 * reflejan en combos y estos pueden ser utilizados en diferentes
 * pantallas de la aplicación.
 * 
 * @author Softtek
 */
public class BaseSupportBean implements Serializable {
    /**
     * Numero de serie.
     */
    private static final long serialVersionUID = -1;

    /** Constructor vacio */
    protected BaseSupportBean() {
        super();
    }

}
