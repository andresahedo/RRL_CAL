/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import mx.gob.sat.siat.juridica.base.dao.domain.BaseModel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCA_ROL_USUARIO")
public class InformacionUsuario extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "informacionUsuarioPK" tipo
     * InformacionUsuarioPK
     */
    @EmbeddedId
    private InformacionUsuarioPK informacionUsuarioPK;

    /**
     * Atributo privado "fechaInicio" tipo Date
     */
    @Column(name = "FECINIVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    /**
     * Atributo privado "fechaFin" tipo Date
     */
    @Column(name = "FECFINVIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Column(name = "BLNACTIVO")
    private Boolean blnActivo;

    @Column(name = "BLNROLEXTENDIDO")
    private Boolean rolReplica;

    /**
     * @return the rolReplica
     */
    public Boolean getRolReplica() {
        return rolReplica;
    }

    /**
     * @param rolReplica
     *            the rolReplica to set
     */
    public void setRolReplica(Boolean rolReplica) {
        this.rolReplica = rolReplica;
    }

    /**
     * @return the blnActivo
     */
    public Boolean getBlnActivo() {
        return blnActivo;
    }

    /**
     * 
     * @return informacionUsuarioPK
     */
    public InformacionUsuarioPK getInformacionUsuarioPK() {
        return informacionUsuarioPK;
    }

    /**
     * 
     * @param informacionUsuarioPK
     *            a fijar
     */
    public void setInformacionUsuarioPK(InformacionUsuarioPK informacionUsuarioPK) {
        this.informacionUsuarioPK = informacionUsuarioPK;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaInicio() {
        return fechaInicio != null ? (Date) fechaInicio.clone() : null;
    }

    /**
     * 
     * @param fechaInicio
     *            a fijar
     */
    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaInicio);
            this.fechaInicio = cal.getTime();
        }
        else {
            this.fechaInicio = null;
        }
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaFin() {
        return fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    /**
     * 
     * @param fechaFin
     *            a fijar
     */
    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaFin);
            this.fechaFin = cal.getTime();
        }
        else {
            this.fechaFin = null;
        }
    }

    /**
     * @return the blnActivo
     */
    public Boolean isBlnActivo() {
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
