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
import mx.gob.sat.siat.juridica.base.dao.domain.constants.MediosDefensa;

import javax.persistence.*;

/**
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCT_MEDIODEFENSA")
public class MedioDefensa extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = -3775036194290192979L;

    /**
     * Atributo privado "idMedioDefensa" tipo Long
     */
    @Id
    @Column(name = "IDMEDIODEFENSA", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_MEDIODEFENSA")
    @SequenceGenerator(name = "RVCQ_MEDIODEFENSA", sequenceName = "RVCQ_MEDIODEFENSA", allocationSize = 1)
    private Long idMedioDefensa;

    /**
     * Atributo privado "solicitud" tipo Solicitud
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "IDSOLICITUD", nullable = true, referencedColumnName = "IDSOLICITUD", insertable = false,
            updatable = false)
    private Solicitud solicitud;

    /**
     * Atributo privado "idSolicitud" tipo Long
     */
    @Basic(optional = false)
    @Column(name = "IDSOLICITUD", nullable = false)
    private Long idSolicitud;

    /**
     * Atributo privado "tipoMedioDefensa" tipo String
     */
    @Column(name = "IDETIPOMEDIODEFENSA")
    private String tipoMedioDefensa;

    /**
     * Atributo privado "numeroAsunto" tipo String
     */
    @Column(name = "NUMASUNTO")
    private String numeroAsunto;

    /**
     * Atributo privado "sentidoResolucion" tipo String
     */
    @Column(name = "IDESENTRESOLUCION")
    private String sentidoResolucion;

    /**
     * Atributo privado "otroMedio" tipo String
     */
    @Column(name = "DESCOTROMEDIO")
    private String otroMedio;

    @Column(name = "DESCAUTORIDADMEDIO")
    private String descAutoridad;

    /**
     * Atributo privado "blnActivo" tipo Boolean
     */
    @Column(name = "BLNACTIVO")
    private Boolean blnActivo;

    /**
     * 
     * @return idMedioDefensa
     */
    public Long getIdMedioDefensa() {
        return idMedioDefensa;
    }

    /**
     * 
     * @param idMedioDefensa
     *            a fijar
     */
    public void setIdMedioDefensa(Long idMedioDefensa) {
        this.idMedioDefensa = idMedioDefensa;
    }

    /**
     * 
     * @return solicitud
     */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /**
     * 
     * @param solicitud
     *            a fijar
     */
    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    /**
     * 
     * @return idSolicitud
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * 
     * @param idSolicitud
     *            a fijar
     */
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * 
     * @return MediosDefensa
     */
    public MediosDefensa getTipoMedioDefensa() {
        return MediosDefensa.parse(tipoMedioDefensa);
    }

    /**
     * 
     * @param tipoMedioDefensa
     *            a fijar
     */
    public void setTipoMedioDefensa(MediosDefensa tipoMedioDefensa) {
        this.tipoMedioDefensa = tipoMedioDefensa.getClave();
    }

    /**
     * 
     * @return numeroAsunto
     */
    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    /**
     * 
     * @param numeroAsunto
     *            a fijar
     */
    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    /**
     * @return the sentidoResolucion
     */
    public String getSentidoResolucion() {
        return sentidoResolucion;
    }

    /**
     * @param sentidoResolucion
     *            the sentidoResolucion to set
     */
    public void setSentidoResolucion(String sentidoResolucion) {
        this.sentidoResolucion = sentidoResolucion;
    }

    /**
     * 
     * @return otroMedio
     */
    public String getOtroMedio() {
        return otroMedio;
    }

    /**
     * 
     * @param otroMedio
     *            a fijar
     */
    public void setOtroMedio(String otroMedio) {
        this.otroMedio = otroMedio;
    }

    /**
     * @return the descAutoridad
     */
    public String getDescAutoridad() {
        return descAutoridad;
    }

    /**
     * @param descAutoridad
     *            the descAutoridad to set
     */
    public void setDescAutoridad(String descAutoridad) {
        this.descAutoridad = descAutoridad;
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
