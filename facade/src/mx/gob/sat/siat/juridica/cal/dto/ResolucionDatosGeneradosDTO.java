/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.cal.dto;

import mx.gob.sat.siat.juridica.base.dto.BaseDataTransferObject;
import mx.gob.sat.siat.juridica.base.dto.TramiteDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Softtek
 * 
 */
public class ResolucionDatosGeneradosDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -4519732583451124611L;

    /**
     * Atributo privado "resolucion" tipo Resolucion
     */
    private Long resolucion;
    /**
     * 
     */
    private String idEsentreSolucion;

    /**
     * Atributo privado "unidadAdministrativa" tipo
     * UnidadAdministrativa
     */
    private String unidadAdministrativa;

    /**
     * Atributo privado "fechaEmbargo" tipo date
     */
    private Date fechaEmbargo;
    /**
     * Atributo privado "tipoMedioPago" tipo string
     */
    private String tipoMedioPago;
    /**
     * Atributo privado "numeroAsunto" tipo string
     */
    private String numeroAsunto;
    /**
     * Atributo privado "cumplimentacion" tipo boolean
     */
    private boolean cumplimentacion;
    /**
     * Atributo privado "fraccionArancelaria" tipo bigDecimal
     */
    private String fraccionArancelaria;
    /**
     * Atributo privado "confirmaFraccion" tipo boolean
     */
    private boolean confirmaFraccion = true;
    /**
     * Atributo privado "numeroMinuta" tipo string
     */
    private String numeroMinuta;
    /**
     * Atributo privado "montoTotal" tipo bigDecimal
     */
    private BigDecimal montoTotal;
    /**
     * Atributo privado "montoTotal" tipo bigDecimal
     */
    private BigDecimal montoTotalOrdenado;
    /**
     * Atributo privado "montoSentencia" tipo bigDecimal
     */
    private BigDecimal montoSentencia;
    /**
     * Atributo privado "fechaSesion" tipo date
     */
    private Date fechaSesion;
    /**
     * Atributo privado "fechaResolucion" tipo date
     */
    private Date fechaResolucion;
    /**
     * 
     */
    private String numeroOficio;

    /**
     * 
     */
    private String medioDefensa;

    /**
     * 
     */
    private String autoridadEmbargo;
    /**
     * 
     */
    private List<BienResarcimientoDTO> bienesResolucion;
    /**
     * 
     */
    private List<BienResarcimientoDTO> bienesResolucionDelete;
    /**
     * 
     */
    private String resolucionSelected;
    /**
     * 
     */
    private TramiteDTO tramiteDTO;
    /**
     * 
     */
    private String numeroAsuntoResarcimiento;
    /**
     * estado de la resoluci&oacute;n
     */
    private String ideEstadoResolucion;
    /**
     * Nombre de la persona que va a firmar la resoluci&oacute;n.
     */
    private String rfcFirmante;

    private Date fechaOficio;

    /**
     * @return the resolucion
     */
    public Long getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion
     *            the resolucion to set
     */
    public void setResolucion(Long resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * @return the unidadAdministrativa
     */
    public String getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * @param unidadAdministrativa
     *            the unidadAdministrativa to set
     */
    public void setUnidadAdministrativa(String unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * @return the fehcaEmbargo
     */
    public Date getFechaEmbargo() {
        return fechaEmbargo != null ? (Date) fechaEmbargo.clone() : null;
    }

    /**
     * @param fehcaEmbargo
     *            the fehcaEmbargo to set
     */
    public void setFechaEmbargo(Date fechaEmbargo) {
        this.fechaEmbargo = fechaEmbargo != null ? (Date) fechaEmbargo.clone() : null;
    }

    /**
     * @return the tipoMedioPago
     */
    public String getTipoMedioPago() {
        return tipoMedioPago;
    }

    /**
     * @param tipoMedioPago
     *            the tipoMedioPago to set
     */
    public void setTipoMedioPago(String tipoMedioPago) {
        this.tipoMedioPago = tipoMedioPago;
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
     * @return the cumplimentacion
     */
    public boolean isCumplimentacion() {
        return cumplimentacion;
    }

    /**
     * @param cumplimentacion
     *            the cumplimentacion to set
     */
    public void setCumplimentacion(boolean cumplimentacion) {
        this.cumplimentacion = cumplimentacion;
    }

    /**
     * @return the fraccionArancelaria
     */
    public String getFraccionArancelaria() {
        return fraccionArancelaria;
    }

    /**
     * @param fraccionArancelaria
     *            the fraccionArancelaria to set
     */
    public void setFraccionArancelaria(String fraccionArancelaria) {
        this.fraccionArancelaria = fraccionArancelaria;
    }

    /**
     * @return the confirmaFraccion
     */
    public boolean isConfirmaFraccion() {
        return confirmaFraccion;
    }

    /**
     * @param confirmaFraccion
     *            the confirmaFraccion to set
     */
    public void setConfirmaFraccion(boolean confirmaFraccion) {
        this.confirmaFraccion = confirmaFraccion;
    }

    /**
     * @return the numeroMinuta
     */
    public String getNumeroMinuta() {
        return numeroMinuta;
    }

    /**
     * @param numeroMinuta
     *            the numeroMinuta to set
     */
    public void setNumeroMinuta(String numeroMinuta) {
        this.numeroMinuta = numeroMinuta;
    }

    /**
     * @return the montoTotal
     */
    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    /**
     * @param montoTotal
     *            the montoTotal to set
     */
    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    /**
     * @return the montoSentencia
     */
    public BigDecimal getMontoSentencia() {
        return montoSentencia;
    }

    /**
     * @param montoSentencia
     *            the montoSentencia to set
     */
    public void setMontoSentencia(BigDecimal montoSentencia) {
        this.montoSentencia = montoSentencia;
    }

    /**
     * @return the fechaSesion
     */
    public Date getFechaSesion() {
        return fechaSesion != null ? (Date) fechaSesion.clone() : null;
    }

    /**
     * @param fechaSesion
     *            the fechaSesion to set
     */
    public void setFechaSesion(Date fechaSesion) {
        this.fechaSesion = fechaSesion != null ? (Date) fechaSesion.clone() : null;
    }

    /**
     * @return the idEsentreSolucion
     */
    public String getIdEsentreSolucion() {
        return idEsentreSolucion;
    }

    /**
     * @param idEsentreSolucion
     *            the idEsentreSolucion to set
     */
    public void setIdEsentreSolucion(String idEsentreSolucion) {
        this.idEsentreSolucion = idEsentreSolucion;
    }

    /**
     * @return the autoridadEmbargo
     */
    public String getAutoridadEmbargo() {
        return autoridadEmbargo;
    }

    /**
     * @param autoridadEmbargo
     *            the autoridadEmbargo to set
     */
    public void setAutoridadEmbargo(String autoridadEmbargo) {
        this.autoridadEmbargo = autoridadEmbargo;
    }

    /**
     * @return the medioDefensa
     */
    public String getMedioDefensa() {
        return medioDefensa;
    }

    /**
     * @param medioDefensa
     *            the medioDefensa to set
     */
    public void setMedioDefensa(String medioDefensa) {
        this.medioDefensa = medioDefensa;
    }

    /**
     * @return the bienesResolucion
     */
    public List<BienResarcimientoDTO> getBienesResolucion() {
        return bienesResolucion;
    }

    /**
     * @param bienesResolucion
     *            the bienesResolucion to set
     */
    public void setBienesResolucion(List<BienResarcimientoDTO> bienesResolucion) {
        this.bienesResolucion = bienesResolucion;
    }

    /**
     * @return the resolucionSelected
     */
    public String getResolucionSelected() {
        return resolucionSelected;
    }

    /**
     * @param resolucionSelected
     *            the resolucionSelected to set
     */
    public void setResolucionSelected(String resolucionSelected) {
        this.resolucionSelected = resolucionSelected;
    }

    /**
     * @return the tramiteDTO
     */
    public TramiteDTO getTramiteDTO() {
        return tramiteDTO;
    }

    /**
     * @param tramiteDTO
     *            the tramiteDTO to set
     */
    public void setTramiteDTO(TramiteDTO tramiteDTO) {
        this.tramiteDTO = tramiteDTO;
    }

    /**
     * @return the numeroAsuntoResarcimiento
     */
    public String getNumeroAsuntoResarcimiento() {
        return numeroAsuntoResarcimiento;
    }

    /**
     * @param numeroAsuntoResarcimiento
     *            the numeroAsuntoResarcimiento to set
     */
    public void setNumeroAsuntoResarcimiento(String numeroAsuntoResarcimiento) {
        this.numeroAsuntoResarcimiento = numeroAsuntoResarcimiento;
    }

    /**
     * @return the bienesResolucionDelete
     */
    public List<BienResarcimientoDTO> getBienesResolucionDelete() {
        return bienesResolucionDelete;
    }

    /**
     * @param bienesResolucionDelete
     *            the bienesResolucionDelete to set
     */
    public void setBienesResolucionDelete(List<BienResarcimientoDTO> bienesResolucionDelete) {
        this.bienesResolucionDelete = bienesResolucionDelete;
    }

    /**
     * @return the montoTotalOrdenado
     */
    public BigDecimal getMontoTotalOrdenado() {
        return montoTotalOrdenado;
    }

    /**
     * @param montoTotalOrdenado
     *            the montoTotalOrdenado to set
     */
    public void setMontoTotalOrdenado(BigDecimal montoTotalOrdenado) {
        this.montoTotalOrdenado = montoTotalOrdenado;
    }

    /**
     * @return the rfcFirmante
     */
    public String getRfcFirmante() {
        return rfcFirmante;
    }

    /**
     * @param rfcFirmante
     *            the rfcFirmante to set
     */
    public void setRfcFirmante(String rfcFirmante) {
        this.rfcFirmante = rfcFirmante;
    }

    /**
     * @return the ideEstadoResolucion
     */
    public String getIdeEstadoResolucion() {
        return ideEstadoResolucion;
    }

    /**
     * @param ideEstadoResolucion
     *            the ideEstadoResolucion to set
     */
    public void setIdeEstadoResolucion(String ideEstadoResolucion) {
        this.ideEstadoResolucion = ideEstadoResolucion;
    }

    /**
     * @return the fechaOficio
     */
    public Date getFechaOficio() {
        return fechaOficio != null ? (Date) fechaOficio.clone() : null;
    }

    /**
     * @param fechaOficio
     *            the fechaOficio to set
     */
    public void setFechaOficio(Date fechaOficio) {
        this.fechaOficio = fechaOficio != null ? (Date) fechaOficio.clone() : null;
    }

    public Date getFechaResolucion() {
        return fechaResolucion != null ? (Date) fechaResolucion.clone() : null;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion != null ? (Date) fechaResolucion.clone() : null;
    }

    public String getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

}
