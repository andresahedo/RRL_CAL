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
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCC_ENUMERACIONH")
public class EnumeracionH extends BaseModel implements Serializable {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "cveEnumeracionH" tipo String
     */
    @Id
    @Column(name = "IDENUMERACIONH")
    private String cveEnumeracionH;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Atributo privado "fecCaptura" tipo Date
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FECCAPTURA")
    private Date fecCaptura;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Constructor vacio
     */
    public EnumeracionH() {}

    /**
     * 
     * @return cveEnumeracionH
     */
    public String getCveEnumeracionH() {
        return this.cveEnumeracionH;
    }

    /**
     * 
     * @param cveEnumeracionH
     *            a fijar
     */
    public void setCveEnumeracionH(String cveEnumeracionH) {
        this.cveEnumeracionH = cveEnumeracionH;
    }

    /**
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * 
     * @param descripcion
     *            a fijar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return fecCaptura
     */
    public Date getFecCaptura() {
        return fecCaptura != null ? (Date) fecCaptura.clone() : null;
    }

    /**
     * 
     * @param fecCaptura
     *            a fijar
     */
    public void setFecCaptura(Date fecCaptura) {
        if (fecCaptura != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fecCaptura);
            this.fecCaptura = cal.getTime();
        }
        else {
            this.fecCaptura = null;
        }
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
     * 
     * @return vigencia
     */
    public Vigencia getVigencia() {
        return vigencia;
    }

}
