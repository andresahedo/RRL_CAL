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
 * Clase Base para el CatalogoH
 * 
 * Bean que contienen atributos del modelo
 * 
 * @author Softtek
 */

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "RVCC_CATALOGOH")
public class CatalogoH extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "clave" tipo String
     */
    @Id
    @Column(name = "IDCATALOGOH")
    private String clave;

    /**
     * Atributo privado "nombreCatalogo" tipo String
     */
    @Basic(optional = false)
    @Column(name = "NOMCATALOGO")
    private String nombreCatalogo;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * 
     * Costructor de la clase
     */
    public CatalogoH() {}

    /**
     * 
     * Sobrecarga del constructor
     */
    public CatalogoH(String clave) {
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
     * @return nombreCatalogo
     */
    public String getNombreCatalogo() {
        return nombreCatalogo;
    }

    /**
     * 
     * @param nombreCatalogo
     *            a fijar
     */
    public void setNombreCatalogo(String nombreCatalogo) {
        this.nombreCatalogo = nombreCatalogo;
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
