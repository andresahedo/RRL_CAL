/*
 *  Todos los Derechos Reservados © 2013 SAT
 *  Servicio de Administracion Tributaria (SAT).
 *  
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.juridica.base.web.controller.bean.utility;

import java.io.Serializable;

/**
 * Clase Base para los UtilityBean
 * 
 * Bean que contienen funcionalidad que puede ser reutilizable, por
 * ejemplo la creacion de reportes que pueden ser utilizados en
 * diferentes pantallas.
 * 
 * @author Softtek
 */
public class BaseUtilityBean implements Serializable {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -1;

    /** Constructor vacio */
    protected BaseUtilityBean() {
        super();
    }
}
