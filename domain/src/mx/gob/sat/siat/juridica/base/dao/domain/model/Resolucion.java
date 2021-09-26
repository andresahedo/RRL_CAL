/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCT_RESOLUCION")
public class Resolucion extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 2525414306967918233L;

    /**
     * Atributo privado "idResolucion" tipo Long
     */
    @Id
    @Column(name = "IDRESOLUCION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_RESOLUCION")
    @SequenceGenerator(name = "RVCQ_RESOLUCION", sequenceName = "RVCQ_RESOLUCION", allocationSize = 1)
    private Long idResolucion;

    /**
     * Atributo privado "tramite" tipo Tramite
     */
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDTRAMITE", referencedColumnName = "IDTRAMITE")
    private Tramite tramite;

    /**
     * Atributo privado "ideSentidoResolucion" tipo String
     */
    @Column(name = "IDESENTRESOLUCION")
    private String ideSentidoResolucion;

    /**
     * Atributo privado "fechaInicioVigencia" tipo Date
     */
    @Column(name = "FECINIVIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioVigencia;

    /**
     * Atributo privado "fechaResolucion" tipo Date
     */
    @Column(name = "FECRESOLUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaResolucion;

    /**
     * Atributo privado "fechaFinVigencia" tipo Date
     */
    @Column(name = "FECFINVIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinVigencia;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Atributo privado "ideEstadoResolucion" tipo String
     */
    @Column(name = "IDEESTRESOLUCION")
    private String ideEstadoResolucion;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    /**
     * Atributo privado "monto" tipo BigDecimal
     */
    @Column(name = "MONTO")
    private BigDecimal monto;

    /**
     * Atributo privado "IDUSUARIO" tipo String
     */
    @Column(name = "IDUSUARIO")
    private String rfcAbogado;

    /**
     * Atributo privado "resolucionesImpugnadas" tipo Lista
     * resolucionesImpugnadas
     */
    @Transient
    private List<ResolucionImpugnada> resolucionesImpugnadas;

    /**
     * Atributo privado "resolucionesCatalogoD" tipo Lista
     * resolucionesCatalogoD
     */
    @Transient
    private List<ResolucionCatalogoD> resolucionesCatalogoD;

    @Transient
    private String numeroOficio;
    @Transient
    private Date fechaEmision;

    /**
     * Constructor
     */
    public Resolucion() {
        super();
    }

    /**
     * Sobrecarga de constructor
     * 
     * @param idResolucion
     */
    public Resolucion(Long idResolucion) {
        super();
        this.idResolucion = idResolucion;
    }

    /**
     * 
     * @return idResolucion
     */
    public Long getIdResolucion() {
        return idResolucion;
    }

    /**
     * 
     * @param idResolucion
     *            a fijar
     */
    public void setIdResolucion(Long idResolucion) {
        this.idResolucion = idResolucion;
    }

    /**
     * 
     * @return ideSentidoResolucion
     */
    public String getIdeSentidoResolucion() {
        return ideSentidoResolucion;
    }

    /**
     * 
     * @param ideSentidoResolucion
     *            a fijar
     */
    public void setIdeSentidoResolucion(String ideSentidoResolucion) {
        this.ideSentidoResolucion = ideSentidoResolucion;
    }

    /**
     * 
     * @return fechaInicioVigencia
     */
    public Date getFechaInicioVigencia() {
        return (fechaInicioVigencia != null ? (Date) fechaInicioVigencia.clone() : null);
    }

    /**
     * 
     * @param fechaInicioVigencia
     *            a fijar
     */
    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = (fechaInicioVigencia != null ? (Date) fechaInicioVigencia.clone() : null);
    }

    /**
     * 
     * @return fechaResolucion
     */
    public Date getFechaResolucion() {
        return (fechaResolucion != null ? (Date) fechaResolucion.clone() : null);
    }

    /**
     * 
     * @param fechaResolucion
     *            a fijar
     */
    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = (fechaResolucion != null ? (Date) fechaResolucion.clone() : null);
    }

    /**
     * 
     * @return fechaFinVigencia
     */
    public Date getFechaFinVigencia() {
        return (fechaFinVigencia != null ? (Date) fechaFinVigencia.clone() : null);
    }

    /**
     * 
     * @param fechaFinVigencia
     *            a fijar
     */
    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = (fechaFinVigencia != null ? (Date) fechaFinVigencia.clone() : null);
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
     * 
     * @return ideEstadoResolucion
     */
    public String getIdeEstadoResolucion() {
        return ideEstadoResolucion;
    }

    /**
     * 
     * @param ideEstadoResolucion
     *            a fijar
     */
    public void setIdeEstadoResolucion(String ideEstadoResolucion) {
        this.ideEstadoResolucion = ideEstadoResolucion;
    }

    /**
     * 
     * @return resolucionesImpugnadas
     */
    public List<ResolucionImpugnada> getResolucionesImpugnadas() {
        return resolucionesImpugnadas;
    }

    /**
     * 
     * @param resolucionesImpugnadas
     *            a fijar
     */
    public void setResolucionesImpugnadas(List<ResolucionImpugnada> resolucionesImpugnadas) {
        this.resolucionesImpugnadas = resolucionesImpugnadas;
    }

    /**
     * 
     * @return fechaBaja
     */
    public Date getFechaBaja() {
        return (fechaBaja != null ? (Date) fechaBaja.clone() : null);
    }

    /**
     * 
     * @param fechaBaja
     *            a fijar
     */
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = (fechaBaja != null ? (Date) fechaBaja.clone() : null);
    }

    /**
     * 
     * @return monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * 
     * @param monto
     *            a fijar
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * 
     * @return resolucionesCatalogoD
     */
    public List<ResolucionCatalogoD> getResolucionesCatalogoD() {
        return resolucionesCatalogoD;
    }

    /**
     * 
     * @param resolucionesCatalogoD
     *            a fijar
     */
    public void setResolucionesCatalogoD(List<ResolucionCatalogoD> resolucionesCatalogoD) {
        this.resolucionesCatalogoD = resolucionesCatalogoD;
    }

    /**
     * 
     * @return tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * 
     * @param tramite
     *            a fijar
     */
    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * Metodo hashCode
     * 
     * @return result
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idResolucion == null) ? 0 : idResolucion.hashCode());
        return result;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Resolucion)) {
            return false;
        }
        Resolucion other = (Resolucion) object;
        if (this.idResolucion == null || other.idResolucion == null || !this.idResolucion.equals(other.idResolucion)) {
            return false;
        }
        return true;
    }

    /**
     * @return the numeroOficio
     */
    public String getNumeroOficio() {
        return numeroOficio;
    }

    /**
     * @param numeroOficio
     *            the numeroOficio to set
     */
    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    /**
     * @return the fechaEmision
     */
    public Date getFechaEmision() {
        return fechaEmision != null ? (Date) fechaEmision.clone() : null;
    }

    /**
     * @param fechaEmision
     *            the fechaEmision to set
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision != null ? (Date) fechaEmision.clone() : null;
    }

    /**
     * @return the rfcAbogado
     */
    public String getRfcAbogado() {
        return rfcAbogado;
    }

    /**
     * @param rfcAbogado
     *            the rfcAbogado to set
     */
    public void setRfcAbogado(String rfcAbogado) {
        this.rfcAbogado = rfcAbogado;
    }

}
