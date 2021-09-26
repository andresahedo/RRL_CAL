/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;
import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@DiscriminatorValue(DiscriminadorConstants.PERSONA_INTERNA)
public class PersonaInterna extends Persona {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 7654758798756786L;

    @Transient
    private String numPendientes;

    @Column(name = "IDUSUARIO", length = NumerosConstantes.VEINTICINCO, insertable = false, updatable = false)
    private String claveUsuario;

    /**
     * @return the numPendientes
     */
    public String getNumPendientes() {
        return numPendientes;
    }

    /**
     * @param numPendientes
     *            the numPendientes to set
     */
    public void setNumPendientes(String numPendientes) {
        this.numPendientes = numPendientes;
    }

    /**
     * @return the claveUsuario
     */
    public String getClaveUsuario() {
        return claveUsuario;
    }

    /**
     * @param claveUsuario
     *            the claveUsuario to set
     */
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

}
