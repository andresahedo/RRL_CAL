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
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Persona;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Softtek
 * 
 */
@Entity
@Table(name = "RVCT_DOCUMENTO")
public class Documento extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idDictamen" tipo Long
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDDOCUMENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_DOCUMENTO")
    @SequenceGenerator(name = "RVCQ_DOCUMENTO", sequenceName = "RVCQ_DOCUMENTO", allocationSize = 1)
    private Long idDocumento;

    /**
     * Atributo privado "idTipoDocumento" tipo Integer
     */
    @Column(name = "IDTIPODOCUMENTO")
    private Integer idTipoDocumento;

    /**
     * Atributo privado "persona" tipo Persona
     */
    @JoinColumn(name = "IDPERSONA", referencedColumnName = "IDPERSONA")
    @ManyToOne
    private Persona persona;

    /**
     * Atributo privado "inicioVigencia" tipo Date
     */
    @Column(name = "FECINIVIGENCIA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inicioVigencia;

    /**
     * Atributo privado "finVigencia" tipo Date
     */
    @Column(name = "FECFINVIGENCIA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date finVigencia;

    /**
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE", length = NumerosConstantes.DOSCIENTOS_CINCUENTA)
    private String nombre;

    /**
     * Atributo privado "firma" tipo Firma
     */
    @JoinColumn(name = "IDFIRMA", referencedColumnName = "IDFIRMA")
    @ManyToOne
    private Firma firma;

    /**
     * Atributo privado "fechaCreacion" tipo Date
     */
    @Column(name = "FECCREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    /**
     * Atributo privado "idEDocumento" tipo String
     */
    @Column(name = "IDEDOCUMENT")
    private String idEDocumento;

    /**
     * Atributo privado "cveUsuarioCapturista" tipo String
     */
    @Column(name = "IDUSUCAPTURISTA")
    private String cveUsuarioCapturista;

    /**
     * Atributo privado "cveRolCapturista" tipo String
     */
    @Column(name = "IDROLCAPTURISTA")
    private String cveRolCapturista;

    /**
     * Atributo privado "estadoDocumento" tipo String
     */
    @Column(name = "IDEESTDOCUMENTO")
    private String estadoDocumento;

    /**
     * Atributo privado "urlDocumento" tipo String
     */
    @Column(name = "URLDOCUMENTO")
    private String urlDocumento;

    /**
     * Atributo privado "blnActivo" tipo Boolean
     */
    @Column(name = "BLNACTIVO")
    private Integer blnActivo;

    @Column(name = "HASHDOC")
    private String hash;

    @Transient
    private Boolean complementario;

    @Column(name = "LONGITUD")
    private Long longitud;

    @Column(name = "SELLO")
    private String sello;

    /**
     * Constructor vacio
     */
    public Documento() {}

    /**
     * Sobrecarga del constructor
     * 
     * @param idTipoDocumento
     */
    public Documento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    /**
     * 
     * @return idTipoDocumento
     */
    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    /**
     * 
     * @param idTipoDocumento
     *            a fijar
     */
    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    /**
     * 
     * @return persona
     */
    public Persona getPersona() {
        return persona;
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
     * @return fecha
     */
    public Date getInicioVigencia() {
        return inicioVigencia != null ? (Date) inicioVigencia.clone() : null;
    }

    /**
     * 
     * @param inicioVigencia
     *            a fijar
     */
    public void setInicioVigencia(Date inicioVigencia) {
        if (inicioVigencia != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(inicioVigencia);
            this.inicioVigencia = cal.getTime();
        }
        else {
            this.inicioVigencia = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFinVigencia() {
        return finVigencia != null ? (Date) finVigencia.clone() : null;
    }

    /**
     * 
     * @param finVigencia
     *            a fijar
     */
    public void setFinVigencia(Date finVigencia) {
        if (finVigencia != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(finVigencia);
            this.finVigencia = cal.getTime();
        }
        else {
            this.finVigencia = null;
        }
    }

    /**
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
     * @return idDocumento
     */
    public Long getIdDocumento() {
        return idDocumento;
    }

    /**
     * 
     * @param idDocumento
     *            a fijar
     */
    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    /**
     * 
     * @return firma
     */
    public Firma getFirma() {
        return firma;
    }

    /**
     * 
     * @param firma
     *            a fijar
     */
    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaCreacion() {
        return fechaCreacion != null ? (Date) fechaCreacion.clone() : null;
    }

    /**
     * 
     * @param fechaCreacion
     *            a fijar
     */
    public void setFechaCreacion(Date fechaCreacion) {
        if (fechaCreacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaCreacion);
            this.fechaCreacion = cal.getTime();
        }
        else {
            this.fechaCreacion = null;
        }
    }

    /**
     * 
     * @return idEDocumento
     */
    public String getIdEDocumento() {
        return idEDocumento;
    }

    /**
     * 
     * @param idEDocumento
     *            a fijar
     */
    public void setIdEDocumento(String idEDocumento) {
        this.idEDocumento = idEDocumento;
    }

    /**
     * 
     * @return idEDocumento
     */
    public String getEdocumento() {
        return idEDocumento;
    }

    /**
     * 
     * @param idEDocumento
     *            a fijar
     */
    public void setEdocumento(String idEDocumento) {
        this.idEDocumento = idEDocumento;
    }

    /**
     * 
     * @return cveUsuarioCapturista
     */
    public String getCveUsuarioCapturista() {
        return cveUsuarioCapturista;
    }

    /**
     * 
     * @param cveUsuarioCapturista
     *            a fijar
     */
    public void setCveUsuarioCapturista(String cveUsuarioCapturista) {
        this.cveUsuarioCapturista = cveUsuarioCapturista;
    }

    /**
     * 
     * @return cveRolCapturista
     */
    public String getCveRolCapturista() {
        return cveRolCapturista;
    }

    /**
     * 
     * @param cveRolCapturista
     *            a fijar
     */
    public void setCveRolCapturista(String cveRolCapturista) {
        this.cveRolCapturista = cveRolCapturista;
    }

    /**
     * 
     * @return estadoDocumento
     */
    public String getEstadoDocumento() {
        return estadoDocumento;
    }

    /**
     * 
     * @param estadoDocumento
     *            a fijar
     */
    public void setEstadoDocumento(String estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }

    /**
     * 
     * @return urlDocumento
     */
    public String getUrlDocumento() {
        return urlDocumento;
    }

    /**
     * 
     * @param urlDocumento
     *            a fijar
     */
    public void setUrlDocumento(String urlDocumento) {
        this.urlDocumento = urlDocumento;
    }

    /**
     * 
     * @return blnActivo
     */
    public Integer getBlnActivo() {
        return blnActivo;
    }

    /**
     * 
     * @param blnActivo
     *            a fijar
     */
    public void setBlnActivo(Integer blnActivo) {
        this.blnActivo = blnActivo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return the complementario
     */
    public Boolean getComplementario() {
        return complementario;
    }

    /**
     * @param complementario
     *            the complementario to set
     */
    public void setComplementario(Boolean complementario) {
        this.complementario = complementario;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

}
