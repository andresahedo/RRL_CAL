package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EstadoManifiestoPK extends BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "IDSOLICITUD", updatable = false)
    private Long idSolicitud;

    @Basic(optional = false)
    @Column(name = "IDMANIFIESTO", updatable = false)
    private Integer idManifiesto;

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getIdManifiesto() {
        return idManifiesto;
    }

    public void setIdManifiesto(Integer idManifiesto) {
        this.idManifiesto = idManifiesto;
    }



}
