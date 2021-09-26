/*

 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 * 
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.*;

import javax.persistence.*;

/**
 * 
 * @author softtek
 */

@Entity
@Table(name = "RVCT_SOLDIRECCION")
@NamedQueries({
        @NamedQuery(name = "Domicilio.buscarTodos", query = "SELECT ds FROM DomicilioSolicitud ds"),
        @NamedQuery(name = "Domicilio.buscarPorId",
                query = "SELECT ds FROM DomicilioSolicitud ds WHERE ds.idDomicilio = :idDomicilio"),
        @NamedQuery(name = "Domicilio.buscarPorCP",
                query = "SELECT ds FROM DomicilioSolicitud ds WHERE ds.codigoPostal = :codigoPostal") })
public class DomicilioSolicitud extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idDomicilio" tipo Long
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDSOLDIRECCION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_SOLDIRECCION")
    @SequenceGenerator(name = "RVCQ_SOLDIRECCION", sequenceName = "RVCQ_SOLDIRECCION", allocationSize = 1)
    private Long idDomicilio;

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
     * Atributo privado "delegacionMunicipio" tipo DelegacionMunicipio
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDDELEGMUN", referencedColumnName = "IDDELEGMUN")
    private DelegacionMunicipio delegacionMunicipio;

    /**
     * Atributo privado "entidadFederativa" tipo EntidadFederativa
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDENTIDAD", referencedColumnName = "IDENTIDAD")
    private EntidadFederativa entidadFederativa;

    /**
     * Atributo privado "colonia" tipo Colonia
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDCOLONIA", referencedColumnName = "IDCOLONIA")
    private Colonia colonia;

    /**
     * Atributo privado "localidad" tipo Localidad
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDLOCALIDAD", referencedColumnName = "IDLOCALIDAD")
    private Localidad localidad;

    /**
     * Atributo privado "codigoPostal" tipo String
     */
    @Column(name = "CP")
    private String codigoPostal;

    /**
     * Atributo privado "datosDomicilio" tipo String
     */
    @Column(name = "ESTADOMUNCOLEXTR")
    private String datosDomicilio;

    /**
     * Atributo privado "ciudad" tipo String
     */
    @Column(name = "CIUDAD")
    private String ciudad;

    /**
     * Atributo privado "telefono" tipo String
     */
    @Column(name = "TELEFONO")
    private String telefono;

    /**
     * Atributo privado "fax" tipo String
     */
    @Column(name = "FAX")
    private String fax;

    /**
     * Atributo privado "descripcionMunicipio" tipo String
     */
    @Column(name = "MUNICIPIO")
    private String descripcionMunicipio;

    /**
     * Atributo privado "descripcionColonia" tipo String
     */
    @Column(name = "COLONIA")
    private String descripcionColonia;

    /**
     * Atributo privado "descripcionUbicacion" tipo String
     */
    @Column(name = "DESCUBICACION")
    private String descripcionUbicacion;

    /**
     * Atributo privado "claveAdministracionLocalRecaudadora" tipo
     * String
     */
    @Column(name = "CVEADMLOCALRECIDC")
    private String claveAdministracionLocalRecaudadora;

    /**
     * Constructor vacio
     */
    public DomicilioSolicitud() {}

    /**
     * 
     * @param idDomicilio
     *            a fijar
     */
    public DomicilioSolicitud(Long idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    /**
     * 
     * @return idDomicilio
     */
    public Long getIdDomicilio() {
        return idDomicilio;
    }

    /**
     * 
     * @param idDomicilio
     *            a fijar
     */
    public void setIdDomicilio(Long idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

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
     * @return datosDomicilio
     */
    public String getInformacionExtra() {
        return datosDomicilio;
    }

    /**
     * 
     * @param informacionExtra
     *            a fijar
     */
    public void setInformacionExtra(String informacionExtra) {
        this.datosDomicilio = informacionExtra;
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
     * @return ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * 
     * @param ciudad
     *            a fijar
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (idDomicilio != null ? idDomicilio.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof DomicilioSolicitud)) {
            return false;
        }
        DomicilioSolicitud other = (DomicilioSolicitud) object;
        if ((this.idDomicilio == null && other.idDomicilio != null)
                || (this.idDomicilio != null && !this.idDomicilio.equals(other.idDomicilio))) {
            return false;
        }
        return true;
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
     * @return descripcionMunicipio
     */
    public String getDescripcionMunicipio() {
        return descripcionMunicipio;
    }

    /**
     * 
     * @param descripcionMunicipio
     *            a fijar
     */
    public void setDescripcionMunicipio(String descripcionMunicipio) {
        this.descripcionMunicipio = descripcionMunicipio;
    }

    /**
     * 
     * @return descripcionColonia
     */
    public String getDescripcionColonia() {
        return descripcionColonia;
    }

    /**
     * 
     * @param descripcionColonia
     *            a fijar
     */
    public void setDescripcionColonia(String descripcionColonia) {
        this.descripcionColonia = descripcionColonia;
    }

    /**
     * 
     * @return descripcionUbicacion
     */
    public String getDescripcionUbicacion() {
        return descripcionUbicacion;
    }

    /**
     * 
     * @param descripcionUbicacion
     *            a fijar
     */
    public void setDescripcionUbicacion(String descripcionUbicacion) {
        this.descripcionUbicacion = descripcionUbicacion;
    }

    /**
     * 
     * @return claveAdministracionLocalRecaudadora
     */
    public String getClaveAdministracionLocalRecaudadora() {
        return claveAdministracionLocalRecaudadora;
    }

    /**
     * 
     * @param claveAdministracionLocalRecaudadora
     *            a fijar
     */
    public void setClaveAdministracionLocalRecaudadora(String claveAdministracionLocalRecaudadora) {
        this.claveAdministracionLocalRecaudadora = claveAdministracionLocalRecaudadora;
    }

}
