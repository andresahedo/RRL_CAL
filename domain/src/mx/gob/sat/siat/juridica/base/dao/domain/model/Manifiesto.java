package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.TipoTramite;

import javax.persistence.*;

@Entity
@Table(name = "RVCC_MANIFIESTO")
public class Manifiesto {

    @Id
    @Column(name = "IDMANIFIESTO")
    private Integer idManifiesto;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDTIPOTRAMITE", nullable = true, referencedColumnName = "IDTIPOTRAMITE")
    private TipoTramite tipoTramite;

    @Column(name = "SECUENCIA")
    private Integer secuencia;

    @Column(name = "MANIFIESTO")
    private String manifiesto;

    @Column(name = "BLNOBLIGATORIO")
    private Boolean obligatorio;

    @Column(name = "BLNACTIVO")
    private Boolean activo;

    public Integer getIdManifiesto() {
        return idManifiesto;
    }

    public void setIdManifiesto(Integer idManifiesto) {
        this.idManifiesto = idManifiesto;
    }

    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Integer getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }

    public String getManifiesto() {
        return manifiesto;
    }

    public void setManifiesto(String manifiesto) {
        this.manifiesto = manifiesto;
    }

    public Boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
