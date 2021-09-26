package mx.gob.sat.siat.juridica.base.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.catalogs.model.Dependencia;

import java.util.Date;

public class TipoTramiteDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -7378942133226770743L;

    /**
     * Atributo privado "vigencia" tipo Vigencia
     */
    private Integer idVigencia;

    /**
     * Atributo privado "idTipoTramite" tipo Integer
     */
    private Integer idTipoTramite;

    /**
     * Atributo privado "servicio" tipo String
     */
    private String servicio;

    /**
     * Atributo privado "descripcionServicio" tipo String
     */
    private String descripcionServicio;

    /**
     * Atributo privado "subservicio" tipo String
     */
    private String subservicio;

    /**
     * Atributo privado "descripcionSubservicio" tipo String
     */
    private String descripcionSubservicio;

    /**
     * Atributo privado "modalidad" tipo String
     */
    private String modalidad;

    /**
     * Atributo privado "descripcionModalidad" tipo String
     */
    private String descripcionModalidad;

    /**
     * Atributo privado "flujo" tipo String
     */
    private String flujo;

    /**
     * Atributo privado "descripcionFlujo" tipo String
     */
    private String descripcionFlujo;

    /**
     * Atributo privado "nivelServicio" tipo Integer
     */
    private Integer nivelServicio;

    /**
     * Atributo privado "dependencia" tipo Dependencia
     */
    private Dependencia dependencia;

    /**
     * Atributo privado "nombreServicioAxway" tipo String
     */
    private String nombreServicioAxway;

    /**
     * Atributo privado "nombreMensajeAxway" tipo String
     */
    private String nombreMensajeAxway;

    /**
     * Atributo privado "urlAxway" tipo String
     */
    private String urlAxway;

    /**
     * Atributo privado "fechaCaptura" tipo Date
     */
    private Date fechaCaptura;

    /**
     * Atributo privado "nombre" tipo String
     */
    private String nombre;

    /**
     * Atributo privado "blnReplicaInfo" tipo String
     */
    private String blnReplicaInfo;

    /**
     * Atributo privado "cveUnidadAdmResponsable" tipo
     * UnidadAdministrativa
     */
    private String cveUnidadAdmResponsable;

    /**
     * Atributo privado "blnAutomatico" tipo String
     */
    private String blnAutomatico;

    /**
     * Atributo privado "asignado" tipo Boolean
     */
    private Boolean asignado;

    /**
     * Atributo privado "ideTipoOperacion" tipo String
     */
    private String ideTipoOperacion;

    /**
     * Atributo privado "claveModulo" tipo int
     */
    private Integer claveModulo;
    /**
     * 
     */

    private Boolean blnServicioSelect;

    private Boolean blnAsignado;

    private Boolean blnResponsable;

    /**
     * @return the idVigencia
     */
    public Integer getIdVigencia() {
        return idVigencia;
    }

    /**
     * @param idVigencia
     *            the idVigencia to set
     */
    public void setIdVigencia(Integer idVigencia) {
        this.idVigencia = idVigencia;
    }

    /**
     * @return the idTipoTramite
     */
    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    /**
     * @param idTipoTramite
     *            the idTipoTramite to set
     */
    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    /**
     * @return the servicio
     */
    public String getServicio() {
        return servicio;
    }

    /**
     * @param servicio
     *            the servicio to set
     */
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    /**
     * @return the descripcionServicio
     */
    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    /**
     * @param descripcionServicio
     *            the descripcionServicio to set
     */
    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    /**
     * @return the subservicio
     */
    public String getSubservicio() {
        return subservicio;
    }

    /**
     * @param subservicio
     *            the subservicio to set
     */
    public void setSubservicio(String subservicio) {
        this.subservicio = subservicio;
    }

    /**
     * @return the descripcionSubservicio
     */
    public String getDescripcionSubservicio() {
        return descripcionSubservicio;
    }

    /**
     * @param descripcionSubservicio
     *            the descripcionSubservicio to set
     */
    public void setDescripcionSubservicio(String descripcionSubservicio) {
        this.descripcionSubservicio = descripcionSubservicio;
    }

    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad
     *            the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the descripcionModalidad
     */
    public String getDescripcionModalidad() {
        return descripcionModalidad;
    }

    /**
     * @param descripcionModalidad
     *            the descripcionModalidad to set
     */
    public void setDescripcionModalidad(String descripcionModalidad) {
        this.descripcionModalidad = descripcionModalidad;
    }

    /**
     * @return the flujo
     */
    public String getFlujo() {
        return flujo;
    }

    /**
     * @param flujo
     *            the flujo to set
     */
    public void setFlujo(String flujo) {
        this.flujo = flujo;
    }

    /**
     * @return the descripcionFlujo
     */
    public String getDescripcionFlujo() {
        return descripcionFlujo;
    }

    /**
     * @param descripcionFlujo
     *            the descripcionFlujo to set
     */
    public void setDescripcionFlujo(String descripcionFlujo) {
        this.descripcionFlujo = descripcionFlujo;
    }

    /**
     * @return the nivelServicio
     */
    public Integer getNivelServicio() {
        return nivelServicio;
    }

    /**
     * @param nivelServicio
     *            the nivelServicio to set
     */
    public void setNivelServicio(Integer nivelServicio) {
        this.nivelServicio = nivelServicio;
    }

    /**
     * @return the dependencia
     */
    public Dependencia getDependencia() {
        return dependencia;
    }

    /**
     * @param dependencia
     *            the dependencia to set
     */
    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    /**
     * @return the nombreServicioAxway
     */
    public String getNombreServicioAxway() {
        return nombreServicioAxway;
    }

    /**
     * @param nombreServicioAxway
     *            the nombreServicioAxway to set
     */
    public void setNombreServicioAxway(String nombreServicioAxway) {
        this.nombreServicioAxway = nombreServicioAxway;
    }

    /**
     * @return the nombreMensajeAxway
     */
    public String getNombreMensajeAxway() {
        return nombreMensajeAxway;
    }

    /**
     * @param nombreMensajeAxway
     *            the nombreMensajeAxway to set
     */
    public void setNombreMensajeAxway(String nombreMensajeAxway) {
        this.nombreMensajeAxway = nombreMensajeAxway;
    }

    /**
     * @return the urlAxway
     */
    public String getUrlAxway() {
        return urlAxway;
    }

    /**
     * @param urlAxway
     *            the urlAxway to set
     */
    public void setUrlAxway(String urlAxway) {
        this.urlAxway = urlAxway;
    }

    /**
     * @return the fechaCaptura
     */
    public Date getFechaCaptura() {
        return fechaCaptura != null ? (Date) fechaCaptura.clone() : null;
    }

    /**
     * @param fechaCaptura
     *            the fechaCaptura to set
     */
    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura != null ? (Date) fechaCaptura.clone() : null;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the blnReplicaInfo
     */
    public String getBlnReplicaInfo() {
        return blnReplicaInfo;
    }

    /**
     * @param blnReplicaInfo
     *            the blnReplicaInfo to set
     */
    public void setBlnReplicaInfo(String blnReplicaInfo) {
        this.blnReplicaInfo = blnReplicaInfo;
    }

    /**
     * @return the cveUnidadAdmResponsable
     */
    public String getCveUnidadAdmResponsable() {
        return cveUnidadAdmResponsable;
    }

    /**
     * @param cveUnidadAdmResponsable
     *            the cveUnidadAdmResponsable to set
     */
    public void setCveUnidadAdmResponsable(String cveUnidadAdmResponsable) {
        this.cveUnidadAdmResponsable = cveUnidadAdmResponsable;
    }

    /**
     * @return the blnAutomatico
     */
    public String getBlnAutomatico() {
        return blnAutomatico;
    }

    /**
     * @param blnAutomatico
     *            the blnAutomatico to set
     */
    public void setBlnAutomatico(String blnAutomatico) {
        this.blnAutomatico = blnAutomatico;
    }

    /**
     * @return the asignado
     */
    public Boolean getAsignado() {
        return asignado;
    }

    /**
     * @param asignado
     *            the asignado to set
     */
    public void setAsignado(Boolean asignado) {
        this.asignado = asignado;
    }

    /**
     * @return the ideTipoOperacion
     */
    public String getIdeTipoOperacion() {
        return ideTipoOperacion;
    }

    /**
     * @param ideTipoOperacion
     *            the ideTipoOperacion to set
     */
    public void setIdeTipoOperacion(String ideTipoOperacion) {
        this.ideTipoOperacion = ideTipoOperacion;
    }

    /**
     * @return the claveModulo
     */
    public Integer getClaveModulo() {
        return claveModulo;
    }

    /**
     * @param claveModulo
     *            the claveModulo to set
     */
    public void setClaveModulo(Integer claveModulo) {
        this.claveModulo = claveModulo;
    }

    /**
     * @return the blnServicioSelect
     */
    public Boolean getBlnServicioSelect() {
        return blnServicioSelect;
    }

    /**
     * @param blnServicioSelect
     *            the blnServicioSelect to set
     */
    public void setBlnServicioSelect(Boolean blnServicioSelect) {
        this.blnServicioSelect = blnServicioSelect;
    }

    /**
     * @return the blnAsignado
     */
    public Boolean getBlnAsignado() {
        return blnAsignado;
    }

    /**
     * @param blnAsignado
     *            the blnAsignado to set
     */
    public void setBlnAsignado(Boolean blnAsignado) {
        this.blnAsignado = blnAsignado;
    }

    /**
     * @return the blnResponsable
     */
    public Boolean getBlnResponsable() {
        return blnResponsable;
    }

    /**
     * @param blnResponsable
     *            the blnResponsable to set
     */
    public void setBlnResponsable(Boolean blnResponsable) {
        this.blnResponsable = blnResponsable;
    }

}
