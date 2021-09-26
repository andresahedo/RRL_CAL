/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCC_PAIS")
public class Pais extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Atributo privado "clave" tipo String
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDPAIS")
    private String clave;

    /**
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * Atributo privado "cvePaisWco" tipo String
     */
    @Column(name = "IDPAISWCO")
    private String cvePaisWco;

    /**
     * Atributo privado "fechaCaptura" tipo Date
     */
    @Column(name = "FECCAPTURA")
    @Temporal(TemporalType.DATE)
    private Date fechaCaptura;

    /**
     * Atributo privado "blnRestriccion" tipo Integer
     */
    @Column(name = "BLNRESTRICCION")
    private Integer blnRestriccion;

    /**
     * Atributo privado "nombreAlterno" tipo String
     */
    @Column(name = "NOMBREALTERNO")
    private String nombreAlterno;

    /**
     * 
     * @return cvePaisWco
     */
    public String getCvePaisWco() {
        return cvePaisWco;
    }

    /**
     * 
     * @param cvePaisWco
     *            a fijar
     */
    public void setCvePaisWco(String cvePaisWco) {
        this.cvePaisWco = cvePaisWco;
    }

    /**
     * Constructor vacio
     */
    public Pais() {}

    /**
     * Sobrecarga de Constructor
     * 
     * @param clave
     */
    public Pais(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * 
     * @param clave
     *            a fijar
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre
     *            a fijar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * 
     * @return fechaCaptura
     */
    public Date getFechaCaptura() {
        return (null == fechaCaptura ? null : (Date) fechaCaptura.clone());
    }

    /**
     * 
     * @param fechaCaptura
     *            a fijar
     */
    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = (null == fechaCaptura ? null : (Date) fechaCaptura.clone());
    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (clave != null ? clave.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @return vigencia
     */
    public Vigencia getVigencia() {
        return vigencia;
    }

    /**
     * 
     * @param vigencia
     *            a fijar
     */
    public void setVigencia(Vigencia vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * Atributos a fijar
     * 
     * @param fechaInicioVigencia
     * @param fechaFinVigencia
     * @param blnActivo
     */
    public void setVigencia(Date fechaInicioVigencia, Date fechaFinVigencia, Integer blnActivo) {
        this.vigencia = new Vigencia(fechaInicioVigencia, fechaFinVigencia, blnActivo);
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     * 
     */

    public boolean equals(Object object) {
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if (this.clave == null || other.clave == null) {
            return false;
        }
        else if (!this.clave.equals(other.clave)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entitys.Pais[clave=" + clave + "]";
     */
    public String toString() {
        return "entitys.Pais[clave=" + clave + "]";
    }

    /**
     * 
     * @return blnRestriccion
     */
    public Integer getBlnRestriccion() {
        return blnRestriccion;
    }

    /**
     * 
     * @param blnRestriccion
     *            a fijar
     */
    public void setBlnRestriccion(Integer blnRestriccion) {
        this.blnRestriccion = blnRestriccion;
    }

    /**
     * 
     * @return nombreAlterno
     */
    public String getNombreAlterno() {
        return nombreAlterno;
    }

    /**
     * 
     * @param nombreAlterno
     *            a fijar
     */
    public void setNombreAlterno(String nombreAlterno) {
        this.nombreAlterno = nombreAlterno;
    }

}
