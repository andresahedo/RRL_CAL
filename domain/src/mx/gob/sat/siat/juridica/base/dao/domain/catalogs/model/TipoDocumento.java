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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCC_TIPODOCUMENTO")
public class TipoDocumento extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Atributo privado "idTipoDocumento" tipo Integer
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPODOCUMENTO")
    private Integer idTipoDocumento;

    /**
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * Atributo privado "fechaCaptura" tipo Date
     */
    @Column(name = "FECCAPTURA")
    @Temporal(TemporalType.DATE)
    private Date fechaCaptura;

    /**
     * Atributo privado "rangoResolucionImagen" tipo String
     */
    @Column(name = "IDERANGORESOLUCIONIMAGEN")
    private String rangoResolucionImagen;

    /**
     * Atributo privado "tamanoMaximo" tipo Integer
     */
    @Column(name = "TAMANIOMAXIMO")
    private Integer tamanoMaximo;
    /**
     * Descripcion para texto de ayuda al usuario.
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Sobrecarga de Constructor
     * 
     * @param documento
     */
    public TipoDocumento(TipoDocumento documento) {
        this.idTipoDocumento = documento.getIdTipoDocumento();
        this.nombre = documento.getNombre();

    }

    /**
     * Constructor vacio
     */
    public TipoDocumento() {}

    /**
     * Sobrecarga de constructor
     * 
     * @param idTipoDocumento
     */
    public TipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    /**
     * 
     * @return idTipoDocumento
     */
    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    /**
     * 
     * @param idTipoDocumento
     *            a fijar
     */
    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
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
     * Metodo toString
     * 
     * @return "entity.TipoDocumento[idTipoDocumento=" +
     *         idTipoDocumento + "]";
     */
    public String toString() {
        return "entity.TipoDocumento[idTipoDocumento=" + idTipoDocumento + "]";
    }

    /**
     * 
     * @return fechaCaptura
     */
    public Date getFechaCaptura() {
        return fechaCaptura != null ? (Date) fechaCaptura.clone() : null;
    }

    /**
     * 
     * @param fechaCaptura
     *            a fijar
     */
    public void setFechaCaptura(Date fechaCaptura) {
        if (fechaCaptura != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaCaptura);
            this.fechaCaptura = cal.getTime();
        }
        else {
            this.fechaCaptura = null;
        }
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
     * @param rangoResolucionImagen
     *            a fijar
     */
    public void setRangoResolucionImagen(String rangoResolucionImagen) {
        this.rangoResolucionImagen = rangoResolucionImagen;
    }

    /**
     * 
     * @return rangoResolucionImagen
     */
    public String getRangoResolucionImagen() {
        return rangoResolucionImagen;
    }

    /**
     * 
     * @param tamanoMaximo
     *            a fijar
     */
    public void setTamanoMaximo(Integer tamanoMaximo) {
        this.tamanoMaximo = tamanoMaximo;
    }

    /**
     * 
     * @return tamanoMaximo
     */
    public Integer getTamanoMaximo() {
        return tamanoMaximo;
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

}
