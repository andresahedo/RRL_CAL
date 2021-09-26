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
@Table(name = "RVCC_ENTIDAD")
public class EntidadFederativa extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "clave" tipo String
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDENTIDAD")
    private String clave;

    /**
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * Atributo privado "pais" tipo Pais
     */
    @JoinColumn(name = "IDPAIS", referencedColumnName = "IDPAIS")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pais pais;

    /**
     * Atributo privado "fechaCaptura" tipo Date
     */
    @Column(name = "FECCAPTURA")
    @Temporal(TemporalType.DATE)
    private Date fechaCaptura;

    /**
     * Atributo privado "claveEnIDC" tipo String
     */
    @Column(name = "CODENTIDADIDC")
    private String claveEnIDC;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Constructor vacio
     */
    public EntidadFederativa() {}

    /**
     * Sobrecarga del constructor
     * 
     * @param clave
     */
    public EntidadFederativa(String clave) {
        this.clave = clave;
    }

    /**
     * Sobrecarga del constructor
     * 
     * @param clave
     *            , nombre
     */
    public EntidadFederativa(String clave, String nombre) {
        this.clave = clave;
        this.nombre = nombre;
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
     * 
     * @return claveEnIDC
     */
    public String getClaveEnIDC() {
        return claveEnIDC;
    }

    /**
     * 
     * @param claveEnIDC
     *            a fijar
     */
    public void setClaveEnIDC(String claveEnIDC) {
        this.claveEnIDC = claveEnIDC;
    }

    /**
     * 
     * @return pais
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * 
     * @param pais
     *            a fijar
     */
    public void setPais(Pais pais) {
        this.pais = pais;
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
     * Metodo hashCode hash
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
        if (!(object instanceof EntidadFederativa)) {
            return false;
        }
        EntidadFederativa other = (EntidadFederativa) object;
        if (this.clave == null || other.clave == null) {
            return false;
        }
        else if (!this.clave.equals(other.clave)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString return "entitys.EntidadFederativa[clave=" +
     * clave + "]";
     */
    public String toString() {
        return "entitys.EntidadFederativa[clave=" + clave + "]";
    }

}
