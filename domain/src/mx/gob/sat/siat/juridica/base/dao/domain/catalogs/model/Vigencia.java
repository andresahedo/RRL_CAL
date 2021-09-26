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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 
 * @author softtek
 */
@Embeddable
public class Vigencia extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "fechaInicioVigencia" tipo Date
     */
    @Column(name = "FECINIVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioVigencia;

    /**
     * Atributo privado "fechaFinVigencia" tipo Date
     */
    @Column(name = "FECFINVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date fechaFinVigencia;
    
    /**
     * Atributo privado "blnActivo" tipo Integer
     */
    @Column(name = "BLNACTIVO")
    private Integer blnActivo;

    /**
     * Constructor vacio
     */
    public Vigencia() {}

    /**
     * Sobrecarga de constructor
     * 
     * @param fechaInicioVigencia
     * @param fechaFinVigencia
     * @param blnActivo
     */
    public Vigencia(Date fechaInicioVigencia, Date fechaFinVigencia, Integer blnActivo) {
        this.fechaInicioVigencia = (null == fechaInicioVigencia ? null : (Date) fechaInicioVigencia.clone());
        this.fechaFinVigencia = (null == fechaFinVigencia ? null : (Date) fechaFinVigencia.clone());
        this.blnActivo = blnActivo;
    }

    /**
     * 
     * @return fechaInicioVigencia
     */
    public Date getFechaInicioVigencia() {
        return (null == fechaInicioVigencia ? null : (Date) fechaInicioVigencia.clone());
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = (null == fechaInicioVigencia ? null : (Date) fechaInicioVigencia.clone());
    }

    /**
     * 
     * @return fechaFinVigencia
     */
    public Date getFechaFinVigencia() {
        return (null == fechaFinVigencia ? null : (Date) fechaFinVigencia.clone());
    }

    /**
     * 
     * @param fechaFinVigencia
     *            a fijar
     */
    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = (null == fechaFinVigencia ? null : (Date) fechaFinVigencia.clone());
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

}
