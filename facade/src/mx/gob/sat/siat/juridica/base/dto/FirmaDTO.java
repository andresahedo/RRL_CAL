package mx.gob.sat.siat.juridica.base.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FirmaDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1842491955100103150L;
    private String cadenaOriginal;
    private String sello;
    private String certificado;
    private String tipoFirma;
    private Date fechaFirma;
    private String rfcAsignar;
    private String rfcUsuario;
    private String cveRol;
    private String cveProceso;

    public FirmaDTO() {

    }

    public FirmaDTO(Date fechaFirma) {
        if (fechaFirma != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaFirma);
            this.fechaFirma = cal.getTime();
        }
        else {
            this.fechaFirma = null;
        }
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getTipoFirma() {
        return tipoFirma;
    }

    public void setTipoFirma(String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    public Date getFechaFirma() {
        return fechaFirma != null ? (Date) fechaFirma.clone() : null;
    }

    public void setFechaFirma(Date fechaFirma) {
        if (fechaFirma != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(fechaFirma);
            this.fechaFirma = cal.getTime();
        }
        else {
            this.fechaFirma = null;
        }
    }

    public String getRfcAsignar() {
        return rfcAsignar;
    }

    public void setRfcAsignar(String rfcAsignar) {
        this.rfcAsignar = rfcAsignar;
    }

    public String getRfcUsuario() {
        return rfcUsuario;
    }

    public void setRfcUsuario(String rfcUsuario) {
        this.rfcUsuario = rfcUsuario;
    }

    public String getCveRol() {
        return cveRol;
    }

    public void setCveRol(String cveRol) {
        this.cveRol = cveRol;
    }

    /**
     * @return the cveProceso
     */
    public String getCveProceso() {
        return cveProceso;
    }

    /**
     * @param cveProceso
     *            the cveProceso to set
     */
    public void setCveProceso(String cveProceso) {
        this.cveProceso = cveProceso;
    }

}
