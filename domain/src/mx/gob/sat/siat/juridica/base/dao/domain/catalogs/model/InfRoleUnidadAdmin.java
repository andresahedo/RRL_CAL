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
@Table(name = "RVCA_ROL_USU_UA_TT")
public class InfRoleUnidadAdmin extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "unidadAdminPK" tipo InfRoleUnidadAdminPK
     */
    @EmbeddedId
    private InfRoleUnidadAdminPK unidadAdminPK;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Atributo privado "fechaBajaLogica" tipo Date
     */
    @Column(name = "FECBAJA")
    @Temporal(TemporalType.DATE)
    private Date fechaBajaLogica;

    /**
     * Atributo privado "responsable" tipo boolean
     */
    @Column(name = "BLNRESPONSABLE")
    private boolean responsable;

    /**
     * @return the responsable
     */
    public boolean isResponsable() {
        return responsable;
    }

    /**
     * @param responsable
     *            the responsable to set
     */
    public void setResponsable(boolean responsable) {
        this.responsable = responsable;
    }

    /**
     * 
     * @return fechaBajaLogica
     */
    public Date getFechaBajaLogica() {
        return fechaBajaLogica != null ? (Date) fechaBajaLogica.clone() : null;
    }

    /**
     * 
     * @param fechaBajaLogica
     *            a fijar
     */
    public void setFechaBajaLogica(Date fechaBajaLogica) {
        if (fechaBajaLogica != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaBajaLogica);
            this.fechaBajaLogica = cal.getTime();
        }
        else {
            this.fechaBajaLogica = null;
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
     * @return unidadArminPK
     */
    public InfRoleUnidadAdminPK getUnidadAdminPK() {
        return unidadAdminPK;
    }

    /**
     * 
     * @param unidadAdminPK
     *            a fijar
     */
    public void setUnidadAdminPK(InfRoleUnidadAdminPK unidadAdminPK) {
        this.unidadAdminPK = unidadAdminPK;
    }

}
