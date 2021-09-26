/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author daniel.osorio
 */
@Entity
@Table(name = "RVCC_SECUENCIA")
public class Secuencia extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 6478698709756334L;

    /**
     * Atributo privado "secuenciaPK" tipo SecuenciaPK
     */
    @EmbeddedId
    private SecuenciaPK secuenciaPK;

    /**
     * Atributo privado "secuencia" tipo Integer
     */
    @Column(name = "SECUENCIA")
    private Integer secuencia;

    /**
     * Constructor
     */
    public Secuencia() {
        secuenciaPK = new SecuenciaPK();
    }

    /**
     * Sobrecarga de Constructor
     * 
     * @param tipoSecuencia
     * @param claveGenericaUno
     * @param claveGenericaDos
     */
    public Secuencia(String tipoSecuencia, String claveGenericaUno, String claveGenericaDos) {
        this.secuenciaPK = new SecuenciaPK(tipoSecuencia, claveGenericaUno, claveGenericaDos);
    }

    /**
     * 
     * @return secuenciaPK
     */
    public SecuenciaPK getSecuenciaPK() {
        return secuenciaPK;
    }

    /**
     * 
     * @param secuenciaPK
     *            a fijar
     */
    public void setSecuenciaPK(SecuenciaPK secuenciaPK) {
        this.secuenciaPK = secuenciaPK;
    }

    /**
     * 
     * @return secuencia
     */
    public Integer getSecuencia() {
        return secuencia;
    }

    /**
     * 
     * @param secuencia
     *            a fijar
     */
    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }
}
