package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "RVCT_SOLAUTORIDAD")
public class Autoridad extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_SOLAUTORIDAD")
    @SequenceGenerator(name = "RVCQ_SOLAUTORIDAD", sequenceName = "RVCQ_SOLAUTORIDAD", allocationSize = 1)
    @Column(name = "IDSOLAUTORIDAD", nullable = false)
    private Long idAutoridad;

    @Column(name = "IDSOLICITUD", nullable = false)
    private Long idSolicitud;

    @Column(name = "IDUNIADMIN", nullable = false)
    private String idUniAdmin;

    @Column(name = "IDETIPOAUTORIDADSOL")
    private String idTipoAutoridad;

    /**
     * Atributo privado "blnActivo" tipo Boolean
     */
    @Column(name = "BLNACTIVO")
    private Boolean blnActivo;

    /**
     * @return the idAutoridad
     */
    public Long getIdAutoridad() {
        return idAutoridad;
    }

    /**
     * @param idAutoridad
     *            the idAutoridad to set
     */
    public void setIdAutoridad(Long idAutoridad) {
        this.idAutoridad = idAutoridad;
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
     * @return the idUniAdmin
     */
    public String getIdUniAdmin() {
        return idUniAdmin;
    }

    /**
     * @param idUniAdmin
     *            the idUniAdmin to set
     */
    public void setIdUniAdmin(String idUniAdmin) {
        this.idUniAdmin = idUniAdmin;
    }

    /**
     * @return the idTipoAutoridad
     */
    public String getIdTipoAutoridad() {
        return idTipoAutoridad;
    }

    /**
     * @param idTipoAutoridad
     *            the idTipoAutoridad to set
     */
    public void setIdTipoAutoridad(String idTipoAutoridad) {
        this.idTipoAutoridad = idTipoAutoridad;
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
