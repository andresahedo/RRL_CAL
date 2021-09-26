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
@Table(name = "RVCC_LOCALIDAD")
public class Localidad extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "clave" tipo String
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDLOCALIDAD")
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
     * Atributo privado "codigoPostal" tipo String
     */
    @Column(name = "CP")
    private String codigoPostal;

    /**
     * Atributo privado "codigoSAT" tipo String
     */
    @Column(name = "SATTOWNCODE")
    private String codigoSAT;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * 
     * @return codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Constructor vacio
     */
    public Localidad() {}

    /**
     * 
     * @param clave
     *            a fijar
     */
    public Localidad(String clave) {
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
     *            afijar
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
     * 
     * @return hah
     */
    public int hashCode() {
        int hash = 0;
        hash += (clave != null ? clave.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof Localidad)) {
            return false;
        }
        Localidad other = (Localidad) object;
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
     * @return "entitys.Colonia[clave=" + clave + "]";
     */
    public String toString() {
        return "entitys.Colonia[clave=" + clave + "]";
    }

    /**
     * 
     * @param codigoPostal
     *            a fijar
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * 
     * @return codigoSAT
     */
    public String getCodigoSAT() {
        return codigoSAT;
    }

    /**
     * 
     * @param codigoSAT
     *            a fijar
     */
    public void setCodigoSAT(String codigoSAT) {
        this.codigoSAT = codigoSAT;
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
}
