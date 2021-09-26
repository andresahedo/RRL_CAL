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
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCD_RESOLIMP_CATD")
@DiscriminatorColumn(name = "IDCATALOGOH", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force = true)
@DiscriminatorValue("1")
public class ResolucionImpugnadaCatalogoD extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5497172692012772347L;

    /**
     * Atributo privado "idResolucionImpugnadaCatD" tipo Long
     */
    @Id
    @Column(name = "IDRESOLIMPCATD")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_RESOLIMP_CATD")
    @SequenceGenerator(name = "RVCQ_RESOLIMP_CATD", sequenceName = "RVCQ_RESOLIMP_CATD", allocationSize = 1)
    private Long idResolucionImpugnadaCatD;

    /**
     * Atributo privado "idCatalogoH" tipo String
     */
    @Column(name = "IDCATALOGOH", updatable = false, insertable = false)
    private String idCatalogoH;

    /**
     * Atributo privado "catalogoD" tipo CatalogoD
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDCATALOGOD", referencedColumnName = "IDCATALOGOD")
    private CatalogoD catalogoD;

    /**
     * Atributo privado "montoConcepto" tipo BigDecimal
     */
    @Column(name = "MONTOCONCEPTO")
    private BigDecimal montoConcepto;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    private Date fechaBaja;

    /**
     * Constructor
     */
    public ResolucionImpugnadaCatalogoD() {
        super();
    }

    /**
     * Sobrecarga de constructor
     * 
     * @param idResolucionImpugnadaCatD
     */
    public ResolucionImpugnadaCatalogoD(Long idResolucionImpugnadaCatD) {
        super();
        this.idResolucionImpugnadaCatD = idResolucionImpugnadaCatD;
    }

    /**
     * 
     * @return idResolucionImpugnadaCatD
     */
    public Long getIdResolucionImpugnadaCatD() {
        return idResolucionImpugnadaCatD;
    }

    /**
     * 
     * @param idResolucionImpugnadaCatD
     *            a fijar
     */
    public void setIdResolucionImpugnadaCatD(Long idResolucionImpugnadaCatD) {
        this.idResolucionImpugnadaCatD = idResolucionImpugnadaCatD;
    }

    /**
     * 
     * @return idCatalogoH
     */
    public String getIdCatalogoH() {
        return idCatalogoH;
    }

    /**
     * 
     * @return montoConcepto
     */
    public BigDecimal getMontoConcepto() {
        return montoConcepto;
    }

    /**
     * 
     * @param montoConcepto
     *            a fijar
     */
    public void setMontoConcepto(BigDecimal montoConcepto) {
        this.montoConcepto = montoConcepto;
    }

    /**
     * 
     * @param idCatalogoH
     *            a fijar
     */
    public void setIdCatalogoH(String idCatalogoH) {
        this.idCatalogoH = idCatalogoH;
    }

    /**
     * 
     * @return catalogoD
     */
    public CatalogoD getCatalogoD() {
        return catalogoD;
    }

    /**
     * 
     * @param catalogoD
     *            a fijar
     */
    public void setCatalogoD(CatalogoD catalogoD) {
        this.catalogoD = catalogoD;
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
        this.fechaBaja = (Date) fechaBaja.clone();
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
        result = prime * result + ((idResolucionImpugnadaCatD == null) ? 0 : idResolucionImpugnadaCatD.hashCode());
        return result;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ResolucionImpugnadaCatalogoD)) {
            return false;
        }
        ResolucionImpugnadaCatalogoD other = (ResolucionImpugnadaCatalogoD) obj;
        if (idResolucionImpugnadaCatD == null) {
            if (other.idResolucionImpugnadaCatD != null) {
                return false;
            }
        }
        else if (!idResolucionImpugnadaCatD.equals(other.idResolucionImpugnadaCatD)) {
            return false;
        }
        return true;
    }

}
