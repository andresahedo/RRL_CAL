package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dao.domain.constants.DiscriminadorConstants;
import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;

import java.util.Date;

public class DatosSolicitudDTO extends SolicitudDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 7739610614536238737L;
    private String rfcContribuyente;
    private String estadoContribuyente;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String calle;
    private String numeroExterior;
    private String numeroInterior;
    private String colonia;
    private String claveColonia;
    private String delegacionMunicipio;
    private String claveDelegacion;
    private String estado;
    private String paisCve;
    private String paisNombre;
    private String claveEstado;
    private String codigoPostal;
    private String telefono;
    private String correoElectronico;
    private String representanteLegal;
    private String administracionLocal;
    private String nombreAdministracionLocal;
    private String idAutoridadEmisora;
    private String nombreAutoridadEmisora;
    private String razonSocial;
    private String rolCapturista;
    private String numeroFolio;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaCreacion;
    private Date fechaInicioTramite;
    private Date fechaApertura;
    private Long idSolicitud;
    private boolean muestraMensajeBuzon;
    private String mensajeBuzon;
    private String cveLocalidad;
    private String tipoRolContribuyente;

    public String getTipoRolContribuyente() {
        return tipoRolContribuyente;
    }

    public void setTipoRolContribuyente(String tipoRolContribuyente) {
        this.tipoRolContribuyente = tipoRolContribuyente;
    }
    public DatosSolicitudDTO() {

    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getDelegacionMunicipio() {
        return delegacionMunicipio;
    }

    public void setDelegacionMunicipio(String delegacionMunicipio) {
        this.delegacionMunicipio = delegacionMunicipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getAdministracionLocal() {
        return administracionLocal;
    }

    public void setAdministracionLocal(String administracionLocal) {
        this.administracionLocal = administracionLocal;
    }

    public String getIdAutoridadEmisora() {
        return idAutoridadEmisora;
    }

    public void setIdAutoridadEmisora(String idAutoridadEmisora) {
        this.idAutoridadEmisora = idAutoridadEmisora;
    }

    public String getNombreAutoridadEmisora() {
        return nombreAutoridadEmisora;
    }

    public void setNombreAutoridadEmisora(String nombreAutoridadEmisora) {
        this.nombreAutoridadEmisora = nombreAutoridadEmisora;
    }

    public Date getFechaInicio() {
        return fechaInicio != null ? (Date) fechaInicio.clone() : null;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio != null ? (Date) fechaInicio.clone() : null;
    }

    public Date getFechaFin() {
        return fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin != null ? (Date) fechaFin.clone() : null;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Date getFechaCreacion() {
        return fechaCreacion != null ? (Date) fechaCreacion.clone() : null;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion != null ? (Date) fechaCreacion.clone() : null;
    }

    public Date getFechaInicioTramite() {
        return fechaInicioTramite != null ? (Date) fechaInicioTramite.clone() : null;
    }

    public void setFechaInicioTramite(Date fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite != null ? (Date) fechaInicioTramite.clone() : null;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreAdministracionLocal() {
        return nombreAdministracionLocal;
    }

    public void setNombreAdministracionLocal(String nombreAdministracionLocal) {
        this.nombreAdministracionLocal = nombreAdministracionLocal;
    }

    public String getClaveColonia() {
        return claveColonia;
    }

    public void setClaveColonia(String claveColonia) {
        this.claveColonia = claveColonia;
    }

    public String getClaveDelegacion() {
        return claveDelegacion;
    }

    public void setClaveDelegacion(String claveDelegacion) {
        this.claveDelegacion = claveDelegacion;
    }

    public String getClaveEstado() {
        return claveEstado;
    }

    public void setClaveEstado(String claveEstado) {
        this.claveEstado = claveEstado;
    }

    /**
     * @return the tipoTramite
     */
    @Override
    public String getTipoTramite() {
        return DiscriminadorConstants.T1_CLASIFICACION_ARANCELARIA;
    }

    /**
     * @return the rolCapturista
     */
    public String getRolCapturista() {
        return rolCapturista;
    }

    /**
     * @param rolCapturista
     *            the rolCapturista to set
     */
    public void setRolCapturista(String rolCapturista) {
        this.rolCapturista = rolCapturista;
    }

    /**
     * @return the paisCve
     */
    public String getPaisCve() {
        return paisCve;
    }

    /**
     * @param paisCve
     *            the paisCve to set
     */
    public void setPaisCve(String paisCve) {
        this.paisCve = paisCve;
    }

    /**
     * @return the paisNombre
     */
    public String getPaisNombre() {
        return paisNombre;
    }

    /**
     * @param paisNombre
     *            the paisNombre to set
     */
    public void setPaisNombre(String paisNombre) {
        this.paisNombre = paisNombre;
    }

    /**
     * @return the numeroFolio
     */
    public String getNumeroFolio() {
        return numeroFolio;
    }

    /**
     * @param numeroFolio
     *            the numeroFolio to set
     */
    public void setNumeroFolio(String numeroFolio) {
        this.numeroFolio = numeroFolio;
    }

    /**
     * @return the fechaApertura
     */
    public Date getFechaApertura() {
        return fechaApertura != null ? (Date) fechaApertura.clone() : null;
    }

    /**
     * @param fechaApertura
     *            the fechaApertura to set
     */
    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura != null ? (Date) fechaApertura.clone() : null;
    }

    public boolean isMuestraMensajeBuzon() {
        return muestraMensajeBuzon;
    }

    public void setMuestraMensajeBuzon(boolean muestraMensajeBuzon) {
        this.muestraMensajeBuzon = muestraMensajeBuzon;
    }

    public String getMensajeBuzon() {
        return mensajeBuzon;
    }

    public void setMensajeBuzon(String mensajeBuzon) {
        this.mensajeBuzon = mensajeBuzon;
    }

    /**
     * @return the cveLocalidad
     */
    public String getCveLocalidad() {
        return cveLocalidad;
    }

    /**
     * @param cveLocalidad
     *            the cveLocalidad to set
     */
    public void setCveLocalidad(String cveLocalidad) {
        this.cveLocalidad = cveLocalidad;
    }

    /**
     * @return the estadoContribuyente
     */
    public String getEstadoContribuyente() {
        return estadoContribuyente;
    }

    /**
     * @param estadoContribuyente the estadoContribuyente to set
     */
    public void setEstadoContribuyente(String estadoContribuyente) {
        this.estadoContribuyente = estadoContribuyente;
    }

}
