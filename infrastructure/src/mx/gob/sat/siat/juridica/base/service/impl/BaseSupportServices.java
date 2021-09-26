/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase principal que sirve de base para los servicios de soporte
 * 
 * @author softtek
 * 
 */
public class BaseSupportServices {

    /**
     * Instancia para el registro de eventos
     */
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Constructor de la clase
     */
    protected BaseSupportServices() {
        super();
    }

    public Logger getLogger() {
        return this.logger;
    }
}
