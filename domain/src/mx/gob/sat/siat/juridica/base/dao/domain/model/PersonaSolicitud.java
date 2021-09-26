/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Pais;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

/**
 * 
 * @author softtek
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "IDETIPOPERSONASOL", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ABSTRACT")
@DiscriminatorOptions(force = true)
@Table(name = "RVCT_SOLPERSONA")
@NamedNativeQuery(name = "borrarPersonaSolicitud",
        query = " UPDATE RVCT_SOLPERSONA set idsolicitud = ?1, IDPERSONAPERSONASOLR = null where id_persona_sol = ?2",
        resultSetMapping = "borrado")
@SqlResultSetMapping(name = "borrado", entities = {})
public abstract class PersonaSolicitud extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1747646893425347L;

    /**
     * Atributo privado "estadoContribuyente" tipo String
     */
    @Column(name = "ESTADOSOLICITANTE")
    private String estadoContribuyente;
    
    
    /**
     * Atributo privado "idPersonaSolicitud" tipo Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_SOLPERSONA")
    @SequenceGenerator(name = "RVCQ_SOLPERSONA", sequenceName = "RVCQ_SOLPERSONA", allocationSize = 1)
    @Column(name = "IDSOLPERSONA")
    private Long idPersonaSolicitud;

    /**
     * Atributo privado "idSolicitud" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDSOLICITUD", nullable = false)
    private Long idSolicitud;

    /**
     * Atributo privado "idTipoPersonaSolicitud" tipo Long
     */
    @Column(name = "IDPERSONAPERSONASOLR", insertable = true, updatable = true)
    private Long idTipoPersonaSolicitud;

    /**
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * Atributo privado "apellidoMaterno" tipo String
     */
    @Column(name = "APELLIDOMATERNO")
    private String apellidoMaterno;

    /**
     * Atributo privado "apellidoPaterno" tipo String
     */
    @Column(name = "APELLIDOPATERNO")
    private String apellidoPaterno;

    /**
     * Atributo privado "razonSocial" tipo String
     */
    @Column(name = "RAZONSOCIAL")
    private String razonSocial;

    /**
     * Atributo privado "rfc" tipo String
     */
    @Column(name = "RFC")
    private String rfc;

    /**
     * Atributo privado "curp" tipo String
     */
    @Column(name = "CURP")
    private String curp;

    /**
     * Atributo privado "correoElectronico" tipo String
     */
    @Column(name = "CORREOELECTRONICO")
    private String correoElectronico;

    /**
     * Atributo privado "cedulaProfesional" tipo String
     */
    @Column(name = "CEDULAPROFESIONAL")
    private String cedulaProfesional;

    /**
     * Atributo privado "nss" tipo String
     */
    @Column(name = "NSS")
    private String nss;

    /**
     * Atributo privado "domicilio" tipo DomicilioSolicitud
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDSOLDIRECCION", referencedColumnName = "IDSOLDIRECCION")
    private DomicilioSolicitud domicilio;

    /**
     * Atributo privado "paisOrigen" tipo Pais
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDPAISORIGEN", referencedColumnName = "IDPAIS")
    private Pais paisOrigen;

    /**
     * Atributo privado "telefono" tipo String
     */
    @Column(name = "TELEFONO")
    private String telefono;

    /**
     * Atributo privado "puesto" tipo String
     */
    @Column(name = "PUESTO")
    private String puesto;

    /**
     * Atributo privado "nombreArea" tipo String
     */
    @Column(name = "NOMBREAREA")
    private String nombreArea;

    /**
     * Atributo privado "rfcExtranjero" tipo String
     */
    @Column(name = "TAXID")
    private String rfcExtranjero;

    /**
     * Atributo privado "descripcionGiro" tipo String
     */
    @Column(name = "DESCACTECOPREPONDERANTE", length = NumerosConstantes.DOSCIENTOS_CINCUENTA)
    private String descripcionGiro;

    /**
     * Atributo privado "booleanExtranjero" tipo Boolean
     */
    @Column(name = "BLNEXTRANJERO")
    private Boolean booleanExtranjero;

    /**
     * Atributo privado "personaMoral" tipo Boolean
     */
    @Column(name = "BLNPERSONAMORAL")
    private Boolean personaMoral;

    /**
     * Atributo privado "cveUsuario" tipo String
     */
    @Column(name = "IDUSUARIO")
    private String cveUsuario;

    /**
     * Atributo privado "paginaWeb" tipo String
     */
    @Column(name = "PAGINAWEB")
    private String paginaWeb;

    /**
     * Atributo privado "blnActivo" tipo Boolean
     */
    @Column(name = "BLNACTIVO")
    private Boolean blnActivo;

    /**
     * Atributo privado "blnActivo" tipo Boolean
     */
    @Column(name = "BLNAMPARADO")
    private Boolean blnAmparado;
    
    public Boolean getBlnAmparado() {
        return blnAmparado;
    }

    public void setBlnAmparado(Boolean blnAmparado) {
        this.blnAmparado = blnAmparado;
    }

    /**
     * Atributo privado "solicitud" tipo Solicitud
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "IDSOLICITUD", referencedColumnName = "IDSOLICITUD", insertable = false, updatable = false)
    private Solicitud solicitud;

    /**
     * Constructor vacio
     */
    public PersonaSolicitud() {

    }

    /**
     * Sobrecarga de constructor
     * 
     * @param idSolicitud
     */
    public PersonaSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
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
     * @return apellidoMaterno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * 
     * @param apellidoMaterno
     *            a fijar
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * 
     * @return apellidoPaterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * 
     * @param apellidoPaterno
     *            a fijar
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * 
     * @return razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * 
     * @param razonSocial
     *            a fijar
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * 
     * @return rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * 
     * @param rfc
     *            a fijar
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * 
     * @return curp
     */
    public String getCurp() {
        return curp;
    }

    /**
     * 
     * @param curp
     *            a fijar
     */
    public void setCurp(String curp) {
        this.curp = curp;
    }

    /**
     * 
     * @return correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * 
     * @param correoElectronico
     *            a fijar
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * 
     * @return cedulaProfesional
     */
    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    /**
     * 
     * @param cedulaProfesional
     *            a fijar
     */
    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    /**
     * 
     * @return nss
     */
    public String getNss() {
        return nss;
    }

    /**
     * 
     * @param nss
     *            a fijar
     */
    public void setNss(String nss) {
        this.nss = nss;
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
     * @return paisOrigen
     */
    public Pais getPaisOrigen() {
        return paisOrigen;
    }

    /**
     * 
     * @param paisOrigen
     *            a fijar
     */
    public void setPaisOrigen(Pais paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    /**
     * 
     * @return domicilio
     */
    public DomicilioSolicitud getDomicilio() {
        return domicilio;
    }

    /**
     * 
     * @param domicilio
     *            a fijar
     */
    public void setDomicilio(DomicilioSolicitud domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * 
     * @return solicitud
     */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /**
     * 
     * @param solicitud
     *            a fijar
     */
    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    /**
     * 
     * @return descripcionGiro
     */
    public String getDescripcionGiro() {
        return descripcionGiro;
    }

    /**
     * 
     * @param descripcionGiro
     *            a fijar
     */
    public void setDescripcionGiro(String descripcionGiro) {
        this.descripcionGiro = descripcionGiro;
    }

    /**
     * @return the idPersonaSolicitud
     */
    public Long getIdPersonaSolicitud() {
        return idPersonaSolicitud;
    }

    /**
     * @param idPersonaSolicitud
     *            the idPersonaSolicitud to set
     */
    public void setIdPersonaSolicitud(Long idPersonaSolicitud) {
        this.idPersonaSolicitud = idPersonaSolicitud;
    }

    /**
     * @return the idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud
     *            the idSolicitud to set
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * 
     * @return puesto
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * 
     * @param puesto
     *            a fijar
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    /**
     * 
     * @return serialVersionUID
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * 
     * @return personaMoral
     */
    public Boolean getPersonaMoral() {
        return personaMoral;
    }

    /**
     * 
     * @param personaMoral
     *            a fijar
     */
    public void setPersonaMoral(Boolean personaMoral) {
        this.personaMoral = personaMoral;
    }

    /**
     * 
     * @return booleanExtranjero
     */
    public Boolean getBooleanExtranjero() {
        return booleanExtranjero;
    }

    /**
     * 
     * @param booleanExtranjero
     *            a fijar
     */
    public void setBooleanExtranjero(Boolean booleanExtranjero) {
        this.booleanExtranjero = booleanExtranjero;
    }

    /**
     * 
     * @param cveUsuario
     *            a fijar
     */
    public void setCveUsuario(String cveUsuario) {
        this.cveUsuario = cveUsuario;
    }

    /**
     * 
     * @return cveUsuario
     */
    public String getCveUsuario() {
        return cveUsuario;
    }

    /**
     * 
     * @return rfcExtranjero
     */
    public String getRfcExtranjero() {
        return rfcExtranjero;
    }

    /**
     * 
     * @param rfcExtranjero
     *            a fijar
     */
    public void setRfcExtranjero(String rfcExtranjero) {
        this.rfcExtranjero = rfcExtranjero;
    }

    /**
     * 
     * @return paginaWeb
     */
    public String getPaginaWeb() {
        return paginaWeb;
    }

    /**
     * 
     * @param paginaWeb
     *            a fijar
     */
    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    /**
     * 
     * @return blnActivo
     */
    public Boolean getBlnActivo() {
        return blnActivo;
    }

    /**
     * 
     * @param blnActivo
     *            a fijar
     */
    public void setBlnActivo(Boolean blnActivo) {
        this.blnActivo = blnActivo;
    }

    /**
     * 
     * @return nombreArea
     */
    public String getNombreArea() {
        return nombreArea;
    }

    /**
     * 
     * @param nombreArea
     *            a fijar
     */
    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    /**
     * 
     * @param idTipoPersonaSolicitud
     *            a fijar
     */
    public void setIdTipoPersonaSolicitud(Long idTipoPersonaSolicitud) {
        this.idTipoPersonaSolicitud = idTipoPersonaSolicitud;
    }

    /**
     * 
     * @return idTipoPersonaSolicitud
     */
    public Long getIdTipoPersonaSolicitud() {
        return idTipoPersonaSolicitud;
    }

    public String getEstadoContribuyente() {
        return estadoContribuyente;
    }

    public void setEstadoContribuyente(String estadoContribuyente) {
        this.estadoContribuyente = estadoContribuyente;
    }

}
