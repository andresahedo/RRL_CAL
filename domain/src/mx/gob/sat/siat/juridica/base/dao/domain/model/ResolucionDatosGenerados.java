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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCD_RES_DATOSGEN")
public class ResolucionDatosGenerados extends BaseModel {

    /**
     * Nuacute;mero de serie
     */
    private static final long serialVersionUID = 5511637841246803877L;

    /**
     * Atributo privado "resolucion" tipo Resolucion
     */
    @Id
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "IDRESOLUCION", nullable = false, referencedColumnName = "IDRESOLUCION", insertable = false,
            updatable = false)
    private Resolucion resolucion;

    /**
     * Atributo privado "unidadAdministrativa" tipo
     * UnidadAdministrativa
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "IDUNIADMIN", referencedColumnName = "IDUNIADMIN")
    private UnidadAdministrativa unidadAdministrativa;

    /**
     * Atributo privado "fechaEmbargo" tipo date
     */
    @Column(name = "FECEMBARGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmbargo;
    /**
     * Atributo privado "tipoMedioPago" tipo string
     */
    @Column(name = "IDETIPOMEDIO")
    private String tipoMedioPago;
    /**
     * Atributo privado "numeroAsunto" tipo string
     */
    @Column(name = "NUMASUNTO")
    private String numeroAsunto;
    /**
     * Atributo privado "cumplimentacion" tipo boolean
     */
    @Column(name = "BLNCUMPLIMENTACION")
    private boolean cumplimentacion;
    /**
     * Atributo privado "fraccionArancelaria" tipo String
     */
    @Column(name = "FRACCIONARANCELARIAV")
    private String fraccionArancelaria;
    /**
     * Atributo privado "confirmaFraccion" tipo boolean
     */
    @Column(name = "BLNCONFIRMAFRACCION")
    private boolean confirmaFraccion;
    /**
     * Atributo privado "numeroMinuta" tipo string
     */
    @Column(name = "NUMMINUTA")
    private String numeroMinuta;
    /**
     * Atributo privado "montoTotal" tipo bigDecimal
     */
    @Column(name = "MONTOTOTAL")
    private BigDecimal montoTotal;
    /**
     * Atributo privado "montoSentencia" tipo bigDecimal
     */
    @Column(name = "MONTOSENTENCIA")
    private BigDecimal montoSentencia;
    /**
     * Atributo privado "fechaSesion" tipo date
     */
    @Column(name = "FECSESION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSesion;

    @Column(name = "FECHAOFICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOficio;

    @Column(name = "NUMOFICIO")
    private String numeroOficio;

    /**
     * Primal Constructor
     */
    public ResolucionDatosGenerados() {

    }

    /**
     * Second Constructor
     */
    public ResolucionDatosGenerados(Long idResolucion) {
        this.resolucion = new Resolucion(idResolucion);
    }

    /**
     * @return the resolucion
     */
    public Resolucion getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion
     *            the resolucion to set
     */
    public void setResolucion(Resolucion resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * @return the unidadAdministrativa
     */
    public UnidadAdministrativa getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * @param unidadAdministrativa
     *            the unidadAdministrativa to set
     */
    public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
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

    public String getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public Date getFechaOficio() {
        return fechaOficio != null ? (Date) fechaOficio.clone() : null;
    }

    public void setFechaOficio(Date fechaOficio) {
        this.fechaOficio = fechaOficio != null ? (Date) fechaOficio.clone() : null;
    }

}
