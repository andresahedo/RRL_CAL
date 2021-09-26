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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Clase Base para la Colonia
 * 
 * Bean que contienen atributos del modelo
 * 
 * @author Softtek
 */
@Entity
@Table(name = "RVCC_COLONIA")
public class Colonia extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "clave" tipo String
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDCOLONIA")
    private String clave;

    /**
     * Atributo privado "delegacionMunicipio" tipo DelegacionMunicipio
     */
    @JoinColumn(name = "IDDELEGMUN", referencedColumnName = "IDDELEGMUN")
    @ManyToOne(fetch = FetchType.LAZY)
    private DelegacionMunicipio delegacionMunicipio;

    /**
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * Atributo privado "fechaCaptura" tipo Date
     */
    @Column(name = "FECCAPTURA")
    @Temporal(TemporalType.DATE)
    private Date fechaCaptura;

    /**
     * Atributo privado "localidad" tipo Localidad
     */
    @JoinColumn(name = "IDLOCALIDAD", referencedColumnName = "IDLOCALIDAD")
    @ManyToOne(fetch = FetchType.LAZY)
    private Localidad localidad;

    /**
     * Atributo privado "cp" tipo String
     */
    @Column(name = "CP")
    private String cp;

    /**
     * Atributo privado "idColoniaIDC" tipo String
     */
    @Column(name = "SATCOLONYCD")
    private String idColoniaIDC;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Constructor vacio
     */
    public Colonia() {}

    /**
     * Sobre carga del constructor
     */
    public Colonia(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaCaptura() {
        return fechaCaptura != null ? (Date) fechaCaptura.clone() : null;
    }

    /**
     * 
     * @param fechaCaptura
     *            a fijar
     */
    public void setFechaCaptura(Date fechaCaptura) {
        if (fechaCaptura != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaCaptura);
            this.fechaCaptura = cal.getTime();
        }
        else {
            this.fechaCaptura = null;
        }
    }

    /**
     * 
     * @return localidad
     */
    public Localidad getLocalidad() {
        return localidad;
    }

    /**
     * 
     * @param localidad
     *            a fijar
     */
    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    /**
     * 
     * @return cp
     */
    public String getCp() {
        return cp;
    }

    /**
     * 
     * @param cp
     *            fijar
     */
    public void setCp(String cp) {
        this.cp = cp;
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
     * @return delegacionMunicipio
     */
    public DelegacionMunicipio getDelegacionMunicipio() {
        return delegacionMunicipio;
    }

    /**
     * 
     * @param delegacionMunicipio
     *            a fijar
     */
    public void setDelegacionMunicipio(DelegacionMunicipio delegacionMunicipio) {
        this.delegacionMunicipio = delegacionMunicipio;
    }

    /**
     * Metodo hashCode
     */
    public int hashCode() {
        int hash = 0;
        hash += (clave != null ? clave.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo que verifica la instancia de la colonia
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof Colonia)) {
            return false;
        }
        Colonia other = (Colonia) object;
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
     * @return "entitys.Colonia[clave=" + clave + "]"
     */
    public String toString() {
        return "entitys.Colonia[clave=" + clave + "]";
    }

    /**
     * 
     * @param idColoniaIDC
     *            a fijar
     */
    public void setIdColoniaIDC(String idColoniaIDC) {
        this.idColoniaIDC = idColoniaIDC;
    }

    /**
     * 
     * @return idColoniaIDC
     */
    public String getIdColoniaIDC() {
        return idColoniaIDC;
    }

}
