/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the CAT_ENUMERACION_D_TR database table.
 * 
 * @author softtek
 */
@Embeddable
public class EnumeracionTrPK extends BaseModel implements Serializable {
    // default serial version id, required for serializable classes.

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "cveLenguaje" tipo String
     */
    @Column(name = "IDLENGUAJE")
    private String cveLenguaje;

    /**
     * Atributo privado "cveEnumeracionD" tipo String
     */
    @Column(name = "IDENUMERACIOND")
    private String cveEnumeracionD;

    /**
     * Constructor vacio
     */
    public EnumeracionTrPK() {}

    /**
     * 
     * @return cveLenguaje
     */
    public String getCveLenguaje() {
        return this.cveLenguaje;
    }

    /**
     * 
     * @param cveLenguaje
     *            a fijar
     */
    public void setCveLenguaje(String cveLenguaje) {
        this.cveLenguaje = cveLenguaje;
    }

    /**
     * 
     * @return cveEnumeracionD
     */
    public String getCveEnumeracion() {
        return this.cveEnumeracionD;
    }

    /**
     * 
     * @param cveEnumeracionD
     *            a fijar
     */
    public void setCveEnumeracion(String cveEnumeracionD) {
        this.cveEnumeracionD = cveEnumeracionD;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EnumeracionTrPK)) {
            return false;
        }
        EnumeracionTrPK castOther = (EnumeracionTrPK) other;
        return this.cveLenguaje.equals(castOther.cveLenguaje) && this.cveEnumeracionD.equals(castOther.cveEnumeracionD);

    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        final int prime = 31;
        int hash = NumerosConstantes.DIECISIETE;
        hash = hash * prime + this.cveLenguaje.hashCode();
        hash = hash * prime + this.cveEnumeracionD.hashCode();

        return hash;
    }
}
