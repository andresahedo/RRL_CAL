/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import java.io.Serializable;

/**
 * Clase principal serializada que servira de base para los servicios
 * de negocio
 * 
 * @author softtek
 * 
 */
public class BaseSerializableBusinessServices extends BaseBusinessServices implements Serializable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = -6033174526651084876L;

    /**
     * Constructor de la clase
     */
    protected BaseSerializableBusinessServices() {
        super();
    }
}
