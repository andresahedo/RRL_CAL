/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@DiscriminatorValue(DiscriminadorConstants.PERSONA_OIR_RECIBIR_NOTIFIACIONES)
public class PersonaOirRecibirNotificaciones extends PersonaSolicitud {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 878827569995606884L;

    /**
     * Sobrecarga de constructor
     * 
     * @param idSolicitud
     */
    public PersonaOirRecibirNotificaciones(Long idSolicitud) {
        super(idSolicitud);
    }

    /**
     * Constructor vacio
     */
    public PersonaOirRecibirNotificaciones() {}

}
