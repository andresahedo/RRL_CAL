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
import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.UnidadAdministrativa;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCT_REMISION")
public class Remision extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -6622695840839354895L;

    /**
     * Atributo privado "idRemision" tipo Long
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDREMISION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_REMISION")
    @SequenceGenerator(name = "RVCQ_REMISION", sequenceName = "RVCQ_REMISION", allocationSize = 1)
    private Long idRemision;

    /**
     * Atributo privado "tramite" tipo Tramite
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "IDTRAMITE", referencedColumnName = "IDTRAMITE")
    private Tramite tramite;

    /**
     * Atributo privado "unidadAdminQueRemite" tipo
     * UnidadAdministrativa
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDUNIDADMINREMITE", nullable = true, referencedColumnName = "IDUNIADMIN")
    private UnidadAdministrativa unidadAdminQueRemite;

    /**
     * Atributo privado "unidadAdminNueva" tipo UnidadAdministrativa
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDUNIDADMINNUEVO", nullable = true, referencedColumnName = "IDUNIADMIN")
    private UnidadAdministrativa unidadAdminNueva;

    /**
     * Atributo privado "estadoRemision" tipo String
     */
    @Column(name = "IDEESTREMISION")
    private String estadoRemision;

    /**
     * Atributo privado "fechaAutorizacion" tipo Date
     */
    @Column(name = "FECAUTORIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAutorizacion;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    /**
     * Atributo privado "tipoRequerimiento" tipo String
     */
    @Column(name = "IDETIPOREMISION")
    private String tipoRemision;

    /**
     * Atributo privado "IDUSUARIO" tipo String
     */
    @Column(name = "IDUSUARIO")
    private String rfcAbogado;

    @Column(name = "IDETAREAORIGEN")
    private String ideTareaOrigen;

    /**
     * @return the idRemision
     */
    public Long getIdRemision() {
        return idRemision;
    }

    /**
     * @param idRemision
     *            the idRemision to set
     */
    public void setIdRemision(Long idRemision) {
        this.idRemision = idRemision;
    }

    /**
     * @return the tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * @param tramite
     *            the tramite to set
     */
    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the unidadAdminQueRemite
     */
    public UnidadAdministrativa getUnidadAdminQueRemite() {
        return unidadAdminQueRemite;
    }

    /**
     * @param unidadAdminQueRemite
     *            the unidadAdminQueRemite to set
     */
    public void setUnidadAdminQueRemite(UnidadAdministrativa unidadAdminQueRemite) {
        this.unidadAdminQueRemite = unidadAdminQueRemite;
    }

    /**
     * @return the unidadAdminNueva
     */
    public UnidadAdministrativa getUnidadAdminNueva() {
        return unidadAdminNueva;
    }

    /**
     * @param unidadAdminNueva
     *            the unidadAdminNueva to set
     */
    public void setUnidadAdminNueva(UnidadAdministrativa unidadAdminNueva) {
        this.unidadAdminNueva = unidadAdminNueva;
    }

    /**
     * @return the estadoRemision
     */
    public String getEstadoRemision() {
        return estadoRemision;
    }

    /**
     * @param estadoRemision
     *            the estadoRemision to set
     */
    public void setEstadoRemision(String estadoRemision) {
        this.estadoRemision = estadoRemision;
    }

    /**
     * @return the tipoRemision
     */
    public String getTipoRemision() {
        return tipoRemision;
    }

    /**
     * @param tipoRemision
     *            the tipoRemision to set
     */
    public void setTipoRemision(String tipoRemision) {
        this.tipoRemision = tipoRemision;
    }

    /**
     * @return the fechaAutorizacion
     */
    public Date getFechaAutorizacion() {
        return fechaAutorizacion != null ? (Date) fechaAutorizacion.clone() : null;
    }

    /**
     * @param fechaAutorizacion
     *            the fechaAutorizacion to set
     */
    public void setFechaAutorizacion(Date fechaAutorizacion) {
        if (fechaAutorizacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaAutorizacion);
            this.fechaAutorizacion = cal.getTime();
        }
        else {
            this.fechaAutorizacion = null;
        }
    }

    /**
     * @return the fechaBaja
     */
    public Date getFechaBaja() {
        return fechaBaja != null ? (Date) fechaBaja.clone() : null;
    }

    /**
     * @param fechaBaja
     *            the fechaBaja to set
     */
    public void setFechaBaja(Date fechaBaja) {
        if (fechaBaja != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaBaja);
            this.fechaBaja = cal.getTime();
        }
        else {
            this.fechaBaja = null;
        }
    }

    /**
     * Constructor vacio
     */
    public Remision() {}

    /**
     * Sobrecarga de constructor
     * 
     * @param idRemision
     */
    public Remision(Long idRemision) {
        this.idRemision = idRemision;
    }

    /**
     * @return the rfcAbogado
     */
    public String getRfcAbogado() {
        return rfcAbogado;
    }

    /**
     * @param rfcAbogado
     *            the rfcAbogado to set
     */
    public void setRfcAbogado(String rfcAbogado) {
        this.rfcAbogado = rfcAbogado;
    }

    public String getIdeTareaOrigen() {
        return ideTareaOrigen;
    }

    public void setIdeTareaOrigen(String ideTareaOrigen) {
        this.ideTareaOrigen = ideTareaOrigen;
    }

}
