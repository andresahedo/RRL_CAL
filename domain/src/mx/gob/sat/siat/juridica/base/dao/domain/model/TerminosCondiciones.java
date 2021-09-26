package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Vigencia;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RVCC_CONDICIONUSO")
public class TerminosCondiciones extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 9017452139098298759L;
    @Id
    @Column(name = "IDCONDICIONUSO")
    private Integer idCondicionUso;

    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Lob
    @Column(name = "DESCRIPCIONHTML")
    private String descripcionHtml;

    @Column(name = "FECBAJA")
    private Date fechaBaja;

    /**
     * 
     * Instancia de Vigencia tipo privado
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * @return the idCondicionUso
     */
    public Integer getIdCondicionUso() {
        return idCondicionUso;
    }

    /**
     * @param idCondicionUso
     *            the idCondicionUso to set
     */
    public void setIdCondicionUso(Integer idCondicionUso) {
        this.idCondicionUso = idCondicionUso;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcionHtml
     */
    public String getDescripcionHtml() {
        return descripcionHtml;
    }

    /**
     * @param descripcionHtml
     *            the descripcionHtml to set
     */
    public void setDescripcionHtml(String descripcionHtml) {
        this.descripcionHtml = descripcionHtml;
    }

    /**
     * @return the fechaBaja
     */
    public Date getFechaBaja() {
        return fechaBaja != null ? (Date) fechaBaja.clone() : null;
    }

    /**
     * @param fechaBaja
     *            the fechaBaja to set
     */
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja != null ? (Date) fechaBaja.clone() : null;
    }

    public Vigencia getVigencia() {
        return vigencia;
    }

    public void setVigencia(Vigencia vigencia) {
        this.vigencia = vigencia;
    }

}
