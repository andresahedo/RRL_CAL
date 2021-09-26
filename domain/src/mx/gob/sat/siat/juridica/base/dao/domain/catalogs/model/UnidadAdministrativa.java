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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.TipoUnidadAdministrativa;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "IDETIPOUNIDADADMINISTRATIVA", discriminatorType = DiscriminatorType.STRING)
@Table(name = "RVCC_UNIADMIN")
public class UnidadAdministrativa extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -183572299990992373L;

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
    @Column(name = "IDUNIADMIN")
    private String clave;

    /**
     * Atributo privado "claveUnidadAdminR" tipo String
     */
    @Column(name = "IDUNIADMINR")
    private String claveUnidadAdminR;

    /**
     * Atributo privado "ideTipoUnidadAdministrativa" tipo String
     */
    @Column(name = "IDETIPOUNIDADADMINISTRATIVA", insertable = false, updatable = false)
    private String ideTipoUnidadAdministrativa;

    /**
     * Atributo privado "nivel" tipo Integer
     */
    @Column(name = "NIVEL")
    private Integer nivel;

    /**
     * Atributo privado "acronimo" tipo String
     */
    @Column(name = "ACRONIMO")
    private String acronimo;

    /**
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * Atributo privado "claveAdministracionLocalRecaudadora" tipo
     * String
     */
    @Column(name = "CVEADMLOCALREC")
    private String claveAdministracionLocalRecaudadora;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    /**
     * Atributo privado "fechaExtincion" tipo Date
     */
    @Column(name = "FECEXTINCION")
    @Temporal(TemporalType.DATE)
    private Date fechaExtincion;

    /**
     * Atributo privado "entidadFederativa" tipo EntidadFederativa
     */
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "IDENTIDAD", nullable = true, insertable = false, updatable = false,
            referencedColumnName = "IDENTIDAD")
    private EntidadFederativa entidadFederativa;

    /**
     * Atributo privado "dependencia" tipo Dependencia
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDDEPENDENCIA", referencedColumnName = "IDDEPENDENCIA", insertable = false, updatable = false)
    private Dependencia dependencia;

    /**
     * Atributo privado "domicilio" tipo Domicilio
     */
    @OneToOne
    @JoinColumn(name = "IDDIRECCION")
    private Domicilio domicilio;

    /**
     * Atributo privado "correo" tipo String
     */
    @Column(name = "CORREO")
    private String correoElectronico;

    @Column(name = "BLNLOCAL")
    private boolean blnLocal;

    /**
     * Atributo privado "nombreUnidad" tipo String
     */
    @Transient
    private String nombreUnidad;

    /**
     * Atributo privado "asignado" tipo Boolean
     */
    @Transient
    private Boolean asignado;

    /**
     * Constructor vacio
     */
    public UnidadAdministrativa() {}

    /**
     * Sobrecarga del constructor
     * 
     * @param clave
     */
    public UnidadAdministrativa(String clave) {
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
        this.nombreUnidad = nombre;
        this.nombre = nombre;
    }

    /**
     * 
     * @return dependencia
     */
    public Dependencia getDependencia() {
        return dependencia;
    }

    /**
     * 
     * @param dependencia
     *            a fijar
     */
    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
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
     * @return entidadFederativa
     */
    public EntidadFederativa getEntidadFederativa() {
        return entidadFederativa;
    }

    /**
     * 
     * @return claveUnidadArminR
     */
    public String getClaveUnidadAdminR() {
        return claveUnidadAdminR;
    }

    /**
     * 
     * @param claveUnidadAdminR
     *            a fijar
     */
    public void setClaveUnidadAdminR(String claveUnidadAdminR) {
        this.claveUnidadAdminR = claveUnidadAdminR;
    }

    /**
     * 
     * @return nivel
     */
    public Integer getNivel() {
        return nivel;
    }

    /**
     * 
     * @param nivel
     *            a fijar
     */
    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    /**
     * 
     * @return acronimo
     */
    public String getAcronimo() {
        return acronimo;
    }

    /**
     * 
     * @param acronimo
     *            a fijar
     */
    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    /**
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
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
     * @return fechaExtincion
     */
    public Date getfechaExtincion() {
        return (null == fechaExtincion ? null : (Date) fechaExtincion.clone());
    }
    
    /**
     * @param fechaExtincion to set
     */
    public void getfechaExtincion(Date fechaExtincion) {
        this.fechaExtincion = (null == fechaExtincion ? null : (Date) fechaExtincion.clone());
    }

    /**
     * 
     * @return nombreUnidad
     */
    public String getNombreUnidad() {
        return this.nombreUnidad;
    }

    /**
     * 
     * @return asignado
     */
    public Boolean getAsignado() {
        return asignado;
    }

    /**
     * 
     * @param asignado
     *            a fijar
     */
    public void setAsignado(Boolean asignado) {
        this.asignado = asignado;
    }

    /**
     * 
     * @return TipoUnidadAdministrativa
     */
    public TipoUnidadAdministrativa getIdeTipoUnidadAdministrativa() {
        return TipoUnidadAdministrativa.parse(ideTipoUnidadAdministrativa);
    }

    /**
     * 
     * @param ideTipoUnidadAdministrativa
     *            a fijar
     */
    public void setIdeTipoUnidadAdministrativa(TipoUnidadAdministrativa ideTipoUnidadAdministrativa) {
        this.ideTipoUnidadAdministrativa = ideTipoUnidadAdministrativa.getClave();
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
     * @return domicilio
     */
    public Domicilio getDomicilio() {
        return domicilio;
    }

    /**
     * 
     * @param domicilio
     *            a fijar
     */
    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
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

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico
     *            the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public boolean isBlnLocal() {
        return blnLocal;
    }

    public void setBlnLocal(boolean blnLocal) {
        this.blnLocal = blnLocal;
    }

}
