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

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCD_RESOL_CATD")
public class ResolucionCatalogoD extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5497172692012772347L;

    /**
     * Atributo privado "idResolucionCatD" tipo Long
     */
    @Id
    @Column(name = "IDRESOLCATD")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_RESOL_CATD")
    @SequenceGenerator(name = "RVCQ_RESOL_CATD", sequenceName = "RVCQ_RESOL_CATD", allocationSize = 1)
    private Long idResolucionCatD;

    /**
     * Atributo privado "resolucion" tipo Resolucion
     */
    @JoinColumn(name = "IDRESOLUCION", referencedColumnName = "IDRESOLUCION")
    @ManyToOne(fetch = FetchType.LAZY)
    private Resolucion resolucion;

    /**
     * Atributo privado "idCatalogoH" tipo String
     */
    @Column(name = "IDCATALOGOH")
    private String idCatalogoH;

    /**
     * Atributo privado "catalogoD" tipo CatalogoD
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDCATALOGOD", referencedColumnName = "IDCATALOGOD")
    private CatalogoD catalogoD;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    private Date fechaBaja;

    /**
     * 
     * @return idResolucionCatD
     */
    public Long getIdResolucionCatD() {
        return idResolucionCatD;
    }

    /**
     * 
     * @param idResolucionCatD
     *            a fijar
     */
    public void setIdResolucionCatD(Long idResolucionCatD) {
        this.idResolucionCatD = idResolucionCatD;
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
     * @return idCatalogoH
     */
    public String getIdCatalogoH() {
        return idCatalogoH;
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
        this.fechaBaja = (fechaBaja != null ? (Date) fechaBaja.clone() : null);
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
        result = prime * result + ((idResolucionCatD == null) ? 0 : idResolucionCatD.hashCode());
        return result;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ResolucionCatalogoD)) {
            return false;
        }
        ResolucionCatalogoD other = (ResolucionCatalogoD) object;
        if (this.idResolucionCatD == null || other.idResolucionCatD == null
                || !this.idResolucionCatD.equals(other.idResolucionCatD)) {
            return false;
        }
        return true;
    }

}
