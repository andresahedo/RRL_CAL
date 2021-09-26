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
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Vigencia;

import javax.persistence.*;

/**
 * @author softtek - EQG Since 12/11/2014
 */
@Entity
@Table(name = "RVCC_TRAMITEMODAL")
public class TramiteModalidad extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 7452297077853034054L;

    /**
     * Atributo privado "idTramiteModal" tipo Integer
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDTRAMITEMODAL")
    private Integer idTramiteModal;

    /**
     * Atributo privado "tipoTramite" tipo TipoTramite
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDTIPOTRAMITE", nullable = true, referencedColumnName = "IDTIPOTRAMITE")
    private TipoTramite tipoTramite;

    /**
     * Atributo privado "modalidad" tipo Integer
     */
    @Column(name = "IDMODALIDAD")
    private Integer modalidad;

    /**
     * Atributo privado "idTipoPersona" tipo String
     */
    @Column(name = "IDETIPOPERSONA")
    private String idTipoPersona;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    public Integer getIdTramiteModal() {
        return idTramiteModal;
    }

    public void setIdTramiteModal(Integer idTramiteModal) {
        this.idTramiteModal = idTramiteModal;
    }

    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Integer getModalidad() {
        return modalidad;
    }

    public void setModalidad(Integer modalidad) {
        this.modalidad = modalidad;
    }

    public String getIdTipoPersona() {
        return idTipoPersona;
    }

    public void setIdTipoPersona(String idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }

    public Vigencia getVigencia() {
        return vigencia;
    }

    public void setVigencia(Vigencia vigencia) {
        this.vigencia = vigencia;
    }

}
