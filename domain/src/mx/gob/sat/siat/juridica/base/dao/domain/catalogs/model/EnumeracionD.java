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
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the CAT_ENUMERACION_D database table.
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCC_ENUMERACIOND")
public class EnumeracionD extends BaseModel implements Serializable {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "cveEnumeracion" tipo String
     */
    @Id
    @Column(name = "IDENUMERACIOND")
    private String cveEnumeracion;

    /**
     * Atributo privado "catEnumeracionH" tipo EnumeracionH
     */
    // bi-directional many-to-one association to EnumeracionH
    @ManyToOne
    @JoinColumn(name = "IDENUMERACIONH")
    private EnumeracionH catEnumeracionH;

    /**
     * Atributo privado "catEnumeracionDTrs" tipo List<EnumeracionTr>
     */
    // bi-directional many-to-one association to Enumeracion
    @OneToMany(mappedBy = "catEnumeracionD", fetch = FetchType.EAGER)
    private List<EnumeracionTr> catEnumeracionDTrs;

    @Column(name = "ORDENSEC")
    private Integer orden;
    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Constructor vacio
     */
    public EnumeracionD() {}

    /**
     * 
     * @return cveEnumeracion
     */
    public String getCveEnumeracion() {
        return this.cveEnumeracion;
    }

    /**
     * 
     * @param cveEnumeracion
     *            a fijar
     */
    public void setCveEnumeracion(String cveEnumeracion) {
        this.cveEnumeracion = cveEnumeracion;
    }

    /**
     * 
     * @return catEnumeracionH
     */
    public EnumeracionH getCatEnumeracionH() {
        return this.catEnumeracionH;
    }

    /**
     * 
     * @param catEnumeracionH
     *            a fijar
     */
    public void setCatEnumeracionH(EnumeracionH catEnumeracionH) {
        this.catEnumeracionH = catEnumeracionH;
    }

    /**
     * 
     * @return catEnumeracionDTrs
     */
    public List<EnumeracionTr> getCatEnumeracionDTrs() {
        if (this.catEnumeracionDTrs == null) {
            this.catEnumeracionDTrs = new ArrayList<EnumeracionTr>();
        }
        return this.catEnumeracionDTrs;
    }

    /**
     * 
     * @param catEnumeracionDTrs
     *            a fijar
     */
    public void setCatEnumeracionDTrs(List<EnumeracionTr> catEnumeracionDTrs) {
        this.catEnumeracionDTrs = catEnumeracionDTrs;
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

    /**
     * @return the orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * @param orden
     *            the orden to set
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

}
