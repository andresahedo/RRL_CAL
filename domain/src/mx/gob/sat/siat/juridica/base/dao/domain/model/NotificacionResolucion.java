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
@DiscriminatorValue(DiscriminadorConstants.NOTIFICACION_RESOLUCION)
public class NotificacionResolucion extends Notificacion {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 677703024648436248L;

    /**
     * Atributo privado "resolucion" tipo Resolucion
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IDRESOLUCION", referencedColumnName = "IDRESOLUCION")
    private Resolucion resolucion;

    /**
     * 
     * @return resolucion
     */
    public Resolucion getResolucion() {
        return resolucion;
    }

    /**
     * 
     * @param resolucion
     *            a fijar
     */
    public void setResolucion(Resolucion resolucion) {
        this.resolucion = resolucion;
    }

}
