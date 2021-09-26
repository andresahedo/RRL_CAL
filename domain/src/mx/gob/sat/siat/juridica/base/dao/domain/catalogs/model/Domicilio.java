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

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "IDETIPODIRECCION", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ABSTRACT")
@Table(name = "RVCT_DIRECCION")
public class Domicilio extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "direccion" tipo long
     */
    @Id
    @Column(name = "IDDIRECCION", insertable = false, updatable = false)
    private long direccion;

    /**
     * Atributo privado "persona" tipo Persona
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDPERSONA")
    private Persona persona;

    /**
     * Atributo privado "IDETIPODIRECCION" tipo String
     */
    // Este atributo corresponde al discriminador y permite
    // que esta columna participe en los queries de jpql, es privada y
    // no tiene
    // set y get
    @Column(insertable = false, updatable = false)
    private String ideTipoDireccion;

    /**
     * Atributo privado "calle" tipo String
     */
    @Column(name = "CALLE")
    private String calle;

    /**
     * Atributo privado "numeroExterior" tipo String
     */
    @Column(name = "NUMEXTERIOR")
    private String numeroExterior;

    /**
     * Atributo privado "numeroInterior" tipo String
     */
    @Column(name = "NUMINTERIOR")
    private String numeroInterior;

    /**
     * Atributo privado "pais" tipo Pais
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDPAIS", referencedColumnName = "IDPAIS")
    private Pais pais;

    /**
     * Atributo privado "entidadFederativa" tipo EntidadFederativa
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDENTIDAD")
    private EntidadFederativa entidadFederativa;

    /**
     * Atributo privado "delegacionMunicipio" tipo DelegacionMunicipio
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDDELEGMUN", referencedColumnName = "IDDELEGMUN")
    private DelegacionMunicipio delegacionMunicipio;

    /**
     * Atributo privado "colonia" tipo Colonia
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDCOLONIA", referencedColumnName = "IDCOLONIA")
    private Colonia colonia;

    /**
     * Atributo privado "codigoPostal" tipo String
     */
    @Column(name = "CP")
    private String codigoPostal;

    /**
     * Atributo privado "informacionExtra" tipo String
     */
    @Column(name = "ESTADOMUNCOLEXTR")
    private String informacionExtra;

    /**
     * Atributo privado "localidad" tipo Localidad
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDLOCALIDAD", referencedColumnName = "IDLOCALIDAD")
    private Localidad localidad;

    /**
     * Atributo privado "fax" tipo String
     */
    @Column(name = "FAX")
    private String fax;

    /**
     * Atributo privado "telefono" tipo String
     */
    @Column(name = "TELEFONO")
    private String telefono;

    /**
     * Atributo privado "telefonos" tipo String
     */
    @Column(name = "TELEFONOS")
    private String telefonos;

    /**
     * 
     * @return calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * 
     * @param calle
     *            a fijar
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * 
     * @return numeroExterior
     */
    public String getNumeroExterior() {
        return numeroExterior;
    }

    /**
     * 
     * @param numeroExterior
     *            a fijar
     */
    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    /**
     * 
     * @return numeroInterior
     */
    public String getNumeroInterior() {
        return numeroInterior;
    }

    /**
     * 
     * @param numeroInterior
     *            a fijar
     */
    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    /**
     * 
     * @return codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal;
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
     * @return informacionExtra
     */
    public String getInformacionExtra() {
        return informacionExtra;
    }

    /**
     * 
     * @param informacionExtra
     *            a fijar
     */
    public void setInformacionExtra(String informacionExtra) {
        this.informacionExtra = informacionExtra;
    }

    /**
     * 
     * @return colonia
     */
    public Colonia getColonia() {
        return colonia;
    }

    /**
     * 
     * @param colonia
     *            a fijar
     */
    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
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
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * 
     * @param telefono
     *            a fijar
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * 
     * @return fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * 
     * @param fax
     *            a fijar
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 
     * @return direccion
     */
    public long getDireccion() {
        return direccion;
    }

    /**
     * 
     * @param direccion
     *            a fijar
     */
    public void setDireccion(long direccion) {
        this.direccion = direccion;
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
     * @return telefonos
     */
    public String getTelefonos() {
        return telefonos;
    }

    /**
     * 
     * @param telefonos
     *            a fijar
     */
    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    /**
     * 
     * @param persona
     *            a fijar
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * 
     * @return persona
     */
    public Persona getPersona() {
        return persona;
    }

    public String getIdeTipoDireccion() {
        return this.ideTipoDireccion;
    }

    public void setIdeTipoDireccion(String ideTipoDireccion) {
        this.ideTipoDireccion = ideTipoDireccion;
    }
}
