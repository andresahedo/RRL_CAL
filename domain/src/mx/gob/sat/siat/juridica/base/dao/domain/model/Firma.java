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

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author softtek
 * 
 */
@Entity
@Table(name = "RVCT_FIRMA")
public class Firma extends BaseModel {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo privado "idFirma" tipo Long
     */
    @Id
    @Basic(optional = false)
    @Column(name = "IDFIRMA", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RVCQ_FIRMA")
    @SequenceGenerator(name = "RVCQ_FIRMA", sequenceName = "RVCQ_FIRMA", allocationSize = 1)
    private Long idFirma;

    /**
     * Atributo privado "firmaElectronica" tipo String
     */
    @Column(name = "FIRMAELECTRONICA")
    private String firmaElectronica;

    /**
     * Atributo privado "certificado" tipo String
     */
    @Column(name = "CERTSERIALNUMBER")
    private String certificado;

    /**
     * Atributo privado "claveUsuario" tipo String
     */
    @Column(name = "IDUSUARIO")
    private String claveUsuario;

    /**
     * Atributo privado "fechaFirma" tipo Date
     */
    @Column(name = "FECFIRMA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFirma;

    /**
     * Atributo privado "claveRol" tipo String
     */
    @Column(name = "IDROL")
    private String claveRol;

    /**
     * Atributo privado "cadenaOriginal" tipo String
     */
    @Column(name = "CADENAORIGINAL")
    @Lob
    private String cadenaOriginal;

    @Column(name = "IDEPROCESOFIRMA")
    private String ideProcesoFirma;

    /**
     * Constructor vacio
     */
    public Firma() {

    }

    /**
     * Sobrecarga del constructor
     * 
     * @param date
     */
    public Firma(Date date) {
        if (date != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(date);
            this.fechaFirma = cal.getTime();
        }
        else {
            this.fechaFirma = null;
        }
    }

    /**
     * 
     * @return idFirma
     */
    public Long getIdFirma() {
        return idFirma;
    }

    /**
     * 
     * @param idFirma
     *            a fijar
     */
    public void setIdFirma(Long idFirma) {
        this.idFirma = idFirma;
    }

    /**
     * 
     * @return cadenaOriginal
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     * 
     * @param cadenaOriginal
     *            a fijar
     */
    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    /**
     * 
     * @return firmaElectronica
     */
    public String getFirmaElectronica() {
        return firmaElectronica;
    }

    /**
     * 
     * @param firmaElectronica
     *            a fijar
     */
    public void setFirmaElectronica(String firmaElectronica) {
        this.firmaElectronica = firmaElectronica;
    }

    /**
     * 
     * @return certificado
     */
    public String getCertificado() {
        return certificado;
    }

    /**
     * 
     * @param certificado
     *            a fijar
     */
    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    /**
     * 
     * @return claveUsuario
     */
    public String getClaveUsuario() {
        return claveUsuario;
    }

    /**
     * 
     * @param claveUsuario
     *            a fijar
     */
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    /**
     * 
     * @return fecha
     */
    public Date getFechaFirma() {
        return fechaFirma != null ? (Date) fechaFirma.clone() : null;
    }

    /**
     * 
     * @param fechaFirma
     *            a fijar
     */
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

    /**
     * 
     * @return formato de fecha
     */
    public String getFechaFirmaTxt() {
        String dateMask = "dd/MM/yyyy HH:mm:ss";
        if (getFechaFirma() == null) {
            return "";
        }
        SimpleDateFormat sFormat = new SimpleDateFormat(dateMask);
        return sFormat.format(getFechaFirma());
    }

    /**
     * 
     * @param fechaInicioStr
     *            a fijar
     */
    public void setFechaFirmaTxt(String fechaInicioStr) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            this.fechaFirma = df.parse(fechaInicioStr);
        }
        catch (ParseException e) {
            this.fechaFirma = null;
        }
    }

    /**
     * 
     * @return claveRol
     */
    public String getClaveRol() {
        return claveRol;
    }

    /**
     * 
     * @param claveRol
     *            a fijar
     */
    public void setClaveRol(String claveRol) {
        this.claveRol = claveRol;
    }

    public String getIdeProcesoFirma() {
        return ideProcesoFirma;
    }

    public void setIdeProcesoFirma(String ideProcesoFirma) {
        this.ideProcesoFirma = ideProcesoFirma;
    }

}
