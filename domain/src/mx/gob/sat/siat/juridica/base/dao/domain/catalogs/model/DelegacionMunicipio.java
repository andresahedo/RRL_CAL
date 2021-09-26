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
import java.util.List;

/**
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCC_DELEGMUN")
public class DelegacionMunicipio extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "clave" tipo String
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDDELEGMUN")
    private String clave;

    /**
     * Atributo privado "entidadFederativa" tipo EntidadFederativa
     */
    @JoinColumn(name = "IDENTIDAD", referencedColumnName = "IDENTIDAD")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntidadFederativa entidadFederativa;

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
     * Atributo privado "idMunicipioIDC" tipo String
     */
    @Column(name = "SATMUNICIPALITY")
    private String idMunicipioIDC;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Atributo privado "colonias" tipo List<Colonia>
     */
    @OneToMany(mappedBy = "delegacionMunicipio", fetch = FetchType.LAZY)
    private List<Colonia> colonias;

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
     * Sobrecarga del constructor
     */
    public DelegacionMunicipio(String clave) {
        this.clave = clave;
    }

    /**
     * Constructor vacio
     */
    public DelegacionMunicipio() {}

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
     * @return entidadFederativa
     */
    public EntidadFederativa getEntidadFederativa() {
        return entidadFederativa;
    }

    /**
     * 
     * @param entidadFederativa
     *            a fijar
     */
    public void setEntidadFederativa(EntidadFederativa entidadFederativa) {
        this.entidadFederativa = entidadFederativa;
    }

    /**
     * 
     * @return colonias
     */
    public List<Colonia> getColonias() {
        return colonias;
    }

    /**
     * 
     * @param colonias
     *            a fijar
     */
    public void setColonias(List<Colonia> colonias) {
        this.colonias = colonias;
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
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof DelegacionMunicipio)) {
            return false;
        }
        DelegacionMunicipio other = (DelegacionMunicipio) object;
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
     * @return "entitys.DelegacionMunicipio[clave=" + clave + "]";
     */
    public String toString() {
        return "entitys.DelegacionMunicipio[clave=" + clave + "]";
    }

    /**
     * 
     * @param idMunicipioIDC
     *            a fijar
     */
    public void setIdMunicipioIDC(String idMunicipioIDC) {
        this.idMunicipioIDC = idMunicipioIDC;
    }

    /**
     * 
     * @return idMunicipioIDC
     */
    public String getIdMunicipioIDC() {
        return idMunicipioIDC;
    }

}
