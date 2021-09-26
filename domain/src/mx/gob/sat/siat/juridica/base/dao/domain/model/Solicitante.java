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
import javax.persistence.Transient;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@DiscriminatorValue(DiscriminadorConstants.SOLICITANTE)
public class Solicitante extends PersonaSolicitud {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5432143357657651L;

    /**
     * Sobrecarga de constructor
     * 
     * @param idSolicitud
     */
    public Solicitante(Long idSolicitud) {
        super(idSolicitud);
    }

    @Transient
    private boolean tieneCorreo;

    /**
     * Constructor
     */
    public Solicitante() {}

    public boolean isTieneCorreo() {
        return tieneCorreo;
    }

    public void setTieneCorreo(boolean tieneCorreo) {
        this.tieneCorreo = tieneCorreo;
    }

}
