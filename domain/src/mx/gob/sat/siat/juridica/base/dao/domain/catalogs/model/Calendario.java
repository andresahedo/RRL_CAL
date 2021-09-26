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
 * Clase Base para el calendario
 * 
 * Bean que contienen los atributos del modelo
 * 
 * @author Softtek
 */
@Entity
@Table(name = "RVCC_CALENDARIO")
public class Calendario extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "clave" tipo String
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDCALENDARIO")
    private String clave;

    /**
     * 
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * 
     * Instancia de Vigencia tipo privado
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * 
     * Costructor de la clase
     */
    public Calendario() {}

    /**
     * 
     * Sobrecarga del constructor
     */
    public Calendario(String clave) {
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
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre
     *            a fijar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * Metodo hashCode
     * 
     */
    public int hashCode() {
        int hash = 0;
        hash += (clave != null ? clave.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo comprobar la instancia del calendario
     * 
     * @param object
     * @return boolean
     */
    public boolean equals(Object object) {
        if (!(object instanceof Calendario)) {
            return false;
        }
        Calendario other = (Calendario) object;
        if (this.clave == null || other.clave == null || !this.clave.equals(other.clave)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.Calendario[clave=" + clave + "]"
     */
    public String toString() {
        return "entity.Calendario[clave=" + clave + "]";
    }

}
