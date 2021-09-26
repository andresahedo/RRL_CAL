package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "RVCA_SOL_MANIFIEST")
public class EstadoManifiesto extends BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private EstadoManifiestoPK estadoManifiestoPK;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDMANIFIESTO", referencedColumnName = "IDMANIFIESTO", insertable = false, updatable = false)
    private transient Manifiesto manifiesto;

    @Column(name = "BLNACEPTADO", updatable = false)
    private Boolean aceptado;

    public EstadoManifiestoPK getEstadoManifiestoPK() {
        return estadoManifiestoPK;
    }

    public void setEstadoManifiestoPK(EstadoManifiestoPK estadoManifiestoPK) {
        this.estadoManifiestoPK = estadoManifiestoPK;
    }

    public Manifiesto getManifiesto() {
        return manifiesto;
    }

    public void setManifiesto(Manifiesto manifiesto) {
        this.manifiesto = manifiesto;
    }

    public Boolean getAceptado() {
        return aceptado;
    }

    public void setAceptado(Boolean aceptado) {
        this.aceptado = aceptado;
    }

}
