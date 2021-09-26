/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "IDETIPOPERSONA", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ABSTRACT")
@Table(name = "RVCP_PERSONA")
public abstract class Persona extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -5108262720409585236L;

    /**
     * Atributo privado "idPersona" tipo Long
     */
    @Id
    @Column(name = "IDPERSONA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_PERSONA")
    @SequenceGenerator(name = "RVCQ_PERSONA", sequenceName = "RVCQ_PERSONA", allocationSize = 1)
    private Long idPersona;

    /**
     * Atributo privado "rfc" tipo String
     */
    @Column(name = "RFC", length = NumerosConstantes.QUINCE)
    private String rfc;

    /**
     * Atributo privado "claveUsuario" tipo String
     */
    @Column(name = "IDUSUARIO", length = NumerosConstantes.VEINTICINCO)
    private String claveUsuario;

    /**
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE", length = NumerosConstantes.CINCUENTA)
    private String nombre;

    /**
     * Atributo privado "apellidoMaterno" tipo String
     */
    @Column(name = "APELLIDOMATERNO", length = NumerosConstantes.CINCUENTA)
    private String apellidoMaterno;

    /**
     * Atributo privado "apellidoPaterno" tipo String
     */
    @Column(name = "APELLIDOPATERNO", length = NumerosConstantes.CINCUENTA)
    private String apellidoPaterno;

    /**
     * Atributo privado "curp" tipo String
     */
    @Column(name = "CURP", length = NumerosConstantes.VEINTICINCO)
    private String curp;

    /**
     * Atributo privado "correoElectronico" tipo String
     */
    @Column(name = "CORREOELECTRONICO", length = NumerosConstantes.SETENTA)
    private String correoElectronico;

    /**
     * Atributo privado "razonSocial" tipo String
     */
    @Column(name = "RAZONSOCIAL", length = NumerosConstantes.VEINTICINCO)
    private String razonSocial;

    /**
     * Atributo privado "paisOrigen" tipo Pais
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDPAISORIGEN", nullable = true, referencedColumnName = "IDPAIS")
    private Pais paisOrigen;

    /**
     * Atributo privado "descripcionGiro" tipo String
     */
    @Column(name = "DESCACTECOPREPONDERANTE", length = NumerosConstantes.VEINTICINCO)
    private String descripcionGiro;

    /**
     * Atributo privado "registroConFiel" tipo Boolean
     */
    @Column(name = "BLNCONFIEL", length = NumerosConstantes.VEINTE)
    private Boolean registroConFiel = false;

    /**
     * Atributo privado "fechaRegistro" tipo Date
     */
    @Column(name = "FECREGISTRO")
    private Date fechaRegistro;

    /**
     * Atributo privado "nombrePuesto" tipo String
     */
    @Column(name = "NOMPUESTO")
    private String nombrePuesto;

    /**
     * Atributo privado "fechaFinVigencia" tipo Date
     */
    @Column(name = "FECFINVIGENCIA")
    private Date fechaFinVigencia;

    /**
     * Atributo privado "informacionConfidencial" tipo Boolean
     */
    @Column(name = "BLNINFORMACIONCONFIDENCIAL")
    private Boolean informacionConfidencial = false;

    /**
     * Atributo privado "numeroSeguridadSocial" tipo String
     */
    @Column(name = "NSS")
    private String numeroSeguridadSocial;

    /**
     * Atributo privado "numeroIdentificacionFiscal" tipo String
     */
    @Column(name = "NUMIDFISCAL")
    private String numeroIdentificacionFiscal;

    /**
     * Atributo privado "ideTipoPersona" tipo String
     */
    @Column(insertable = false, updatable = false)
    private String ideTipoPersona;

    /**
     * Atributo privado "fechaFinVigencia" tipo Date
     */
    @Column(name = "FECBAJA")
    private Date fecBaja;

    /**
     * Atributo privado "rfcCorto" tipo String
     */
    @Column(name = "RFCCORTO")
    private String rfcCorto;

    @Column(name = "NUMEROEMPLEADO")
    private Long numeroEmpleado;

    /**
     * @return the fecBaja
     */
    public Date getFecBaja() {
        return fecBaja != null ? (Date) fecBaja.clone() : null;
    }

    /**
     * @param fecBaja
     *            the fecBaja to set
     */
    public void setFecBaja(Date fecBaja) {
        this.fecBaja = fecBaja != null ? (Date) fecBaja.clone() : null;
    }

    /**
     * Constructor vacio
     */
    public Persona() {}

    /**
     * 
     * @param idPersona
     *            a fijar
     */
    public Persona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * 
     * @return idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * 
     * @param idPersona
     *            a fijar
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
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
     * @return descrpcionGiro
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
     * 
     * @return registroConFiel
     */
    public Boolean getRegistroConFiel() {
        return registroConFiel;
    }

    /**
     * 
     * @param registroConFiel
     *            a fijar
     */
    public void setRegistroConFiel(Boolean registroConFiel) {
        this.registroConFiel = registroConFiel;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaRegistro() {
        return fechaRegistro != null ? (Date) fechaRegistro.clone() : null;
    }

    /**
     * 
     * @param fechaRegistro
     *            a fijar
     */
    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaRegistro);
            this.fechaRegistro = cal.getTime();
        }
        else {
            this.fechaRegistro = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaFinVigencia() {
        return fechaFinVigencia != null ? (Date) fechaFinVigencia.clone() : null;
    }

    /**
     * 
     * @param fechaFinVigencia
     *            a fijar
     */
    public void setFechaFinVigencia(Date fechaFinVigencia) {
        if (fechaFinVigencia != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaFinVigencia);
            this.fechaFinVigencia = cal.getTime();
        }
        else {
            this.fechaFinVigencia = null;
        }
    }

    /**
     * 
     * @return nombrePuesto
     */
    public String getNombrePuesto() {
        return nombrePuesto;
    }

    /**
     * 
     * @param nombrePuesto
     *            a fijar
     */
    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    /**
     * 
     * @return informacionConfidencial
     */
    public Boolean getInformacionConfidencial() {
        return informacionConfidencial;
    }

    /**
     * 
     * @param informacionConfidencial
     *            a fijar
     */
    public void setInformacionConfidencial(Boolean informacionConfidencial) {
        this.informacionConfidencial = informacionConfidencial;
    }

    /**
     * 
     * @return numeroSeguridadSocial
     */
    public String getNumeroSeguridadSocial() {
        return numeroSeguridadSocial;
    }

    /**
     * 
     * @param numeroSeguridadSocial
     *            a fijar
     */
    public void setNumeroSeguridadSocial(String numeroSeguridadSocial) {
        this.numeroSeguridadSocial = numeroSeguridadSocial;
    }

    /**
     * 
     * @return numeroIdentificacionFiscal
     */
    public String getNumeroIdentificacionFiscal() {
        return numeroIdentificacionFiscal;
    }

    /**
     * 
     * @param numeroIdentificacionFiscal
     *            a fijar
     */
    public void setNumeroIdentificacionFiscal(String numeroIdentificacionFiscal) {
        this.numeroIdentificacionFiscal = numeroIdentificacionFiscal;
    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if (this.idPersona == null || other.idPersona == null || !this.idPersona.equals(other.idPersona)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.Persona[idPersona=" + idPersona + "]";
     */
    public String toString() {
        return "entity.Persona[idPersona=" + idPersona + "]";
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
     * @return paisOrigen
     */
    public Pais getPaisOrigen() {
        return paisOrigen;
    }

    /**
     * 
     * @return claveUsuario
     */
    public String getClaveUsuario() {
        return claveUsuario;
    }

    /**
     * 
     * @param claveUsuario
     *            a fijar
     */
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    /**
     * @return the ideTipoPersona
     */
    public String getIdeTipoPersona() {
        return ideTipoPersona;
    }

    /**
     * @param ideTipoPersona
     *            the ideTipoPersona to set
     */
    public void setIdeTipoPersona(String ideTipoPersona) {
        this.ideTipoPersona = ideTipoPersona;
    }

    /**
     * 
     * @return rfcCorto
     */
    public String getRfcCorto() {
        return rfcCorto;
    }

    /**
     * 
     * @param rfcCorto
     *            a fijar
     */
    public void setRfcCorto(String rfcCorto) {
        this.rfcCorto = rfcCorto;
    }

    /**
     * @return the numeroEmpleado
     */
    public Long getNumeroEmpleado() {
        return numeroEmpleado;
    }

    /**
     * @param numeroEmpleado
     *            the numeroEmpleado to set
     */
    public void setNumeroEmpleado(Long numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

}
