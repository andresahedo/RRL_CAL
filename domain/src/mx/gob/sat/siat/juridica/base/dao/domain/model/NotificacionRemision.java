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

import javax.persistence.*;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@DiscriminatorValue(DiscriminadorConstants.NOTIFICACION_REMISION)
public class NotificacionRemision extends Notificacion {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -439208117867075252L;

    /**
     * Atributo privado "remision" tipo Remision
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IDREMISION", referencedColumnName = "IDREMISION")
    private Remision remision;

    /**
     * 
     * @return remision
     */
    public Remision getRemision() {
        return remision;
    }

    /**
     * 
     * @param remision
     *            a fijar
     */
    public void setRemision(Remision remision) {
        this.remision = remision;
    }

}
