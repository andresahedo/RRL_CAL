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
@DiscriminatorValue(DiscriminadorConstants.NOTIFICACION_REQUERIMIENTO)
public class NotificacionRequerimiento extends Notificacion {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -1372169515335956420L;

    /**
     * Atributo privado "idDictamen" tipo Long
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IDREQUERIMIENTO", referencedColumnName = "IDREQUERIMIENTO")
    private Requerimiento requerimiento;

    /**
     * 
     * @return requerimiento
     */
    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    /**
     * 
     * @param requerimiento
     *            a fijar
     */
    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

}
