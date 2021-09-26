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
import java.util.Date;

/**
 * 
 * @author softtek
 */
@Entity
@Table(name = "RVCC_TIPOTRAMITE")
public class TipoTramite extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 433758234975213902L;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    @Embedded
    private Vigencia vigencia;

    /**
     * Atributo privado "idTipoTramite" tipo Integer
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPOTRAMITE")
    private Integer idTipoTramite;

    /**
     * Atributo privado "servicio" tipo String
     */
    @Column(name = "IDSERVICIO")
    private String servicio;

    /**
     * Atributo privado "descripcionServicio" tipo String
     */
    @Column(name = "DESCSERVICIO")
    private String descripcionServicio;

    /**
     * Atributo privado "subservicio" tipo String
     */
    @Column(name = "IDSUBSERVICIO")
    private String subservicio;

    /**
     * Atributo privado "descripcionSubservicio" tipo String
     */
    @Column(name = "DESCSUBSERVICIO")
    private String descripcionSubservicio;

    /**
     * Atributo privado "modalidad" tipo String
     */
    @Column(name = "IDMODALIDAD")
    private String modalidad;

    /**
     * Atributo privado "descripcionModalidad" tipo String
     */
    @Column(name = "DESCMODALIDAD")
    private String descripcionModalidad;

    /**
     * Atributo privado "flujo" tipo String
     */
    @Column(name = "IDFLUJO")
    private String flujo;

    /**
     * Atributo privado "descripcionFlujo" tipo String
     */
    @Column(name = "DESCFLUJO")
    private String descripcionFlujo;

    /**
     * Atributo privado "nivelServicio" tipo Integer
     */
    @Column(name = "NIVELSERVICIO")
    private Integer nivelServicio;

    /**
     * Atributo privado "dependencia" tipo Dependencia
     */
    @JoinColumn(name = "IDDEPENDENCIA", referencedColumnName = "IDDEPENDENCIA")
    @ManyToOne(fetch = FetchType.LAZY)
    private Dependencia dependencia;

    /**
     * Atributo privado "nombreServicioAxway" tipo String
     */
    @Column(name = "NOMSERVAXWAY")
    private String nombreServicioAxway;

    /**
     * Atributo privado "nombreMensajeAxway" tipo String
     */
    @Column(name = "NOMMENSAJEAXWAY")
    private String nombreMensajeAxway;

    /**
     * Atributo privado "urlAxway" tipo String
     */
    @Column(name = "URLAXWAY")
    private String urlAxway;

    /**
     * Atributo privado "fechaCaptura" tipo Date
     */
    @Column(name = "FECCAPTURA")
    @Temporal(TemporalType.DATE)
    private Date fechaCaptura;

    /**
     * Atributo privado "nombre" tipo String
     */
    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * Atributo privado "blnReplicaInfo" tipo String
     */
    @Column(name = "BLNREPLICAINFO")
    private String blnReplicaInfo;

    /**
     * Atributo privado "cveUnidadAdmResponsable" tipo
     * UnidadAdministrativa
     */
    @JoinColumn(name = "IDUNIDADADMRESPONSABLE", referencedColumnName = "IDUNIADMIN")
    @ManyToOne(fetch = FetchType.EAGER)
    private UnidadAdministrativa cveUnidadAdmResponsable;

    /**
     * Atributo privado "blnAutomatico" tipo String
     */
    @Column(name = "BLNAUTOMATICO")
    private String blnAutomatico;

    /**
     * Atributo privado "blnActivo" tipo String
     */
    @Column(name = "BLNACTIVO", insertable = false, updatable = false)
    private String blnActivo;

    /**
     * Atributo privado "asignado" tipo Boolean
     */
    @Column(name = "BLNASIGNACION")
    private Boolean asignado;

    /**
     * Atributo privado "ideTipoOperacion" tipo String
     */
    @Column(name = "IDETIPOOPERACION")
    private String ideTipoOperacion;

    /**
     * Atributo privado "claveModulo" tipo int
     */
    @Column(name = "IDMODULO")
    private Integer claveModulo;

    @Column
    @Transient
    private String tooltip;

    @Transient
    private Integer blnServicioSelect;

    @Transient
    private Integer blnResponsable;

    /**
     * Sobrecarga de Constructor
     * 
     * @param tipoTramite
     */
    public TipoTramite(TipoTramite tipoTramite) {

        this.dependencia = tipoTramite.getDependencia();
        this.descripcionFlujo = tipoTramite.getDescripcionFlujo();
        this.descripcionModalidad = tipoTramite.getDescripcionModalidad();
        this.descripcionServicio = tipoTramite.getDescripcionServicio();
        this.descripcionSubservicio = tipoTramite.getDescripcionSubservicio();
        this.fechaCaptura = tipoTramite.getFechaCaptura();
        this.flujo = tipoTramite.getFlujo();
        this.idTipoTramite = tipoTramite.getIdTipoTramite();
        this.modalidad = tipoTramite.getModalidad();
        this.nivelServicio = tipoTramite.getNivelServicio();
        this.nombreMensajeAxway = tipoTramite.getNombreMensajeAxway();
        this.nombreServicioAxway = tipoTramite.getNombreServicioAxway();
        this.servicio = tipoTramite.getServicio();
        this.subservicio = tipoTramite.getSubservicio();
        this.urlAxway = tipoTramite.getUrlAxway();
        this.claveModulo = tipoTramite.getClaveModulo();

    }

    /**
     * Constructor vacio
     */
    public TipoTramite() {}

    /**
     * Sobrecarga de Constructor
     * 
     * @param idTipoTramite
     */
    public TipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    /**
     * 
     * @return idTipoTramite
     */
    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    /**
     * 
     * @param idTipoTramite
     *            a fijar
     */
    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    /**
     * 
     * @return servicio
     */
    public String getServicio() {
        return servicio;
    }

    /**
     * 
     * @param servicio
     *            a fijar
     */
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    /**
     * 
     * @return descripcionServicio
     */
    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    /**
     * 
     * @param descripcionServicio
     *            a fijar
     */
    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    /**
     * 
     * @return subservicio
     */
    public String getSubservicio() {
        return subservicio;
    }

    /**
     * 
     * @param subservicio
     *            a fijar
     */
    public void setSubservicio(String subservicio) {
        this.subservicio = subservicio;
    }

    /**
     * 
     * @return descripcionSubservicio
     */
    public String getDescripcionSubservicio() {
        return descripcionSubservicio;
    }

    /**
     * 
     * @param descripcionSubservicio
     *            a fijar
     */
    public void setDescripcionSubservicio(String descripcionSubservicio) {
        this.descripcionSubservicio = descripcionSubservicio;
    }

    /**
     * 
     * @return modaliad
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * 
     * @param modalidad
     *            a fijar
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * 
     * @return descripcionModalidad
     */
    public String getDescripcionModalidad() {
        return descripcionModalidad;
    }

    /**
     * 
     * @param descripcionModalidad
     *            a fijar
     */
    public void setDescripcionModalidad(String descripcionModalidad) {
        this.descripcionModalidad = descripcionModalidad;
    }

    /**
     * 
     * @return flujo
     */
    public String getFlujo() {
        return flujo;
    }

    /**
     * 
     * @param flujo
     *            a fijar
     */
    public void setFlujo(String flujo) {
        this.flujo = flujo;
    }

    /**
     * 
     * @return descripcionFlujo
     */
    public String getDescripcionFlujo() {
        return descripcionFlujo;
    }

    /**
     * 
     * @param descripcionFlujo
     *            a fijar
     */
    public void setDescripcionFlujo(String descripcionFlujo) {
        this.descripcionFlujo = descripcionFlujo;
    }

    /**
     * 
     * @return nivelServicio
     */
    public Integer getNivelServicio() {
        return nivelServicio;
    }

    /**
     * 
     * @param nivelServicio
     *            a fijar
     */
    public void setNivelServicio(Integer nivelServicio) {
        this.nivelServicio = nivelServicio;
    }

    /**
     * 
     * @return nombreServicioAxway
     */
    public String getNombreServicioAxway() {
        return nombreServicioAxway;
    }

    /**
     * 
     * @param nombreServicioAxway
     *            a fijar
     */
    public void setNombreServicioAxway(String nombreServicioAxway) {
        this.nombreServicioAxway = nombreServicioAxway;
    }

    /**
     * 
     * @return nombreMensajeAxway
     */
    public String getNombreMensajeAxway() {
        return nombreMensajeAxway;
    }

    /**
     * 
     * @param nombreMensajeAxway
     *            a fijar
     */
    public void setNombreMensajeAxway(String nombreMensajeAxway) {
        this.nombreMensajeAxway = nombreMensajeAxway;
    }

    /**
     * 
     * @return urlAxway
     */
    public String getUrlAxway() {
        return urlAxway;
    }

    /**
     * 
     * @param urlAxway
     *            a fijar
     */
    public void setUrlAxway(String urlAxway) {
        this.urlAxway = urlAxway;
    }

    /**
     * 
     * @return fechaCaptura
     */
    public Date getFechaCaptura() {
        return (null == fechaCaptura ? null : (Date) fechaCaptura.clone());
    }

    /**
     * 
     * @param fechaCaptura
     *            a fijar
     */
    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = (null == fechaCaptura ? null : (Date) fechaCaptura.clone());
    }

    /**
     * 
     * @return dependencia
     */
    public Dependencia getDependencia() {
        return dependencia;
    }

    /**
     * 
     * @param dependencia
     *            a fijar
     */
    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    /**
     * 
     * @return blnReplicaInfo
     */
    public String getBlnReplicaInfo() {
        return blnReplicaInfo;
    }

    /**
     * 
     * @param blnReplicaInfo
     *            a fijar
     */
    public void setBlnReplicaInfo(String blnReplicaInfo) {
        this.blnReplicaInfo = blnReplicaInfo;
    }

    /**
     * 
     * @return blnAutomatico
     */
    public String getBlnAutomatico() {
        return blnAutomatico;
    }

    /**
     * 
     * @param blnAutomatico
     *            a fijar
     */
    public void setBlnAutomatico(String blnAutomatico) {
        this.blnAutomatico = blnAutomatico;
    }

    /**
     * 
     * @return cveUnidadAdmResponsable
     */
    public UnidadAdministrativa getCveUnidadAdmResponsable() {
        return cveUnidadAdmResponsable;
    }

    /**
     * 
     * @param cveUnidadAdmResponsable
     *            a fijar
     */
    public void setCveUnidadAdmResponsable(UnidadAdministrativa cveUnidadAdmResponsable) {
        this.cveUnidadAdmResponsable = cveUnidadAdmResponsable;
    }

    /**
     * Metodo hashCode
     * 
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoTramite != null ? idTipoTramite.hashCode() : 0);
        return hash;
    }

    /**
     * Metodo equals
     * 
     * @return boolean
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TipoTramite)) {
            return false;
        }
        TipoTramite other = (TipoTramite) object;
        if (this.idTipoTramite == null || other.idTipoTramite == null
                || !this.idTipoTramite.equals(other.idTipoTramite)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo toString
     * 
     * @return "entity.TipoTramite[idTipoTramite=" + idTipoTramite +
     *         "]";
     */
    @Override
    public String toString() {
        return "entity.TipoTramite[idTipoTramite=" + idTipoTramite + "]";
    }

    /**
     * 
     * @return asignado
     */
    public Boolean getAsignado() {
        return asignado;
    }

    /**
     * 
     * @param asignado
     *            a fijar
     */
    public void setAsignado(Boolean asignado) {
        this.asignado = asignado;
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
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @return descripcionSubservicio
     */
    public String getNombreMenu() {
        if (descripcionModalidad != null) {
            return descripcionModalidad;
        }
        return descripcionSubservicio;
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
     * @return claveModulo
     */
    public Integer getClaveModulo() {
        return claveModulo;
    }

    /**
     * 
     * @param claveModulo
     *            a fijar
     */
    public void setClaveModulo(Integer claveModulo) {
        this.claveModulo = claveModulo;
    }

    /**
     * 
     * @param ideTipoOperacion
     *            a fijar
     */
    public void setIdeTipoOperacion(String ideTipoOperacion) {
        this.ideTipoOperacion = ideTipoOperacion;
    }

    /**
     * 
     * @return ideTipoOperacion
     */
    public String getIdeTipoOperacion() {
        return ideTipoOperacion;
    }

    /**
     * @return the tooltip
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * @param tooltip
     *            the tooltip to set
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * @return the blnActivo
     */
    public String getBlnActivo() {
        return blnActivo;
    }

    /**
     * @param blnActivo
     *            the blnActivo to set
     */
    public void setBlnActivo(String blnActivo) {
        this.blnActivo = blnActivo;
    }

    /**
     * @return the blnServicioSelect
     */
    public Integer getBlnServicioSelect() {
        return blnServicioSelect;
    }

    /**
     * @param blnServicioSelect
     *            the blnServicioSelect to set
     */
    public void setBlnServicioSelect(Integer blnServicioSelect) {
        this.blnServicioSelect = blnServicioSelect;
    }

    /**
     * @return the blnResponsable
     */
    public Integer getBlnResponsable() {
        return blnResponsable;
    }

    /**
     * @param blnResponsable
     *            the blnResponsable to set
     */
    public void setBlnResponsable(Integer blnResponsable) {
        this.blnResponsable = blnResponsable;
    }

}
