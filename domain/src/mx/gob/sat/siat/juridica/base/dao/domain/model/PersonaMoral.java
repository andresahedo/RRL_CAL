/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@DiscriminatorValue(DiscriminadorConstants.PERSONA_MORAL)
public class PersonaMoral extends Persona {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 176734678866663L;

    /**
     * Losgitud del rfc
     */
    public static final int LONGITUD_RFC = 12;
}
