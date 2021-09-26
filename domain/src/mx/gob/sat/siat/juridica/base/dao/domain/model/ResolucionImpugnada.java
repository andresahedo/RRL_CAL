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
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.CatalogoD;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

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
@Table(name = "RVCT_RESOLIMPUG")
@FilterDef(name = "fechaBaja", parameters = @ParamDef(name = "fechaBaja", type = "date"))
@Filters({ @Filter(name = "fechaBaja", condition = ":fechaBaja != null") })
public class ResolucionImpugnada extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5132731547585236566L;

    /**
     * Atributo privado "idResolucionImpugnada" tipo Long
     */
    @Id
    @Column(name = "IDRESOLIMPUG")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_RESOLIMPUG")
    @SequenceGenerator(name = "RVCQ_RESOLIMPUG", sequenceName = "RVCQ_RESOLIMPUG", allocationSize = 1)
    private Long idResolucionImpugnada;

    /**
     * Atributo privado "resolucion" tipo Resolucion
     */
    @JoinColumn(name = "IDRESOLUCION", referencedColumnName = "IDRESOLUCION")
    @ManyToOne(fetch = FetchType.LAZY)
    private Resolucion resolucion;

    /**
     * Atributo privado "numeroFolioResolucionImpugnada" tipo String
     */
    @Column(name = "NUMFOLIORESOLIMPUG")
    private String numeroFolioResolucionImpugnada;

    /**
     * Atributo privado "unidadAdministrativa" tipo
     * UnidadAdministrativa
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDUNIADMIN", nullable = true, referencedColumnName = "IDUNIADMIN")
    private UnidadAdministrativa unidadAdministrativa;

    /**
     * Atributo privado "fechaEmision" tipo Date
     */
    @Column(name = "FECEMISION")
    private Date fechaEmision;

    /**
     * Atributo privado "blnDeterminanteCredito" tipo Integer
     */
    @Column(name = "BLNDETERMINANTECRED")
    private Integer blnDeterminanteCredito;

    /**
     * Atributo privado "monto" tipo BigDecimal
     */
    @Column(name = "MONTO")
    private BigDecimal monto;

    /**
     * Atributo privado "procedimientoDeriva" tipo CatalogoD
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "CVCPROCDERIVA", referencedColumnName = "IDCATALOGOD")
    private CatalogoD procedimientoDeriva;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    private Date fechaBaja;

    /**
     * Atributo privado "sentidoResolucionImpugnada" tipo CatalogoD
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "CVCSENTIDORESOLIMPUG", referencedColumnName = "IDCATALOGOD")
    private CatalogoD sentidoResolucionImpugnada;

    /**
     * Atributo privado "conceptos" tipo List<ConceptoDetalle>
     */
    @Transient
    private List<ConceptoDetalle> conceptos;

    /**
     * Atributo privado "agravios" tipo List<AgravioDetalle>
     */
    @Transient
    private List<AgravioDetalle> agravios;

    /**
     * Atributo privado "importante" tipo boolean
     */
    @Column(name = "BLNIMPORTANTE")
    private Boolean importante;

    /**
     * 
     * @return idResolucionImpugnada
     */
    public Long getIdResolucionImpugnada() {
        return idResolucionImpugnada;
    }

    /**
     * 
     * @param idResolucionImpugnada
     *            a fijar
     */
    public void setIdResolucionImpugnada(Long idResolucionImpugnada) {
        this.idResolucionImpugnada = idResolucionImpugnada;
    }

    /**
     * 
     * @return resolucion
     */
    public Resolucion getResolucion() {
        return resolucion;
    }

    /**
     * 
     * @param resolucion
     *            a fijar
     */
    public void setResolucion(Resolucion resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * 
     * @return numeroFolioResolucionImpugnada
     */
    public String getNumeroFolioResolucionImpugnada() {
        return numeroFolioResolucionImpugnada;
    }

    /**
     * 
     * @param numeroFolioResolucionImpugnada
     *            a fijar
     */
    public void setNumeroFolioResolucionImpugnada(String numeroFolioResolucionImpugnada) {
        this.numeroFolioResolucionImpugnada = numeroFolioResolucionImpugnada;
    }

    /**
     * 
     * @return unidadAdministrativa
     */
    public UnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * 
     * @param unidadAdministrativa
     *            a fijar
     */
    public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * 
     * @return fechaEmision
     */
    public Date getFechaEmision() {
        return (fechaEmision != null ? (Date) fechaEmision.clone() : null);
    }

    /**
     * 
     * @param fechaEmision
     *            a fijar
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = (fechaEmision != null ? (Date) fechaEmision.clone() : null);
    }

    /**
     * 
     * @return blnDeterminanteCredito
     */
    public Integer getBlnDeterminanteCredito() {
        return blnDeterminanteCredito;
    }

    /**
     * 
     * @param blnDeterminanteCredito
     *            a fijar
     */
    public void setBlnDeterminanteCredito(Integer blnDeterminanteCredito) {
        this.blnDeterminanteCredito = blnDeterminanteCredito;
    }

    /**
     * 
     * @return blnDeterminanteCredito
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
     * @return procedimientoDeriva
     */
    public CatalogoD getProcedimientoDeriva() {
        return procedimientoDeriva;
    }

    /**
     * 
     * @param procedimientoDeriva
     *            a fijar
     */
    public void setProcedimientoDeriva(CatalogoD procedimientoDeriva) {
        this.procedimientoDeriva = procedimientoDeriva;
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

    public CatalogoD getSentidoResolucionImpugnada() {
        return sentidoResolucionImpugnada;
    }

    public void setSentidoResolucionImpugnada(CatalogoD sentidoResolucionImpugnada) {
        this.sentidoResolucionImpugnada = sentidoResolucionImpugnada;
    }

    public List<ConceptoDetalle> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<ConceptoDetalle> conceptos) {
        this.conceptos = conceptos;
    }

    public List<AgravioDetalle> getAgravios() {
        return agravios;
    }

    public void setAgravios(List<AgravioDetalle> agravios) {
        this.agravios = agravios;
    }

    public Boolean isImportante() {
        return importante == null ? Boolean.FALSE : importante;
    }

    public void setImportante(boolean importante) {
        this.importante = importante;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idResolucionImpugnada == null) ? 0 : idResolucionImpugnada.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ResolucionImpugnada)) {
            return false;
        }
        ResolucionImpugnada other = (ResolucionImpugnada) obj;
        if (idResolucionImpugnada == null) {
            if (other.idResolucionImpugnada != null) {
                return false;
            }
        }
        else if (!idResolucionImpugnada.equals(other.idResolucionImpugnada)) {
            return false;
        }
        return true;
    }

}
