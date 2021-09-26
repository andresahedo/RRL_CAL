/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author daniel.osorio
 */
@Embeddable
public class SecuenciaPK extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "tipoSecuencia" tipo String
     */
    @Basic(optional = false)
    @Column(name = "IDETIPOSECUENCIA")
    private String tipoSecuencia;

    /**
     * Atributo privado "claveGenerica1" tipo String
     */
    @Basic(optional = false)
    @Column(name = "CVEGENERICA1")
    private String claveGenerica1;

    /**
     * Atributo privado "claveGenerica2" tipo String
     */
    @Basic(optional = false)
    @Column(name = "CVEGENERICA2")
    private String claveGenerica2;

    /**
     * Constructor vacio
     */
    public SecuenciaPK() {}

    /**
     * Sobrecarga de constructor
     * 
     * @param tipoSecuencia
     * @param claveGenericaUno
     * @param claveGenericaDos
     */
    public SecuenciaPK(String tipoSecuencia, String claveGenericaUno, String claveGenericaDos) {
        this.tipoSecuencia = tipoSecuencia;
        this.claveGenerica1 = claveGenericaUno;
        this.claveGenerica2 = claveGenericaDos;
    }

    /**
     * 
     * @return tipoSecuencia
     */
    public String getTipoSecuencia() {
        return tipoSecuencia;
    }

    /**
     * 
     * @param tipoSecuencia
     *            a fijar
     */
    public void setTipoSecuencia(String tipoSecuencia) {
        this.tipoSecuencia = tipoSecuencia;
    }

    /**
     * 
     * @return claveGenerica1
     */
    public String getClaveGenerica1() {
        return claveGenerica1;
    }

    /**
     * 
     * @param claveGenerica1
     *            a fijar
     */
    public void setClaveGenerica1(String claveGenerica1) {
        this.claveGenerica1 = claveGenerica1;
    }

    /**
     * 
     * @return claveGenerica2
     */
    public String getClaveGenerica2() {
        return claveGenerica2;
    }

    /**
     * 
     * @param claveGenerica2
     *            a fijar
     */
    public void setClaveGenerica2(String claveGenerica2) {
        this.claveGenerica2 = claveGenerica2;
    }
}
