/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

/**
 * Clase Base para el CatalogoD
 * 
 * Bean que contienen atributos del modelo
 * 
 * @author Softtek
 */

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "RVCC_CATALOGOD")
public class CatalogoD extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "clave" tipo String
     */
    @Id
    @Column(name = "IDCATALOGOD")
    private String clave;

    /**
     * Atributo privado "catalogoH" tipo CatalogoH
     */
    @JoinColumn(name = "IDCATALOGOH", referencedColumnName = "IDCATALOGOH")
    @ManyToOne(fetch = FetchType.LAZY)
    private CatalogoH catalogoH;

    /**
     * Atributo privado "catalogoPadre" tipo CatalogoD
     */
    @JoinColumn(name = "IDCATALOGODR", referencedColumnName = "IDCATALOGOD")
    @ManyToOne(fetch = FetchType.LAZY)
    private CatalogoD catalogoPadre;

    /**
     * Atributo privado "codigoGenerico1" tipo String
     */
    @Column(name = "CODGENERICO1")
    private String codigoGenerico1;

    /**
     * Atributo privado "codigoGenerico2" tipo String
     */
    @Column(name = "CODGENERICO2")
    private String codigoGenerico2;

    /**
     * Atributo privado "descripcionGenerica1" tipo String
     */
    @Column(name = "DESCGENERICA1")
    private String descripcionGenerica1;

    /**
     * Atributo privado "descripcionGenerica2" tipo String
     */
    @Column(name = "DESCGENERICA2")
    private String descripcionGenerica2;

    /**
     * Atributo privado "numeroGenerico1" tipo BigDecimal
     */
    @Column(name = "NUMGENERICO1")
    private BigDecimal numeroGenerico1;

    /**
     * Atributo privado "numeroGenerico2" tipo BigDecimal
     */
    @Column(name = "NUMGENERICO2")
    private BigDecimal numeroGenerico2;

    /**
     * Atributo privado "ordenSec" tipo Integer
     */
    @Column(name = "ORDENSEC")
    private Integer ordenSec;

    /**
     * Atributo privado "ordenSec" tipo Integer
     */
    @Column(name = "BLNACTIVO", insertable = false, updatable = false)
    private Boolean blnActivo;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * 
     * Costructor de la clase
     */
    public CatalogoD() {}

    /**
     * 
     * Sobrecarga del constructor
     */
    public CatalogoD(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * 
     * @param clave
     *            a fijar
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * 
     * @return catalogoH
     */
    public CatalogoH getCatalogoH() {
        return catalogoH;
    }

    /**
     * 
     * @param catalogoH
     *            a fijar
     */
    public void setCatalogoH(CatalogoH catalogoH) {
        this.catalogoH = catalogoH;
    }

    /**
     * 
     * @return codigoGenerico1
     */
    public String getCodigoGenerico1() {
        return codigoGenerico1;
    }

    /**
     * 
     * @param codigoGenerico1
     *            a fijar
     */
    public void setCodigoGenerico1(String codigoGenerico1) {
        this.codigoGenerico1 = codigoGenerico1;
    }

    /**
     * 
     * @return codigoGenerico2
     */
    public String getCodigoGenerico2() {
        return codigoGenerico2;
    }

    /**
     * 
     * @param codigoGenerico2
     *            a fijar
     */
    public void setCodigoGenerico2(String codigoGenerico2) {
        this.codigoGenerico2 = codigoGenerico2;
    }

    /**
     * 
     * @return catalogoPadre
     */
    public CatalogoD getCatalogoPadre() {
        return catalogoPadre;
    }

    /**
     * 
     * @param catalogoPadre
     *            a fijar
     */
    public void setCatalogoPadre(CatalogoD catalogoPadre) {
        this.catalogoPadre = catalogoPadre;
    }

    /**
     * 
     * @return descripcionGenerica1
     */
    public String getDescripcionGenerica1() {
        return descripcionGenerica1;
    }

    /**
     * 
     * @param descripcionGenerica1
     *            a fijar
     */
    public void setDescripcionGenerica1(String descripcionGenerica1) {
        this.descripcionGenerica1 = descripcionGenerica1;
    }

    /**
     * 
     * @return descripcionGenerica2
     */
    public String getDescripcionGenerica2() {
        return descripcionGenerica2;
    }

    /**
     * 
     * @param descripcionGenerica2
     *            a fijar
     */
    public void setDescripcionGenerica2(String descripcionGenerica2) {
        this.descripcionGenerica2 = descripcionGenerica2;
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
     * 
     * @return numeroGenerico1
     */
    public BigDecimal getNumeroGenerico1() {
        return numeroGenerico1;
    }

    /**
     * 
     * @param numeroGenerico1
     *            a fijar
     */
    public void setNumeroGenerico1(BigDecimal numeroGenerico1) {
        this.numeroGenerico1 = numeroGenerico1;
    }

    /**
     * 
     * @return numeroGenerico2
     */
    public BigDecimal getNumeroGenerico2() {
        return numeroGenerico2;
    }

    /**
     * 
     * @param numeroGenerico2
     *            a fijar
     */
    public void setNumeroGenerico2(BigDecimal numeroGenerico2) {
        this.numeroGenerico2 = numeroGenerico2;
    }

    /**
     * 
     * @return ordenSec
     */
    public Integer getOrdenSec() {
        return ordenSec;
    }

    /**
     * 
     * @param ordenSec
     *            a fijar
     */
    public void setOrdenSec(Integer ordenSec) {
        this.ordenSec = ordenSec;
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
