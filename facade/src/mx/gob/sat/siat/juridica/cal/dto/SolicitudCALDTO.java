package mx.gob.sat.siat.juridica.cal.dto;

import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SolicitudCALDTO extends SolicitudDTO {

    private static final long serialVersionUID = 6853055295631257691L;

    private List<PersonaOirNotificacionesDTO> listaPersonasNotificaciones;
    private List<PersonaInvolucradaDTO> listaPersonasInvolucradas;
    private List<FraccionArancelariaDudaDTO> listaFraccionDuda;
    private transient List<ManifiestoDTO> manifiestos;

    private String granContribuyete;
    private BigDecimal montoOperacion;
    private String actividadInteresado;
    private String hechosCircunstancias;
    private String razonesNegocio;
    private String hechosCircunstanciasPrevPlanteadas;
    private String claveAutoridad;
    private String claveUnidadEmisora;
    private Date fechaPromocion;
    private Date fechaCreacion;

    private String hechosCircunstanciasMatMedios;
    private Long idMedioDefensa;
    private String claveMedioDefensa;
    private String numeroAsunto;
    private String claveSentidoResolucion;
    private String descripcionAutoridad;

    private String contribuyenteSujetoEjercicio;
    private String periodosContribuciones;
    private String plazo;
    private String claveAutoridadRevisando;

    private String tipoClasificacion;
    private String descripcionMercancia;
    private String fraccionAplicable;
    private String fraccionDuda;

    private String numeroFolio;
    private Date fechaApertura;
    private String mensajeBuzon;
    private boolean muestraMensaje;
    private String tipoRolContribuyente;
    private String estadoContribuyente;

    public SolicitudCALDTO() {}

    /**
     * @return the listaPersonasNotificaciones
     */
    public List<PersonaOirNotificacionesDTO> getListaPersonasNotificaciones() {
        return listaPersonasNotificaciones;
    }

    /**
     * @param listaPersonasNotificaciones
     *            the listaPersonasNotificaciones to set
     */
    public void setListaPersonasNotificaciones(List<PersonaOirNotificacionesDTO> listaPersonasNotificaciones) {
        this.listaPersonasNotificaciones = listaPersonasNotificaciones;
    }

    /**
     * @return the listaPersonasInvolucradas
     */
    public List<PersonaInvolucradaDTO> getListaPersonasInvolucradas() {
        return listaPersonasInvolucradas;
    }

    /**
     * @param listaPersonasInvolucradas
     *            the listaPersonasInvolucradas to set
     */
    public void setListaPersonasInvolucradas(List<PersonaInvolucradaDTO> listaPersonasInvolucradas) {
        this.listaPersonasInvolucradas = listaPersonasInvolucradas;
    }

    /**
     * @return the granContribuyete
     */
    public String getGranContribuyete() {
        return granContribuyete;
    }

    /**
     * @param granContribuyete
     *            the granContribuyete to set
     */
    public void setGranContribuyete(String granContribuyete) {
        this.granContribuyete = granContribuyete;
    }

    /**
     * @return the actividadInteresado
     */
    public String getActividadInteresado() {
        return actividadInteresado;
    }

    /**
     * @param actividadInteresado
     *            the actividadInteresado to set
     */
    public void setActividadInteresado(String actividadInteresado) {
        this.actividadInteresado = actividadInteresado;
    }

    /**
     * @return the hechosCircunstancias
     */
    public String getHechosCircunstancias() {
        return hechosCircunstancias;
    }

    /**
     * @param hechosCircunstancias
     *            the hechosCircunstancias to set
     */
    public void setHechosCircunstancias(String hechosCircunstancias) {
        this.hechosCircunstancias = hechosCircunstancias;
    }

    /**
     * @return the razonesNegocio
     */
    public String getRazonesNegocio() {
        return razonesNegocio;
    }

    /**
     * @param razonesNegocio
     *            the razonesNegocio to set
     */
    public void setRazonesNegocio(String razonesNegocio) {
        this.razonesNegocio = razonesNegocio;
    }

    /**
     * @return the hechosCircunstanciasPrevPlanteadas
     */
    public String getHechosCircunstanciasPrevPlanteadas() {
        return hechosCircunstanciasPrevPlanteadas;
    }

    /**
     * @param hechosCircunstanciasPrevPlanteadas
     *            the hechosCircunstanciasPrevPlanteadas to set
     */
    public void setHechosCircunstanciasPrevPlanteadas(String hechosCircunstanciasPrevPlanteadas) {
        this.hechosCircunstanciasPrevPlanteadas = hechosCircunstanciasPrevPlanteadas;
    }

    /**
     * @return the claveAutoridad
     */
    public String getClaveAutoridad() {
        return claveAutoridad;
    }

    /**
     * @param claveAutoridad
     *            the claveAutoridad to set
     */
    public void setClaveAutoridad(String claveAutoridad) {
        this.claveAutoridad = claveAutoridad;
    }

    /**
     * @return the fechaPromocion
     */
    public Date getFechaPromocion() {
        return fechaPromocion != null ? (Date) fechaPromocion.clone() : null;
    }

    /**
     * @param fechaPromocion
     *            the fechaPromocion to set
     */
    public void setFechaPromocion(Date fechaPromocion) {
        this.fechaPromocion = fechaPromocion != null ? (Date) fechaPromocion.clone() : null;
    }

    /**
     * @return the hechosCircunstanciasMatMedios
     */
    public String getHechosCircunstanciasMatMedios() {
        return hechosCircunstanciasMatMedios;
    }

    /**
     * @param hechosCircunstanciasMatMedios
     *            the hechosCircunstanciasMatMedios to set
     */
    public void setHechosCircunstanciasMatMedios(String hechosCircunstanciasMatMedios) {
        this.hechosCircunstanciasMatMedios = hechosCircunstanciasMatMedios;
    }

    /**
     * @return the claveMedioDefensa
     */
    public String getClaveMedioDefensa() {
        return claveMedioDefensa;
    }

    /**
     * @param claveMedioDefensa
     *            the claveMedioDefensa to set
     */
    public void setClaveMedioDefensa(String claveMedioDefensa) {
        this.claveMedioDefensa = claveMedioDefensa;
    }

    /**
     * @return the numeroAsunto
     */
    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    /**
     * @param numeroAsunto
     *            the numeroAsunto to set
     */
    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    /**
     * @return the claveSentidoResolucion
     */
    public String getClaveSentidoResolucion() {
        return claveSentidoResolucion;
    }

    /**
     * @param claveSentidoResolucion
     *            the claveSentidoResolucion to set
     */
    public void setClaveSentidoResolucion(String claveSentidoResolucion) {
        this.claveSentidoResolucion = claveSentidoResolucion;
    }

    /**
     * @return the descripcionAutoridad
     */
    public String getDescripcionAutoridad() {
        return descripcionAutoridad;
    }

    /**
     * @param descripcionAutoridad
     *            the descripcionAutoridad to set
     */
    public void setDescripcionAutoridad(String descripcionAutoridad) {
        this.descripcionAutoridad = descripcionAutoridad;
    }

    /**
     * @return the contribuyenteSujetoEjercicio
     */
    public String getContribuyenteSujetoEjercicio() {
        return contribuyenteSujetoEjercicio;
    }

    /**
     * @param contribuyenteSujetoEjercicio
     *            the contribuyenteSujetoEjercicio to set
     */
    public void setContribuyenteSujetoEjercicio(String contribuyenteSujetoEjercicio) {
        this.contribuyenteSujetoEjercicio = contribuyenteSujetoEjercicio;
    }

    /**
     * @return the periodosContribuciones
     */
    public String getPeriodosContribuciones() {
        return periodosContribuciones;
    }

    /**
     * @param periodosContribuciones
     *            the periodosContribuciones to set
     */
    public void setPeriodosContribuciones(String periodosContribuciones) {
        this.periodosContribuciones = periodosContribuciones;
    }

    /**
     * @return the plazo
     */
    public String getPlazo() {
        return plazo;
    }

    /**
     * @param plazo
     *            the plazo to set
     */
    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    /**
     * @return the claveAutoridadRevisando
     */
    public String getClaveAutoridadRevisando() {
        return claveAutoridadRevisando;
    }

    /**
     * @param claveAutoridadRevisando
     *            the claveAutoridadRevisando to set
     */
    public void setClaveAutoridadRevisando(String claveAutoridadRevisando) {
        this.claveAutoridadRevisando = claveAutoridadRevisando;
    }

    /**
     * @return the tipoClasificacion
     */
    public String getTipoClasificacion() {
        return tipoClasificacion;
    }

    /**
     * @param tipoClasificacion
     *            the tipoClasificacion to set
     */
    public void setTipoClasificacion(String tipoClasificacion) {
        this.tipoClasificacion = tipoClasificacion;
    }

    /**
     * @return the descripcionMercancia
     */
    public String getDescripcionMercancia() {
        return descripcionMercancia;
    }

    /**
     * @param descripcionMercancia
     *            the descripcionMercancia to set
     */
    public void setDescripcionMercancia(String descripcionMercancia) {
        this.descripcionMercancia = descripcionMercancia;
    }

    /**
     * @return the fraccionAplicable
     */
    public String getFraccionAplicable() {
        return fraccionAplicable;
    }

    /**
     * @param fraccionAplicable
     *            the fraccionAplicable to set
     */
    public void setFraccionAplicable(String fraccionAplicable) {
        this.fraccionAplicable = fraccionAplicable;
    }

    /**
     * @return the claveUnidadEmisora
     */
    public String getClaveUnidadEmisora() {
        return claveUnidadEmisora;
    }

    /**
     * @param claveUnidadEmisora
     *            the claveUnidadEmisora to set
     */
    public void setClaveUnidadEmisora(String claveUnidadEmisora) {
        this.claveUnidadEmisora = claveUnidadEmisora;
    }

    /**
     * @return the fraccionDuda
     */
    public String getFraccionDuda() {
        return fraccionDuda;
    }

    /**
     * @param fraccionDuda
     *            the fraccionDuda to set
     */
    public void setFraccionDuda(String fraccionDuda) {
        this.fraccionDuda = fraccionDuda;
    }

    /**
     * @return the listaFraccionDuda
     */
    public List<FraccionArancelariaDudaDTO> getListaFraccionDuda() {
        return listaFraccionDuda;
    }

    /**
     * @param listaFraccionDuda
     *            the listaFraccionDuda to set
     */
    public void setListaFraccionDuda(List<FraccionArancelariaDudaDTO> listaFraccionDuda) {
        this.listaFraccionDuda = listaFraccionDuda;
    }

    /**
     * @return the idMedioDefensa
     */
    public Long getIdMedioDefensa() {
        return idMedioDefensa;
    }

    /**
     * @param idMedioDefensa
     *            the idMedioDefensa to set
     */
    public void setIdMedioDefensa(Long idMedioDefensa) {
        this.idMedioDefensa = idMedioDefensa;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion != null ? (Date) fechaCreacion.clone() : null;
    }

    /**
     * @param fechaCreacion
     *            the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion != null ? (Date) fechaCreacion.clone() : null;
    }

    public String getNumeroFolio() {
        return numeroFolio;
    }

    public void setNumeroFolio(String numeroFolio) {
        this.numeroFolio = numeroFolio;
    }

    public Date getFechaApertura() {
        return fechaApertura != null ? (Date) fechaApertura.clone() : null;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura != null ? (Date) fechaApertura.clone() : null;
    }

    public String getMensajeBuzon() {
        return mensajeBuzon;
    }

    public void setMensajeBuzon(String mensajeBuzon) {
        this.mensajeBuzon = mensajeBuzon;
    }

    public boolean isMuestraMensaje() {
        return muestraMensaje;
    }

    public void setMuestraMensaje(boolean muestraMensaje) {
        this.muestraMensaje = muestraMensaje;
    }

    /**
     * @param montoOperacion
     *            the montoOperacion to set
     */
    public void setMontoOperacion(BigDecimal montoOperacion) {
        this.montoOperacion = montoOperacion;
    }

    /**
     * @return the montoOperacion
     */
    public BigDecimal getMontoOperacion() {
        return montoOperacion;
    }

    public List<ManifiestoDTO> getManifiestos() {
        return manifiestos;
    }

    public void setManifiestos(List<ManifiestoDTO> manifiestos) {
        this.manifiestos = manifiestos;
    }

    public String getTipoRolContribuyente() {
        return tipoRolContribuyente;
    }

    public void setTipoRolContribuyente(String tipoRolContribuyente) {
        this.tipoRolContribuyente = tipoRolContribuyente;
    }

    public String getEstadoContribuyente() {
        return estadoContribuyente;
    }

    public void setEstadoContribuyente(String estadoContribuyente) {
        this.estadoContribuyente = estadoContribuyente;
    }
}
