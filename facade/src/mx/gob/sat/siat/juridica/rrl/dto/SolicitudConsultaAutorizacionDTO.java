package mx.gob.sat.siat.juridica.rrl.dto;

import mx.gob.sat.siat.juridica.base.dto.SolicitudDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SolicitudConsultaAutorizacionDTO extends SolicitudDTO {

    private static final long serialVersionUID = 6853055295631257691L;

    private String claveUnidadEmisora;
    private String nombreUnidadEmisora;
    private String rfcPAORN;
    private String mailPAORN;

    private String rfcResidente;
    private String nombreResidente;
    private String apellidoPaternoResidente;
    private String apellidoMaternoResidente;
    private String direccion;

    private String granContribuyete;
    private String monto;
    private String actividadInteresado;
    private String hechosCircunstancias;
    private String razonNegocio;
    private boolean manifiesto1;
    private String claveMedioDefensa;
    private String descripcionMedioDefensa;
    private String numeroAsunto;
    private String claveSentidoResolucion;
    private String descripcionSentidoResolucion;
    private String claveAutoridad;
    private String descripcionAutoridad;
    private boolean manifiesto2;
    private String periodosContribuciones;
    private String plazo;
    private String estadoProcesal;
    private Date fechaCreacion;

    public SolicitudConsultaAutorizacionDTO() {}

    public String getClaveUnidadEmisora() {
        return claveUnidadEmisora;
    }

    public void setClaveUnidadEmisora(String claveUnidadEmisora) {
        this.claveUnidadEmisora = claveUnidadEmisora;
    }

    public String getRfcPAORN() {
        return rfcPAORN;
    }

    public void setRfcPAORN(String rfcPAORN) {
        this.rfcPAORN = rfcPAORN;
    }

    public String getMailPAORN() {
        return mailPAORN;
    }

    public void setMailPAORN(String mailPAORN) {
        this.mailPAORN = mailPAORN;
    }

    public String getRfcResidente() {
        return rfcResidente;
    }

    public void setRfcResidente(String rfcResidente) {
        this.rfcResidente = rfcResidente;
    }

    public String getNombreResidente() {
        return nombreResidente;
    }

    public void setNombreResidente(String nombreResidente) {
        this.nombreResidente = nombreResidente;
    }

    public String getApellidoPaternoResidente() {
        return apellidoPaternoResidente;
    }

    public void setApellidoPaternoResidente(String apellidoPaternoResidente) {
        this.apellidoPaternoResidente = apellidoPaternoResidente;
    }

    public String getApellidoMaternoResidente() {
        return apellidoMaternoResidente;
    }

    public void setApellidoMaternoResidente(String apellidoMaternoResidente) {
        this.apellidoMaternoResidente = apellidoMaternoResidente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGranContribuyete() {
        return granContribuyete;
    }

    public void setGranContribuyete(String granContribuyete) {
        this.granContribuyete = granContribuyete;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getActividadInteresado() {
        return actividadInteresado;
    }

    public void setActividadInteresado(String actividadInteresado) {
        this.actividadInteresado = actividadInteresado;
    }

    public String getHechosCircunstancias() {
        return hechosCircunstancias;
    }

    public void setHechosCircunstancias(String hechosCircunstancias) {
        this.hechosCircunstancias = hechosCircunstancias;
    }

    public String getRazonNegocio() {
        return razonNegocio;
    }

    public void setRazonNegocio(String razonNegocio) {
        this.razonNegocio = razonNegocio;
    }

    public boolean getManifiesto1() {
        return manifiesto1;
    }

    public void setManifiesto1(boolean manifiesto1) {
        this.manifiesto1 = manifiesto1;
    }

    public String getClaveMedioDefensa() {
        return claveMedioDefensa;
    }

    public void setClaveMedioDefensa(String claveMedioDefensa) {
        this.claveMedioDefensa = claveMedioDefensa;
    }

    public String getNumeroAsunto() {
        return numeroAsunto;
    }

    public void setNumeroAsunto(String numeroAsunto) {
        this.numeroAsunto = numeroAsunto;
    }

    public String getClaveSentidoResolucion() {
        return claveSentidoResolucion;
    }

    public void setClaveSentidoResolucion(String claveSentidoResolucion) {
        this.claveSentidoResolucion = claveSentidoResolucion;
    }

    public String getClaveAutoridad() {
        return claveAutoridad;
    }

    public void setClaveAutoridad(String claveAutoridad) {
        this.claveAutoridad = claveAutoridad;
    }

    public boolean getManifiesto2() {
        return manifiesto2;
    }

    public void setManifiesto2(boolean manifiesto2) {
        this.manifiesto2 = manifiesto2;
    }

    public String getPeriodosContribuciones() {
        return periodosContribuciones;
    }

    public void setPeriodosContribuciones(String periodosContribuciones) {
        this.periodosContribuciones = periodosContribuciones;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getEstadoProcesal() {
        return estadoProcesal;
    }

    public void setEstadoProcesal(String estadoProcesal) {
        this.estadoProcesal = estadoProcesal;
    }

    public Date getFechaCreacion() {
        if (fechaCreacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaCreacion);
            return cal.getTime();
        }
        else {
            return null;
        }
    }

    public void setFechaCreacion(Date fechaCreacion) {
        if (fechaCreacion != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaCreacion);
            this.fechaCreacion = cal.getTime();
        }
        else {
            this.fechaCreacion = null;
        }
    }

    public String getNombreUnidadEmisora() {
        return nombreUnidadEmisora;
    }

    public void setNombreUnidadEmisora(String nombreUnidadEmisora) {
        this.nombreUnidadEmisora = nombreUnidadEmisora;
    }

    public String getDescripcionMedioDefensa() {
        return descripcionMedioDefensa;
    }

    public void setDescripcionMedioDefensa(String descripcionMedioDefensa) {
        this.descripcionMedioDefensa = descripcionMedioDefensa;
    }

    public String getDescripcionSentidoResolucion() {
        return descripcionSentidoResolucion;
    }

    public void setDescripcionSentidoResolucion(String descripcionSentidoResolucion) {
        this.descripcionSentidoResolucion = descripcionSentidoResolucion;
    }

    public String getDescripcionAutoridad() {
        return descripcionAutoridad;
    }

    public void setDescripcionAutoridad(String descripcionAutoridad) {
        this.descripcionAutoridad = descripcionAutoridad;
    }

}
