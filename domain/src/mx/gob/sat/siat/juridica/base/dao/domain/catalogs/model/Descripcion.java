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

/**
 * Mapeo a la tabla RVCC_DESCRIPCION destinada inicialmente para
 * almacenar textos de tooltips
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCC_DESCRIPCION")
public class Descripcion extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idDescripcion" tipo Long
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDDESCRIPCION")
    private Long idDescripcion;

    /**
     * Atributo privado "descripcion" tipo String Campo para indicar
     * la descripci&oacute;n
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Atributo privado "ideTipoDescripcion" tipo String Identificador
     * del tipo de descripci&oacute;n
     */
    @Column(name = "IDETIPODESCRIPCION")
    private String ideTipoDescripcion;

    /**
     * Atributo privado "identificador" tipo String Para indicar la
     * llave con la que se relaciona la descripci&oacute;n.
     */
    @Column(name = "IDENTIFICADOR")
    private String identificador;

    /**
     * Atributo privado "blnActivo" tipo Integer
     */
    @Column(name = "BLNACTIVO")
    private Integer blnActivo;

    /**
     * Constructor vacio
     */
    public Descripcion() {}

    /**
     * 
     * Sobrecarga del constructor
     */
    public Descripcion(Long idDescripcion) {
        this.idDescripcion = idDescripcion;
    }

    /**
     * 
     * @return idDescripcion
     */
    public Long getIdDescripcion() {
        return idDescripcion;
    }

    /**
     * 
     * @param idDescripcion
     *            a fijar
     */
    public void setIdDescripcion(Long idDescripcion) {
        this.idDescripcion = idDescripcion;
    }

    /**
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
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
     * @return ideTipoDescripcion
     */
    public String getIdeTipoDescripcion() {
        return ideTipoDescripcion;
    }

    /**
     * 
     * @param ideTipoDescripcion
     *            a fijar
     */
    public void setIdeTipoDescripcion(String ideTipoDescripcion) {
        this.ideTipoDescripcion = ideTipoDescripcion;
    }

    /**
     * 
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * 
     * @param identificador
     *            a fijar
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * 
     * @return blnActivo
     */
    public Integer getBlnActivo() {
        return blnActivo;
    }

    /**
     * 
     * @param blnActivo
     *            a fijar
     */
    public void setBlnActivo(Integer blnActivo) {
        this.blnActivo = blnActivo;
    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    public int hashCode() {
        int hash = 0;
        hash += (idDescripcion != null ? idDescripcion.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof Descripcion)) {
            return false;
        }
        Descripcion other = (Descripcion) object;
        if (this.idDescripcion == null || other.idDescripcion == null
                || !this.idDescripcion.equals(other.idDescripcion)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.Descripcion[idDescripcion=" + idDescripcion +
     *         "]";
     */
    public String toString() {
        return "entity.Descripcion[idDescripcion=" + idDescripcion + "]";
    }

}
