package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "RVCT_FRACCIONDUDA")
public class FraccionDuda extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDFRACCIONDUDA", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_FRACCIONDUDA")
    @SequenceGenerator(name = "RVCQ_FRACCIONDUDA", sequenceName = "RVCQ_FRACCIONDUDA", allocationSize = 1)
    private Long idFraccionDuda;

    @Column(name = "IDSOLICITUD", nullable = false)
    private Long idSolicitud;

    @Column(name = "FRACCIONDUDAV", nullable = false)
    private String fraccionDuda;

    /**
     * Atributo privado "blnActivo" tipo Boolean
     */
    @Column(name = "BLNACTIVO")
    private Boolean blnActivo;

    /**
     * @return the idFraccionDuda
     */
    public Long getIdFraccionDuda() {
        return idFraccionDuda;
    }

    /**
     * @param idFraccionDuda
     *            the idFraccionDuda to set
     */
    public void setIdFraccionDuda(Long idFraccionDuda) {
        this.idFraccionDuda = idFraccionDuda;
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
     * @return the fraccionDuda
     */
    public String getFraccionDuda() {
        return fraccionDuda;
    }

    /**
     * @param fraccionDuda
     *            the fraccionDuda to set
     */
    public void setFraccionDuda(String fraccionDuda) {
        this.fraccionDuda = fraccionDuda;
    }

    /**
     * @return the blnActivo
     */
    public Boolean getBlnActivo() {
        return blnActivo;
    }

    /**
     * @param blnActivo
     *            the blnActivo to set
     */
    public void setBlnActivo(Boolean blnActivo) {
        this.blnActivo = blnActivo;
    }

}
