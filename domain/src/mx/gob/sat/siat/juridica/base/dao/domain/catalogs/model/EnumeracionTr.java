/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCC_ENUMERACIONTR")
public class EnumeracionTr extends BaseModel implements Serializable {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "id" tipo EnumeracionTrPK
     */
    @EmbeddedId
    private EnumeracionTrPK id;

    /**
     * Atributo privado "codEnumeracion" tipo String
     */
    @Column(name = "CODENUMERACION")
    private String codEnumeracion;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Atributo privado "catEnumeracionD" tipo EnumeracionD
     */
    // bi-directional many-to-one association to EnumeracionD
    @ManyToOne
    @JoinColumn(name = "IDENUMERACIOND", insertable = false, updatable = false)
    private EnumeracionD catEnumeracionD;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Constructor vacio
     */
    public EnumeracionTr() {}

    /**
     * 
     * @return id
     */
    public EnumeracionTrPK getId() {
        return this.id;
    }

    /**
     * 
     * @param id
     *            a fijar
     */
    public void setId(EnumeracionTrPK id) {
        this.id = id;
    }

    /**
     * 
     * @return codEnumeracion
     */
    public String getCodEnumeracion() {
        return this.codEnumeracion;
    }

    /**
     * 
     * @param codEnumeracion
     *            a fijar
     */
    public void setCodEnumeracion(String codEnumeracion) {
        this.codEnumeracion = codEnumeracion;
    }

    /**
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return this.descripcion;
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
     * @return catEnumeracionD
     */
    public EnumeracionD getCatEnumeracionD() {
        return this.catEnumeracionD;
    }

    /**
     * 
     * @param catEnumeracionD
     *            a fijar
     */
    public void setCatEnumeracionD(EnumeracionD catEnumeracionD) {
        this.catEnumeracionD = catEnumeracionD;
    }

    /**
     * 
     * @return vigencia
     */
    public Vigencia getVigencia() {
        return vigencia;
    }

    /**
     * 
     * @param vigencia
     *            a fijar
     */
    public void setVigencia(Vigencia vigencia) {
        this.vigencia = vigencia;
    }
}
