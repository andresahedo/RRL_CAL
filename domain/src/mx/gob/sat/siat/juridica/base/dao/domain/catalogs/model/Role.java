/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;

/**
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCC_ROL")
public class Role extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "name" tipo String
     */
    @Id
    @Column(name = "IDROL", length = NumerosConstantes.TREINTA_DOS)
    private String name;

    /**
     * Atributo privado "description" tipo String
     */
    @Column(name = "DESCRIPCION", length = NumerosConstantes.SESENTA_CUATRO)
    private String description;

    /**
     * Atributo privado "externo" tipo Boolean
     */
    @Column(name = "BLNEXTERNO")
    private Boolean externo;

    /**
     * Atributo privado "agregaRol" tipo Boolean
     */
    @Column(name = "BLNAGREGAROL")
    private Boolean agregaRol;

    /**
     * Atributo privado "tercero" tipo Boolean
     */
    @Column(name = "BLNTERCERO")
    private Boolean tercero;

    /**
     * Atributo privado "tercero" tipo Boolean
     */
    @Column(name = "ROLNOVELL")
    private String rolNovell;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Constructor
     */
    public Role() {
        super();
    }

    /**
     * Sobrecarga de constructor
     * 
     * @param name
     */
    public Role(final String name) {
        this();

        this.name = name;
    }

    /**
     * Sobrecarga de constructor
     * 
     * @param name
     * @param description
     */
    public Role(final String name, final String description) {
        this(name);

        this.description = description;
    }

    /**
     * Sobrecarga de constructor
     * 
     * @param name
     * @param description
     * @param externo
     */
    public Role(final String name, final String description, final Boolean externo) {
        this(name);
        this.description = description;
        this.externo = externo;
    }

    /**
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *            a fijar
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *            a fijar
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * 
     * @return externo
     */
    public Boolean getExterno() {
        return externo;
    }

    /**
     * 
     * @param externo
     *            a fijar
     */
    public void setExterno(Boolean externo) {
        this.externo = externo;
    }

    /**
     * 
     * @return tercero
     */
    public Boolean getTercero() {
        return tercero;
    }

    /**
     * 
     * @param tercero
     *            a fijar
     */
    public void setTercero(Boolean tercero) {
        this.tercero = tercero;
    }

    /**
     * 
     * @param agregaRol
     *            a fijar
     */
    public void setAgregaRol(Boolean agregaRol) {
        this.agregaRol = agregaRol;
    }

    /**
     * 
     * @return agregaRol
     */
    public Boolean getAgregaRol() {
        return agregaRol;
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
     * @return vigencia
     */
    public Vigencia getVigencia() {
        return vigencia;
    }

    /**
     * @return the rolNovell
     */
    public String getRolNovell() {
        return rolNovell;
    }

    /**
     * @param rolNovell
     *            the rolNovell to set
     */
    public void setRolNovell(String rolNovell) {
        this.rolNovell = rolNovell;
    }

}
