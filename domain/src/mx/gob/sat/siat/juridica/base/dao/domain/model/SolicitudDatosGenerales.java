/*
 * Todos los Derechos Reservados 2013 SAT. Servicio de Administracion Tributaria
 * (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.siat.juridica.base.dao.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@DiscriminatorValue("000")
@SecondaryTable(name = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES, pkJoinColumns = @PrimaryKeyJoinColumn(
        name = "IDSOLICITUD"))
public abstract class SolicitudDatosGenerales extends Solicitud {

    public static final String TABLA_SOL_DATOS_GENERALES = "RVCD_SOL_DATOSGEN";
    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "descripcion" tipo String
     */
    @Lob
    @Column(name = "DESCRIPCION", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES, nullable = true)
    private String descripcion;

    /**
     * Atributo privado "fechaBaja" tipo Date
     */
    @Column(name = "FECBAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    /**
     * Atributo privado "unidadAdminBalanceo" tipo String
     */
    @Column(name = "IDUNIADMINEMISORA", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private String unidadAdminBalanceo;

    /**
     * Atributo privado "monto" tipo BigDecimal
     */
    @Column(name = "MONTO", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private BigDecimal monto;

    /**
     * Atributo privado "granContribuyente" tipo Integer
     */
    @Column(name = "BLNGRANCONTRIBUYENTE", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private Integer granContribuyente;

    /**
     * Atributo privado "medioDefensa" tipo Integer
     */
    @Column(name = "BLNMEDIODEFENSA", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private Integer medioDefensa;

    /**
     * Atributo privado "sujetoEjercicio" tipo Integer
     */
    @Column(name = "BLNSUJETOEJERCICIO", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private Integer sujetoEjercicio;

    /**
     * Atributo privado "dentroPlazo" tipo Integer
     */
    @Column(name = "BLNDENTROPLAZO", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private Integer dentroPlazo;

    /**
     * Atributo privado "hecho" tipo Integer
     */
    @Column(name = "BLNHECHO", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private Integer hecho;

    /**
     * Atributo privado "describirActividad" tipo String
     */
    @Column(name = "DESCACTIVIDAD", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private String describirActividad;

    /**
     * Atributo privado "describirHecho" tipo String
     */
    @Column(name = "DESCHECHO", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private String describirHecho;

    /**
     * Atributo privado "describirRazon" tipo String
     */
    @Column(name = "DESCRAZON", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private String describirRazon;

    /**
     * Atributo privado "describirPeriodoContribucion" tipo String
     */
    @Column(name = "DESCPERIODOCONTRIBUCION", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private String describirPeriodoContribucion;

    @Column(name = "IDECLASIFARANCELARIA", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private String ideClasifArancelaria;

    @Column(name = "DESCNOMBREMERCANCIA", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private String descNombreMercancia;

    @Column(name = "FRACCIONAPLICABLEV", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private String fraccionAplicable;

    @Column(name = "FECHECHO", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechHecho;

    @Column(name = "FOLIO", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private Long folio;

    @Column(name = "FECHAAPERTURA", table = SolicitudDatosGenerales.TABLA_SOL_DATOS_GENERALES)
    private Date fechaApertura;

    /**
     * 
     * @param descripcion
     *            a fijar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @return fechaBaja
     */
    public Date getFechaBaja() {
        if (fechaBaja != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaBaja);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    /**
     * 
     * @param fechaBaja
     *            a fijar
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
     * 
     * @return unidadAdminBalanceo
     */
    public String getUnidadAdminBalanceo() {
        return unidadAdminBalanceo;
    }

    /**
     * 
     * @param unidadAdminBalanceo
     *            a fijar
     */
    public void setUnidadAdminBalanceo(String unidadAdminBalanceo) {
        this.unidadAdminBalanceo = unidadAdminBalanceo;
    }

    /**
     * 
     * @return monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * 
     * @param monto
     *            a fijar
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * 
     * @return granContribuyente
     */
    public Integer getGranContribuyente() {
        return granContribuyente;
    }

    /**
     * 
     * @param granContribuyente
     *            a fijar
     */
    public void setGranContribuyente(Integer granContribuyente) {
        this.granContribuyente = granContribuyente;
    }

    /**
     * 
     * @return medioDefensa
     */
    public Integer getMedioDefensa() {
        return medioDefensa;
    }

    /**
     * 
     * @param medioDefensa
     *            a fijar
     */
    public void setMedioDefensa(Integer medioDefensa) {
        this.medioDefensa = medioDefensa;
    }

    /**
     * 
     * @return sujetoEjercicio
     */
    public Integer getSujetoEjercicio() {
        return sujetoEjercicio;
    }

    /**
     * 
     * @param sujetoEjercicio
     *            a fijar
     */
    public void setSujetoEjercicio(Integer sujetoEjercicio) {
        this.sujetoEjercicio = sujetoEjercicio;
    }

    /**
     * 
     * @return dentroPlazo
     */
    public Integer getDentroPlazo() {
        return dentroPlazo;
    }

    /**
     * 
     * @param dentroPlazo
     *            a fijar
     */
    public void setDentroPlazo(Integer dentroPlazo) {
        this.dentroPlazo = dentroPlazo;
    }

    /**
     * 
     * @return describirActividad
     */
    public String getDescribirActividad() {
        return describirActividad;
    }

    /**
     * 
     * @param describirActividad
     *            a fijar
     */
    public void setDescribirActividad(String describirActividad) {
        this.describirActividad = describirActividad;
    }

    /**
     * 
     * @return describirHecho
     */
    public String getDescribirHecho() {
        return describirHecho;
    }

    /**
     * 
     * @param describirHecho
     *            a fijar
     */
    public void setDescribirHecho(String describirHecho) {
        this.describirHecho = describirHecho;
    }

    /**
     * 
     * @return describirRazon
     */
    public String getDescribirRazon() {
        return describirRazon;
    }

    /**
     * 
     * @param describirRazon
     *            a fijar
     */
    public void setDescribirRazon(String describirRazon) {
        this.describirRazon = describirRazon;
    }

    /**
     * 
     * @return describirPeriodoContribucion
     */
    public String getDescribirPeriodoContribucion() {
        return describirPeriodoContribucion;
    }

    /**
     * 
     * @param describirPeriodoContribucion
     *            a fijar
     */
    public void setDescribirPeriodoContribucion(String describirPeriodoContribucion) {
        this.describirPeriodoContribucion = describirPeriodoContribucion;
    }

    /**
     * @return the hecho
     */
    public Integer getHecho() {
        return hecho;
    }

    /**
     * @param hecho
     *            the hecho to set
     */
    public void setHecho(Integer hecho) {
        this.hecho = hecho;
    }

    /**
     * @return the ideClasifArancelaria
     */
    public String getIdeClasifArancelaria() {
        return ideClasifArancelaria;
    }

    /**
     * @param ideClasifArancelaria
     *            the ideClasifArancelaria to set
     */
    public void setIdeClasifArancelaria(String ideClasifArancelaria) {
        this.ideClasifArancelaria = ideClasifArancelaria;
    }

    /**
     * @return the descNombreMercancia
     */
    public String getDescNombreMercancia() {
        return descNombreMercancia;
    }

    /**
     * @param descNombreMercancia
     *            the descNombreMercancia to set
     */
    public void setDescNombreMercancia(String descNombreMercancia) {
        this.descNombreMercancia = descNombreMercancia;
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
     * @return the fechHecho
     */
    public Date getFechHecho() {
        return fechHecho != null ? (Date) fechHecho.clone() : null;
    }

    /**
     * @param fechHecho
     *            the fechHecho to set
     */
    public void setFechHecho(Date fechHecho) {
        this.fechHecho = fechHecho != null ? (Date) fechHecho.clone() : null;
    }

    /**
     * @return the folio
     */
    public Long getFolio() {
        return folio;
    }

    /**
     * @param folio
     *            the folio to set
     */
    public void setFolio(Long folio) {
        this.folio = folio;
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

}
